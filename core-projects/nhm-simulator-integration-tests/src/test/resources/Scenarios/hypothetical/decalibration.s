(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 01/01/2014
          quantum: 400

	(context.calibration
		(replace 
			fuels: MainsGas
			99))
	
	(report.aggregate
		name:blah
		
			
		(aggregate.sum (debug/function		
			name:is-99-without-decal
			delegate:(house.energy-use by-fuel:MainsGas)
		))
		
		(aggregate.sum (debug/function		
			name:is-not-99-with-decal
			delegate:(under
						(action.decalibrate-energy)
						evaluate:(house.energy-use by-fuel:MainsGas)
		))
		)
	
	)
)