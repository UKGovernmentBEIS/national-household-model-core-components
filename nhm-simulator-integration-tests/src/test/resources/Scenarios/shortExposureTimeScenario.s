(scenario stock-id: test-survey-cases
          end-date: 02/01/2012
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "install cwi in urban houses and give flats electric boilers"

                  (target exposure: (schedule.repeat interval: "1 second"

                                                     (sample.count  10000))
                          name: "install cavity wall insulation"
                          action: (measure.wall-insulation capex: (function.quadratic b: 2
                                                                                      c: 3
                                                                                      a: 1
                                                                                      x: (size.m2)
                                                                  )
                                                           resistance: 10
                                                           thickness: 50
                                                           type: Cavity
                                  )
                          group: (group.filter source: (group.all)

                                               (house.region-is WestMidlands))
                  )))