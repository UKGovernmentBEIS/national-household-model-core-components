(example
	caption:"Tariff with Standing Charge and Tax"
	description:"A tariff is comprised of one or more elements for each
		type of fuel it provides prices for. Each fuel in term contains one or
		more charges which are paid by the householder each year."
		
	(tariff
		name:"Gas with standing charge"
		
		(fuel
			type:MainsGas
			
			; 100 pound standing charge due to the energy company
			(charge 100)
			
			; unit-rate (7p per kWh) due to the energy company
			(charge
				(example
					caption:"Multiplying two numbers together"
					description:"This function will work out 0.07 times the house's current meter reading. You could use this in, for example, a tariff definition."
					
					(* (house.meter-reading) 0.07)))
					
			; taxation paid as a proportion of the total cost so far, due to HMRC
			(charge payee:HMRC
				(* (net-cost) 10%)))))
