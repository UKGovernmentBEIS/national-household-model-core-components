(scenario
    stock-id: test-survey-cases
    start-date: 01/01/2013
    end-date: 31/12/2013
    quantum: 400
    name: 1
    
    (policy
        (target
            exposure: (schedule.on-group-entry)
            name: action.ref_1
            action: (action.do-nothing)
            group: (group.filter
                        (all
                            (house.region-is SouthEast)
                            (house.loft-insulation-thickness-is
                                exactly: 0)
                            (house.has-wall
                                with-insulation: NoInsulation
                                with-construction: Cavity))))
        (target
            exposure: (schedule.on-group-entry)
            name: action.ref_2
            action: (action.do-nothing)
            group: (group.filter
                        (all
                            (house.region-is SouthWest)
                            (none
                                (house.main-heating-fuel-is MainsGas))))))
                                
     (template ACT06_1 []
        (do
            all:true
            name:ACT06_1
            (measure.loft-insulation
                top-up:false
                thickness:270)
            (measure.wall-insulation
                thickness:50
                type:Cavity)))
                
    (template ACT06_2 []
        (do
            all:true
            name:ACT06_2
            (measure.solar-dhw
                area:8.1
                cost:3500
                cylinder-volume:100)
            (measure.standard-boiler
                fuel:BiomassPellets
                cylinder-insulation-thickness:54
                winter-efficiency:0.90
                cylinder-volume:150)))
    
    (report.state)
    (report.measure-installations
        name: installationLog))