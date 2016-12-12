(example
 caption: "Setting Thermal Bridging"
 description: "Shows how to set the thermal bridging factor for a dwelling.
In this example, we are illustrating a hypothetical improvement in construction standards,
with newer dwellings getting lower thermal bridging factors."

 (action.set-thermal-bridging-factor
  (function.case
   (when (> (house.build-year) 2020) 0.04)
   (when (> (house.build-year) 2015) 0.06)
   (when (> (house.build-year) 2010) 0.08)
   (when (> (house.build-year) 2000) 0.10)
   ;; This is the value from SAP 2012.
   default: 0.15)))
