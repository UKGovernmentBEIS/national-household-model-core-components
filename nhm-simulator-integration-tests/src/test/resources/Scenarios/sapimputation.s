(scenario stock-id: test-survey-cases
          end-date: 01/01/2015
          start-date: 01/01/2013
          quantum: 400
          
          (include href:resetting/resetToSap.s)
          
           (report.dwellings name: gmr
                                     group: (group.all)

                                     (debug/function name: debug-outside
                                                     delegate: (under evaluate: (debug/function name: debug-under
                                                                                                delegate: (house.fuel-cost)
                                                                                )

                                                                      (counterfactual.carbon mains-gas: 2)
                                                                      (counterfactual.weather 
                                                                      	(weather
                                                                      		temperature: [10 10 10 10 10 10 10 10 10 10 10 10]
                                                                      		windspeed:[11 11 11 11 11 11 11 11 11 11 11 11]
                                                                      		insolation: [12 12 12 12 12 12 12 12 12 12 12 12]
                                                                  		))
                                                                      (action.set-tariffs (tariff.simple name: sap09

                                                                                                         (function.simple-pricing unit-price: 1
                                                                                                                                  fuel: MainsGas
                                                                                                                                  standing-charge: 10
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0573
                                                                                                                                  fuel: BulkLPG
                                                                                                                                  standing-charge: 70
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0834
                                                                                                                                  fuel: BottledLPG
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0406
                                                                                                                                  fuel: Oil
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0297
                                                                                                                                  fuel: HouseCoal
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0342
                                                                                                                                  fuel: BiomassWood
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0249
                                                                                                                                  fuel: BiomassWoodchip
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0545
                                                                                                                                  fuel: BiomassPellets
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.0378
                                                                                                                                  fuel: CommunityHeat
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.1146
                                                                                                                                  fuel: PeakElectricity
                                                                                                         )
                                                                                                         (function.simple-pricing unit-price: 0.1146
                                                                                                                                  fuel: OffPeakElectricity
                                                                                                         )))
                                                                      (action.set-heating-temperatures living-area-temperature: 21
                                                                                                       threshold-external-temperature: 12
                                                                      )
                                                                      (action.set-heating-schedule
                                                                                                   (schedule on: Weekdays

                                                                                                             (heating between: 7
                                                                                                                      and: 9
                                                                                                             )
                                                                                                             (heating between: 16
                                                                                                                      and: 23
                                                                                                             ))
                                                                                                   (schedule on: Weekends

                                                                                                             (heating between: 7
                                                                                                                      and: 23
                                                                                                             )))
                                                                      (action.set-wall-u u-value: 100)
                                                                      (reset-to-sap))
                                     ))
)