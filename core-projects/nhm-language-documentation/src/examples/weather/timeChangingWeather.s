(example
	caption: "Time-Changing Weather"
	description: "An example showing how to modify the weather over time."
	
	(context.weather
		; weather.case specifies a series of tests and the weather which should be applied if they are met. 
		(weather.case
			
			; This first test sets the temperature to be a degree higher than #my-weather from 2100 onwards. 
			(when
				(sim.year-is above: 2099) 
				(weather.perturb
					offset-temperature: 1
					; #my-weather is a cross reference to the weather named "my-weather".
					#my-weather))

			; If the year is before 2100, this second test will have a chance to take effect.
			; It sets the temperature to be half a degree higher than #my-weather default from 2050 onwards.
			(when
				(sim.year-is above: 2049)

				(example
					caption: "Increasing the Temperature"
					description: "This examples shows how we can take some weather that we have written and use weather.perturb to modify it.
									There must exist elsewhere in our scenario a weather, weather.case or weather.peturb element with 'name: my-weather' for this example to work."
					
					(weather.perturb
						offset-temperature: 0.5
						; #my-weather is a cross reference to the weather which has the name "my-weather".
						#my-weather)))					

			; If the year is before 2050, neither of the above tests will have taken effect. We will instead apply this default weather.
			default: (weather
				name: my-weather
				windspeed: [6 5 4 3 2 1 1 2 3 4 5 6]
				insolation: [20, 60, 100, 140, 180, 220, 220, 180, 140, 100, 60, 20]
				temperature: [4, 6, 8, 10, 12, 14, 14, 12, 10, 8, 6, 4])
		)
	))