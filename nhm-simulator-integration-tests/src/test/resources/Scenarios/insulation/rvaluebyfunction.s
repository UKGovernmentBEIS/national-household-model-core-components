(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (policy name: insulate

                  (target exposure: (schedule.on-group-entry)
                          name: set-r-value-by-function
                          action: (measure.wall-insulation resistance: (function.case default: (number 0.2)

                                                                                       (when (house.region-is London)
                                                                                             (number 0.1)
                                                                                       ))
                                                           thickness: 33
                                                           update-flags: affected
                                                           type: Cavity
                                  )
                          group: (group.filter (all
                                                    (house.value-is below: 1.6
                                                                    above: 1.4

                                                                    (house.u-value of: Walls))
                                                    (house.flags-match !affected)))
                  )))