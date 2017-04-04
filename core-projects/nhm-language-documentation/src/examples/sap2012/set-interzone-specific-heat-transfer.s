(example
 caption: "Setting interzone specific heat transfer"
 description: "Shows how to set the interzone specific heat transfer for a dwelling.
In this example, we set it to 10 Watts / Kelvin per m^2 of floor area."

 (action.set-interzone-specific-heat-transfer
  ;; We can specify an arbitrary number function here, with the restriction that the result must always be at least 0.
  (* (house.total-floor-area) 10)))
