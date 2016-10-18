(template reset-doors-to-sap []
	(action.reset-doors
			areas:
				(lookup-table
					name:"CHM door areas"
					row-keys:(door.type)
					
					[Type	Area]
					[Glazed	1.85]
					[Solid	1.85]
				)
			u-values:
				(lookup-table
					name:"RDSAP door u-values"
					row-keys:(door.type)
					column-key:(house.sap-age-band)
				
					[Type	A	B	C	D	E	F	G	H 	I	J	K]
					[Solid	3	3	3	3	3	3	3	3	3	3	2]
					[Glazed	3	3	3	3	3	3	3	3	3	3	2]
				)
		)
)