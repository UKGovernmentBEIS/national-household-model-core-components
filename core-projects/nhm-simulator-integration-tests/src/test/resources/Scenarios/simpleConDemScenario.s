(scenario stock-id: test-survey-cases
          end-date: 01/12/2015
          start-date: 01/01/2013
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "urban test"

                  (target exposure: (schedule.on-group-entry)
                          name: urban
                          action: (action.demolish)
                          group: (group.filter source: (group.all)

                                               (house.morphology-is Urban))
                  )
                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.proportion   0.1))
                          name: Owner-occupied
                          action: (action.demolish)
                          group: (group.filter source: (group.all)

                                               (house.tenure-is OwnerOccupied))
                  )))