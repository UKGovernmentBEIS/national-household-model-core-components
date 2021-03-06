
result <- load.probe("hideem-report")

fail.test.if(
    any(result$no.of.fans..Before. >= result$no.of.fans..After.),
    "Number of fans should have increased by 1")

fail.test.if(
    any(result$permeability..Before. > result$permeability..After.),
    "Permeability should increase.")
