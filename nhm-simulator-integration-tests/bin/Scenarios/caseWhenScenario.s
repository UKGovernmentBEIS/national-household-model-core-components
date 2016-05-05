(scenario stock-id: test-survey-cases
          end-date: 31/12/2011
          start-date: 01/01/2011
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "case when test"

                  (case default: (otherwise exposure: (schedule.repeat interval: "1 year"

                                                                       (sample.proportion   1))
                                            name: "Catch everything"
                                            action: (debug/probe name: catch)
                                 )
                        cases: (case exposure: (schedule.repeat interval: "1 year"

                                                                (sample.proportion   1))
                                     test: (any)
                                     name: "Ignore everything"
                                     action: (debug/probe name: ignore)
                               )
                        source: (group.all)
                  )
                  (case default: (otherwise exposure: (schedule.repeat interval: "1 year"

                                                                       (sample.proportion   1))
                                            name: "Catch everything"
                                            action: (debug/probe name: "nothing left")
                                 )
                        cases: (case exposure: (schedule.repeat interval: "1 year"

                                                                (sample.proportion   1))
                                     test: (all)
                                     name: "Catch everything first"
                                     action: (debug/probe name: "catch first")
                               )
                        source: (group.all)
                  )))