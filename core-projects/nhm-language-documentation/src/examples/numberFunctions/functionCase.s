(example
	caption: "Using function.case to conditionally produce numbers."
	description: "This example shows how we can use function.case to 
		return different numbers depending on the condition of a house.
		In this example, all bungalows will get the number 200. 
		Non-bungalows in London will get 1000. 
		All other dwellings will get 500."
	
	(function.case
		(when
			(house.built-form-is Bungalow)
			200)
		(when
			(house.region-is London)
			1000)

		default: 500
	))