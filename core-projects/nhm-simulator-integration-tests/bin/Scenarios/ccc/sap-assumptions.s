(no-include
    (scenario
        ; Test scenario
        start-date: 01/01/2013
        end-date: 01/01/2014
        stock-id: NIHCS2009_DTO
        
        (on.dates
            (scenario-start)
            (apply
                (do
                    (reset-heating-temperatures)
                    (reset-doors-to-sap)
                    (reset-roofs-to-sap)
                    (reset-walls-to-sap)
                    (reset-floors-to-sap)
                    (reset-glazing-to-sap))))))

; a template which expands out to a list of sap assumptions
(template sap-assumptions
    [ [@standard-occupancy true] [@name 1] ]

    ; turn of energy calibration
    (action.decalibrate-energy)

    ; sap weather
    (counterfactual.weather)


    (reset-heating-temperatures)

    ; carbon factors, just in case
    (counterfactual.carbon)

    ; standard occupancy - you may not want this
    (macro.match @standard-occupancy
        [true (sap.occupancy)])

    ; standard heating schedule
    (reset-heating-schedule)

    ; sap pricing is important for sap score
    (action.set-tariffs
        (sap-tariffs name:@name))

    ; finally sap u/k/etc values
    (reset-walls-to-sap)
    (reset-floors-to-sap)
    (reset-roofs-to-sap)
    (reset-glazing-to-sap)
    (reset-doors-to-sap))
    
(template reset-heating-schedule []
    (action.set-heating-schedule
        (schedule
            on: Weekends
            (heating
                between: 7
                and: 23))
        (schedule
            on: Weekdays
            (heating
                between: 7
                and: 9)
            (heating
                between: 16
                and: 23)))
)
    
(template reset-heating-temperatures []
    ; sap heating temperatures, and threshold which is important  to select the 
    ; right heating months
    (action.set-heating-temperatures
        living-area-temperature: 21
        temperature-difference: 3
        threshold-external-temperature: 14.5))
    
(template sap-tariffs
    [ @name ]

     (tariff.simple
        name: (join sap-tariff @name)
        (function.simple-pricing
            fuel: MainsGas
            standing-charge: 106
            unit-price: 3.10)
        (function.simple-pricing

            ; used 10 hour tariff
            fuel: PeakElectricity
            standing-charge: 0
            unit-price: 11.83)
        (function.simple-pricing

            ; used 10 hour tariff
            fuel: OffPeakElectricity
            standing-charge: 18
            unit-price: 6.17)
        (function.simple-pricing
            fuel: BulkLPG
            standing-charge: 70
            unit-price: 5.73)
        (function.simple-pricing
            fuel: Oil
            standing-charge: 0
            unit-price: 4.06)
        (function.simple-pricing
            fuel: BiomassPellets
            standing-charge: 0
            unit-price: 4.93)
        (function.simple-pricing
            fuel: BiomassWoodchip
            standing-charge: 0
            unit-price: 2.49)
        (function.simple-pricing
            fuel: BiomassWood
            standing-charge: 0
            unit-price: 3.42)
        (function.simple-pricing
            fuel: HouseCoal
            standing-charge: 0
            unit-price: 2.97)
        (function.simple-pricing
            fuel: CommunityHeat
            standing-charge: 0
            unit-price: 3.78)
        ; and so on and so on
        )) (template sap-score
    [ ]
    (under
        name: sap-score
        (sap-assumptions) evaluate:
        (house.fuel-cost-index)))
; a template which works out a function under the sap assumptions above
(template under-sap
    [ @name @evaluate ]
    (under
        name: @name
        (sap-assumptions) evaluate: @evaluate)) 
        
(template reset-doors-to-sap
    [ ]
    (action.reset-doors
        areas: (lookup-table
                    name: "CHM door areas"
                    row-keys: (door.type)
                    [ Type Area ]
                    [ Glazed 1.85 ]
                    [ Solid 1.85 ])
        u-values: (lookup-table
                       name: "RDSAP door u-values"
                       row-keys: (door.type)
                       column-key: (house.sap-age-band)
                       [ Type A B C D E F G H I J K ]
                       [ Solid 3 3 3 3 3 3 3 3 3 3 2 ]
                       [ Glazed 3 3 3 3 3 3 3 3 3 3 2 ]))) 

