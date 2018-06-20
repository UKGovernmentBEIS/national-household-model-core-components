probe <- load.probe("report")

##  [1] "Sequence"        "weight"          "Date"            "dwelling.id"    
##  [5] "sent.from"       "selected"        "suitable"        "flags..Before." 
##  [9] "flags..After."   "winter..Before." "winter..After."  "summer..Before."
## [13] "summer..After."

## Remove the 'used' flag, which isn't interesting to us.
probe$flags..After <- gsub(",used", "", probe$flags..After)

gotBoiler <- probe[probe$flags..After == "got-boiler",]

fail.test.if(
    any(gotBoiler$winter..After != 0.9),
    "All newly installed boilers should have winter efficiency of 90%."
)

fail.test.if(
    any(gotBoiler$summer..After != 0.85),
    "All newly installed boilers should have summer efficiency of 85%."
)


gotExplicitEfficiency <- probe[probe$flags..After == "got-explicit-efficiency",]

fail.test.if(
    any(gotExplicitEfficiency$winter..After != 0.95),
    "Explicit efficiency group should have winter efficiency of 95%."
)

fail.test.if(
    any(gotExplicitEfficiency$summer..After != 0.8),
    "Explicit efficiency group should have summer efficiency of 80%."
)

gotWinterEfficiency <- probe[probe$flags..After == "got-winter-efficiency",]

fail.test.if(
    any(gotWinterEfficiency$winter..After != 0.5),
    "Winter efficiency group should have winter efficiency of 50%."
)

fail.test.if(
    any(gotWinterEfficiency$summer..After != gotWinterEfficiency$summer..Before),
    "Winter efficiency group should have unchanged summer efficiency."
)

gotSummerEfficiencyRelative <- probe[probe$flags..After == "got-summer-efficiency-relative",]

fail.test.if(
    any(gotSummerEfficiencyRelative$winter..After != gotSummerEfficiencyRelative$winter..Before),
    "Summer efficiency group should have unchanged winter efficiency."
)

fail.test.if(
    !all(
         all.equal( # Compare numbers with some tolerance for floating point inaccuracies.
             gotSummerEfficiencyRelative$summer..After,
             gotSummerEfficiencyRelative$winter..After - 0.2
         )
     ),
    "Summer efficiency group should have a summer efficiency 20% less than their winter efficiency."
)
