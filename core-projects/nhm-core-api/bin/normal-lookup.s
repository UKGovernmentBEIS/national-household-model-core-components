[
	(~lookup-table
		name:my-table
		row-keys:[(f1) (f2) (f3)]
		column-key:(f4)
		
		[F1  F2 F3 X   Y   Z]
		[9   10 11 o1 o2  o3]
		[<12 33 FO o4 o5  o6]
	)
	
	(lookup
		name:my-table
		keys:[(f1) (f2) (f3) (f4)]
		(entry [9   10 11 X] o1)
		(entry [9   10 11 Y] o2)
		(entry [9   10 11 Z] o3)
		(entry [<12 33 FO X] o4)
		(entry [<12 33 FO Y] o5)
		(entry [<12 33 FO Z] o6)
	)
]
