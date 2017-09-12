(scenario
	start-date: 01/01/2014
	end-date: 01/01/2015
	stock-id: stock
	
	(policy
		(target
			name: install-lighting
			group: (group.filter
				(house.region-is London))
			exposure: (schedule.init)
			action: (measure.low-energy-lighting
				proportion-cfl: 0
				propotionOfHALL: 0
				proportionOfLED: 0
				proportionOfIcandescent: 1)
		)))