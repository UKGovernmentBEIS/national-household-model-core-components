(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
          (policy 
          		(target	name:haslofttest 
          			group: (group.filter (house.has-loft))
          			exposure: (schedule.on-group-entry)
          			action: (house.flag has-loft-boom)
          )))
