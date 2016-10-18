(batch inputs: (cross
                       (range start: 0.6
                              placeholder: $efficiency
                              end: 0.9
                              step: 0.05
                       )
                       (table [row $fuel]
                              [row MainsGas]
                              [row Oil]
                              [row BulkLPG]
                              [row HouseCoal]))

			scenario: (scenario stock-id: test-survey-cases
			          end-date: 31/12/2013
			          start-date: 01/01/2013
			          quantum: 400
			
			          (include href: ../common/weather.s)
			          (include href: ../common/technologyCostsSpecifiedByDECC.s)
			          (include href: ../common/fuelproperties.s)))