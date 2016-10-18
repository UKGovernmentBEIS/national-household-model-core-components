(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
          (policy 
          		(target	name:hascentralheatingtest 
          			group: (group.filter (house.has-central-heating include-broken: true))
          			exposure: (schedule.on-group-entry)
          			action: (house.flag does-have-central-heating)
          )))
