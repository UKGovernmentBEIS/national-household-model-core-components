(~lookup-table
	caption:"An interpolating lookup table"
	description:"The lookup table macro can also produce an interpolating lookup for tables where
				 there is a single numeric row key. In this example table, a house in London with 150mm
				 of roof insulation would produce the interpolated value 20 (halfway between 10 and 30)"
				 
				 
	(~lookup-table name:my-table
	  row-keys:(house.loft-insulation-thickness)
	  interpolate:true
	  column-key:(house.region)
	  [Insulation   London  Scotland]
	  [100            10     20     ]
	  [200            30     80     ]
	  [300            20     30     ])
)
