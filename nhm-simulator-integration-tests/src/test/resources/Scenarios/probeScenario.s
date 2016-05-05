(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
           (report.group-transitions name: "look at houses"
                                             group: (group.all)

                                             (when  name: true
                                                   (all)
                                             ))

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "Do something and probe it"

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.count  30000000))
                          name: "Destroy houses"
                          action: (action.demolish)
                          group: (group.all)
                  )))