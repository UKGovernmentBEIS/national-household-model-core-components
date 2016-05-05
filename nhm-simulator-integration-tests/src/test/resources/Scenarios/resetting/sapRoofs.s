(template reset-roofs-to-sap []
	(action.reset-roofs
			u-values:
				(lookup-table
					name:"RDSAP roof u-value"
					row-keys:[(house.loft-insulation-thickness)]
					column-key:(house.roof-construction-type)
					interpolate:true
					log-warnings:false
					default:
						(lookup-table
							name:"RDSAP fallback roof u-value"
							row-keys:[(house.roof-construction-type)]
							column-key:(house.sap-age-band)
							
							[Roof					A		B		C		D		E		F		G		H		I		J		K	]
							[PitchedSlateOrTiles	2.30	2.30	2.30	2.30	1.50	0.68	0.40	0.29	0.26	0.16	0.16]
							[Thatched				0.35	0.35	0.35	0.35	0.35	0.35	0.35	0.35	0.35	0.30	0.25]
							[Flat					2.30	2.30	2.30	2.30	1.50	0.68	0.40	0.35	0.35	0.25	0.25]
						)
					
					[Insulation		PitchedSlateOrTiles		Thatched]
					[0		        2.30	                  0.40	]
					[25		        1.00	                  0.30	]
					[50		        0.70	                  0.30	]
					[75		        0.50	                  0.20	]
					[100	        0.40	                  0.20	]
					[125	        0.30	                  0.20	]
					[150	        0.30	                  0.20	]
					[200	        0.20	                  0.10	]
					[250	        0.20	                  0.10	]
					[300	        0.10	                  0.10	]
					[350	        0.10	                  0.10	]
				)
			k-values:9
			party-k-values:13.5
		)
)