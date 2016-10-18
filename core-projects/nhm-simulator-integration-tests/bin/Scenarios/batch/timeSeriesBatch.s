(batch inputs: (table 
					[$temperature-2013 $temperature-2014]
                   	[18 19]
                   	[19 20]
                   	[20 21]
                   	[21 22])

				scenario: (scenario stock-id: test-small-dto-collection
				          end-date: 01/01/2015
				          start-date: 01/01/2012
				          quantum: 400
				          
			          (report.aggregate name: time-series
			          	mode: (mode.dates 
			          		(on 01/01/2013)
			          		(on 01/01/2014))
			          		
		          		(aggregate.mean name: time-changing-number
				          	(function.time-series initial: 0.0
				          		(on 01/01/2013 $temperature-2013)
				          		(on 01/01/2014 $temperature-2014)))
				)))