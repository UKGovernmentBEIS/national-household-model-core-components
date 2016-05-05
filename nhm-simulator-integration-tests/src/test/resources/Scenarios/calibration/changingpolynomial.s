(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 01/01/2014
          quantum: 400

	(context.calibration
		(polynomial 
			fuels: MainsGas
			0 
			(function.case 
			   default:1 
			   (when (house.flags-match affected) 2))))
			
	(policy
		(target name:mytarget
			group:(group.all)
			exposure:(schedule.on-group-entry delay:"1 year")
			action:(action.do-nothing update-flags:affected))))