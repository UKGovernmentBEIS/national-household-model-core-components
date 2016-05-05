(scenario
	start-date:01/01/2014
	end-date:01/01/2014
	stock-id:asdf
	
	(def global on:Simulation)
	(def local on:House)
	
	(on.dates scenario-start
		(set #global #local)
	)
)