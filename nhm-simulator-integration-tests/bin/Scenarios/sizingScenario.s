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
                                                            cylinder-insulation-thickness: 50
                                                            efficiency: 0.8
                                                            opex: (function.quadratic b: 2
                                                                                      c: 3
                                                                                      a: 1
                                                                                      x: (size.kw)
                                                                  )
                                                            cylinder-volume: 110
                                                            size: (size max: 100
                                                                        (function.steps value: (house.peak-load)
                                                                                               round: Up
                                                                                               steps: [0.5 100]
                                                                               )
                                                                  )
                                  )
                          group: (group.all)
                  )))