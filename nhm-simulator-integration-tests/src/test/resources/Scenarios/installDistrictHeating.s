(scenario stock-id: test-survey-cases
          end-date: 31/12/2012
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "DHP 1"

                  (target exposure: (schedule.repeat interval: "1 year"

                                                     (sample.count  10000))
                          name: "install DH in 100% of urban cases"
                          action: (measure.district-heating cylinder-insulation-thickness: 50
                                                            efficiency: 0.85
                                                            cylinder-volume: 100
                                  )
                          group: (group.filter source: (group.all)

                                               (all
                                                    (house.morphology-is Urban)
                                                    (any
                                                         (house.built-form-is ConvertedFlat)
                                                         (house.built-form-is PurposeBuiltLowRiseFlat)
                                                         (house.built-form-is PurposeBuiltHighRiseFlat))))
                  )))