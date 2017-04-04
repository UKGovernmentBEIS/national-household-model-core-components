(example
 caption: "Improving a dwelling to SAP Band C"
 description: "If there are any measures that can improve a dwelling such that it reaches SAP band C, do the cheapest of those. Otherwise do the best measure possible."

           (select.fallback
           ; Capture the SAP score for later use. (This example does not include SAP counterfactual conditions.)
            do-first: (set 
            	(def sap-score on:Event)
            	(house.fuel-cost-index))
            
            ; This is our preferred selector, so we write it first.            
            (select.filter 
             ; For the options which have a SAP score above the upper limit for SAP band D...
             test: (< 69 #sap-score)

             ; ...pick the option which was cheapest to install.
             selector: (select.minimum
                        (net-cost)))
            
            ; Pick the option with the highest SAP score. This is our fallback selector, so we write it second.
            (select.maximum #sap-score)))
