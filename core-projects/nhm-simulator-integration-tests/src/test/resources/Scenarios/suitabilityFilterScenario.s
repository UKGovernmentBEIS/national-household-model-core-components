(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
 (include href: common/fuelproperties.s)

 (def-action district-heating (measure.district-heating
                                                            efficiency: 1.0
                                  ))


  (def-action standard-gas-boiler (measure.standard-boiler 
                                                           fuel: MainsGas
                                                           winter-efficiency: 1.0
                                  ))
 
          (policy
                  (target exposure: (schedule.time-series (expose on: 01/02/2012

                                                                  (sample.count  1000000)))
                          name: "install district heating, testing its suitability first"
                          action: #district-heating
                          group: (group.filter source: (group.all)

                                               (house.is-suitable-for #district-heating))
                  )
                  (target exposure: (schedule.time-series (expose on: 01/03/2012

                                                                  (sample.count  1)))
                          name: "should do nothing because all dwellings will be unsuitable"
                          action: #standard-gas-boiler
                          group: (group.filter source: (group.all)
                                               (house.is-suitable-for #standard-gas-boiler))
                  )))
