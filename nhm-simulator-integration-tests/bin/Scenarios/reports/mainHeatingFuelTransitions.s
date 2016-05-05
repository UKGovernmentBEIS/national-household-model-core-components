(report.group-transitions name: "fuel changes"
                          group: (group.all)

                          (when name: MainsGas
                          	(house.main-heating-fuel-is MainsGas)    
                          )
                          (when name: BulkLPG
                          	(house.main-heating-fuel-is BulkLPG)
                                
                          )
                          (when name: BottledLPG
                          	(house.main-heating-fuel-is BottledLPG)
                                
                          )
                          (when
                                name: Electricity
                                 (house.main-heating-fuel-is Electricity)
                          )
                          (when 
                                name: PeakElectricity
                                 (house.main-heating-fuel-is PeakElectricity)
                          )
                          (when 
                                name: OffPeakElectricity
                                (house.main-heating-fuel-is OffPeakElectricity)
                          )
                          (when 
                                name: Oil
                                (house.main-heating-fuel-is Oil)
                          )
                          (when 
                                name: BiomassPellets
                                (house.main-heating-fuel-is BiomassPellets)
                          )
                          (when 
                                name: BiomassWoodchip
                                (house.main-heating-fuel-is BiomassWoodchip)
                          )
                          (when 
                                name: BiomassWood
                                (house.main-heating-fuel-is BiomassWood)
                          )
                          (when 
                                name: Photons
                                (house.main-heating-fuel-is Photons)
                          )
                          (when 
                                name: HouseCoal
								(house.main-heating-fuel-is HouseCoal)                                
                          )
                          (when 
                                name: CommunityHeat
                                (house.main-heating-fuel-is CommunityHeat)
                          ))