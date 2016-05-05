(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

		  (template li []
		  	(measure.loft-insulation resistance: 0.2 thickness: 300))
		  	
		  (template cwi []
		  	(measure.wall-insulation resistance: 0.1 thickness: 50 type: Cavity))

		  (def base 			on:House)
		  (def initial-wall-u 	on:House)
		  (def initial-roof-u 	on:House)
		  (def with-cavity 		on:House)
		  (def with-loft 		on:House)
		  (def with-both		on:House)

          (policy name: insulate

                  (target exposure: (schedule.on-group-entry)
                          name: cavity-and-loft
                          action: 
                          (do all:true update-flags:affected
                          	(set #base (house.energy-use))
                          	(set #initial-wall-u (house.u-value of:Walls))
                          	(set #initial-roof-u (house.u-value of:Roofs))
                          	
							(set #with-cavity 
								(under (cwi)
									evaluate:(house.energy-use)))
							
							(set #with-loft
								(under (li)
									evaluate:(house.energy-use)))
                          
                          	(cwi)
                          	(li)
                          	
                          	(set #with-both (house.energy-use)))
                          
                          group: (group.all)
                  )))