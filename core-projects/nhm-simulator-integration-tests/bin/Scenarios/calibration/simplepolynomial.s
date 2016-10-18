(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 01/01/2014
          quantum: 400

	(context.calibration
		(polynomial 
			fuels: [MainsGas PeakElectricity OffPeakElectricity]
			1 2 3 4)))