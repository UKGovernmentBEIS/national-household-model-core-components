result <- load.probe("cooking")
result$elec <- result$elec..Before
result$gas <- result$gas..Before
result$occupancy <- result$occupancy..Before

## TODO: this test has not been run, and is likely to fail.

## Expected energy use based on BREDEM 2012 Table 5
### "Normal size cooker: electric"
elec <- result[result$gas == 0]
elec$expected <- 275 + (elec$occupancy * 55)
failed_elec <- elec[elec$expected != elec$elec]

fail.test.if(
    any(failed_elec)
    paste("Cooking energy did not match the BREDEM 2012 numbers for the electricity only group ", failed_elec)
)

### "Normal size cooker: electric/gas"
mixed <- result[result$gas..Before > 0]

#### Gas Use
mixed$expected_gas <- 241 + (elec$occupancy * 48)
failed_mixed_gas <-mixed[mixed$expected_gas != mixed$gas]

fail.test.if(
    any(failed_mixed_gas)
    paste("Cooking gas use did not match the BREDEM 2012 numbers for the mixed electricity/gas group ", failed_mixed_gas)
)

#### Electricity Use
mixed$expected_elec <- 131 + (elec$occupancy * 28)
failed_mixed_elec <- mixed[mixed$expected_elec != mixed$elec]

fail.test.if(
    any(failed_mixed_elec)
    paste("Cooking electricity use did not match the BREDEM 2012 numbers for the mixed electricity/gas group ", failed_mixed_elec)
)
