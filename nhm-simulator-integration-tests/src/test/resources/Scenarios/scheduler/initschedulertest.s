(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (policy (target exposure: (schedule.init)
                          name: "set at start"
                          action: (house.flag scheduled-init-boom)
                          group: (group.all)
                  )))
 