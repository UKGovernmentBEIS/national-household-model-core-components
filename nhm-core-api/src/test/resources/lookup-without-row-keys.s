[
	(~lookup-table
		name:my-table
		column-key:(f1)
		
		[X   Y   Z]
		[o1 o2  o3]
		[o4 o5  o6]
	)
	
	(lookup
		name:my-table
		keys:[(f1)]
		(entry [X] o1)
		(entry [Y] o2)
		(entry [Z] o3)
		(entry [X] o4)
		(entry [Y] o5)
		(entry [Z] o6)
	)
]
