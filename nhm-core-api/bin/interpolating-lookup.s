[
	(~lookup-table
		name:my-table
		interpolate:true
		row-keys:(f1)
		column-key:(f2)
		
		[F1  A   B]
		[10  1   2]
		[11  2   3]
		[12  4   5]
	)
	
	(do
		(set (def some-guid on:Event) (f1))
		
		return: 
		(lookup
			   name: my-table
			   keys: [#some-guid (f2)]
			   (entry [10..11 A] (interpolate #some-guid [10 11] [1 2]))
			   (entry [10..11 B] (interpolate #some-guid [10 11] [2 3]))
			   (entry [11..12 A] (interpolate #some-guid [11 12] [2 4]))
			   (entry [11..12 B] (interpolate #some-guid [11 12] [3 5])))
	)
]
