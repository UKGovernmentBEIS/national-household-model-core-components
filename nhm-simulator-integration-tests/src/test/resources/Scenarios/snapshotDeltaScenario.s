(scenario stock-id: test-survey-cases
          end-date: 31/12/2014
          start-date: 01/01/2012
          quantum: 400

	(def-snapshot before-choice)
	(def-snapshot after-choice)
	
	(def value on:House)
	(def delta on:Event)
	(def variable on:House default:0)
	
	(policy 
		(target 
			name: "put delta into register"
			group: (group.all)
			exposure: (schedule.on-group-entry)
                          
          	action: 
          		(do
          			(take-snapshot #before-choice)
          			
          			(choice select:
          				(select.minimum
          					do-first:(take-snapshot #after-choice)
	          				
	          				(do
          						(set #delta 
          							(snapshot.delta 
          								before:#before-choice 
          								after:#after-choice 
          								#variable))
          						
          						return:#delta))
          				
          				(set #variable 1)
          			)
          				
          			(set #value #delta)
          		)
          	)
          )
        )