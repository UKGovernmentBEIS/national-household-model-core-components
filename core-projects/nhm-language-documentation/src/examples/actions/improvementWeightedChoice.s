(example
	caption: "Improvement Weighted Choice"
	description: "This example shows a weighted choice which chooses probabilistically between measures based on their emissions reduction.
					For this example, the weight for a particular choice is e^reduction, so a measure which provides a large emissions reduction is much more likely to be picked."
	target: uk.org.cse.nhm.language.definition.action.choices.XChoiceAction
	class: uk.org.cse.nhm.language.definition.sequence.XSequenceAction
					
	(do
		; store the initial emissions of the house
		(set (def emissions-before on:Event) (house.emissions))
					
	(choice
		; we will try applying each of these two options to the house
		(measure.wall-insulation type: Cavity thickness: 50 resistance: 0.1)
		(measure.standard-boiler fuel: MainsGas winter-efficiency: 90)
		
		; we will choose between the options using this weighted selector
		select: (select.weighted
		
			; we're using e^reduction as our weight, heavily biasing the choice in favour of large emissions savings
			weight: (exp
						; reduction = emissions before - emissions now
						(-
							; retrieve the original emissions of the house
							#emissions-before
							; recalculate the emissions after applying each of the options 
							(house.emissions)))))))