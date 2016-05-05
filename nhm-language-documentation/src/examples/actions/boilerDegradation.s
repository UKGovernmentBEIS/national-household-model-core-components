(example caption:"Degrading a Boiler"
	description: "This example shows a measure which reduces the efficiency of a boiler to 50% if it currently has efficiency greater than that."
	
	(action.change-efficiency winter-efficiency:0.5 summer-efficiency: -1% direction:Decrease)
)