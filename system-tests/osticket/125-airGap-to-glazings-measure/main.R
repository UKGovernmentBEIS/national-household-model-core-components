curtain.effect <- 0.04

results <- load.probe("glazing-report")
results$air6mm.u.val <- format(1/((1/3.1) + curtain.effect),digits=4)
results$air12mm.u.val <- format(1/((1/2.8) + curtain.effect),digits=4)
results$air16mm.u.val <- format(1/((1/2.7) + curtain.effect),digits=4)

results$tripleArgonLowESoftCoat12mm.u.val <- format(1/((1/1.5) + curtain.effect),digits=4)

#COMMON.WRITE.CSV(results, "../../t.csv")

fail.test.if(results[results$sent.from == "6mm",]$window.u.value..After. != results$air6mm.u.val,
             "U-value for frame=wood, type=double, insulation=air, airgap=6mm")

fail.test.if(results[results$sent.from == "12mm",]$window.u.value..After. != results$air12mm.u.val,
             "U-value for frame=wood, type=double, insulation=air, airgap=12mm")

fail.test.if(results[results$sent.from == "16mm",]$window.u.value..After. != results$air16mm.u.val,
             "U-value for frame=wood, type=double, insulation=air, airgap=16mm")

fail.test.if(results[results$sent.from == "UpvcArgonLowESoft12mm",]$window.u.value..After. != results$tripleArgonLowESoftCoat12mm.u.val,
             "U-value for frame=upvc, type=triple, insulation=ArgonSoftCoat, airgap=12mm")
