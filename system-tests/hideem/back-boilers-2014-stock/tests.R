result <- load.probe("hideem-report")

results <- data.table(result)
back.boiler.replaced <- results[boiler.type..Before. == "BackBoiler" & suitable == "true",]
permeability.changed <- back.boiler.replaced[permeability..Before. != permeability..After., .N]

fail.test.if(permeability.changed > 0, "Permability does not change when back-boiler is replaced")
