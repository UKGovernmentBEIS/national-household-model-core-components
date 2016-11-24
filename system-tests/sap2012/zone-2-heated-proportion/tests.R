## Test to check that the space heating energy use is lower after changing the rest-of-dwelling heating proportion.

energy <- load.probe("energy")

fell <- energy[energy$space.heating..After < energy$space.heating..Before,]
unchanged <- energy[energy$space.heating..After == energy$space.heating..Before & energy$space.heating..After != 0,]
rose <- energy[energy$space.heating..After > energy$space.heating..Before,]

fail.test.if(
    nrow(rose) > 0,
    paste(
        "Space heating energy use increased for some dwellings",
        paste(rose$dwelling.id, collapse = ", ")
    )
)

fail.test.if(
    nrow(unchanged) > 0,
    paste(
        "Space heating energy use did not fall for some dwellings",
        paste(unchanged$dwelling.id, collapse = ", ")
    )
)