(template reset-roofs-to-sap
    [ ]
    (action.reset-roofs
        u-values: (lookup-table
                       name: "RDSAP roof u-value"
                       row-keys: [ (house.loft-insulation-thickness) ]
                       column-key: (house.roof-construction-type)
                       interpolate: true
                       log-warnings: false
                       default: (lookup-table
                                     name: "RDSAP fallback roof u-value"
                                     row-keys: [ (house.roof-construction-type) ]
                                     column-key: (house.sap-age-band)
                                     [ Roof A B C D E F G H I J K ]
                                     [ PitchedSlateOrTiles 2.30 2.30 2.30 2.30 1.50
                                         0.68 0.40 0.29 0.26 0.16 0.16 ]
                                     [ Thatched 0.35 0.35 0.35 0.35 0.35 0.35 0.35
                                         0.35 0.35 0.30 0.25 ]
                                     [ Flat 2.30 2.30 2.30 2.30 1.50 0.68 0.40 0.35
                                         0.35 0.25 0.25 ])
                   [ Insulation PitchedSlateOrTiles Thatched ]
                   [ 0 2.30 0.40 ]
                   [ 25 1.00 0.30 ]
                   [ 50 0.70 0.30 ]
                   [ 75 0.50 0.20 ]
                   [ 100 0.40 0.20 ]
                   [ 125 0.30 0.20 ]
                   [ 150 0.30 0.20 ]
                   [ 200 0.20 0.10 ]
                   [ 250 0.20 0.10 ]
                   [ 300 0.10 0.10 ]
                   [ 350 0.10 0.10 ])
    k-values: 9
    party-k-values: 13.5))
    
