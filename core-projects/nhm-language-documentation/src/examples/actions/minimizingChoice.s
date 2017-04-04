(example
	caption:"Minimizing NPV"
	description: "By using a discounted future cost together with the current net cost
		this example constructs a choice which will yield whichever
		of the measures within it has least present cost."
	class:"uk.org.cse.nhm.language.definition.action.choices.XChoiceAction"

	(choice
		; using select.minimum with npv
		select: (select.minimum (+ net-cost (future-value (exponential-discount (house.annual-cost)))))
		
		; the options to minimize over
		(measure.wall-insulation type:Cavity)
		; more measures can go here
		
		; to allow nothing to happen, use action.do-nothing
		(action.do-nothing)
	)
)
