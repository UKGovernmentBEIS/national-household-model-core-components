(example
 caption:"Rounding peak heat load"
 description:"In this example we use the step function to round up the
		heat load. Given a house where the peak load produces 11kW, this
		function will produce 20kW."

 (function.steps round:up
				 steps:[5 10 20]
				 value: (house.peak-load external-temperature:-5 internal-temperature:19)))
