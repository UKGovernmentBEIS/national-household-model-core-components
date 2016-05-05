(template tariff-part [@fuel @price]
    	(fuel type: @fuel
    		(charge (* (house.meter-reading) (number @price)))))

(context.tariffs
	defaults: [
		(tariff name: Electricity-Tariff
			(tariff-part fuel: PeakElectricity 			price: 0.14)
			(tariff-part fuel: OffPeakElectricity 		price: 0.07))
			
		(tariff name: MainsGas-Tariff
			(tariff-part fuel: MainsGas price:0.045))
		
		(tariff name: Oil-Tariff
			(tariff-part fuel:Oil price:0.06))
		
		(tariff name: LPG-Tariff
			(tariff-part fuel:BulkLPG price:0.075)
			(tariff-part fuel:BottledLPG price:0.075))
		
		(tariff name: Biomass-Tariff
			(tariff-part fuel:BiomassPellets price:0.04)
			(tariff-part fuel:BiomassWood price:0.04)
			(tariff-part fuel:BiomassWoodchip price:0.03))
	]
)