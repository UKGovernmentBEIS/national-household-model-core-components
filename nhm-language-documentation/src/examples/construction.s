(example
 caption: "Reporting on new-build houses"
 description: "The on.construction element runs some commands when new dwellings are constructed from the stock; this happens at the start of the run, and in subsequent years if the stock contains houses whose build year is in the future."

 (on.construction
  (aggregate
   name: new-build-counts
   ;; affected-houses contains the new builds, when used inside on.construction.
   over: (affected-houses)
   (aggregate.count name:count)))
 )
