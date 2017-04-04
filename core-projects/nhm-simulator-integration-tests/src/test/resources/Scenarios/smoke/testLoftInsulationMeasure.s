(scenario stock-id: test-survey-cases
          end-date: 01/01/2014
          start-date: 01/01/2012
          quantum: 400
           (debug/assert aggregation: (aggregate.count)
                                    continuous: false
                                    name: "The set of houses flagged done which do not have 400mm of insulation should be empty"
                                    group: (group.filter source: (group.all)

                                                         (all
                                                              (none (house.loft-insulation-thickness-is exactly: 400))
                                                              (house.flags-match done)))
                                    type: Unchanging
                      )(debug/assert aggregation: (aggregate.count)
                                    continuous: false
                                    name: "The set of flats flagged done should not increase in size (should be zero, actually)"
                                    group: (group.filter source: (group.all)

                                                         (all
                                                              (any
                                                                   (house.built-form-is ConvertedFlat)
                                                                   (house.built-form-is PurposeBuiltHighRiseFlat)
                                                                   (house.built-form-is PurposeBuiltLowRiseFlat))
                                                              (house.flags-match done)))
                                    type: Unchanging
                      )(debug/assert aggregation: (aggregate.count)
                                    continuous: false
                                    name: "The set of houses flagged done with 400mm of insulation should increase in size"
                                    group: (group.filter source: (group.all)

                                                         (all
                                                              (house.flags-match done)
                                                              (house.loft-insulation-thickness-is exactly: 400)))
                                    type: Increasing
                      )

          (include href: ../common/all-weather.s)
          (policy (target exposure: (schedule.on-group-entry delay: "1 year")
                          name: "loft insulation"
                          action: (do all:true 
                                                 (measure.loft-insulation capex: (number 0)
                                                                          top-up: true
                                                                          name: li
                                                                          resistance: 10
                                                                          thickness: 400
                                                 )
                                                 (house.flag done))
                          group: (group.all)
                  )))