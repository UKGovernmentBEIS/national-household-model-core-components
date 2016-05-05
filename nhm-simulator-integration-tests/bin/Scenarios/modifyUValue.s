(scenario stock-id: test-survey-cases
          end-date: 01/01/2015
          start-date: 01/01/2013
          quantum: 400

		  (def uvalue on:House)

          (policy name: a

                  (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: b
                          action: (do all:true
                          			(action.set-wall-u u-value:99)
                          			(set #uvalue (house.u-value of:Walls)) 
                          		)
                          
                          group: (group.all)
                  )))