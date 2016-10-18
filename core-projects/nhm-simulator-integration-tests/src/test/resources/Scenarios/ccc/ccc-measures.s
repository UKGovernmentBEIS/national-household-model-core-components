(no-include
    (scenario
        ; A test scenario to ensure that our templates validate.
        start-date: 01/01/2013
        end-date: 01/01/2014
        stock-id: UnitedKingdomStock
        
        (include name: sap-assumptions version: 4)
        (include name: "DECC Standard energy calculation inputs" version: 1)
        
        (on.dates
            (scenario-end)
            (apply
                (do
                    (gas-condensing-boiler)
                    (oil-condensing-boiler)
                    (storage-heater)
                    (loft-insulation-full)
                    (loft-insulation-topup)
                    (cavity-wall-insulation)
                    (solid-wall-insulation-external)
                    (solid-wall-insulation-internal)
                    (solar-photovoltaic)
                    (air-source-heat-pump)
                    (warm-homes-discount))))))
        
; The insulation measures are designed to be SAP-compatible.
; Normally we would work out the change to a building of adding a layer of material with a given resistance and thickness.
; Instead, we look up the resulting u-value in a SAP table.
; We are including DECC's modifications to the SAP wall u-values in this.

; This approach has serious weaknesses because it assumes that new insulation in an old house is the same as old insulation in an old house.
; However, it is necessary when using the energy calibration formula created by CARs.

(template once [@1 @2]
    ; name, measure
    (do
        update-flags: @1
        test-flags: (join ! @1)
        @2))
        
(template gas-condensing-boiler []
    (once
        gas-condensing-boiler
        (measure.standard-boiler 
            efficiency: 90%
            fuel: MainsGas)))
        
(template oil-condensing-boiler []
    (once
        oil-condensing-boiler
        (measure.standard-boiler 
            efficiency: 90%
            fuel: Oil)))
            
(template storage-heater []
    (once
        storage-heaters
        (measure.storage-heater
            type: SlimlineCelect)))

(template loft-insulation-full []
    (once
        loft-insulation
        (do
            (measure.loft-insulation
                thickness: 270
                top-up: false)
            (reset-roofs-to-sap))))
            
(template loft-insulation-topup []
    (once
        loft-insulation-topup
        (do
            (measure.loft-insulation
                thickness: 270
                top-up: true)
            (reset-roofs-to-sap))))

(template cavity-wall-insulation []
    (once
        cavity-insulation
        (do
            (measure.wall-insulation
                type: Cavity
                thickness: 50)
            (reset-walls-to-sap.with-decc-amendments))))


(template solid-wall-insulation-external []
    (once
        solid-wall-external
        (do
            (measure.wall-insulation
                type: External
                thickness: 100)
            (reset-walls-to-sap.with-decc-amendments))))

(template solid-wall-insulation-internal []
    (once
        solid-wall-internal
        (do
            (measure.wall-insulation
                type: Internal
                thickness: 50)
            (reset-walls-to-sap.with-decc-amendments))))
            
(template solar-photovoltaic []
    (once
        solar-pv
        (measure.solar-photovoltaic 
            roof-coverage: 50%
            efficiency: 15%
            capex: 0)))

(template air-source-heat-pump []
    (once
        air-source-heat-pump
        (measure.heat-pump
            type: AirSource)))            
            
(template warm-homes-discount []
    (once
        warm-homes-discount
        (action.extra-fuel-charge
            (extra-charge
                fuel: PeakElectricity
                (function.time-series
                    ; Actual billing dates in the NHM are at the end of the year.
                    initial: 0
                    (on 01/04/2013 -130)
                    (on 01/04/2014 -140)
                    (on 01/04/2015 0))))))
            