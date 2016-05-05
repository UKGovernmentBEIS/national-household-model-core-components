(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
          
          (def  test-register on:House)
          
          (policy 
          		(target	name:ongastest 
          			group: (group.all)
          			exposure: (schedule.on-group-entry)
          			action: (set #test-register
          					(lookup-table
          						row-keys:[(house.is-on-gas) (house.region)]
          						column-key:(house.buildyear)
          						
          						[ONGAS  REGION   <1950     >=1950]
          						[true   London	 1			2	 ]
          						[false  London	 3			4	 ]
          						[*		 *		 5			6	 ]
							)
						)
				)
			)
)