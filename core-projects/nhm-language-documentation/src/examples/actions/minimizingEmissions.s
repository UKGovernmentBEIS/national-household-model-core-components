(example
	caption:"Minimizing Emissions"
	description:"In this example the choice is trying to minimize
		house.emissions; the measure under which the house has least total
		emissions will be the winner."
	class:"uk.org.cse.nhm.language.definition.action.choices.XChoiceAction"
		
	(choice
		;the function to choose by - in this case it is minimize house.emissions
		select:(select.minimum (house.emissions))
		
		; the options over which to minimize are the remaining arguments
		(measure.wall-insulation type:Cavity)
	)
)