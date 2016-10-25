## Test to check that the space heating energy use is lower after changing the rest-of-dwelling heating proportion.

energy <- load.probe("energy")

fell <- energy[energy$space-heating..After < energy$space-heating..Before]
unchanged <- energy[energy$space-heating..After == energy$space-heating..Before & energy$space-heating..After != 0]
rose <- energy[energy$space-heating..After > energy$space-heating..Before]

fail.test.if(
    any(rose),
    paste("Space heating energy use increased for some dwellings", rose)
)

fail.test.if(
    any(unchanged),
    paste("Space heating energy use did not fall for some dwellings", unchanged)
)
