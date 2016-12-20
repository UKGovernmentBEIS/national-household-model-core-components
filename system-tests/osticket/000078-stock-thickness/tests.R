#!/usr/bin/Rscript

## Floating point error
EPSILON <- 0.01

library(jsonlite)

f <- gzfile("../../stock.json")

lines <- readLines(f)

for (line in lines) {
    dwelling <- fromJSON(line)

    ## I have no idea what is going on with these data structures...
    for (storey in dwelling$structure$storeys$segments) {
        storey$party <- grepl("Party", storey$wallConstructionType)

        thick.party <- storey[storey$party & storey$thickness != 0,]
        flat.external <- storey[!storey$party & storey$thickness <= 0,]

        fail.test.if(
            nrow(thick.party) > 0,
            "Party walls shouldn't have a thickness")

        fail.test.if(
            nrow(flat.external) > 0,
            "External walls should always have a thickness")
    }
}

close(f)
