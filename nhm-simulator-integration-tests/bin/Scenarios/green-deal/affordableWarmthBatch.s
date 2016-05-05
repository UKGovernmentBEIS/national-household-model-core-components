(batch inputs: (range start: 0.0
                      placeholder: $poundPerPoint
                      end: 2.0
                      step: 0.1
                      )

       scenario: (scenario stock-id: test-survey-cases
                           end-date: 31/12/2015
                           start-date: 01/01/2013
                           quantum: 400
                           
                           	(def measure-costs on:House default:0)
							(def aw-points on:House default:0)
                           
                           (report.aggregate
                            name: total-points
                            mode: (mode.dates
                                   (scenario-end)
                                   )
                            
                            (aggregate.sum  name: sum-of-AW-points #aw-points)
                            (aggregate.sum name: sum-of-measure-costs #measure-costs)
                            
                            )
                           
                           (report.aggregate name: total-installations
                            mode: (mode.dates
                                   (scenario-end))
                                   
                            division: (division.by-group
                                       (group.filter
                                        (house.flags-match got-aw)))
                                        
                            (aggregate.count name: count-of-installations))
                            
                            (include href: greenDealPackages.s)
                            (include href: affordableWarmthPolicy.s)
                            (include href: ../common/fuelproperties.s)
                            (include href: ../common/technologyCostsSpecifiedByDECC.s)
                            (include href: ../common/carbon.s)
                            (include href: ../common/weather.s)))