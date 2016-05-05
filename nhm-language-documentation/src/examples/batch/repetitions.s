(example
  caption: "Using repetitions to run a scenario many times with different seeds"
  description: "In this example we will run a scenario which has some randomness inherent to it.
                We will use the repetitions element around a table with 4 rows to run it 25 * 4 = 100 times, each time with a different seed.
                This will enable us to investigate the variability in results caused by the randomness."
  
  (batch
    inputs:
    (example
      caption: "Introducing variability with repetitions"
      description: "This example shows how to use repetitions to examine the variability of a scenario which has some randomness.
It will run the scenario 25 * 4 = 100 times, 25 times for each of the values in the input inside it.
Each time a scenario is run, the repetitions element will set the value of the $seed placeholder anywhere it is written in the scenario.
"
      (repetitions count: 25
                   (table 
                     [$p]
                     [10%]
                     [20%]
                     [30%]
                     [40%]
                     )))
  
  scenario: (scenario
              ; The $seed is placeholder is automatically filled by the repetitions element.  
              seed: $seed
              start-date: "01/01/2013"
              end-date: "01/01/2014"
              quantum: 400
              stock-id: my-stock
              
              (on.dates
                (scenario-start)
                
                (apply
                    ; Each house in the simulation will have a $p chance of exposure.    
                    to: (sample $p)
                    (house.flag flagged))))))