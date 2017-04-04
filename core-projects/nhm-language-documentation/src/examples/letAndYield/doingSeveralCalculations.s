(example
 caption: "Access to temporary, context-sensitive values within do"
 description: "This example illustrates how do has special access to some values, like (net-cost)"
 class: "uk.org.cse.nhm.language.definition.sequence.XSequenceAction"

 (do
     (measure.wall-insulation type:Cavity resistance:0.5 thickness:50)
     (measure.loft-insulation resistance:0.1 thickness:200)  

     (increase
      ; in this case we are declaring the variable right here - it is also possible
      ; to declare variables in the top level of a scenario, and then cross-reference
      ; them where you want to set or get them by writing their name prefixed with an
      ; octothorpe (# symbol). If we wanted to see total-spending in a report, for example
      ; you could just add #total-spending to the list of report fields.
      (example
       caption: "Defining a value to be stored on a house"
       description: "This use of def will declare the variable total-spending as stored on a house. Variables stored on houses persist as long as the house is not demolished."
       
       (def total-spending on:House default:0))
      ; capital-cost here will be the total capital cost spent in the do so far,
      ; and so includes both the wall insulation and loft insulation.
      (capital-cost))

     (finance.with-subsidy 
      ; in constrast, the subsidy written here is just the capex of the standard boiler,
      ; because the capital-cost is evaluated in the context of the finance.with-subsidy,
      ; and cannot see the capex related to the other measures
      subsidy:(capital-cost)
      (measure.standard-boiler fuel:MainsGas))))
