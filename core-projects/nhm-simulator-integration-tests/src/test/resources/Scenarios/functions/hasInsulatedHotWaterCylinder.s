(scenario stock-id: ehs2012_from_spss
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
    
        (on.dates
            [01/02/2013]
            (apply
                to: (filter (house.has-insulated-hot-water-cylinder))
                (do (house.flag has-insulated-hot-water-cylinder))))
)
