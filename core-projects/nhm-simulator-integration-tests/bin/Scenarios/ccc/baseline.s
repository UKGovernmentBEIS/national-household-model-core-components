(scenario
    start-date: 01/01/2013
    end-date: 01/01/2014
    stock-id: UnitedKingdomStock
    quantum: 200


    ; copied directly from DECC calibration function 
    (include href:"DECC Standard energy calibration.s")
    
    ;DECC Standard energy calculation inputs
    (include href: "DECC Standard energy calculation inputs.s")
    
    ; enables filtering by country group.scotland etc
    (include href: ccc-countries.s)
    
    ; uvalue resets
    (include href: sap-assumptions.s)
    
    ; contains counterfactual fuel tariffs to 2030 as provided by CCC
    (include href: ccc-metered-fuels.s)
    
    ; criteria for targeting baseline measures
    (include href: ccc-groups.s)
    
    ; defines measures
    (include href: ccc-measures.s)
    
    ; faciltates "baseline-measures" and sets income n-tiles
    (include href: ccc-baseline-measures.s)
    
    ; provides flag for HTTC
    (include href: "ccc-htt-cavities")
    
    ;get rid of unoccupied dwellings
    (include href: "demolish-unoccupied.s")

    (include href: energy-report.s)

    ;CCC-CarbonEmissionsFactors from CCC and Defra GHG reporting Carbon factors
    (include href: CCC-CarbonEmissionsFactors.s)
    

    (context.tariffs
        defaults: [
            (include href: "ccc-unmetered-fuels")
        ]
       others: [
            (metered-fuels)
        ])

    (demolish-unoccupied) ; These just get in the way. Bulldoze them so we don't have to think about them.
    (flag-hard-to-treat-cavities)
    
    (set-income-n-tiles)
    (baseline-measures)
   
   (report.measure-installations name: MeasuresInstalled)
    
    (template unfilled-cavities []
        (house.has-wall with-construction: Cavity with-insulation: NoInsulation))
    
    (on.dates
        (scenario-end)
        
        (aggregate
            name: insulation-technical-potential
            over: [
                (filter name: all-masonry-cavity-walls (house.has-wall with-construction: Cavity))
                (filter name: all-masonry-cavity-insulated-walls (house.has-wall with-construction: Cavity with-insulation: OnlyFilledCavity))
                (filter name: all-uninsulated-cavities (unfilled-cavities))
                (filter name: easy-to-treat-uninsulated-cavities
                    (all
                        (unfilled-cavities)
                        (none
                            (group.httc))))
                (filter name: hard-to-treat-insulated-cavities
                    (all
                        (unfilled-cavities)
                        (group.httc)))
                (filter name: External-Wall-insulation
                    (house.has-wall with-construction: AnySolid with-insulation: NoInsulation))
                ;(filter name: External-Wall-insulation
                ;    (house.has-wall with-construction: AnySolid with-insulation: NoInsulation))
                (filter name: loft-insulation-thickness-between-50-124-mm
                    (house.loft-insulation-thickness-is above: 49 below: 125))
                (filter name: loft-insulation-thickness-between-125-199-mm
                    (house.loft-insulation-thickness-is above: 124 below: 200))
                
            ]
            
            divide-by: []
            
            (aggregate.count)))

    (on.dates
        (scenario-end)
        
        (aggregate
            name: carbon-emissions-summary
            over: []
            divide-by: [(house.region)]
            
            (aggregate.mean name: average-emissions (house.emissions))
            (aggregate.sum name: total-emissions (house.emissions))
            (aggregate.count name: dwelling-count)))
)