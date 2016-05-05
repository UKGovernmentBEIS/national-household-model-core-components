; This scenario include implements the energy calibration prepared by CAR April 2014

; For households with gas heating  Gas NHM = 43394.1947 + ((1-0.5374)*Original 
; NHM) - (20.6531 * Year) + (1323.8477 * Bungalow&Detached) + (630.1162 * No. 
; occupants) + (37.4395 * floor area)  For households without gas heating  Gas NHM
; = 1.3147* Original NHM  For households without electric  heating  Electricity: 
; -120.5948 + ((1-0.3493)*NHM) + (0.0130*income) + (239.7583*No. bedrooms) + 
; (158.3228* No. occupants)  For households with electric heating  Electricity: 
; 1614.4222 + ((1-0.6332)*NHM) + (30.2163* floor area) + (488.9948*No. occupants) 
; + (0.0338*income)  For households with gas heating  Gas NHM = 43394.1947 + 
; ((1-0.5374)*Original NHM) - (20.6531 * Year) + (1323.8477 * Bungalow&Detached) +
; (630.1162 * No. occupants) + (37.4395 * floor area)  For households without gas 
; heating  Gas NHM = 1.3147* Original NHM  For households without electric 
; heating  Electricity: -120.5948 + ((1-0.3493)*NHM) + (0.0130*income) + 
; (239.7583*No. bedrooms) + (158.3228* No. occupants)  For households with 
; electric heating  Electricity: 1614.4222 + ((1-0.6332)*NHM) + (30.2163* floor 
; area) + (488.9948*No. occupants) + (0.0338*income)  and overall, national 


; scaling factors to match dukes
(template scale.dukes.gas  [] 0.974350205)
(template scale.dukes.elec [] 0.987860144)
(template scale.dukes.oil  [] 0.937208637)
(template scale.dukes.lpg  [] 0.527419734)
(template scale.dukes.coal [] 0.846581017)


; Supporting templates
(template /!
    [ @a @b ]
    (function.case
        (when
            (house.value-is
                exactly: 0
                (+ @a @b)) 0) default:
        (/ @a @b)))
(template electricity
    [ ]
    (+
        (house.energy-use
            by-fuel: PeakElectricity)
        (house.energy-use
            by-fuel: OffPeakElectricity)))
(template peak-ratio
    [ ]
    (/!
        a: (house.energy-use
                by-fuel: PeakElectricity)
        b: (electricity)))
(template off-peak-ratio
    [ ]
    (/!
        a: (house.energy-use
                by-fuel: OffpeakElectricity)
        b: (electricity)))
(context.calibration
    name: CARcalibration
    (replace
        fuels: [ MainsGas ]
        (max 0
            (*
                (scale.dukes.gas)
                (function.case
                    (when
                        (house.main-heating-fuel-is MainsGas)
                        (+ 43394.1947
                            (* 0.4626
                                (house.energy-use
                                    by-fuel: MainsGas))
                            (* -20.6531
                                (house.buildyear))
                            (* 1323.8477
                                (function.case
                                    default: (number 0)
                                    (when
                                        (any
                                            (house.built-form-is Detached)
                                            (house.built-form-is Bungalow))
                                        (number 1))))
                            (* 630.1162
                                (house.number-of-occupants))
                            (* 37.4395
                                (house.total-floor-area)))) default:
                    (* 1.3147
                        (house.energy-use
                            by-fuel: MainsGas))))))
    (replace
        fuels: [ OffpeakElectricity ]
        (max 0
            (*
                (scale.dukes.elec)
                (function.case
                    (when
                        (house.main-heating-fuel-is Electricity)
                        (+
                            (*
                                (off-peak-ratio)
                                (+ 1614.4222
                                    (* 30.2163
                                        (house.total-floor-area))
                                    (* 488.9948
                                        (house.number-of-occupants))
                                    (* 0.0338
                                        (house.household-income))))
                            (* 0.3668
                                (house.energy-use
                                    by-fuel: OffpeakElectricity)))) default:
                    (+
                        (*
                            (off-peak-ratio)
                            (+ -120.5948
                                (* 0.0130
                                    (house.household-income))
                                (* 239.7583
                                    (house.number-of-bedrooms))
                                (* 158.3228
                                    (house.number-of-occupants))))
                        (* 0.6507
                            (house.energy-use
                                by-fuel: OffpeakElectricity)))))))
    (replace
        fuels: [ PeakElectricity ]
        (max 0
            (*
                (scale.dukes.elec)
                (function.case
                    (when
                        (house.main-heating-fuel-is Electricity)
                        (+
                            (*
                                (peak-ratio)
                                (+ 1614.4222
                                    (* 30.2163
                                        (house.total-floor-area))
                                    (* 488.9948
                                        (house.number-of-occupants))
                                    (* 0.0338
                                        (house.household-income))))
                            (* 0.3668
                                (house.energy-use
                                    by-fuel: PeakElectricity)))) default:
                    (+
                        (*
                            (peak-ratio)
                            (+ -120.5948
                                (* 0.0130
                                    (house.household-income))
                                (* 239.7583
                                    (house.number-of-bedrooms))
                                (* 158.3228
                                    (house.number-of-occupants))))
                        (* 0.6507
                            (house.energy-use
                                by-fuel: PeakElectricity)))))))
    (polynomial
        fuels: [ Oil ] 0
        (scale.dukes.oil))
    (polynomial
        fuels: [ BulkLPG BottledLPG ] 0
        (scale.dukes.lpg))
    (polynomial
        fuels: [ HouseCoal ] 0
        (scale.dukes.coal)))