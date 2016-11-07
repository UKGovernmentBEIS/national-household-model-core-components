(example
 caption:"Setting several values under some assumptions"
 description:"The set action can use counterfactual assumptions when computing the values which it will produce.
Here, all of the values we are calculating will be using the SAP 2012 energy calculator mode."
 class: "uk.org.cse.nhm.language.definition.sequence.XSetAction" 

 (set
  [ (def A)                (def B)            ]
  [ (house.energy-use)     (house.emissions)  ]
  under: (counterfactual.energy-calculator SAP2012))
)
