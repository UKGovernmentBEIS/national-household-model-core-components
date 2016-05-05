(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          quantum: 400
          
          (report.fuel-costs)

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          
          (policy name: Demolition

                  (target exposure: (schedule.time-series (expose on: 01/07/2012

                                                                  (sample.count  10000)))
                          name: "demolish all houses"
                          action: (action.demolish)
                          group: (group.all)
                  )))