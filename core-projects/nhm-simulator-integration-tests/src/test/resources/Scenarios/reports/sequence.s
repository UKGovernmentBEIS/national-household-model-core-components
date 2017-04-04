(scenario
    start-date: 01/01/1
    end-date: 01/01/10
    stock-id: test-survey-cases
    quantum: 4000
    
    (report.sequence)
    
    (on.dates
        (regularly)
        (apply
            (house.flag flagged)
            to:(sample
                50%
                (all-houses))))

    (policy
        (target
            name: destroy
            group: (group.all)
            exposure: (schedule.init
                (sample.proportion 0.1))
            action: (action.demolish))
    
        (target
            name: create
            group: (group.all)
            exposure: (schedule.time-series
                (expose on: 01/01/2 (sample.count 1000))
                (expose on: 01/01/5 (sample.count 500))
                (expose on: 01/01/7 (sample.count 6000)))
            action: (action.construct))))