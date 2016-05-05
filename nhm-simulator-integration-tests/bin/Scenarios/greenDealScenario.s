(scenario stock-id: test-survey-cases
          end-date: 31/12/2015
          start-date: 01/01/2012
          quantum: 400
           (context.named-actions
                                          (do all:true  name: eco-assumptions

                                                         (sap.occupancy)
														 (reset-to-sap)
													     (counterfactual.carbon)
                                                         (counterfactual.weather))
                                          (yield vars: ((var var: measure-coefficient
                                                               value: -1.3086649
                                                          )(var var: measure-lifetime
                                                               value: 20
                                                          )(var var: in-use-factors
                                                               value: 0.9
                                                          ))
                                                 name: cavity
                                                 delegate: (measure.wall-insulation type: Cavity)
                                          )
                                          (yield vars: ((var var: swi-disincentive
                                                               value: 4314
                                                          )(var var: in-use-factors
                                                               value: 0.9
                                                          )(var var: measure-lifetime
                                                               value: 20
                                                          )(var var: measure-coefficient
                                                               value: -2.1666049
                                                          ))
                                                 name: solid-wall-internal
                                                 delegate: (measure.wall-insulation type: Internal)
                                          )
                                          (yield vars: ((var var: measure-coefficient
                                                               value: -2.0643649
                                                          )(var var: measure-lifetime
                                                               value: 20
                                                          )(var var: in-use-factors
                                                               value: 0.9
                                                          ))
                                                 name: solid-wall-external
                                                 delegate: (measure.wall-insulation type: External)
                                          )
                                          (yield vars: ((var var: measure-lifetime
                                                               value: 20
                                                          )(var var: in-use-factors
                                                               value: 0.9
                                                          )(var var: measure-coefficient
                                                               value: -0.8612969
                                                          ))
                                                 name: loft
                                                 delegate: (measure.loft-insulation top-up: true
                                                                                    resistance: 10
                                                                                    thickness: 150
                                                           )
                                          )
                                          (yield vars: ((var var: measure-lifetime
                                                               value: 20
                                                          )(var var: in-use-factors
                                                               value: 0.9
                                                          )(var var: measure-coefficient
                                                               value: -1.3865649
                                                          ))
                                                 name: loft-and-cavity
                                                 delegate: (do all:true 
                                                                          (measure.loft-insulation top-up: true
                                                                                                   resistance: 10
                                                                                                   thickness: 150
                                                                          )
                                                                          (measure.wall-insulation type: Cavity))
                                          ))

		  (include href: ../resetting/resetToSap.s)
          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: aw

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.proportion   0.1))
                          name: aw
                          action: (finance.fully)
                          group: (group.filter (house.tenure-is OwnerOccupied))
                  )
                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.bernoulli (number 0.05)))
                          name: "partial boiler failure"
                          action: (action.change-efficiency winter-efficiency: 0.6
                          									summer-efficiency: -1%
                                                            direction: Decrease
                                                            update-flags: boiler-degraded
                                  )
                          group: (group.filter name: working-boilers

                                               (all
                                                    (house.has-boiler)
                                                    (none
                                                          (house.test-flag named: boiler-broke)
                                                          (house.test-flag named: boiler-degraded))))
                  )
                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.bernoulli (number 0.03)))
                          name: "total boiler failure"
                          action: (measure.break-boiler waterHeater: (measure.point-of-use-hot-water capex: (number 0))
                                                        roomHeater: (measure.room-heater capex: (number 0)
                                                                                         replace-existing: true
                                                                                         fuel: Electricity
                                                                                         size: (size 0)
                                                                                         backup: true
                                                                    )
                                                        name: replace-boiler
                                                        update-flags: boiler-broke
                                  )
                          group: (group.reference to: working-boilers)
                  ))
          (policy name: gd

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.proportion   0.1))
                          name: eco
                          action: (register.add name: ecopoints
                                                value: (get eco-points)
                                  )
                          group: (group.all)
                  )))