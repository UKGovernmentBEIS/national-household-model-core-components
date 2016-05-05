(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (policy name: modifywalltype

                  (target exposure: (schedule.on-group-entry)
                          name: modifywalltype
                          action: (action.set-wall-construction wall-type: Cavity)
                          group: (group.all)
                  )))