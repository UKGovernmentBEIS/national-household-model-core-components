(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (policy name: reset

                  (target 
                  		
                  		group:(group.all)
                  		exposure: (schedule.on-group-entry)
                          name: reset-some-walls
                          action:
                          
                          (action.reset-doors
                          		u-values:(lookup-table
                          					row-keys: [(door.type)]
                          					
		                          			[DOOR 		uvalue]
											[Glazed		0.9	  ]
											[Solid		1.9	  ])

								areas:(lookup-table
											row-keys: [(door.type)]
											
											[DOOR	area]
											[Glazed 10	]
											[Solid	11	])
        		           )
        		    )
        )
 )	
                          			