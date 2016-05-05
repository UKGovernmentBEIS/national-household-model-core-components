(scenario stock-id: ehs2012_from_spss
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
    
    (def heatingSystemType on:House default:0)
    
    (on.dates
        [01/02/2012]
        (apply
            (do
                (set #heatingSystemType 
                    (~lookup-table name:system-type
                        row-keys:[(house.main-heating-system-type)]
                        [Type Value]
                        [StandardBoiler 1]
                        [CombiBoiler 2]
                        [HeatPump 3]
                        [StorageHeater 4]
                        [WarmAirSystem 5]
                        [RoomHeater 6]
                        [BackBoiler 7]
                        [Community 8]
                        [Condensing 9] 
                        [CondensingCombiBoiler 10]
    ))))))