(def-function sap.walls.u-values 
    (lookup-table
        name: "RDSAP wall u-values (england and wales)"
        row-keys: [ (wall.construction)
                      (wall.insulation-thickness Internal External
                          Cavity)
                      (wall.insulation-thickness Cavity) ]
        column-key: (house.sap-age-band)
        [ Construction Ins Cav A B C D E F G H I J K ]
        
        ; bands A-E here are defined by an equation in the SAP 
        ; documentation, (S5.1.1), but the equation always computes
        ;        the same values  so they are shown here directly.
        [ GraniteOrWhinstone 0 0 2.3 2.3 2.3 2.3 1.7 1 0.60 0.60 0.45
           0.35 0.30 ]
        [ Sandstone 0 0 2 2 2 2 1.7 1 0.60 0.60 0.45 0.35 0.30 ]
        
        ; these 6 rows for granite or whinstone are copies of the 
        ; solid brick values, because the SAP tables do not detail 
        ; anything for  insulated granite or insulated sandstone
        [ GraniteOrWhinstone <100 0 0.60 0.60 0.60 0.60 0.55 0.45 0.35
           0.35 0.30 0.25 0.21 ]
        [ GraniteOrWhinstone <150 0 0.35 0.35 0.35 0.35 0.35 0.32 0.24
           0.24 0.21 0.19 0.17 ]
        [ GraniteOrWhinstone >=150 0 0.25 0.25 0.25 0.25 0.25 0.21
           0.18 0.18 0.17 0.15 0.14 ]
        [ Sandstone <100 0 0.60 0.60 0.60 0.60 0.55 0.45 0.35 0.35
           0.30 0.25 0.21 ]
        [ Sandstone <150 0 0.35 0.35 0.35 0.35 0.35 0.32 0.24 0.24
           0.21 0.19 0.17 ]
        [ Sandstone >=150 0 0.25 0.25 0.25 0.25 0.25 0.21 0.18 0.18
           0.17 0.15 0.14 ]
        [ SolidBrick <50 0 2.10 2.10 2.10 2.10 1.70 1.00 0.60 0.60
           0.45 0.35 0.30 ]
        [ SolidBrick <100 0 0.60 0.60 0.60 0.60 0.55 0.45 0.35 0.35
           0.30 0.25 0.21 ]
        [ SolidBrick <150 0 0.35 0.35 0.35 0.35 0.35 0.32 0.24 0.24
           0.21 0.19 0.17 ]
        [ SolidBrick >=150 0 0.25 0.25 0.25 0.25 0.25 0.21 0.18 0.18
           0.17 0.15 0.14 ]
        [ Cob <50 0 0.80 0.80 0.80 0.80 0.80 0.80 0.60 0.60 0.45 0.35
           0.30 ]
        [ Cob <100 0 0.40 0.40 0.40 0.40 0.40 0.40 0.35 0.35 0.30 0.25
           0.21 ]
        [ Cob <150 0 0.26 0.26 0.26 0.26 0.26 0.26 0.26 0.26 0.26 0.25
           0.21 ]
        [ Cob >=150 0 0.20 0.20 0.20 0.20 0.20 0.20 0.20 0.20 0.20
           0.20 0.20 ]
        
        ; ; these two rows are for cavity walls that have no cavity
        ;        insulation in them
        [ Cavity <50 0 2.10 1.60 1.60 1.60 1.60 1.00 0.60 0.60 0.45
           0.35 0.30 ]
        [ Cavity >=50 0 0.65 0.65 0.65 0.65 0.65 0.65 0.35 0.35 0.45
           0.35 0.30 ]
        
        ; ; these are filled cavities with some degree of 
        ; insulation
        [ Cavity <=50 >0 0.65 0.65 0.65 0.65 0.65 0.65 0.35 0.35 0.45
           0.35 0.30 ]
        [ Cavity <=100 >0 0.31 0.31 0.31 0.31 0.31 0.27 0.25 0.25 0.25
           0.25 0.25 ]
        [ Cavity <=150 >0 0.22 0.22 0.22 0.22 0.22 0.20 0.19 0.19 0.19
           0.19 0.19 ]
        [ Cavity >150 >0 0.17 0.17 0.17 0.17 0.17 0.16 0.15 0.15 0.15
           0.15 0.15 ]
        [ TimberFrame 0 0 2.50 1.90 1.90 1.00 0.80 0.45 0.40 0.40 0.40
           0.35 0.30 ]
        
        ; ; this row contains an assumption that extends table S6 
        ; to cover timber frame with any kind of insulation
        [ TimberFrame >0 * 0.60 0.55 0.55 0.40 0.40 0.40 0.40 0.40
           0.40 0.35 0.30 ]
        
        ; ; these rows extend table S6 to cover system build with 
        ; any kind of insulation
        [ SystemBuild <50 * 2.00 2.00 2.00 2.00 1.70 1.00 0.60 0.60
           0.45 0.35 0.30 ]
        [ SystemBuild <100 * 0.60 0.60 0.60 0.60 0.55 0.45 0.35 0.35
           0.30 0.25 0.21 ]
        [ SystemBuild <150 * 0.35 0.35 0.35 0.35 0.35 0.32 0.24 0.24
           0.21 0.19 0.17 ]
        [ SystemBuild >=150 * 0.25 0.25 0.25 0.25 0.25 0.21 0.18 0.18
           0.17 0.15 0.14 ]
        
        ; ; This row handles all metal frame walls - origin unclear
        ;        (CHM?)
        [ MetalFrame * * 2.20 2.20 2.20 0.86 0.86 0.53 0.53 0.53 0.45
           0.35 0.30 ]))

