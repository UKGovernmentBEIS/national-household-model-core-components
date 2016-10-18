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
                          
                          (action.reset-walls
                          		u-values:(lookup-table
                          					row-keys: [(wall.construction)]
                          					column-key: (wall.insulation-thickness External)
                          			
		                          			[CON 		<=50 		>50]
        		                  			[Sandstone   1			2  ]
        		                  			[SolidBrick  9 			10 ]
        		                  			[*			 11 		12 ])
        		           )
        		    )
        )
 )	
                          			