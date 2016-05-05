(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
          (policy name: set-insulation
                  (target exposure: (schedule.on-group-entry)
                          name: modifyinsulation
                          action: (do all:true  update-flags: affected
                          						(action.set-wall-insulation 	thickness: 123)
                          						(action.set-loft-insulation 	thickness: 123 	u-value: 2.22)
                          						(action.set-floor-insulation 	thickness: 123	u-value: 2.22)
                          			)
                          group: (group.all)
                  )))