(scenario
	start-date: 01/01/2014
	end-date: 02/01/2014
	stock-id: test-stock
	
	(policy
		(target
			name: install-pv
			group: (group.all)
			exposure: (schedule.init)
			action: (measure.solar-photovoltaic
						update-flags: solar-panels
						capex: 10000
						efficiency: 20%
						own-use-proportion:100%
						roof-coverage: 30%))))