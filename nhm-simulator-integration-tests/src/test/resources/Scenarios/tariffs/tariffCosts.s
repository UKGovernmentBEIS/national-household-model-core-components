(scenario stock-id: test-survey-cases
          end-date: 31/12/2017
          start-date: 01/01/2012
          quantum: 400
           (context.tariffs defaults: (tariff name: constant_price_per_unit

                                                      (fuel type: MainsGas

                                                            (charge 1)))
                                    others: (tariff name: price_per_unit_increasing_over_time

                                                    (fuel type: MainsGas

                                                          (charge (function.case default: 999

                                                                                         (when  (sim.year-is exactly: 2012)
                                                                                                1
                                                                                         )
                                                                                         (when  (sim.year-is exactly: 2013)
                                                                                               2
                                                                                         )
                                                                                         (when (sim.year-is exactly: 2014)
                                                                                                3
                                                                                         )
                                                                                         (when  (sim.year-is exactly: 2015)
                                                                                                4
                                                                                         )
                                                                                         (when  (sim.year-is exactly: 2016)
                                                                                                5
                                                                                         )))))
                   )

          (policy name: "setup alternative tariff group"

                  (target exposure: (schedule.on-group-entry)
                          name: "change tariffs"
                          action: (do all:true 
                                                 (action.set-tariffs #price_per_unit_increasing_over_time)
                                                 (house.flag tariff-increases-over-time))
                          group: (group.filter (house.region-is London))
                  )))