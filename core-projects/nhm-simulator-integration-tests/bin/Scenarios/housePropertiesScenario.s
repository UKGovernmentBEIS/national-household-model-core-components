(scenario stock-id: test-survey-cases
          end-date: 31/12/2012
          start-date: 01/01/2012
          quantum: 400
          weighting:round

          (policy (target exposure: (schedule.init)
                          name: "build more EHS fuel poverty homes"
                          action: (action.construct)
                          group: (group.filter source: (group.all)

                                               (house.static-property-is name: FPFLGF
                                                                         equal-to: "In FP - full income definition"
                                               ))
                  )))