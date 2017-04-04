(no-include
    (scenario 
        start-date:01/01/2014 
        end-date:01/01/2014 
        stock-id:standard-spss-stock
    
        (on.dates
            (scenario-start)
            (apply
                (with-eco-subsidy
                    subsidy-level:80
                    
                    (eco.boiler)
                )
            )
        )
        
        (report.transactions)
        (report.global-accounts)
    )
)

(template eco.boiler []
    ; the approved template for eco boiler
    (do all:true
        (measure.standard-boiler 
            capex:1000
            fuel:MainsGas 
            winter-efficiency:95%
            summer-efficiency:95%)
        
        (set (def measure-lifetime on:Event) 12)
        (set (def inuse-factor on:Event) 0.9)
    )
)

(template with-eco-subsidy [
    @1                                          ; the measure to subsidize 
    @subsidy-level                              ; the subsidy level in £/pt
    [@measure-lifetime #measure-lifetime]       ; the measure lifetime in years
    [@maximum-subsidy (* 1.5 cost.capex)]       ; the maximum allowed subsidy
    [@inuse-factor #inuse-factor]               ; any inuse factors etc
    ]
	  ; in order
	  (do all:true
	      ; set a temporary variable called eco-emissions-before to (eco-emissions)
	      (set (def eco-emissions-before on:Event) (eco-emissions))
	      ; do the actual measure
	      (finance.with-subsidy
	         counterparty: green-deal-finance
	         subsidy: 
	         (min @maximum-subsidy
	            (* @subsidy-level @measure-lifetime @inuse-factor (- #eco-emissions-before (eco-emissions))))
	         @1
	      )
	    )
)

(template eco-emissions []
    (under
        ;(sap.occupancy)
        ;(counterfactual.weather)
        ;(counterfactual.carbon
            ; TODO fill in IAG guidance carbon factors here
        ;)
        ; TODO
        ; other SAP assumptions
        ; - u values etc
        ; - heating regime
        
        evaluate:house.emissions)
)