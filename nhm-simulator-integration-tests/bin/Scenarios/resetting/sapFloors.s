(template reset-floors-to-sap []
	(action.reset-floors
			u-values:
				(lookup-table
					name:"RDSAP floor u-values"
					row-keys:[(floor.is-on-ground)]
					[GroundFloor		u-value]
					; this is simply the rule SAP uses for ground floor u-values
					; which is a long, tedious equation.
					[true				(floor.sap-ground-floor-u-value)]
					; in this row we fall through to another lookup table
					; which gives exposed floor u-values by age band
					[false				(lookup-table
											name:"RDSAP exposed floor u-values"
											row-keys:[(house.floor-insulation-thickness)]
											column-key:(house.sap-age-band)
											[Insulation	A		B		C		D		E		F		G		H		I		J		K	]
											[   0		1.20	1.20	1.20	1.20	1.20	1.20	1.20	0.51	0.51	0.25	0.22]
											[	>0		0.50	0.50	0.50	0.50	0.50	0.50	0.50	0.50	0.50	0.25	0.22]
										)]
				)
			k-values:
				(lookup-table
					name:"RDSAP external floor k-values"
					row-keys:[(floor.is-on-ground)]
					column-key:(house.floor-construction-type)
					
					[Ground		Solid	SuspendedTimber ]
					[true 		110			20			]
					[false		20			20			]
				)	
			party-k-values:35
		)

		(action.set-floor-insulation
			thickness:
				(lookup-table
					name:"RDSAP floor insulation thickness"
					row-keys:(house.sap-age-band)
					
					[Band Thickness ]
					[A		0		]
					[B		0		]
					[C		0		]
					[D		0		]
					[E		0		]
					[F		0		]
					[G		0		]
					[H		0		]
					[I		25		]
					[J		75		]
					[K		100		]
				)
		)
)