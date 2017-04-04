(no-include
    (scenario
        ; Test scenario to ensure templates validate.
        start-date: 01/01/1
        end-date: 01/01/2
        stock-id: EnglandAndWales_2010
        
        (report.aggregate
            name: country-counts
            division: (division.by-group
                (group.filter name: England (group.england))
                (group.filter name: Wales (group.wales))
                (group.filter name: EnglandAndWales (group.england-and-wales))
                (group.filter name: Scotland (group.scotland))
                (group.filter name: NorthernIreland (group.northern-ireland)))
            
            (aggregate.count))))

(template group.england []
    (any
        (house.region-is NorthEast)
        (house.region-is YorkshireAndHumber)
        (house.region-is NorthWest)
        (house.region-is EastMidlands)
        (house.region-is WestMidlands)
        (house.region-is SouthWest)
        (house.region-is EastOfEngland)
        (house.region-is SouthEast)
        (house.region-is London)))

(template group.wales []
    (house.region-is Wales))
    
(template group.england-and-wales []
    (any 
        (group.england)
        (group.wales)))
        
(template group.scotland []
    (any
        (house.region-is EasternScotland)
        (house.region-is WesternScotland)
        (house.region-is NorthernScotland)))
        
(template group.northern-ireland []
    (house.region-is NorthernIreland))