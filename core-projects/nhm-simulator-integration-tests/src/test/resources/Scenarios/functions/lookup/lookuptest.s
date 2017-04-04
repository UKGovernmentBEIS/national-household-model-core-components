(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
          
          (def test-register on:House)
          
          (policy 
          		(target	name:ongastest 
          			group: (group.all)
          			exposure: (schedule.on-group-entry)
          			action: (set #test-register
	          					(lookup
	          						keys:[(house.is-on-gas) (house.region) (house.buildyear)]
	          						(entry [true  London  *] 10)
	          						(entry [false *	  <1950] 20)
	          						(entry [*     *       *] 30)))
					)
				)
				
				
)