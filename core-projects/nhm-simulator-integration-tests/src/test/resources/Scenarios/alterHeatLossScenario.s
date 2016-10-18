(scenario stock-id: test-survey-cases
          end-date: 31/12/2012
          start-date: 01/01/2012
          quantum: 400

          (include href: common/weather.s)
          (include href: common/technologyCostsSpecifiedByDECC.s)
          (include href: common/fuelproperties.s)
          (policy name: "loft insulation test"

                  (target exposure: (schedule.on-group-entry)
                          name: everything
                          action: (action.set-wall-u u-value: 1000)
                          group: (group.all)
                  )))