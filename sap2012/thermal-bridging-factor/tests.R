energy <- load.probe("energy")

## TODO: check this actually works

energy <- energy[energy$bredem..Before != 0] # Exclude houses with no energy use (no occupants)

bredem_did_not_fall <- energy[energy$bredem..After >= energy$bredem..Before]

fail.test.if(
    any(bredem_did_not_fall),
    paste("Space heating energy use should fall under BREDEM 2012 when the thermal bridging factor is reduced.", bredem_did_not_fall)
)

sap_changes <_ energy[energy$sap..After != energy$sap..Before]

fail.test.if(
    any(sap_changes),
    paste("Space heating energy use should not change under SAP 2012 when the thermal bridging factor is changed, since it is hardcoded to 0.15 in SAP 2012 mode.", sap_changes)
)
