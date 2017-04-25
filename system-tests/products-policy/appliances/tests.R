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
