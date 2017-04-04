(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400

          (policy name: a

                  (target exposure: (schedule.time-series (expose on: 01/01/2012

                                                                  (sample.proportion   0.25)))
                          name: put-a-on-houses
                          action: (action.do-nothing update-flags: a)
                          group: (group.all)
                  )
                  (target exposure: (schedule.time-series (expose on: 01/01/2012

                                                                  (sample.proportion   0.25)))
                          name: put-b-on-houses
                          action: (action.do-nothing update-flags: b)
                          group: (group.all)
                  )
                  (target exposure: (schedule.time-series (expose on: 01/01/2012

                                                                  (sample.proportion   0.25)))
                          name: put-c-on-houses
                          action: (action.do-nothing update-flags: c)
                          group: (group.all)
                  )
                  (target exposure: (schedule.time-series (expose on: 01/01/2013

                                                                  (sample.proportion   1)))
                          name: go
                          action: (probe name: p
                                         (action.do-nothing test-flags: [a !b]
                                                                    update-flags: [!c affected]
                                                 )
                                  )
                          group: (group.all)
                  )))