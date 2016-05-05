(batch inputs: (bounded inputs: (zip
                                 (triangular peak: 0.85
                                             start: 0.6
                                             placeholder: $efficiency
                                             end: 0.93
                                             )
                                 (uniform-integers start: 0
                                                   placeholder: $seed
                                                   end: 10000000
                                                   ))
                        bound: 1000
                        )
       scenario: (scenario stock-id: my-stock
                           end-date: 31/12/2013
                           start-date: 01/01/2013
                           quantum: 400
                           seed: $seed

                           (policy (target exposure: (schedule.on-group-entry)
                                           action: (measure.standard-boiler fuel: MainsGas
                                                                            winter-efficiency: $efficiency
                                                                            )
                                           group: (group.all)
                                           ))))
