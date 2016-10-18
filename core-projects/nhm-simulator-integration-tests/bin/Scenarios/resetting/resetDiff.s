(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
	
		  (include href:resetToSap.s)
		  
		  (def-snapshot before)
		  (def ignore)
		  
		  (policy
		  	(target name:a
		  			group:(group.all)
		  			exposure:(schedule.on-group-entry)
		  			
		  			action:
		  				(do
		  					(take-snapshot #before)
		  					(reset-floors-to-sap)
		  					(set #ignore (debug/function name:diff delegate:0))
		  				)
		  		)
		  	)
)