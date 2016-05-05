(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400

          (policy name: glazing

                  (target exposure: (schedule.on-group-entry)
                          name: glazing
                          action: (measure.install-glazing 	u-value:1.8
                          									light-transmittance: 0.65
                          									gains-transmittance: 0.59
                          									frame-type: uPVC
                          									frame-factor: 0.9
                          									insulation-type: LowEHardCoat
                          									glazing-type: Double
	                          )
                          group: (group.all)
                  )))