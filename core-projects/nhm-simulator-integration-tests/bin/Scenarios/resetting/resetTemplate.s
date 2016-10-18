(template reset-with [@action]

		(def old-energy on:House)
		(def new-energy on:House)

		(policy name: reset
              (target
                    name: reset-some-walls
              		group:(group.all)
              		exposure: (schedule.on-group-entry)
                    action: 
                    	(do
                    		(set #old-energy (house.energy-use))
                    		@action
                    		(set #new-energy (house.energy-use))
                    	)
          ))
)