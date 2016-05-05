(example
	caption: "Decalibrating Energy"
	description: "In this example we temporarily undo energy calibration inside an under function.
				For the duration of that under function, only the raw, uncalibrated energy calculator results will be available. 
				Other functions which depends on the energy usage of a house will also use these uncalibrated results.
				This example assumes that we have previously calibrated the energy calculator."
				
	(under
		; This action performs the decalibration.
		(action.decalibrate-energy)
		
		; house.emissions will now return a value based on the raw uncalibrated energy calculator results for the house multiplied by the carbon factors.
		evaluate: (house.emissions)))