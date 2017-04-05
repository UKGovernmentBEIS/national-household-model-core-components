(scenario
	start-date: 01/01/2014
	end-date: 01/01/2015
	stock-id: stock
	
	(on.construction
        (apply
            to: (affected-houses)
            (measure.low-energy-lighting
				update-flags: better-lighting
				capex: (* 1 (size.m2))))))
	
	;;(policy
	;;	(target
	;;		name: install-lighting
	;;		group: (group.filter
	;;			(house.region-is London))
	;;		exposure: (schedule.init)
	;;		action: (measure.low-energy-lighting
	;;			update-flags: better-lighting
	;;			capex: (* 1 (size.m2)))
	;;	)))