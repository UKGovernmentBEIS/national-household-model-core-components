(scenario stock-id: test-survey-cases
          end-date: 31/12/2012
          start-date: 01/01/2012
          quantum: 400
           (include href: ../common/technologyCostsSpecifiedByDECC.s) 
           (context.tariffs defaults: (tariff name: stepped_price_per_unit

                                                       (fuel type: MainsGas

                                                             (charge payee: "Gas Company"
                                                             
                                                             	(function.stepped-pricing standing-charge: 100
                                                                                                      units: (house.meter-reading)

                                                                                                      (range to: 100
                                                                                                             unit-price: (number 1)
                                                                                                      )
                                                                                                      (range to: 10000
                                                                                                             unit-price: (number 10)
                                                                                                      ))
                                                                     
                                                             ))))

          (policy name: "try efficient and inefficient boilers"

                  (target exposure: (schedule.on-group-entry)
                          name: "start with implausibly bad boilers"
                          action: (do all:true 
                                                 (measure.standard-boiler fuel: MainsGas
                                                                          winter-efficiency: 0.5
                                                                          summer-efficiency: 0.5
                                                 )
                                                 (house.flag inefficient-boiler))
                          group: (group.all)
                  )
                  (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: "impossibly efficient boilers after 1 year"
                          action: (do all:true 
                                                 (measure.standard-boiler fuel: MainsGas
                                                                          winter-efficiency: 10.0
                                                                          summer-efficiency: 10.0
                                                 )
                                                 (house.flag efficient-boiler))
                          group: (group.all)
                  )))