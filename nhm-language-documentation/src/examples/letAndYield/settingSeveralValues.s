(example
 caption:"Setting several values at once"
 description:"The set action allows you to set the values of several variables at once. The changes are made in sequence,
so each value can depend on the last. In this example the final value of B will be 2, because the change to A takes immediate effect."
 class: "uk.org.cse.nhm.language.definition.sequence.XSetAction" 

 (set
  [ (def A) (def B) ]
  [   1     (+ 1 A)  ]
  )
)
