results <- load.probe("solar-thermal")

results.sub <- results[which(results$selected == "true"
                             & results$suitable == "true"
                             & results$has.solar.thermal..Before. == "false"),]

fail.test.if(
    results.sub$has.solar.thermal..After. == "false",
    "Should always have installed solar thermal and reported as true")
