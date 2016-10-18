(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
	
		  (include href:resetToSap.s)
		  (include href:resetTemplate.s)
		  (reset-with action:(reset-doors-to-sap))
)