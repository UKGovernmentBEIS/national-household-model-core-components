(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (include href: common/technologyCostsSpecifiedByDECC.s)
          (policy (target exposure: (schedule.on-group-entry)
                          name: "install standard boiler with hassle cost"
                          action: (probe name: "costs probe"
                                         (measure.with-cost tags: installation
                                                                    counterparty: boiler-company
                                                                    cost: (number 1.0)
                                                                    
                                                                    (measure.with-cost tags: hassle
                                                                                               counterparty: time
                                                                                               cost: (function.quadratic b: 1
                                                                                                                         c: 0
                                                                                                                         a: 0
                                                                                                                         x: (cost.capex)
                                                                                                     )
                                                                                                     
                                                                                               (measure.decc.standard-boiler capex: (number 1)
                                                                                                                                efficiency: 2.5
                                                                                                                                fuel: MainsGas
                                                                                                       )
                                                                            )
                                                 )
                                  )
                          group: (group.all)
                  )))