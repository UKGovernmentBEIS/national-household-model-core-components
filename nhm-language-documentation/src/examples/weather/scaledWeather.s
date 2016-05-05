(example
	caption: "Scaled Weather"
	description: "An example showing how to modify the weather in bulk using the weather.perturb element."
	
		(weather.perturb
			; Decrease all insolation by 10%.
			scale-insolation: -10%
			
			; Increase all temperatures by 1 degree.
			offset-temperature: 1
		
			; The weather before perturbation. We could alternatively have put a weather.case, or even another weather.perturb here instead.
			(weather
				; windspeed, insolation and temperature each contain 12 numbers corresponding to the 12 months from January to December. 
				windspeed: [6 5 4 3 2 1 1 2 3 4 5 6]
				insolation: [20, 60, 100, 140, 180, 220, 220, 180, 140, 100, 60, 20]
				temperature: [4, 6, 8, 10, 12, 14, 14, 12, 10, 8, 6, 4]
			))
	)