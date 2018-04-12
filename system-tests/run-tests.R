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

run <- function(scenario) {
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

failures <- data.frame(scenario=character(), msg=character())

run.scenario <- function(scenario) {
    println("[INFO] Scenario %s", scenario)
    result <- run(scenario)
    if (result$status) {
        println("[FAIL] %s has non-zero exit status %d", scenario, result$status)
        failures <<- bind_rows(failures,
                             data.frame(scenario = scenario,
                                        msg = result$out))
    } else {
        println("[INFO] OK, output %s", result$file)
        ## look for verifier script and run it
        test.script <- paste(dirname(scenario), "tests.R", sep = "/")
        if (file.exists(test.script)) {
            println("[INFO] Test script %s", test.script)
            failed <- run.test.script(test.script)
            if (length(failed) > 0) {
                failures <<- bind_rows(failures,
                                     data.frame(scenario = scenario, msg = failed))
                println("[FAIL] Errors from %s: %s", test.script, failed)
            }
        }
        ## remove output
        unlink(result$file)
    }
}

println("[INFO] Model version: %s", version)

args <- commandArgs(trailingOnly = TRUE)
if (length(args) == 0) {
    scenarios <- list.files(recursive = TRUE, pattern = "main\\.nhm")
} else {
    scenarios <- args
}

for (scenario in scenarios) run.scenario(scenario)

print(failures)
