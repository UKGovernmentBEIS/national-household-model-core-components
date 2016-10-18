(example
	caption:"Detailed Energy Breakdown"
	description:"This example shows how to report on energy use broken down by the fuel type used and the service which was powered.
		It will output a row for a dwelling each time that dwelling's energy-use changes."
		
	(probe
		capture: [
	
			; Columns for each of the services for mains gas.
			(house.energy-use by-fuel:MainsGas by-service:Cooking)
			(house.energy-use by-fuel:MainsGas by-service:Appliances)
			(house.energy-use by-fuel:MainsGas by-service:SpaceHeating)
			(house.energy-use by-fuel:MainsGas by-service:WaterHeating)
			
			; Columns for each of the services for electricity.
			(house.energy-use by-fuel:Electricity by-service:Cooking)
			(house.energy-use by-fuel:Electricity by-service:Lighting)
			(house.energy-use by-fuel:Electricity by-service:Appliances)
			(house.energy-use by-fuel:Electricity by-service:SpaceHeating)
			(house.energy-use by-fuel:Electricity by-service:WaterHeating)
			
			; Remaining combinations of fuel and service omitted for brevity.
		]
	)
)