(example
	caption:"Using on.change to report on affected houses"
	description:"The on.change element lets you run an action or generate a report whenever something in the simulation changes some houses.
	In this example, whenever a house is affected its survey code and energy use will be output into the specified probe."
	
	(on.change
		(apply (probe name:affected capture:house.survey-code) to:affected-houses)
	)
)