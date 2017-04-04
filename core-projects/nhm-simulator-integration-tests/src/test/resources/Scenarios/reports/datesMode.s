(scenario stock-id: small-spss-collection
          end-date: 01/01/2014
          start-date: 01/01/2013
          quantum: 400
           (report.aggregate name: test-report
                                     mode: (mode.dates
                                                       (scenario-start)
                                                       (between interval: "3 months"
                                                                start: 01/01/2010
                                                                end: 01/01/2050
                                                       )
                                                       (scenario-end))

                                     division: (division.by-group (group.all))
                                     (aggregate.count))
)