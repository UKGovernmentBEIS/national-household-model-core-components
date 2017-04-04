(example
 caption: "Setting the energy calculator mode"
 description: "Shows how to configure the energy calculator mode for a scenario.
Also illustrates how to temporarily override it for a dwelling, by using counterfactual.energy-calcutor.
"

 (scenario
  ;; Sets the energy calculator to be used for every dwelling in the scenario.
  energy-calculator: SAP2012

  ;; These properties must be set in any scenario.
  start-date: 2016
  end-date: 2016
  stock-id: my-stock

  ;; This report illustrates how to temporarily override the energy calculator we set above.
  (def-report energy
    (column name: sap2012 value: (house.energy-use))
    (column name: bredem2012 value: (under
				     (counterfactual.energy-calculator BREDEM2012)
				     evaluate: (house.energy-use))))

  ;; This causes houses to go into the report.
  (on.construction
   (apply
    to: (affected-houses)
    (send-to-report energy)))))
