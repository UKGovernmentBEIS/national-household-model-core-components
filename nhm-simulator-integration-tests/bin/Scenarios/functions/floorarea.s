(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
    (policy
        (target name:floorareatest
            group: (group.filter 
                        source: (group.all)
                        (house.value-is
                            exactly: 61.74
                            (house.total-floor-area)))
            exposure: (schedule.on-group-entry)
            action: (house.flag area-match)
)))
