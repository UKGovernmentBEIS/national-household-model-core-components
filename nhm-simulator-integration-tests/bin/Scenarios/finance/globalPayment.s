(scenario
	start-date: 01/01/2013
	end-date: 01/01/2015
	stock-id: my-stock
	
	(on.dates
		(regularly)
		
		(pay
			(+ 10000 (account.balance account: government-policies))
			from: general-taxation
			to: government-policies
			tags: taxation))
			
	(report.transactions name: transactions))
			