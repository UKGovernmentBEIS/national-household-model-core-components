energy <- load.probe("energy")

## Prove that modifying the thermal bridging levels for a house:
## 1. Improves energy use for space heating under BREDEM
## 2. Has no effect under SAP

energy <- energy[energy$bredem..Before != 0,] # Exclude houses with no energy use (no occupants)

bredem_rose <- energy[energy$bredem..After > energy$bredem..Before,]
bredem_stayed_constant <- energy[energy$bredem..After == energy$bredem..Before,]

fail.test.if(
    nrow(bredem_rose) > 0,
    paste(
        "Space heating energy use should not rise under BREDEM 2012 when the thermal bridging factor is reduced.",
        paste(bredem_rose$dwelling.id, collapse = ", ")
    )
)

## Currently failing, probably because of dwellings without external walls.
fail.test.if(
    nrow(bredem_stayed_constant) > 0,
    paste(
        "Space heating energy use should fall under BREDEM 2012 when the thermal bridging factor is reduced.",
        paste(bredem_stayed_constant$dwelling.id, collapse = ", ")
    )
)

sap_changes <- energy[energy$sap..After != energy$sap..Before,]

fail.test.if(
    nrow(sap_changes) > 0,
    paste(
        "Space heating energy use should not change under SAP 2012 when the thermal bridging factor is changed, since it is hardcoded to 0.15 in SAP 2012 mode.",
        paste(sap_changes$dwelling.id, collapse = ", ")
    )
)
