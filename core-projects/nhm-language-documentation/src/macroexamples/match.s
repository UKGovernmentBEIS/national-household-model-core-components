(~match
 caption:"Using ~match to choose whether to apply a subsidy in a template"
 description:"One way macro.match can be useful is to allow turning things on and off within templates. For example here we are enabling a probe using match, and not enabling it otherwise."
 

  (~match true ; this could be a template variable - for this example we show what happens with true
   [true
    (probe (my-action) capture:[x y z])
    ]

   [false (my-action)]

   default: (~error expected true or false!)
   )
 
 )
