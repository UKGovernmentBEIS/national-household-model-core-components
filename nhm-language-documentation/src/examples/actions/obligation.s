(example
	caption: "An obligation to pay a percentage of the capex."
	description: "In this example, we will install a boiler and finance it fully with a subsidy. 
		However, the household will enter into an obligation to pay half a percent of the capital cost, forever."
	class: uk.org.cse.nhm.language.definition.sequence.XSequenceAction
	target: uk.org.cse.nhm.language.definition.money.obligations.XObligationAction;
	
	(do
		; the thing to do before entering into the obligation
		(finance.fully
            (measure.standard-boiler fuel:MainsGas winter-efficiency:85%))
			
		(set (def cost-capex on:Event) (capital-cost))
		
		(finance.with-obligation
			; we use amount-vars to capture capital-cost from the action, so that amount
			; can use it.
			
			; amount is worked out every time a payment is made, which is why
			; we had to set cost-capex at the moment when the boiler was
			; installed.
			amount: (* 0.5% #cost-capex)
			
			; the obligation should occur every year
			schedule: (periodic-payment interval:"1 year"))
	)
)
