## Tests 2 things:

result <- load.probe("appliance-energy")

result$occupancy <- result$occupancy..Before
result$floor.area <- result$floor.area..Before
result$sap <- result$sap..Before
result$bredem <- result$bredem..Before

## This is quite high, because it includes both floating point error and a kWh/y conversion.
## Year length of 365.25 is used, but the numbers which come out of the NHM will vary depending on whether it is a leap year.
EPSILON <- 10

result$number <- (result$floor.area * result$occupancy)^0.4714
## BREDEM 2012 1I to 1K
result$bredem.expected <- result$number * 184.8
## SAP 2012 L10 to L12
result$sap.expected <- result$number * 207.8

wrong_bredem <- result[abs(result$bredem - result$bredem.expected) > EPSILON,]
wrong_sap <- result[abs(result$sap - result$sap.expected) > EPSILON,]

print(head(wrong_sap))

fail.test.if(
    nrow(wrong_bredem) > 0,
    "Appliance energy did not match the BREDEM 2012 numbers."
)

fail.test.if(
    nrow(wrong_sap) > 0,
    "Appliance energy did not match the SAP 2012 numbers."
)
