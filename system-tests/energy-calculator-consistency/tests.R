trybrary(dplyr)

expectation <- read.table("stored-values.tab", sep="\t", header = TRUE)

comparison <- load.probe("energy") %>%
    select(survey.code = survey.code..Before.,
           bredem2012 = bredem2012..Before.,
           sap2012 = sap2012..Before.) %>%
    distinct %>%
    full_join(expectation, by="survey.code") %>%
    mutate(delta.bredem = bredem2012.x - bredem2012.y,
           delta.sap = sap2012.x - sap2012.y) %>%
    select(survey.code, delta.bredem, delta.sap)

print(filter(comparison, delta.bredem > 0.01 | delta.sap > 0.01))

fail.test.unless(with(comparison, abs(delta.bredem) > 0.01),
                 "BREDEM2012 mode values changed")

fail.test.unless(with(comparison, abs(delta.sap) > 0.01),
                 "SAP2012 mode values changed")
