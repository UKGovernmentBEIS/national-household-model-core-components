(example
	caption:"Varying the efficiency of a boiler measure"
	description:"In this example we run a batch of scenarios
		deterministically. Each run of a scenario will take its fuel from one
		of the four rows of the table [MainsGas, Oil, BulkLPG, HouseCoal], and its efficiency from the range
		[0.60, 0.61, 0.62, ..., 0.89]. This batch will cover all possible combinations of these two variables exactly once."
	
	(batch
   inputs: 
  
   (example
     caption: "Using cross to explore combinations"
     description: "This example shows two different batch inputs being combined into one table containing all the different possible combinations."
   (cross
     
     (example
       caption: "Varying efficiency within a range"
       description: "This example should a range batch inputs which will produce the values [0.60, 0.61, 0.62, ..., 0.88, 0.89].
Each of these values will be inserted into the scenario whenever $efficiency is specified."
       
       (range placeholder:$efficiency start:0.6 end:0.9 step:0.01))
					
     (example
       caption:"A table of fuel types"
       description:"In this example we create a table which will assign each of the values [MainsGas, Oil, BulkLPG, HouseCoal] in turn to the $fuel variable."
						
       (table
         [$fuel]
         [MainsGas]
         [Oil]
         [BulkLPG]
         [HouseCoal]))))
   
		scenario: (scenario
						start-date:01/01/2013
						end-date:31/12/2013
						quantum:400
						stock-id:my-stock
						;report on the energy use of house in the scenario, summed over all of the houses and then integrated across the duration of the scenario
						(report.aggregate name:"cumulative national energy use"
							mode: (mode.integral)
							division: (division.by-group (group.all))
							
							(aggregate.sum (house.energy-use)))
							
						(policy
							(target name:"install boiler"
									group:(group.all)
									exposure:(schedule.on-group-entry)
									action:(measure.standard-boiler fuel:$fuel winter-efficiency:$efficiency)))))

	
	additional: "
		<formalpara xmlns=\"http://docbook.org/ns/docbook\">
	        <title>
	            Sample batch output</title>
	        <para>
	            This is how the table produced by the batch would look.
	        </para>
	    </formalpara>
	    <informaltable xmlns=\"http://docbook.org/ns/docbook\">
	        <tgroup cols=\"3\">
	            <thead>
	                <row>
	                    <entry>$efficiency</entry>
	                    <entry>$fuel</entry>
	                    <entry>cumulative national energy use</entry>
	                </row>
	            </thead>
	            <tbody>
	                <row>
	                    <entry>0.6</entry>
	                    <entry>MainsGas</entry>
	                    <entry>500000000000</entry>
	                </row>
	                <row>
	                    <entry>0.6</entry>
	                    <entry>Oil</entry>
	                    <entry>500000000000</entry>
	                </row>
	                <row>
	                    <entry>0.6</entry>
	                    <entry>BulkLPG</entry>
	                    <entry>500000000000</entry>
	                </row>
	                <row>
	                    <entry>0.6</entry>
	                    <entry>HouseCoal</entry>
	                    <entry>500000000000</entry>
	                </row>
	                <row>
	                    <entry>0.61</entry>
	                    <entry>MainsGas</entry>
	                    <entry>495000000000</entry>
	                </row>
	                <row>
	                    <entry>...</entry>
	                    <entry>...</entry>
	                    <entry>...</entry>
	                </row>
	                <row>
	                    <entry>0.89</entry>
	                    <entry>HouseCoal</entry>
	                    <entry>400000000000</entry>
	                </row>
	            </tbody>
	        </tgroup>
	    </informaltable>"
)
