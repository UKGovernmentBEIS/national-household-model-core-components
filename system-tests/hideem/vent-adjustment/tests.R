result <- load.probe("hideem-report")

fail.test.if(
    (result$permeability..Before. + result$no.of.vents..After.) == 1,
    "Number of ventes should have increased by 1")

fail.test.if(
    !result$permeability..Before. < result$permeability..After.,
    "Permeability should increase.")
