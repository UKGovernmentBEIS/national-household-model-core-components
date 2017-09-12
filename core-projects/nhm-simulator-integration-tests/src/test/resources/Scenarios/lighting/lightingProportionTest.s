(scenario
	start-date: 01/01/2014
	end-date: 01/01/2015
	stock-id: stock
	
	 (def-report report
                (column name:incandescent-light-proportion value: (house.lighting-proportion minEfficiency: 6.8 maxEfficiency: 6.9)))
	
	 (on.construction
        (apply
            to: (affected-houses)
            (measure.lighting
            	proportion-cfl: 0.25
            	proportion-incandescent: 0.25
            	proportion-hal: 0.25
            	proportion-lef: 0.25))))
            	