(def-function sap.walls.thicknesses 
    (lookup-table
        name: "RDSAP wall thicknesses"
        row-keys: [ (wall.construction)
                     (wall.insulation-thickness Internal External
                         ) ]
        column-key: (house.sap-age-band)
        [ Construction Insulation A B C D E F G H I J K ]
        [ Sandstone 0 500 500 500 500 450 420 420 420 450 450 450
          ]
        [ Sandstone >0 570 570 570 570 520 490 490 490 520 520 520
          ]
        [ GraniteOrWhinstone 0 500 500 500 500 450 420 420 420 450
          450 450 ]
        [ GraniteOrWhinstone >0 570 570 570 570 520 490 490 490
          520 520 520 ]
        [ SolidBrick 0 220 220 220 220 240 250 270 270 300 300 300
          ]
        [ SolidBrick >0 290 290 290 290 310 320 340 340 370 370
          370 ]
        [ Cavity 0 250 250 250 250 250 260 270 270 300 300 300 ]
        [ Cavity >0 300 300 300 300 300 310 320 320 330 330 330
          ]
        [ TimberFrame 0 150 150 150 250 270 270 270 270 300 300
          300 ]
        [ TimberFrame >0 200 200 200 290 310 270 270 270 300 300
          300 ]
        [ Cob 0 540 540 540 540 540 540 560 560 590 590 590 ]
        [ Cob >0 590 590 590 590 590 590 610 610 640 640 640 ]
        [ SystemBuild 0 250 250 250 250 250 300 300 300 300 300
          300 ]
        [ SystemBuild >0 320 320 320 320 320 370 370 370 370 370
          370 ]
        [ MetalFrame 0 100 100 100 200 220 220 220 220 250 250 250
          ]
        [ MetalFrame >0 150 150 150 240 260 220 220 220 250 250
          250 ]))

(def-function sap.walls.k-values
    (lookup-table
        name: "RDSAP wall k-values"
        row-keys: [ (wall.construction)
                      (wall.insulation-thickness Internal)
                      (wall.insulation-thickness External) ]
        [ Construction Internal External k-value ]
        
        ; the internal k-values are presumed to be most important, so 
        ; we presume internal k-value  no matter the external thickness
        ;        if there is any internal insulation
        [ SolidBrick >0 * 17.0 ]
        [ Cob >0 * 66 ]
        [ SystemBuild >0 * 10 ]
        
        ; for these construction types, we use these values if there is
        ;        external but no internal
        [ SolidBrick 0 >0 135.0 ]
        [ Cob 0 >0 148 ]
        [ SystemBuild 0 >0 211 ]
        
        ; these k-values will be used if none of the rows above match
        [ Party_DensePlasterBothSidesDenseBlocksCavity * * 180.00 ]
        [ Party_SinglePlasterboardBothSidesDenseBlocksCavity * * 70.00
           ]
        [ Party_SinglePlasterboardBothSidesDenseAACBlocksCavity * * 45.00
           ]
        [ Party_DoublePlasterBothSidesTwinTimberFrame * * 50.00 ]
        [ Party_MetalFrame * * 20.00 ]
        [ GraniteOrWhinstone * * 202.00 ]
        [ Sandstone * * 156.00 ]
        [ SolidBrick * * 135.00 ]
        [ Cob * * 148.00 ]
        [ Cavity * * 139.00 ]
        [ TimberFrame * * 9.00 ]
        [ SystemBuild * * 211.00 ]
        [ MetalFrame * * 14.00 ]))

(template sap.walls.infiltration-rates []
    (lookup-table
        name: "RDSAP wall infiltration rates"
        row-keys: [ (wall.construction) ]
        [ Construction Infiltration ]
        [ MetalFrame 0.25 ]
        [ TimberFrame 0.25 ]
        [ * 0.35 ]))
    
(template reset-walls-to-sap
    [ ]
    (action.reset-walls
        u-values: (sap.walls.u-values)
        thicknesses: (sap.walls.thicknesses)
        k-values: (sap.walls.k-values)
        infiltrations: (sap.walls.infiltration-rates)))
                    
