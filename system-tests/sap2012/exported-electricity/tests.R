result <- load.probe("energy")

## Exclude houses which were unsuitable, or which had no energy demand
result <- result[result$suitable == TRUE & result$energy..Before > 0,]

fuel.cost.index.did.not.improve <- result[result$sap.rating..Before <= result$sap.rating..After & result$sap.rating..Before != 100,]
fail.test.if(
    nrow(fuel.cost.index.did.not.improve) > 0,
    "SAP score should improve"
)

off.peak.solar <- result[result$off.peak.solar..After > 0,]
fail.test.if(
    nrow(off.peak.solar) > 0,
    "Off-peak-solar should be 0"
)

peak.solar.did.not.increase <- result[result$peak.solar..Before >= result$peak.solar..After,]
fail.test.if(
    nrow(peak.solar.did.not.increase) > 0,
    "Peak solar should increase"
)

energy.without.photons.did.not.decrease <- result[result$energy.without.photons..Before >= result$energy.without.photons..After,]
fail.test.if(
    nrow(energy.without.photons.did.not.decrease) > 0,
    "Energy without photons should go down"
)

energy.with.photons.did.not.increase <- result[result$energy..Before <= result$energy..After,]
fail.test.if(
    nrow(energy.with.photons.did.not.increase) > 0,
    "Energy with photons should go up (we use a lot of photons to make a little electricity)"
)

exported.electricity.did.not.increase <- result[result$exported.electricity..Before <= result$exported.electricity..After,]
fail.test.if(
    nrow(exported.electricity.did.not.increase) > 0,
    "Exported electricity should go up"
)

emissions.did.not.decrease <- result[result$emissions..Before >= result$emissions..After,]
fail.test.if(
    nrow(emissions.did.not.decrease) > 0,
    "Emissions should fall"
)

fuel.cost.did.not.decrease <- result[result$fuel.cost..Before >= result$fuel.cost..After,]
fail.test.if(
    nrow(fuel.cost.did.not.decrease) > 0,
    "Fuel cost should fall"
)
