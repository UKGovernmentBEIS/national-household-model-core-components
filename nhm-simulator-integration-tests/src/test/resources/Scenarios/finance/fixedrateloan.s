(scenario stock-id: test-survey-cases
          end-date: 31/12/2016
          start-date: 01/01/2012
          quantum: 400

          (policy name: jim

                  (target exposure: (schedule.on-group-entry)
                          name: t
                          action: (finance.with-loan rate: 5%
                                                     principal: (number 1000)
                                                     term: 1
                                                     (house.flag myflag)
                                  )
                          group: (group.filter (house.region-is London))
                  )))