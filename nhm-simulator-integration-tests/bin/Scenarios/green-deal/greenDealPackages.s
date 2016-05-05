(def measure-lifetime)
(def measure-coefficient)
(def in-use-factors)
(def repayment-coefficient)

    (template gd-measure [@measure @lifetime @measure-coefficient @in-use-factors @repayment-coefficient]
        (do
            (set #measure-lifetime @lifetime)
            (set #measure-coefficient @measure-coefficient)
            (set #in-use-factors @in-use-factors)
            (set #repayment-coefficient @repayment-coefficient)
            
            @measure))

    (def-action eco-assumptions
        (do all:true
            (sap.occupancy)
            (counterfactual.carbon)
            (counterfactual.weather)))
            
    (def-action cavity
        (gd-measure
            lifetime:42
            measure-coefficient:-1.3086649
            in-use-factors:0.35
            repayment-coefficient:-0.1230
            measure:(measure.decc.wall-insulation
                resistance:10
                thickness:50
                type:Cavity)))
                
    (def-action loft
        (gd-measure
            lifetime:42
            measure-coefficient:-0.8612969
            in-use-factors:0.35
            repayment-coefficient:0
            measure:(measure.loft-insulation
                top-up:true
                resistance:10
                thickness:150
                capex:(decc.loft-insulation-capex))))
                
    (def-action combi-boiler-replacement
        (gd-measure
            lifetime:12
            measure-coefficient:-1
            in-use-factors:0.0
            repayment-coefficient:0
            measure:(measure.decc.combi-boiler
                efficiency:0.9)))
                
    (def-action standard-boiler-replacement
        (gd-measure
            lifetime:12
            measure-coefficient:-1
            in-use-factors:0.0
            repayment-coefficient:0
            measure:(measure.decc.standard-boiler
                cylinder-insulation-thickness:50
                fuel:MainsGas
                efficiency:0.9
                cylinder-volume:110)))
