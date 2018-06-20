result <- load.probe("cooking")
result$elec <- result$elec..Before
result$gas <- result$gas..Before
result$occupancy <- result$occupancy..Before

## This is quite high, because it includes both floating point error and a kWh/y conversion.
## Year length of 365.25 is used, but the numbers which come out of the NHM will vary depending on whether it is a leap year.
EPSILON <- 1

## Expected energy use based on BREDEM 2012 Table 5
### "Normal size cooker: electric"
elec <- result[result$gas == 0,]
elec$expected <- 275 + (elec$occupancy * 55)
failed_elec <- elec[abs(elec$expected - elec$elec) > EPSILON,]

fail.test.if(
    nrow(failed_elec) > 0,
    paste(
        "Cooking energy did not match the BREDEM 2012 numbers for the electricity only group ",
        paste(failed_elec$dwelling.id, collapse = ", ")
    )
)

### "Normal size cooker: electric/gas"
mixed <- result[result$gas > 0,]

#### Gas Use
mixed$expected_gas <- 241 + (mixed$occupancy * 48)
failed_mixed_gas <- mixed[abs(mixed$expected_gas - mixed$gas) > EPSILON,]

fail.test.if(
    nrow(failed_mixed_gas) > 0,
    paste(
        "Cooking gas use did not match the BREDEM 2012 numbers for the mixed electricity/gas group ",
        paste(failed_mixed_gas$dwelling.id, collapse = ", ")
    )
)

#### Electricity Use
mixed$expected_elec <- 138 + (mixed$occupancy * 28)
failed_mixed_elec <- mixed[abs(mixed$expected_elec - mixed$elec) > EPSILON,]

fail.test.if(
    nrow(failed_mixed_elec) > 0,
    paste(
        "Cooking electricity use did not match the BREDEM 2012 numbers for the mixed electricity/gas group ",
        paste(failed_mixed_elec$dwelling.id, collapse = ", ")
    )
)
