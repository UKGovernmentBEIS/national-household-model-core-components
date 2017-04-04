(scenario
    start-date:01/01/2013
    end-date:01/01/2016
    stock-id:UnitedKingdomStock
    quantum:1000
    
    (def proportionDoubleGlazed on:House default:0)
    
    (on.dates
        [01/02/2013]
        (apply
            (do (set #proportionDoubleGlazed (house.double-glazed-proportion))))))