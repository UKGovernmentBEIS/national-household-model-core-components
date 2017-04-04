(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 01/01/2014
          quantum: 400

	(def uncal-energy on:Event)

	(context.calibration
		(replace 
			fuels: MainsGas
			0))
	
	(report.aggregate
		name:blah
		
		(aggregate.sum
			(do
				(set #uncal-energy (house.energy-use by-fuel:MainsGas calibrated:false))
				
				return:(debug/function		
					name:carbon-is-not-zero
					delegate:(under (action.decalibrate-energy)
									evaluate:
										(house.emissions by-fuel:MainsGas))
				)
			)
		)
	)
)