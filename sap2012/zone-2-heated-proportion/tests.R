## Test to check that the space heating energy use is lower after changing the rest-of-dwelling heating proportion.

energy <- load.probe("energy")

fell <- energy[energy$space-heating..After < energy$space-heating..Before]
unchanged <- energy[energy$space-heating..After == energy$space-heating..Before & energy$space-heating..After != 0]
rose <- energy[energy$space-heating..After > energy$space-heating..Before]

if (any(rose)) {
    stop(paste("Space heating energy use increased for some dwellings", rose))
}

if (any(unchanged)) {
    stop(paste("Space heating energy use did not fall for some dwellings", unchanged))
}

