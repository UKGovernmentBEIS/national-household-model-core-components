trybrary(dplyr)

expectation <- read.table("stored-values.tab", sep="\t", header = TRUE)

comparison <- load.probe("energy") %>%
    select(survey.code = survey.code..Before.,
           weight = weight..Before.,
           bredem2012 = bredem2012..Before.,
           sap2012 = sap2012..Before.) %>%
    distinct %>%
    {
        write.table(select(., -weight),
                    "new-values.tab", sep="\t", row.names = FALSE, col.names = TRUE)
        .
    } %>%
    full_join(expectation, by="survey.code") %>%
    mutate(delta.bredem = bredem2012.x - bredem2012.y,
           delta.sap = sap2012.x - sap2012.y,
           bredem.pc = 100*(delta.bredem / bredem2012.x),
           sap.pc = 100*(delta.sap / sap2012.x)) %>%
    select(survey.code, weight, delta.bredem, delta.sap, bredem.pc, sap.pc)

bad.rows <- filter(comparison, (abs(delta.bredem) > 0.01) | (abs(delta.sap) > 0.01))

if (nrow(bad.rows)) {
    print(bad.rows)
    print(summary(bad.rows$bredem.pc))
    print(summary(bad.rows$sap.pc))
    write.table(bad.rows, "errors.tab", sep="\t", row.names=FALSE, col.names=TRUE)
}

fail.test.if(with(comparison, abs(delta.bredem) > 0.01),
             "BREDEM2012 mode values changed")

fail.test.if(with(comparison, abs(delta.sap) > 0.01),
             "SAP2012 mode values changed")
