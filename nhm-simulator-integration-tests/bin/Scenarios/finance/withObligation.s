(scenario
	start-date: 01/01/2010
	end-date: 01/01/2020
	stock-id: test-survey-cases
	
(report.aggregate name: house-money
	mode: (mode.dates (scenario-end))

	(aggregate.mean
		name: average-obligated-spend
		(house.sum-transactions counterparty: mr-moneybags))
)
	
	(policy
		name: obligate-houses
		
		(target
			name: steal-some-cash
			group: (group.all)
			exposure: (schedule.on-group-entry)
			
			action: (finance.with-obligation
				(action.do-nothing)
				tags: [some tags]
				counterparty: mr-moneybags
				amount: 1
				schedule: (periodic-payment
					interval: "1 year"
					lifetime: "2 years")))))