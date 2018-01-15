result <- load.probe("r")
results <- data.table(result)

fail.test.if(results$FuelFixed..After. != 1, msg="Cost of fuel should be just the fixed charge")
