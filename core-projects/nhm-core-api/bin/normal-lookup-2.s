[
	 (~lookup-table
          row-keys:[(sim.year)]
          [A  B]
          [20 1]
          [25 2]
          [30 3]
          )

	(lookup
		keys:[(sim.year)]
	
		(entry [20] 1)
		(entry [25] 2)
		(entry [30] 3)
		
	)
]
		