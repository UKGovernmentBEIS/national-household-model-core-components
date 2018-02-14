result <- load.probe("hideem-report")

fail.test.if(
    (result$permeability..Before. + result$no.of.vents..After.) == 1,
    "Number of ventes should have increased by 1")

fail.test.if(
    !result$permeability..Before. < result$permeability..After.,
    "Permeability should increase.")

fail.test.if(
    !result$permeability.exc..Before. == result$permeability.exc..After.,
    "Permeability should not change if deliberate ventilation excluded.")


write.csv(result,file.path("~/software-projects/nhm-cse/system-tests/t.csv"))
