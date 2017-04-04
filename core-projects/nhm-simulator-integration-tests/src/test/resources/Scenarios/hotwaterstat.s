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
                          action: (measure.hot-water-tank-thermostat capex: 1000 update-flags: has-thermostat)
                          group: (group.all)
                  )))