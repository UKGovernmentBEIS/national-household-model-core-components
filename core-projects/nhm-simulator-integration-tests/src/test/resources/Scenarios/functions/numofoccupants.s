(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
    (policy
        (target name:occupantNumTest
            group: (group.filter 
                        source: (group.all)
                        (house.value-is
                            exactly: 13
                            (house.number-of-occupants)))
            exposure: (schedule.on-group-entry)
            action: (house.flag has-13-occupants)
)))
