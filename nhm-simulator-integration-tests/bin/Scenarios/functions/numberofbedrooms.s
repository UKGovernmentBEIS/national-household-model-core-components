(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
    (policy
        (target name:bedroomNumTest
            group: (group.filter 
                        source: (group.all)
                        (house.value-is
                            exactly: 8
                            (house.number-of-bedrooms)))
            exposure: (schedule.on-group-entry)
            action: (house.flag bedrooms-matched)
)))
