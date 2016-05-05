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
                         bound: 50
                )

				scenario: (scenario stock-id: test-survey-cases
				          end-date: 31/12/2013
				          start-date: 01/01/2013
				          quantum: 400
				          seed: $seed
				
				          (include href: ../common/all-weather.s)
				          (include href: ../common/technologyCostsSpecifiedByDECC.s)
				          (include href: ../common/fuelproperties.s)))