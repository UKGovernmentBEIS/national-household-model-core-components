(context.technologies
    (technology.wet-heating-system

        ; Assumption:Costs for wet central heating system: 2000 for a flat, 4000 
        ; for other properties         Owner:Chris Wickins         Source:Rough 
        ; calculation based on average number of rooms in different properties and
        ; average radiator prices         Date:01/01/2013         Quality:Amber   
        ;   !
        capex: (function.case
                    default: 2000
                    (when
                        (any
                            (house.built-form-is Bungalow)
                            (house.built-form-is SemiDetached)
                            (house.built-form-is Detached)
                            (house.built-form-is EndTerrace)) 4000)))
    (technology.boiler

        ; Boiler costs are currently dummy data !
        capex: 2800
        fuel: Electricity
        type: Standard
        opex: 75

        ; Assumption: Boilers (of all fuel types) are available on the market in 
        ; sizes that increase by 5kW from 5KW to 65kW     Owner: None     Source: 
        ; Dummy data?     Date: 02/11/2013     Quality: Red          Assumption: 
        ; Boilers (of all fuel types) are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Owner: None     Source: CSE     Date: 02/11/2013    
        ; Quality: Amber     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )
    (technology.district-heating

        ; District heating costs to be determined !
        capex: 0
        opex: 0

        ; District heating sizing function to be determined !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 0 10000 ])))
    (technology.ashp

        ; Assumption: Capex of renewable heating technologies fit a quadratic 
        ; function of size of technology.     Assumption: The costs collected by 
        ; Sweett Group include all upfront costs associated with the installation.
        ; Assumption: Opex is best modelled as a function of capex     Source: 
        ; Analaysis of data collected by Sweett Group. See D13/123017 and 
        ; D13/1237118.     Date: 02/11//2013     Quality: Green     Testing: Peer 
        ; reviewed by NHM TG including Phil Sargeant, Hunter Danskin, Chris 
        ; Wickins.     !
        capex: (function.quadratic
                    b: 1199.574660
                    c: 0
                    a: -30.397130
                    x: (size.kw))
        opex: (function.quadratic
                   b: 13.67515
                   c: 0
                   a: -0.34653
                   x: (size.kw))

        ; Assumption: Heat pumps are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Source: CSE     Date: 02/11/2013     Quality: Amber    
        ; ! Heat pumps are availble on the market in size increments of 5kW  from 
        ; a minimum of 5kW to a maximum of 20     Source: Dummy Data?     Date: 
        ; 02/11/2013     Quality: Red     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )
    (technology.gshp

        ; Assumption: Capex of renewable heating technologies fit a quadratic 
        ; function of size of technology.     Assumption: The costs collected by 
        ; Sweett Group include all upfront costs associated with the installation.
        ; Assumption: Opex is best modelled as a function of capex     Source: 
        ; Analaysis of data collected by Sweett Group. See D13/123017 and 
        ; D13/1237118.     Date: 02/11//2013     Quality: Green     Testing: Peer 
        ; reviewed by NHM TG including Phil Sargeant, Hunter Danskin, Chris 
        ; Wickins.     !
        capex: (function.quadratic
                    b: 2267.015530
                    c: 0
                    a: -20.068810
                    x: (size.kw))
        opex: (function.quadratic
                   b: 19.72304
                   c: 0
                   a: -0.17460
                   x: (size.kw))

        ; Assumption: Heat pumps are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Source: CSE     Date: 02/11/2013     Quality: Amber    
        ; ! Heat pumps are availble on the market in size increments of 5kW  from 
        ; a minimum of 5kW to a maximum of 65     Source: Dummy Data?     Date: 
        ; 02/11/2013     Quality: Red     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )
    (technology.boiler

        ; Assumption: Capex of renewable heating technologies fit a quadratic 
        ; function of size of technology.     Assumption: The costs collected by 
        ; Sweett Group include all upfront costs associated with the installation.
        ; Assumption: Opex is best modelled as a function of capex     Source: 
        ; Analaysis of data collected by Sweett Group. See D13/123017 and 
        ; D13/1237118.     Date: 02/11//2013     Quality: Green     Testing: Peer 
        ; reviewed by NHM TG including Phil Sargeant, Hunter Danskin, Chris 
        ; Wickins.     !
        capex: (function.quadratic
                    b: 850.40501
                    c: 0
                    a: -7.67448
                    x: (size.kw))
        fuel: BiomassPellets
        type: Standard
        opex: (function.quadratic
                   b: 7.39852
                   c: 0
                   a: -0.06677
                   x: (size.kw))

        ; Assumption: Boilers (of all fuel types) are available on the market in 
        ; sizes that increase by 5kW from 5KW to 65kW     Owner: None     Source: 
        ; Dummy data?     Date: 02/11/2013     Quality: Red          Assumption: 
        ; Boilers (of all fuel types) are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Owner: None     Source: CSE     Date: 02/11/2013    
        ; Quality: Amber     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )

    ; Assumption: Capex of renewable heating technologies fit a quadratic function
    ; of size of technology.     Assumption: The costs collected by Sweett Group 
    ; include all upfront costs associated with the installation.     Assumption: 
    ; Opex is best modelled as a function of capex     Source: Analaysis of data 
    ; collected by Sweett Group. See D13/123017 and D13/1237118.     Date: 
    ; 02/11//2013     Quality: Green     Testing: Peer reviewed by NHM TG 
    ; including Phil Sargeant, Hunter Danskin, Chris Wickins.     !
    (technology.boiler
        capex: (function.quadratic
                    b: 850.40501
                    c: 0
                    a: -7.67448
                    x: (size.kw))
        fuel: BiomassWood
        type: Standard
        opex: (function.quadratic
                   b: 7.39852
                   c: 0
                   a: -0.06677
                   x: (size.kw))

        ; Assumption: Boilers (of all fuel types) are available on the market in 
        ; sizes that increase by 5kW from 5KW to 65kW     Owner: None     Source: 
        ; Dummy data?     Date: 02/11/2013     Quality: Red          Assumption: 
        ; Boilers (of all fuel types) are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Owner: None     Source: CSE     Date: 02/11/2013    
        ; Quality: Amber     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )
    (technology.boiler

        ; Boiler costs are currently dummy data !
        capex: 3300
        fuel: MainsGas
        type: Standard
        opex: 75

        ; Assumption: Boilers (of all fuel types) are available on the market in 
        ; sizes that increase by 5kW from 5KW to 65kW     Owner: None     Source: 
        ; Dummy data?     Date: 02/11/2013     Quality: Red          Assumption: 
        ; Boilers (of all fuel types) are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Owner: None     Source: CSE     Date: 02/11/2013    
        ; Quality: Amber     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )

    ; Boiler costs are currently dummy data !
    (technology.boiler
        capex: 3100
        fuel: Oil
        type: Standard
        opex: 75

        ; Assumption: Boilers (of all fuel types) are available on the market in 
        ; sizes that increase by 5kW from 5KW to 65kW     Owner: None     Source: 
        ; Dummy data?     Date: 02/11/2013     Quality: Red          Assumption: 
        ; Boilers (of all fuel types) are sized from their peak load calculated 
        ; assuming a 19°C internal temperature and -5°C design external 
        ; temperature.     Owner: None     Source: CSE     Date: 02/11/2013    
        ; Quality: Amber     !
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 5 10 15 20 25 30 35 40 45 50 55 60 65 ]))
        )
    (technology.boiler

        ; Boiler costs are currently dummy data !
        capex: 2500
        fuel: MainsGas
        type: StorageCombi
        opex: 75
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 0 20000 35000 ])))
    (technology.boiler
        capex: 2500
        fuel: MainsGas
        type: InstantCombi
        opex: 75
        size: (size
                   (function.steps
                               value: (house.peak-load)
                               round: Up
                               steps: [ 0 10000 ])))
    (technology.cavity-insulation
        capex: 500)
    (technology.external-wall-insulation
        capex: (function.quadratic
                    b: 57.7
                    c: 5508
                    a: 0
                    x: (size.m2)))
    (technology.internal-wall-insulation
        capex: (function.quadratic
                    b: 42.5
                    c: 6837
                    a: 0
                    x: (size.m2)))
    (technology.loft-insulation
        capex: 300)
    ; bogus hot water values. <technology.solar-hot-water> <capex> <real 
    ; 			value="100" /> </capex> <opex> <real value="50" /> </opex> 
    ; <stepwise-sizing>  			<size>0</size> <size>100000</size> </stepwise-sizing> 
    ; </technology.solar-hot-water>  			<technology.wet-heating-system> <capex> 
    ; <real value="2000" /> </capex> </technology.wet-heating-system>
    )