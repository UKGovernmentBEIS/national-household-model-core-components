any.failed <- character()

fail.test <- function(msg) {
    any.failed <<- c(any.failed, msg)
}

trybrary <- function(symbol) {
    symbol <- substitute(symbol)
    name <- as.character(symbol)
    if (name %in% installed.packages()) {
        eval(bquote(library(.(symbol))))
    } else {
        fail.test(paste("test script depends on", name, "which is not installed here"))
    }
}

trybrary(stringr)
trybrary(plyr)
trybrary(data.table)
trybrary(dplyr)

fail.test.if <- function(cond, msg) {
    if (any(cond)) {
        fail.test(msg)
    }
}

fail.test.unless <- function(cond, msg) {
    if (any(!cond)) {
        fail.test(msg)
    }
}

die.if.failed <- function() {
    if (length(any.failed) > 0) {
        cat(paste(any.failed), collapse="\n")
        quit("no", 1, FALSE)
    } else {
        print("All the tests passed!")
    }
}

load.tables <- function(zipname, env = parent.env(environment())) {
    # generate names
    names <- unzip(zipname, list=T)
    # reduce to tab files
    names <- names[grep(".+\\.tab", names)]
    create <- function(a, b) assign(b,
                            read.table(unz(zipname, a),
                                       sep="\t",
                                       header=T,
                                       na.strings="n/a"),
                            envir=env)
    mapply(create, names, make.names(apply(names, 1, str_sub, 1, -5)))
    paste("loaded", names)
}

load.probe <- function(name, current.output="output.zip") {
    read.table(unz(current.output, file.path("dwelling", paste("probe-", name, ".tab", sep=""))),
               comment.char = "",
               header=T,
               sep="\t",
               na.strings="n/a")
}

load.aggregate <- function(name, current.output="output.zip") {
    read.table(unz(current.output, file.path("aggregate", paste(name, ".tab", sep=""))),
               comment.char = "",
               header=T,
               sep="\t",
               na.strings="n/a")
}

COMMON.READ.CSV <- function(file.name){
    data.table(read.csv(file.name,header=T,sep=","))
}

COMMON.WRITE.CSV <- function(data, file.name){
    print(paste("Writing ", nrow(data),"rows to csv",file.name,sep=" "))
    write.csv(data,file.name,row.names=F)
}

