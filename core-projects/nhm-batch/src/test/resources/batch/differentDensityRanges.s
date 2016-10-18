(batch inputs: (concat
		(range start: 15
		       placeholder: $t
		       end: 19
		       step: 1
		       )
		(range start: 19
		       placeholder: $t
		       end: 22.5
		       step: 0.5
		       ))
       
       scenario: (scenario stock-id: my-stock
			   end-date: 31/12/2013
			   start-date: 01/01/2013
			   quantum: 400

			   (policy (target exposure: (schedule.on-group-entry)
					   action: (action.set-heating-temperatures livingAreaTemperature: $t)
					   group: (group.all)
					   ))))