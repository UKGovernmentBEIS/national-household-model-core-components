(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          quantum: 400
    (policy
        (target name:spaceHeatingAgeTest
            group: (group.filter 
                        source: (group.all)
                        (house.value-is
                            exactly: 163
                            (house.space-heatingsystem-age)))
            exposure: (schedule.on-group-entry)
            action: (house.flag heating-age-match)
)))
