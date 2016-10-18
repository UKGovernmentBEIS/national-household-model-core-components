(template reset-walls-to-sap []
	(action.reset-walls
			u-values:
				(lookup-table
					name:"RDSAP wall u-values (england and wales)"
					row-keys:[(wall.construction)
							  (wall.insulation-thickness Internal External Cavity)
							  (wall.insulation-thickness Cavity)]
					column-key:(house.sap-age-band)
					[Construction   		Ins		Cav		A		B		C		D		E		F		G		H		I		J		K		]
					; bands A-E here are defined by an equation in the SAP documentation, (S5.1.1), but the equation always computes the same values
					; so they are shown here directly.
					[GraniteOrWhinstone		0		0		2.3		2.3		2.3		2.3		1.7		1		0.60	0.60	0.45	0.35	0.30	]
					[Sandstone				0		0		2		2		2		2		1.7		1		0.60	0.60	0.45	0.35	0.30	]
					; these 6 rows for granite or whinstone are copies of the solid brick values, because the SAP tables do not detail anything for
					; insulated granite or insulated sandstone 
					[GraniteOrWhinstone		<100	0		0.60	0.60	0.60	0.60	0.55	0.45	0.35	0.35	0.30	0.25	0.21	]
					[GraniteOrWhinstone		<150	0		0.35	0.35	0.35	0.35	0.35	0.32	0.24	0.24	0.21	0.19	0.17	]
					[GraniteOrWhinstone		>=150	0		0.25	0.25	0.25	0.25	0.25	0.21	0.18	0.18	0.17	0.15	0.14	]
					[Sandstone				<100	0		0.60	0.60	0.60	0.60	0.55	0.45	0.35	0.35	0.30	0.25	0.21	]
					[Sandstone				<150	0		0.35	0.35	0.35	0.35	0.35	0.32	0.24	0.24	0.21	0.19	0.17	]
					[Sandstone				>=150	0		0.25	0.25	0.25	0.25	0.25	0.21	0.18	0.18	0.17	0.15	0.14	]
					[SolidBrick				<50		0		2.10	2.10	2.10	2.10	1.70	1.00	0.60	0.60	0.45	0.35	0.30	]
					[SolidBrick				<100	0		0.60	0.60	0.60	0.60	0.55	0.45	0.35	0.35	0.30	0.25	0.21	]
					[SolidBrick				<150	0		0.35	0.35	0.35	0.35	0.35	0.32	0.24	0.24	0.21	0.19	0.17	]
					[SolidBrick				>=150	0		0.25	0.25	0.25	0.25	0.25	0.21	0.18	0.18	0.17	0.15	0.14	]
					[Cob					<50		0		0.80	0.80	0.80	0.80	0.80	0.80	0.60	0.60	0.45	0.35	0.30	]
					[Cob					<100	0		0.40	0.40	0.40	0.40	0.40	0.40	0.35	0.35	0.30	0.25	0.21	]
					[Cob					<150	0		0.26	0.26	0.26	0.26	0.26	0.26	0.26	0.26	0.26	0.25	0.21	]
					[Cob					>=150	0		0.20	0.20	0.20	0.20	0.20	0.20	0.20	0.20	0.20	0.20	0.20	]
					;; these two rows are for cavity walls that have no cavity insulation in them
					[Cavity					<50		0		2.10	1.60	1.60	1.60	1.60	1.00	0.60	0.60	0.45	0.35	0.30	]
					[Cavity					>=50	0		0.65	0.65	0.65	0.65	0.65	0.65	0.35	0.35	0.45	0.35	0.30	]
					;; these are filled cavities with some degree of insulation
					[Cavity					<=50	>0		0.65	0.65	0.65	0.65	0.65	0.65	0.35	0.35	0.45	0.35	0.30	]
					[Cavity					<=100	>0		0.31	0.31	0.31	0.31	0.31	0.27	0.25	0.25	0.25	0.25	0.25	]
					[Cavity					<=150	>0		0.22	0.22	0.22	0.22	0.22	0.20	0.19	0.19	0.19	0.19	0.19	]
					[Cavity					>150	>0		0.17	0.17	0.17	0.17	0.17	0.16	0.15	0.15	0.15	0.15	0.15	]
					[TimberFrame			0		0		2.50	1.90	1.90	1.00	0.80	0.45	0.40	0.40	0.40	0.35	0.30	]
					;; this row contains an assumption that extends table S6 to cover timber frame with any kind of insulation
					[TimberFrame			>0		*		0.60	0.55	0.55	0.40	0.40	0.40	0.40	0.40	0.40	0.35	0.30	]
					;; these rows extend table S6 to cover system build with any kind of insulation
					[SystemBuild			<50		*		2.00	2.00	2.00	2.00	1.70	1.00	0.60	0.60	0.45	0.35	0.30	]
					[SystemBuild			<100	*		0.60	0.60	0.60	0.60	0.55	0.45	0.35	0.35	0.30	0.25	0.21	]
					[SystemBuild			<150	*		0.35	0.35	0.35	0.35	0.35	0.32	0.24	0.24	0.21	0.19	0.17	]
					[SystemBuild			>=150	*		0.25	0.25	0.25	0.25	0.25	0.21	0.18	0.18	0.17	0.15	0.14	]
					;; This row handles all metal frame walls - origin unclear (CHM?)
					[MetalFrame				*		*		2.20	2.20	2.20	0.86	0.86	0.53	0.53	0.53	0.45	0.35	0.30	]
				)
			thicknesses:
				(lookup-table
					name:"RDSAP wall thicknesses"
					row-keys:[(wall.construction) (wall.insulation-thickness Internal External)]
					column-key:(house.sap-age-band)
					[Construction		Insulation	A	B	C	D	E	F	G	H	I	J	K  ]
					[Sandstone			0			500	500	500	500	450	420	420	420	450	450	450]
					[Sandstone			>0			570	570	570	570	520	490	490	490	520	520	520]
					[GraniteOrWhinstone	0			500	500	500	500	450	420	420	420	450	450	450]
					[GraniteOrWhinstone	>0			570	570	570	570	520	490	490	490	520	520	520]
					[SolidBrick			0			220	220	220	220	240	250	270	270	300	300	300]
					[SolidBrick			>0			290	290	290	290	310	320	340	340	370	370	370]
					[Cavity				0			250	250	250	250	250	260	270	270	300	300	300]
					[Cavity				>0			300	300	300	300	300	310	320	320	330	330	330]
					[TimberFrame		0			150	150	150	250	270	270	270	270	300	300	300]
					[TimberFrame		>0			200	200	200	290	310	270	270	270	300	300	300]
					[Cob				0			540	540	540	540	540	540	560	560	590	590	590]
					[Cob				>0			590	590	590	590	590	590	610	610	640	640	640]
					[SystemBuild		0			250	250	250	250	250	300	300	300	300	300	300]
					[SystemBuild		>0			320	320	320	320	320	370	370	370	370	370	370]
					[MetalFrame			0			100	100	100	200	220	220	220	220	250	250	250]
					[MetalFrame			>0			150	150	150	240	260	220	220	220	250	250	250]
				)
			k-values:
				(lookup-table
					name:"RDSAP wall k-values"
					row-keys:[(wall.construction) (wall.insulation-thickness Internal) (wall.insulation-thickness External)]
					
					[Construction 												Internal	External	k-value ]
					; the internal k-values are presumed to be most important, so we presume internal k-value
					; no matter the external thickness if there is any internal insulation
					[SolidBrick													   >0			*		 17.0	]
					[Cob														   >0			*		 66		]
					[SystemBuild												   >0			*		 10		]
					; for these construction types, we use these values if there is external but no internal
					[SolidBrick													   0			>0		 135.0	]
					[Cob														   0			>0		 148	]
					[SystemBuild												   0			>0		 211	]
					; these k-values will be used if none of the rows above match
					[Party_DensePlasterBothSidesDenseBlocksCavity		           *             *		180.00	]
					[Party_SinglePlasterboardBothSidesDenseBlocksCavity	           *             *		70.00	]
					[Party_SinglePlasterboardBothSidesDenseAACBlocksCavity	       *             *		45.00	]
					[Party_DoublePlasterBothSidesTwinTimberFrame		           *             *		50.00	]
					[Party_MetalFrame									           *             *		20.00	]
					[GraniteOrWhinstone									           *             *		202.00	]
					[Sandstone											           *             *		156.00	]
					[SolidBrick											           *             *		135.00	]
					[Cob												           *             *		148.00	]
					[Cavity												           *             *		139.00	]
					[TimberFrame										           *             *		9.00	]
					[SystemBuild										           *             *		211.00	]
					[MetalFrame											           *             *		14.00	]
					
				)
			infiltrations:
				(lookup-table
					name:"RDSAP wall infiltration rates"
					row-keys:[(wall.construction)]
					[Construction	Infiltration]
					[MetalFrame		0.25		]
					[TimberFrame	0.25		]
					[*				0.35		]
				)
		)
)