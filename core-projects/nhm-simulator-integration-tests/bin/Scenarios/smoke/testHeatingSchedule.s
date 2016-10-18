(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400
           (debug/assert aggregation: (aggregate.sum (house.energy-use))
                                 continuous: false
                                 name: "Energy use should go up lots"
                                 group: (group.all)
                                 type: Increasing
                   )

           (include href: ../common/all-weather.s)
          (policy name: "turn on heating all week"

                  (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: "heating all week"
                          action: (action.set-heating-schedule name: constantheating

                                                               (schedule on: AllDays

                                                                         (heating between: 0
                                                                                  and: 24
                                                                         )))
                          group: (group.all)
                  )))