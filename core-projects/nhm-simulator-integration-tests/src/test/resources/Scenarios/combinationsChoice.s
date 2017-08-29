(scenario stock-id: test-survey-cases
          end-date: 01/01/2013
          start-date: 01/01/2012
          weighting: round
          quantum: 1000000
          
          (on.dates
        [2012]
        (apply
        	(combination.choice
        		select: (select.minimum (house.energy-use))
            	;;Package 1
            	[(action.do-nothing)]
	
				;;Package 2
            	[
            		(measure.wall-insulation type: Cavity thickness: 50)
            		(measure.install-glazing glazing-type: Triple frame-type: uPVC insulation-type: LowESoftCoat)
            		(action.do-nothing)]
            	
            	;;Package 3
            	[
            		(measure.wall-insulation type: External thickness: 100)
            		(measure.install-glazing glazing-type: Triple frame-type: uPVC insulation-type: LowESoftCoat)
            		(action.do-nothing)])
            )))