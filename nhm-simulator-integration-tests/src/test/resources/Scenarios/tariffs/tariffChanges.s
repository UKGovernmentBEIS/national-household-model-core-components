(scenario stock-id: test-survey-cases
          end-date: 31/12/2016
          start-date: 01/01/2012
          quantum: 400
           (debug/assert aggregation: (aggregate.count)
                                    name: "ignored tariff unaffected"
                                    group: (group.filter (house.is-on-tariff #ignored))
                                    type: Unchanging
                      )(context.tariffs defaults: [(tariff name: replaced

                                                            (fuel type: MainsGas

                                                                  (charge 0)))
                                                                 (tariff name: ignored

                                                            (fuel type: Oil

                                                                  (charge 0)))]
                                       others: (tariff name: replacement

                                                       (fuel type: MainsGas

                                                             (charge 0)))
                      )(debug/assert aggregation: (aggregate.count)
                                    name: "replacement tariff increases"
                                    group: (group.filter (house.is-on-tariff #replacement))
                                    type: Increasing
                      )(debug/assert aggregation: (aggregate.count)
                                    name: "replaced tariff decreases"
                                    group: (group.filter (house.is-on-tariff #replaced))
                                    type: Decreasing
                      )

          (policy name: "change tariffs"

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.proportion   0.05))
                          name: "change tariffs"
                          action: (action.set-tariffs #replacement)
                          group: (group.all)
                  )))