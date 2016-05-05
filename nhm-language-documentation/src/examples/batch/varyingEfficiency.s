(example
 caption:"Varying the efficiency of a boiler measure"
 description:"In this example we run a batch of 1000 scenarios, each of which
		will install a boiler measure in a random sample of houses. We will
		vary the efficiency of the boilers to be installed with a triangular distribution, and the
		number of houses to be exposed will be will be drawn uniformly from a set of integers."
 
 (batch
                                        ;restrict the maximum number of scenarios which can be run as part of this batch
  
  inputs: 
  
  (example
   caption: "Using bounded to restrict an infinite number of runs"
   description: "In this example we zip together two random distributions to produce a batch run. 
Each distribution is unbounded (can produce infinite scenarios), so the result of zipping them together is also unbounded. 
Since we cannot run infinite scenarios, we surround the result in a bounded element to restrict it to 1000 runs."
   
   (bounded bound:1000
            
            inputs: (example
             caption: "Using zip to combine multiple inputs"
             description: "In this example we zip together two random distributions to produce a batch run.
For each scenario run, a number will be drawn from each of the distributions and used to fill in the appropriate placeholder in the scenario.
This example would not be a valid batch input by itself because it is unbounded (could produce an infinite number of scenarios).
It would therefore need to be combined with some other inputs or wrapped in a bounded element."
             (zip
              (uniform-integers placeholder:$count start:0 end:10000000)
              (triangular placeholder:$efficiency start:0.6 peak:0.85 end:0.93))))
  
  scenario: (scenario start-date:01/01/2013
                      end-date:01/01/2014
                      quantum:400
                      stock-id:my-stock
                      
                      (on.dates
                      	01/01/2013
                      	
                      	(apply
                      		to: (sample $count (all-houses))
                      		(measure.standard-boiler fuel:MainsGas winter-efficiency:$efficiency)))))))