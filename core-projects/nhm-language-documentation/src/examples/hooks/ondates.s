(example
	caption:"Triggering events on particular dates using a dates hook"
	description:"The on.dates hook lets you modify houses, report on things, or update global values on specific dates."
	
	(on.dates
	
		; the first argument is the list of dates on which to trigger the event
		; you can write literal dates, or expressions for generating date sequences here
	
		[(scenario-start) 01/01/2015 01/01/2016 (scenario-end)]
		
		; subsequent arguments are the things you want to do to the houses on those dates
		; these things will be done in the order written.
	
	
		(example
			caption:"Using apply to apply an action to some houses"
			description:"Apply can be used within on.dates to apply an action to a set of houses"
			
			(apply
				; the action to apply:
				(house.flag hello)
				
				; the houses to apply the action to
				to:(sample 5%)
			)
		)
		
		(example
			caption:"Using aggregate to compute aggregations for some houses"
			description:"Aggregate can be used to compute a set of aggregated values and emit them. In this example, we are asking for
			the mean energy use for the set of houses in London, broken down by houses' built forms."
			
			(aggregate name:my-report
				over:(filter (house.region-is London))
				divide-by:(house.built-form)
				
				(aggregate.mean house.energy-use)
			)
		)
	)
)