## tests for EXS-43

p <- load.aggregate("totals")

final.count <- subset(p, Date=="2010/01/01")$count

fail.test.if(final.count != 22673919,
             paste("Final count was", final.count, "not", 22673919))

