(example
	caption: "Weather by Region"
	description: "Demonstrates how to assign different weather to different regions."
	
	(context.weather
		; weather.case allows us to assign weather to houses based on some criteria that we decide
		(weather.case
			(when
				; test whether a house is in Wales
				(house.region-is Wales) 
				; if the house was in Wales, it will experience this weather
				(weather
					windspeed: [10 10 10 10 10 10 10 10 10 10 10 10]
					insolation: [20 20 20 20 20 20 20 20 20 20 20 20]
					temperature: [5 5 5 5 5 5 5 5 5 5 5 5]))
					
			(when 
				; test whether a house is in the South West
				(house.region-is SouthWest)
				; if the house was in the South West, it will experience this weather 
				(weather
					windspeed: [5 5 5 5 5 5 5 5 5 5 5 5]
					insolation: [30 30 30 30 30 30 30 30 30 30 30 30]
					temperature: [10 10 10 10 10 10 10 10 10 10 10 10]))
			
			; weather.case always needs a default, which will take effect if none of the when clauses passed
			; in this example, all of the regions which weren't mentioned inside a when clause will get the default weather
			default: (weather
				windspeed: [6 5 4 3 2 1 1 2 3 4 5 6]
				insolation: [20, 60, 100, 140, 180, 220, 220, 180, 140, 100, 60, 20]
				temperature: [4, 6, 8, 10, 12, 14, 14, 12, 10, 8, 6, 4])			
)))