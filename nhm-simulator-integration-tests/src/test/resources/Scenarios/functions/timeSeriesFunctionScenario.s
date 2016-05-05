(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
           (report.aggregate 
       			name: "Time Series Function Report"
            	division: (division.by-group (group.all))
                (aggregate.mean 
                	(function.time-series initial: 0
						(on 01/06/2012 1)
                       	(on 01/01/2013 10)))))