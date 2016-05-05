(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 31/12/2013
          quantum: 400
          
          (report.aggregate name:my-report
          	(aggregate.mean (inflate rate:10% 1))))