(scenario stock-id: test-survey-cases
          start-date: 01/01/2012
          end-date: 01/01/2014
          quantum: 400

	(template /! [@a @b]
		(function.case
			(when (house.value-is exactly:0 (+ @a @b)) 0.5)
			default: (/ @a @b)
		)
	)

	(template electricity [] (+ (house.energy-use by-fuel:PeakElectricity) (house.energy-use by-fuel:OffPeakElectricity))) 

	(context.calibration
		(polynomial
			fuels:PeakElectricity
			(* 100
				(/! a:(house.energy-use by-fuel:PeakElectricity) b:(electricity)
				)
			)
			1
		)
		
		(polynomial
			fuels:OffPeakElectricity
			(* 100 (- 1
				(/! a:(house.energy-use by-fuel:PeakElectricity) b:(electricity) )))
			1
		)
	)
)