(template curtains [@u-value @factor]
	;; apply curtain effect factor
	(/ 1 (+ @factor (/ 1 @u-value))))

(template reset-glazing-to-sap []
	(action.reset-glazing
			frame-factor:
				(lookup-table
					name:"RDSAP frame factors"
					row-keys:(glazing.frame-type)
					
					[Frame	Factor]
					[Wood	0.7	  ]
					[Metal	0.8	  ]
					[uPVC	0.7	  ]
				)
			u-value:
				(curtains factor:0.04
						 u-value:
							(lookup-table
								name:"RDSAP glazing u-values"
								row-keys:[(glazing.type) (glazing.insulation-type)]
								column-key:(glazing.frame-type)
								
								;; a curtain effect factor of 0.04 is added to all of these
								;; within the scenario outside the lookup table.
								[Glazing	Insulation		Wood	Metal	uPVC]
								[Single		*				4.8		5.7		4.8	]
								[Secondary	*				2.4		2.4		2.4	]
								[Double		Air				3.1		3.7		3.1	]
								[Double		LowEHardCoat	2.7		3.3		2.7	]
								[Double		LowESoftCoat	2.6		3.2		2.6	]
								[Triple		Air				2.4		2.9		2.4	]
								[Triple		LowEHardCoat	2.1		2.6		2.1	]
								[Triple		LowESoftCoat	2.0		2.5		2.0	]
							)
				)
			light-transmittance:
				(lookup-table
					name:"RDSAP light transmittance factors"
					row-keys:(glazing.type)
					
					[Glazing	Transmittance]
					[Single		0.9			 ]
					[Secondary	0.8			 ]
					[Double		0.8			 ]
					[Triple		0.7			 ]
				)
			gains-transmittance:
				(lookup-table
					name:"RDSAP gains transmittance factors"
					row-keys:[(glazing.type) (glazing.insulation-type)]
					
					[Glazing	Insulation		Transmittance]
					[Single		*				0.85		 ]
					[Secondary	*				0.76		 ]
					[Double		Air				0.76		 ]
					[Double		LowESoftCoat	0.63		 ]
					[Double		LowEHardCoat	0.72		 ]
					[Triple		Air				0.68		 ]
					[Triple		LowESoftCoat	0.57		 ]
					[Triple		LowEHardCoat	0.64		 ]
				)
		)
)