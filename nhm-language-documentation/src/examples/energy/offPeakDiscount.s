(example
	caption: "Discounting Off-Peak Electricity Payments"
	description: "Subsidies a house for 10% of their off-peak electricity bill."

	(action.extra-fuel-charge
		    	(extra-charge
		    		 fuel: OffPeakElectricity
				     name: "Off Peak Discount"
				     tags: [off-peak-electric-subsidy]
				     (* (net-cost) -10%))))
