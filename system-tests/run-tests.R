## System tests, reimplemented in R from bash
options(stringsAsFactors = FALSE, warn = 2)

println <- function(fmt, ...) cat(sprintf(paste(fmt, "\n"), ...))

library(plyr)
library(dplyr)

model <- c(list.files(full.names = TRUE,
                     recursive = TRUE, path = "../p2/inputs/plugins",
                     pattern="nhm-impl-bundle.+\\.jar")) %>%
    file.info %>%
    data.frame(., name=rownames(.)) %>%
    arrange(mtime, name) %>%
    slice(n())

model <- model$name

nhm <- function(...) {
    result <- system2("java", args = c("-jar", model, ...), stdout = TRUE, stderr = TRUE)
    status <- attr(result, "status")
    list(status = if (length(status)) status else 0,
         out = paste(result, collapse="\n"))
}

run.nhm <- function(scenario) {
    output.file <- paste(dirname(scenario), "output.zip", sep = "/")
    if (file.exists(output.file)) unlink(output.file)
    out <- nhm("run", scenario, output.file)
    out$file <- output.file
    out
}

version <- nhm("version")

run.test.script <- function(test.script) {
    wd <- getwd()
    setwd(dirname(test.script))
    source(paste(wd, "prefix.R", sep="/"), local=TRUE)
    source(basename(test.script), local=TRUE)
    setwd(wd)
    any.failed # argh, why not working
}

run.results <- data.frame(scenario=character(), status = character(), msg=character())

run.scenario <- function(scenario) {
    println("[INFO] Scenario %s", scenario)
    result <- run.nhm(scenario)
    if (result$status) {
        println("[FAIL] %s has non-zero exit status %d", scenario, result$status)
        run.results <<- bind_rows(run.results,
                             data.frame(scenario = scenario,
                                        status = "Run error",
                                        msg = result$out))
    } else {
        run.results <<- bind_rows(run.results,
                             data.frame(scenario = scenario,
                                        status = "Run OK",
                                        msg = ""))

        println("[INFO] OK, output %s", result$file)

        ## check for run errors
        contents <- tryCatch(unzip(result$file, list=TRUE)$Name,
           error = function(e) character(0))
        
        ## look for verifier script and run it
        test.script <- paste(dirname(scenario), sub("\\.nhm$", ".R", basename(scenario)), sep="/")

        if ("errors.txt" %in% contents) {
            error <- readLines(unz(result$file, "errors.txt"))[[1]]
            run.results <<- bind_rows(run.results,
                     data.frame(scenario = scenario,
                                status = "Scenario error",
                                msg = error))

            println("[FAIL] errors.txt in %s: %s", result$file, error)
        } else if (file.exists(test.script)) {
            println("[INFO] Test script %s", test.script)
            failed <- run.test.script(test.script)
            if (length(failed) > 0) {
                run.results <<- bind_rows(run.results,
                                     data.frame(scenario = scenario,
                                                status = "Test error",
                                                msg = failed))
                println("[FAIL] Errors from %s: %s", test.script, failed)
            } else {
                run.results <<- bind_rows(run.results,
                                     data.frame(scenario = scenario,
                                                status = "Tests OK",
                                                msg = ""))

            }
        }
        ## remove output
        unlink(result$file)
    }
}

println("[INFO] Model version: %s", version)

args <- commandArgs(trailingOnly = TRUE)
if (length(args) == 0) {
    scenarios <- list.files(recursive = TRUE, pattern = ".+\\.nhm$")
} else {
    scenarios <- args
}

for (scenario in scenarios) run.scenario(scenario)

print(run.results)
