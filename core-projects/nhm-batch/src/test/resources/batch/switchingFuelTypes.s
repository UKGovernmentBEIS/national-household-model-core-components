(batch inputs: 
	(cross
		(range start: 0.6
		       placeholder: $efficiency
		       end: 0.9
		       step: 0.01
                       )
		(table [$fuel]
		       [MainsGas]
		       [Oil]
		       [BulkLPG]
		       [HouseCoal])
   	)
       
       scenario: (scenario stock-id: my-stock
			   end-date: 31/12/2013
			   start-date: 01/01/2013
			   quantum: 400

			   (policy (target exposure: (schedule.on-group-entry)
					   action: (measure.standard-boiler fuel: $fuel
									    winter-efficiency: $efficiency
									    )
					   group: (group.all)
					   ))))