(scenario stock-id: test-survey-cases
          end-date: 31/12/2012
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "intall DH"

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.count  10000))
                          name: "install DH for all houses"
                          action: (measure.district-heating capex: (function.quadratic b: 2
                                                                                       c: 3
                                                                                       a: 1
                                                                                       x: (size.kw)
                                                                   )
                                                            efficiency: 0.75
                                                            opex: (function.quadratic b: 2
                                                                                      c: 3
                                                                                      a: 1
                                                                                      x: (size.kw)
                                                                  )
                                                            size: (size max: 100000
                                                                        (function.steps value: (house.peak-load)
                                                                                               round: Up
                                                                                               steps: [0 10000]
                                                                               )
                                                                  )
                                  )
                          group: (group.all)
                  )))