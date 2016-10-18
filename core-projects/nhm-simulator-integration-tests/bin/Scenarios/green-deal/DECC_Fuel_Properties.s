(context.tariffs
    defaults: [ (tariff
                       name: Electricity-Tariff
                       (fuel
                           type: PeakElectricity
                           (charge
                               (*
                                   (house.meter-reading) 0.14)))
                       (fuel
                           type: OffPeakElectricity
                           (charge
                               (*
                                   (house.meter-reading) 0.07))))
                   (tariff
                       name: MainsGas-Tariff
                       (fuel
                           type: MainsGas
                           (charge
                               (*
                                   (house.meter-reading) 0.045))))
                   (tariff
                       name: Oil-Tariff
                       (fuel
                           type: Oil
                           (charge
                               (*
                                   (house.meter-reading) 0.06))))
                   (tariff
                       name: LPG-Tariff
                       (fuel
                           type: BulkLPG
                           (charge
                               (*
                                   (house.meter-reading) 0.075)))
                       (fuel
                           type: BottledLPG
                           (charge
                               (*
                                   (house.meter-reading) 0.075))))
                   (tariff
                       name: Biomass-Tariff
                       (fuel
                           type: BiomassPellets
                           (charge
                               (*
                                   (house.meter-reading) 0.04)))
                       (fuel
                           type: BiomassWood
                           (charge
                               (*
                                   (house.meter-reading) 0.04)))
                       (fuel
                           type: BiomassWoodchip
                           (charge
                               (*
                                   (house.meter-reading) 0.03)))) ])