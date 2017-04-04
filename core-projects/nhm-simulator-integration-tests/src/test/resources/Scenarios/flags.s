(scenario stock-id: test-survey-cases
          end-date: 01/01/2050
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: cycle-flags

                  (case default: (otherwise exposure: (schedule.on-group-entry)
                                            name: otherwises
                                            action: (house.flag not-in-london)
                                 )
                        cases: (case exposure: (schedule.on-group-entry)
                                     test: (house.region-is London)
                                     name: "flag 1s"
                                     action: (house.flag is-in-london)
                               )
                        source: (group.all)
                  )))