(scenario
	start-date: 01/01/2012
	end-date: 01/01/2013
	stock-id: test-survey-cases
	
	(def number on:Event)
	
	(policy
		(target
			name: flag-houses-based-on-morphology
			
			group: (group.all)
			exposure: (schedule.on-group-entry)
			
			action: (choice
				select: (select.fallback
					
					; preferred
					(select.filter
						test: (house.morphology-is Urban)
						selector: (select.minimum (get #number)))
					
					; fallback
					(select.maximum (get #number)))
				
				(do
					(house.flag urban)
					(set #number 0))
				
				(do
					(house.flag not-urban)
					(set #number 1))
				
				))))