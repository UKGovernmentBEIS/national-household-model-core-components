(example
 caption: "Measure Change Using Set"
 description: "In this example we give a house cavity insulation, and then subsidise it based on the improvement to its u-value. We need to use a set element to capture the initial value, since otherwise this information would be lost."
 class: "uk.org.cse.nhm.language.definition.sequence.XSequenceAction"
 
 (do
 	; 1. initial-u-value is bound to the average u-value of the house
 	(set 
 		; We should declare each variable exactly once using def. Normally we would put these def elements further up in the scenario and refer to them by name. 
 		(def initial-u-value on:Event)
		; The value which will be set is calculated here. 
 		(house.u-value of: Walls))
 
     ; initial-u-value is then available to finance.with-subsidy and everything inside it
     (finance.with-subsidy

      ; 2. we apply this cavity insulation measure which may change the u-value of the house
      (measure.wall-insulation type: Cavity thickness: 50 resistance: 0.1)

      ; 3. after insulating the house, we calculate and apply the subsidy based on the difference between the original u-value which we captured, and the current u-value
      subsidy: (-
		#initial-u-value
                (house.u-value of: Walls)
      ))

     ; 4. the value of initial-u-value is available on this house until the end of the 'apply' element.
     ))
