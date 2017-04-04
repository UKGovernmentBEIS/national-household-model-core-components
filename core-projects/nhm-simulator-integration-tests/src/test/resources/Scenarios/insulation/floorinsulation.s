(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
		  
		  (policy name:insulate
				  (target 
				   name:solidfloor
				   exposure:(schedule.init)
				   group:(group.all)
				   action:(measure.floor-insulation
						   type:Solid
						   update-flags:solid-insulated
						   resistance:0.5
						   thickness:20
						   capex:1000))
				  (target
				   name:suspendedfloor
				   exposure:(schedule.init)
				   group:(group.all)
				   action:(measure.floor-insulation
						   type:Suspended
						   update-flags:suspended-insulated
						   u-value:1234
						   thickness:20
						   capex:1000))))
