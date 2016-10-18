(scenario stock-id: test-survey-cases
          end-date: 31/12/2015
          start-date: 01/01/2012
          quantum: 400

          (include href: ../common/weather.s)
          (include href: ../common/technologyCostsSpecifiedByDECC.s)
          (include href: ../common/fuelproperties.s)
          (policy name: "Cheapest heating system over time"

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.proportion   0.1))
                          name: "Cheapest heating system over time"
                          action: (choice select: (select.minimum (npv horizon: 20
                                                                       discount: 0.075
                                                                  ))

                                          (measure.heat-pump cop: 2.5
                                                             type: GroundSource
                                          )
                                          (measure.heat-pump cop: 2.5
                                                             type: AirSource
                                          )
                                          (measure.standard-boiler fuel: BiomassPellets
                                                                   winter-efficiency: 0.65
                                          )
                                          (measure.standard-boiler fuel: MainsGas
                                                                   winter-efficiency: 0.89
                                          )
                                          (measure.district-heating name: "heat network"
                                                                    efficiency: 0.75
                                          )
                                          (action.do-nothing))
                          group: (group.all)
                  )))