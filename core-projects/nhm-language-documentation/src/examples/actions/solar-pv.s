(example
 caption: "Installing Solar PV"
 description: "Shows how to install solar photovoltaic panels in a dwelling."

 (measure.solar-photovoltaic
  ;; The capital cost of installing the solar panels.
  capex: 5000

  ;; The peak output of the panels in kW.
  size: (size 10)

  ;; A proportion between 0 and 1.
  ;; This is the amount of electricity which will offset the dwelling's own electricity use.
  ;; Anything left-over will be exported to the grid (potentially at a different price).
  own-use-proportion: 0.3))
