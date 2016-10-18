(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "should do nothing"

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.count  10000))
                          name: "install DH in 0% of cases"
                          action: (measure.district-heating cylinder-insulation-thickness: 50
                                                            efficiency: 1
                                                            cylinder-volume: 100
                                  )
                          group: (group.random-sample proportion: 0
                                                      source: (group.all)
                                                      name: nobody
                                 )
                  )))