(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: a

                  (target exposure: (schedule.on-group-entry)
                          name: b
                          action: (probe name: stuff
                                         (finance.with-loan rate: 0
                                                            principal: (number 2000)
                                                            term: 2
                                                            
                                                            (action.do-nothing)
										  )
                                  )
                          group: (group.all)
                  )))