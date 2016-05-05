(policy
	(target name:initialize-technology-costs
			group:(group.all)
			exposure:(schedule.init)
			
			action:
				(action.reset-opex
					(lookup-table
						name:"Initial operating costs"
						
						row-keys:[(technology.type)]
						
						[Technology			Opex]
						[StandardBoiler		75]
						[InstantCombi		75]
						[StorageCombi		75]
						[GSHP				100]
						[ASHP				50]
					)
				)
	 )
)

(template decc.normal-sizes []
	(size max: 60
         (function.steps value: (house.peak-load)
                                round: Up
                                steps: [5 10 15 20 25 30 35 40 45 50 55 60]
                )
   	)
)

(template decc.large-sizes []
	(size max: 10000
         (function.steps value: (house.peak-load)
                                round: Up
                                steps: [0 10000]
                )
   	)
)

(template decc.wet-heating-capex []
	(function.case 
		default:  2000
	    (when (any
	                     (house.built-form-is Bungalow)
	                     (house.built-form-is SemiDetached)
	                     (house.built-form-is Detached)
	                     (house.built-form-is EndTerrace))
	          4000)
	 )
)

(template decc.boiler-opex [@fuel [@size (size.kw)]]
	(macro.match
		@fuel
		
		[Electricity 		75]
		[BiomassPellets 	(function.quadratic a: -0.06677 b: 7.39852 c:0 x: @size)]
		[BiomassWood	 	(function.quadratic a: -0.06677 b: 7.39852 c:0 x: @size)]
		[MainsGas			75]
		[Oil				75]
		
		default:(macro.error "Undefined fuel in decc.boiler-opex" @fuel)
	)
)

(template decc.boiler-capex [@fuel [@size (size.kw)]]
	(macro.match
		@fuel
		[Electricity 		2800]
		[BiomassPellets		(function.quadratic a: -7.67448 b: 850.40501 c: 0 x: @size)]
		[BiomassWood		(function.quadratic a: -7.67448 b: 850.40501 c: 0 x: @size)]
		[MainsGas			3300]
		[Oil				3100]
		
		default:(macro.error "Undefined fuel in decc.boiler-capex" @fuel)	
	)
)

(template decc.boiler-size [@fuel]
	(macro.match
		@fuel
		[Electricity 	(decc.large-sizes)]
		[BiomassPellets (decc.normal-sizes)]
		[BiomassWood 	(decc.normal-sizes)]
		[MainsGas		(decc.large-sizes)]
		[Oil			(decc.large-sizes)]
		
		default:(macro.error "Undefined fuel in decc.boiler-size")	
	)
)

(template measure.decc.standard-boiler [@fuel
										@efficiency
										[@name]
										[@cylinder-volume]
										[@cylinder-insulation-thickness]
										[@size (decc.boiler-size fuel:@fuel)]
										[@capex (decc.boiler-capex fuel:@fuel)]
										[@opex (decc.boiler-opex fuel:@fuel)]]
	(measure.standard-boiler
		(macro.maybe name: @name)
		winter-efficiency: @efficiency
		capex: @capex
		opex: @opex
		size: @size
		fuel: @fuel
		(macro.maybe cylinder-volume: @cylinder-volume)
		(macro.maybe cylinder-insulation-thickness: @cylinder-insulation-thickness)
		system-capex:(decc.wet-heating-capex)
	)
)

(template measure.decc.combi-boiler [@efficiency
									 [@name]
									 [@capex 2500]
									 [@opex 75]
									 [@size (decc.large-sizes)]
									 [@storage-volume 0]]
	(measure.combi-boiler
		(macro.maybe name:@name)
		winter-efficiency:@efficiency
		size:@size
		capex:@capex
		opex:@opex
		system-capex:(decc.wet-heating-capex)
	)
)

(template measure.decc.district-heating [	[@capex 0] 
											[@opex 0]
											[@name]
											@efficiency	]
	(measure.district-heating
		(macro.maybe name:@name)
		efficiency:@efficiency
		capex:@capex
		opex:@opex
		size:(decc.large-sizes)
		system-capex:(decc.wet-heating-capex)
	)
)

(template decc.heat-pump-capex		[ @type	[@size (size.kw)] ]
	(macro.match @type
		[GroundSource			(function.quadratic b: 2267.015530
                                                                  c: 0
                                                                  a: -20.068810
                                                                  x: @size
                                              )]
		[AirSource				(function.quadratic b: 1199.574660
                                                                  c: 0
                                                                  a: -30.397130
                                                                  x: @size
                                              )]
		default:(macro.error "Unknown type of ground source heat pump")
	)
)

(template decc.heat-pump-opex		[ @type	[@size (size.kw)] ]
	(macro.match @type
		[GroundSource			(function.quadratic b: 19.72304
                                                                 c: 0
                                                                 a: -0.17460
                                                                 x: @size
                                             )]
		[AirSource				(function.quadratic b: 13.67515
                                                                 c: 0
                                                                 a: -0.34653
                                                                 x: @size
                                             )]
		default:(macro.error "Unknown type of ground source heat pump")
	)
)

(template decc.heat-pump-size	[ @type ]
	(macro.match @type
		[AirSource			(size max: 20
                                                   (function.steps value: (house.peak-load)
                                                                          round: Up
                                                                          steps: [5 10 15 20]
                                                          )
                                             )]
		[GroundSource 		(size max: 65
                                                   (function.steps value: (house.peak-load)
                                                                          round: Up
                                                                          steps: [5 10 15 20 25 30 35 40 45 50 55 60 65]
                                                          )
                                             )]
		default:(macro.error "Unknown type of ground source heat pump")
	)
)
(template measure.decc.heat-pump	[	[@name]
										@type
										@cop	]
	(measure.heat-pump type:type
		(macro.maybe name:@name)
		cop:@cop
		capex:(decc.heat-pump-capex type:@type)
		opex:(decc.heat-pump-opex type:@type)
	)
)

(template measure.decc.wall-insulation	[	[@name]
											@type
											@resistance
											@thickness	]
	(measure.wall-insulation type:@type
		(macro.maybe name:@name)
		resistance: @resistance
		thickness: @thickness
		capex:
			(macro.match @type
				[Cavity 		500]
				[Internal		(function.quadratic b: 42.5 
                                                            c: 6837
                                                            a: 0
                                                            x: (size.m2))]
				[External		(function.quadratic b: 57.7 
                                                            c: 5508
                                                            a: 0
                                                            x: (size.m2)
                                                                   )]
				default:(macro.error "Unknown type of cavity wall insulation"))
	)
)

(template decc.loft-insulation-capex [] 300)