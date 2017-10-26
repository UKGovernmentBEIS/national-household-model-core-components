result <- load.probe("hideem-report")

results <- data.table(result)
back.boiler.replaced <- results[boiler.type..Before. == "BackBoiler" & suitable == "true",]
permeability.changed <- back.boiler.replaced[permeability..Before. != permeability..After.,]

## Read in space heating DTO as space.heating.2012.sub - make sure aacode is character not factor
#results$survey.code..Before. <- as.character(results$survey.code..Before.)
#space.heating.2012.sub <- subset(spaceheating.dto.2012, aacode %in% results$survey.code..Before.)
#summarise(group_by(space.heating.2012.sub, fluetype), cn())
#would expect almost all of the house cases to have openflues before....

fail.test.if(nrow(permeability.changed) > 0, "Permability does not change when back-boiler is replaced")

## write.csv(permeability.changed,file.path("~/software-projects/nhm-cse/system-tests/t.csv"))


