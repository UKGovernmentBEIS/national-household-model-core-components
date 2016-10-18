(example
	caption:"Setting the carbon factor of gas fuels and oil"
	description:"In this example, we have set the carbon factors of all the gas fuels together by putting them into a single group. Oil is in another group, and has a different carbon factor. All other fuels will have the default carbon factor of zero."
	
	(context.carbon-factors
		(group
			fuels: [MainsGas BulkLPG BottledLPG]
			carbon-factor: 1)
		(group fuels: Oil
			carbon-factor: 2)))