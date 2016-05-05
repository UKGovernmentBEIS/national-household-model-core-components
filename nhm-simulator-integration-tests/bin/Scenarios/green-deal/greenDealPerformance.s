(scenario
        stock-id: test-survey-cases
        start-date: 01/01/2013
        end-date: 31/12/2023
        quantum: 400
        name: "Green deal scenario"

        (report.aggregate
            name: total-eco-points
            mode: (mode.dates (scenario-end))
            (aggregate.sum
                (register.get r_ecopoints)))
        (report.measure-installations)
        
    (include href: DECC_Standard_Technology_Assumptions.s)
    (include href: DECC_Fuel_Properties.s)
    (include href: DECC_SAP_Weather.s)
    (include href: DECC_Scenario_Inputs.s)
            
    (template green-deal-policy (@gd-choice)
        (policy
            name: GreenDeal
            (target
                name: oo_prs_solidwalls
                
                exposure: (schedule.repeat
                    interval: "1 year"
                    (sample.proportion 0.1))
                    
                group: (group.filter
                    (all
                                (any
                                    (house.tenure-is PrivateRented)
                                    (house.tenure-is OwnerOccupied))
                                (house.has-wall
                                    with-insulation: NoInsulation
                                    with-construction: AnySolid)))
                                    
                action: @gd-choice)
                
            (target
                name: oo_prs_cavitywalls
               
                exposure: (schedule.repeat
                               interval: "1 year"
                               (sample.proportion 0.25))
                group: (group.filter
                            source: (group.all)
                            (all
                                (any
                                    (house.tenure-is PrivateRented)
                                    (house.tenure-is OwnerOccupied))
                                (house.has-wall
                                    with-insulation: NoInsulation
                                    with-construction: AnyCavity)))
                
                action: @gd-choice)
            
            (target
                name: shs_solidwalls
                
                exposure: (schedule.repeat
                               interval: "1 year"
                               (sample.count 100000))
                
                group: (group.filter
                            source: (group.all)
                            (all
                                (house.tenure-is LocalAuthority)
                                (house.has-wall
                                    with-insulation: NoInsulation
                                    with-construction: AnySolid)))
                
                action: @gd-choice)
            
            (target
                name: shs_cavitywalls
                
                exposure: (schedule.repeat
                               interval: "1 year"
                               (sample.count 140000))
                
                group: (group.filter
                            source: (group.all)
                            (all
    
                                (house.tenure-is LocalAuthority)
                                (house.has-wall
                                    with-insulation: NoInsulation
                                    with-construction: AnyCavity)))
                
                action: @gd-choice)
            ))
        
    (green-deal-policy gd-choice: (probe name: gd-choice
        (register.add 
            name: r_ecopoints
            value: (get v_ecopoints)
            action: (choice
                snapshots: (snapshot 
                    name: before
                    #eco_assumptions)
                    
                vars: (var v_fuel_cost_before (house.fuel-cost))
                
                select: (select.filter
                    snapshots: (snapshot 
                        name: after
                        #eco_assumptions)
                        
                    vars: (var bill_saving 
                        (-
                            (-
                                (get v_fuel_cost_before)
                                (house.fuel-cost))
                            (obligations.predict 
                                years: 1 
                                include: NewlyAdded)))

                    test: (house.value-is
                        above: 0
                        (get bill_saving))
                    
                    selector: (select.weighted
                        weight: (exp (* 2 (+
                            (*
                                (+ (cost.sum) (get v_swi_disincentive default: 0 ))
                                -0.0003)
                            (*
                                150
                                -0.0003)
                            (*
                                (get bill_saving)
                                0.00341)
                                
                            (get v_repayment_coefficient default: 0)
                            (get v_measure_coefficient default: 0)
                            )))))
            
                (template green-deal-finance (@measure @term)
                    (finance.with-loan
                        rate: 0.075
                        term: @term
                        principal: (cost.sum)
                        
                        (finance.with-subsidy
                        	@measure
                        
                            subsidy: (min
                                (cost.sum)
                                (*
                                    80
                                    (yield
                                     
                                        var: v_ecopoints
                                        (*
                                            (- 1 (get v_in_use_factors))
                                            (get v_measure_lifetime)
                                            (-
                                                (under
                                                    snapshot: before
                                                    evaluate: (house.emissions))
                                                    
                                                (under
                                                    #eco_assumptions
                                                    evaluate: (house.emissions))))
                                            
                                        ))))))
                                        
                (green-deal-finance measure: #m_solid_wall_external term: 10)
                (green-deal-finance measure: #m_solid_wall_internal term: 10)
                (green-deal-finance measure: #m_cavity term: 10)
                (green-deal-finance measure: #m_loft term: 10)
                (green-deal-finance measure: #m_loft_and_cavity term: 10))
        ))))