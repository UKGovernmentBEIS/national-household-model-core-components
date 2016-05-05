(scenario stock-id: test-survey-cases
          end-date: 31/12/2013
          start-date: 01/01/2012
          weighting:round
          quantum: 400

	(on.dates scenario-start
		(apply to:(filter 
			(all (house.flags-match !default-cwi !double-insulation !sandstone-cwi)
                 (house.any-wall has-construction:AnyCavity has-cavity-insulation: true)))
            
            (house.flag preinsulated-cavity)
		)
		
		(apply to:(filter 
			(all (house.flags-match !default-cwi !double-insulation !sandstone-cwi)
                 (house.all-walls has-construction:AnyCavity has-cavity-insulation: true)))
            
            (house.flag fully-preinsulated-cavity)
		)
		
		(apply to:
			(sample 10000
				(filter (house.any-wall has-construction:AnyCavity has-cavity-insulation:false has-internal-insulation:false has-external-insulation:false))
			)
		
			(measure.wall-insulation resistance: 0.1
                                                       thickness: 50
                                                       update-flags: default-cwi
                                                       type: Cavity)
		)
		
		(apply to:
			(sample 10000)
			(measure.wall-insulation test-flags: !default-cwi
                                                       suitable-construction: Sandstone
                                                       resistance: 0.1
                                                       thickness: 50
                                                       update-flags: sandstone-cwi
                                                       type: Cavity)
		)
		
		(apply to:(sample 10000)
			(measure.wall-insulation suitable-insulation: SomeInsulation
                                                           test-flags: [!default-cwi !sandstone-cwi]
                                                           resistance: 0.1
                                                           thickness: 100
                                                           update-flags: double-insulation
                                                           type: Cavity)
		)
	)
)