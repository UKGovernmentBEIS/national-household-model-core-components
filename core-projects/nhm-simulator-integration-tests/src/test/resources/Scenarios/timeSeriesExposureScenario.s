(scenario stock-id: test-survey-cases
          end-date: 31/12/2016
          start-date: 01/01/2012
          quantum: 800
          weighting:round

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "intall DH"

                  (target exposure: (schedule.time-series
                                                          (expose on: 01/01/2013

                                                                  (sample.count  80000))
                                                          (expose on: 01/01/2014

                                                                  (sample.count  240000))
                                                          (expose on: 01/01/2015

                                                                  (sample.count  320000)))
                          name: "ping some houses"
                          action: (debug/probe name: ping)
                          group: (group.all)
                  )))