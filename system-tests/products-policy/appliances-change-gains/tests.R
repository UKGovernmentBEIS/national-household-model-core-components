## Tests that by increasing the efficiency of appliances that the heat gains removed show as extra heat demand
result <- load.probe("product-report")

result$energy.heat.before <- result$energy.heat..Before.
result$energy.heat.after <- result$energy.heat..After.

result <- result[result$energy.appliances..Before > 0 & result$heat.system.type..Before. == "StorageHeater",]

result$heat.diff <- result$energy.heat.after - result$energy.heat.before
result$gains.increase <- 100 * result$heat.diff / result$energy.appliances..Before.
gains.diff <- max(result$gains.increase, na.rm=TRUE) - min(result$gains.increase, na.rm=TRUE)

print(paste("Gains diff",gains.diff,sep="="))

fail.test.if(gains.diff > 42, "gains diff")
