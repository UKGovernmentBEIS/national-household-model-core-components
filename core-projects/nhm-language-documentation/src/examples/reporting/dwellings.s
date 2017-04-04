(example
 caption:"Report on Peak Electric Heat Demand."
 description:"Contains a group which is the houses which use electricity for their main heating fuel.
		Includes a field: the peak heat demand of the dwelling."

	(apply
		to: (filter (house.main-heating-fuel-is Electricity))
		
		(example
			caption: "Probe Peak Electric Heat Demand"
			description: "Probes a dwelling without doing anything to it. Reports on the peak heat demand."
			
			(probe
				capture: [(house.peak-load external-temperature:-5 internal-temperature:19)]
				(action.do-nothing)))))