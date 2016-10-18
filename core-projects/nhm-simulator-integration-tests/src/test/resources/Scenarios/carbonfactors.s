(no-include
    (scenario
        start-date:01/01/2013
        end-date:01/01/2030
        stock-id:UnitedKingdomStock_ver2
        
        (def cf-2014 on:Simulation)
        (def cf-2029 on:Simulation)
        
        (carbon-factors/factors)
        
        (on.dates [01/01/2014] 
        	(set #cf-2014
				(summarize
	                (aggregate.mean name:f
	                    (// (house.emissions by-fuel: PeakElectricity)
	                        (house.energy-use by-fuel: PeakElectricity))
	                )
                )
            )
        )
        (on.dates [01/01/2029]
			(set #cf-2029
				(summarize
	                (aggregate.mean name:f
	                    (// (house.emissions by-fuel: PeakElectricity)
	                        (house.energy-use by-fuel: PeakElectricity))
	                )
                )
            )
        )
    )
)

(template // [@1 @2]
    (function.case
        (when (= 0 @2)
            0)
        default:(/ @1 @2)
    )
)
(template carbon-factors/factors []
(context.carbon-factors
    (group
        carbon-factor: 0.18404
        fuels: MainsGas)
    (group
        carbon-factor: 0.21452
        fuels: BottledLPG)
    (group
        carbon-factor: 0.21452
        fuels: BulkLPG)
    (group
        carbon-factor: 0.24555
        fuels: Oil)
    (group
        carbon-factor: 0.03895
        fuels: BiomassPellets)
    (group
        carbon-factor: 0.03895
        fuels: BiomassWoodchip)
    (group
        carbon-factor: 0.03895
        fuels: BiomassWood)
    (group
        carbon-factor: 0.33
        fuels: HouseCoal)
    (group
        carbon-factor: 0.18
        fuels: CommunityHeat)
     (group
        fuels: [Electricity PeakElectricity OffPeakElectricity]
        carbon-factor: (~lookup-table
		name: electricity-carbon-factors
		row-keys: [ ]
		column-key: (sim.year)
		[2013	2014	2015	2016	2017	2018	2019	2020	2021	2022	2023	2024	2025	2026	2027	2028	2029	2030]
		[0.275	0.2599	0.2226	0.2241	0.2064	0.1730	0.1531	0.1488	0.1442	0.1202	0.1124	0.0935	0.0811	0.0818	0.0731	0.0584	0.0542	0.0500]
	) 
        
    )
    
    
))