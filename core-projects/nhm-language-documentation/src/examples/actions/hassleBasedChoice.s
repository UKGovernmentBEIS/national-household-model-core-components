(example
	caption:"Hassle Costs"
	description:"In this example we should how to use a pseudo-cost to influence the behaviour of a dwelling during a choice.
		This pseudo-cost represents some dislike that people have for taking a particular action which influences their decision stated as an amount of money.
		No money actually changes hands, so we will not generate any extra transactions or cause any account balances to change."
	class:"uk.org.cse.nhm.language.definition.action.choices.XChoiceAction"

	(choice
		; The utility function for this choice is net present value + hassle-cost.
		select: (select.minimum 
			(+ 
                (net-cost)
                (future-value (exponential-discount house.annual-cost))
				(def hassle-cost 
					default:0 
					on:Event)))
		
		; Option A: does not modify the house, but yields a hassle-cost of 1000.
		(set #hassle-cost 1000)
			
		; Option B: does not modify the house and causes no hassle. This is the option which will win.
		(action.do-nothing)
	)
)