(template reset-floors-to-sap
    [ ]
    (action.reset-floors
        u-values: (lookup-table
                       name: "RDSAP floor u-values"
                       row-keys: [ (floor.is-on-ground) ]
                       [ GroundFloor u-value ]

                       ; this is simply the rule SAP uses for ground floor 
                       ; u-values  which is a long, tedious equation.
                       [ true
                           (floor.sap-ground-floor-u-value) ]

                       ; in this row we fall through to another lookup table 
                       ; which gives exposed floor u-values by age band
                       [ false
                           (lookup-table
                               name: "RDSAP exposed floor u-values"
                               row-keys: [ (house.floor-insulation-thickness) ]
                               column-key: (house.sap-age-band)
                               [ Insulation A B C D E F G H I J K ]
                               [ 0 1.20 1.20 1.20 1.20 1.20 1.20 1.20 0.51 0.51 0.25
                                   0.22 ]
                               [ >0 0.50 0.50 0.50 0.50 0.50 0.50 0.50 0.50 0.50 0.25
                                   0.22 ]) ])
    k-values: (lookup-table
                   name: "RDSAP external floor k-values"
                   row-keys: [ (floor.is-on-ground) ]
                   column-key: (house.floor-construction-type)
                   [ Ground Solid SuspendedTimber ]
                   [ true 110 20 ]
                   [ false 20 20 ])
party-k-values: 35)
(action.set-floor-insulation
    thickness: (lookup-table
                    name: "RDSAP floor insulation thickness"
                    row-keys: (house.sap-age-band)
                    [ Band Thickness ]
                    [ A 0 ]
                    [ B 0 ]
                    [ C 0 ]
                    [ D 0 ]
                    [ E 0 ]
                    [ F 0 ]
                    [ G 0 ]
                    [ H 0 ]
                    [ I 25 ]
                    [ J 75 ]
                    [ K 100 ]))) 
                    
(template curtains
    [ @u-value @factor ]

    ; ; apply curtain effect factor
    (/ 1
        (+ @factor
            (/ 1 @u-value)))) 
            
(template reset-glazing-to-sap
    [ ]
    (action.reset-glazing
        frame-factor: (lookup-table
                           name: "RDSAP frame factors"
                           row-keys: (glazing.frame-type)
                           [ Frame Factor ]
                           [ Wood 0.7 ]
                           [ Metal 0.8 ]
                           [ uPVC 0.7 ])
        u-value: (curtains
                      factor: 0.04
                      u-value: (lookup-table
                                    name: "RDSAP glazing u-values"
                                    row-keys: [ (glazing.type)
                                                   (glazing.insulation-type) ]
                                    column-key: (glazing.frame-type)

                                    ; ; a curtain effect factor of 0.04 is added 
                                    ; to all of these ; within the scenario 
                                    ; outside the lookup table.
                                    [ Glazing Insulation Wood Metal uPVC ]
                                    [ Single * 4.8 5.7 4.8 ]
                                    [ Secondary * 2.4 2.4 2.4 ]
                                    [ Double Air 3.1 3.7 3.1 ]
                                    [ Double LowEHardCoat 2.7 3.3 2.7 ]
                                    [ Double LowESoftCoat 2.6 3.2 2.6 ]
                                    [ Triple Air 2.4 2.9 2.4 ]
                                    [ Triple LowEHardCoat 2.1 2.6 2.1 ]
                                    [ Triple LowESoftCoat 2.0 2.5 2.0 ]))
        light-transmittance: (lookup-table
                                  name: "RDSAP light transmittance factors"
                                  row-keys: (glazing.type)
                                  [ Glazing Transmittance ]
                                  [ Single 0.9 ]
                                  [ Secondary 0.8 ]
                                  [ Double 0.8 ]
                                  [ Triple 0.7 ])
        gains-transmittance: (lookup-table
                                  name: "RDSAP gains transmittance factors"
                                  row-keys: [ (glazing.type)
                                                 (glazing.insulation-type) ]
                                  [ Glazing Insulation Transmittance ]
                                  [ Single * 0.85 ]
                                  [ Secondary * 0.76 ]
                                  [ Double Air 0.76 ]
                                  [ Double LowESoftCoat 0.63 ]
                                  [ Double LowEHardCoat 0.72 ]
                                  [ Triple Air 0.68 ]
                                  [ Triple LowESoftCoat 0.57 ]
                                  [ Triple LowEHardCoat 0.64 ])))