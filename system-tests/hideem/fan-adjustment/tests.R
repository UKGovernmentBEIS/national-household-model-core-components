
result <- load.probe("hideem-report")

fail.test.if(
    !result$no.of.fans..Before. < result$no.of.fans..After.,
    "Number of fans should have increased by 1")

fail.test.if(
    !result$permeability..Before. < result$permeability..After.,
    "Permeability should increase.")

write.csv(result,file.path("~/software-projects/nhm-cse/system-tests/t.csv"))
