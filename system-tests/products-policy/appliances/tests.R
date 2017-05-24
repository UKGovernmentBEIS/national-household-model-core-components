<<<<<<< HEAD
## Tests that by increasing the efficiency of appliances that the heat gains removed show as extra heat demand
result <- load.probe("product-report")

result$energy.heat.before <- result$energy.heat..Before.
result$energy.heat.after <- result$energy.heat..After.

result <- result[result$energy.appliances..Before > 0 & result$heat.system.type..Before. == "StorageHeater",]

result$heat.diff <- result$energy.heat.after - result$energy.heat.before
result$gains.increase <- 100 * result$heat.diff / result$energy.appliances..Before.
gains.diff <- max(result$gains.increase, na.rm=TRUE) - min(result$gains.increase, na.rm=TRUE)

print(paste("Gains diff",gains.diff,sep="="))

write.csv(result,file.path("~/software-projects/nhm-cse/system-tests/t.csv"))

# I think this test is ok, it's really as long as the energy demand for heat doesn't go down then cool
fail.test.if(
    gains.diff > 42,
=======
## Tests that the energy use for applicances is the orginal energyuse plus linear factor and constant term
result <- load.probe("product-report")

result$energy.appliances.before <- result$appliances..Before
result$energy.appliances.after <- result$appliances..After

linear.factor <- 2
constant.term <- 1
result$energy.appliances.expected <- (linear.factor * result$energy.appliances.before) + constant.term
write.csv(result,file.path("~/software-projects/nhm-cse/system-tests/t.csv"))

fail.test.if(
    result$energy.appliances.after != result$energy.appliances.expected,
    "Appliance energy use after set adjustment")
>>>>>>> 2c8a35b8e0c06d8d696fd785d23174f997171587
