(context.named-actions
	(include href:../resetting/resetToSap)
    ; option to provide inputs like below v_assessment_cost = 150 
    ; calibration_coefficient = 2 solidwall_measure_lifetime = 20  which could be 
    ; used in main scenario like below  $v_assessment_cost = 150 
    ; $calibration_coefficient = 2 $solidwall_measure_lifetime = 20
    (do all:true
        name: eco_assumptions
        (sap.occupancy)
        (reset-to-sap)
        (counterfactual.carbon)
        (counterfactual.weather))
    (yield
        vars: [ (var v_measure_coefficient -1.3086649)
                   (var repayment-coefficient -0.1230)
                   (var v_measure_lifetime 20)
                   (var v_in_use_factors 0.9) ]
        name: m_cavity
        update-flags: [cavity]
        delegate: (measure.wall-insulation
                       type: Cavity))
    (yield
        vars: [ (var v_measure_coefficient -2.1666049)
                   (var repayment-coefficient -0.3394)
                   (var v_measure_lifetime 20)
                   (var v_in_use_factors 0.9)
                   (var v_swi_disincentive 4314) ]
        name: m_solid_wall_internal
        update-flags: [solid solid-internal]
        delegate: (measure.wall-insulation
                       type: Internal))
(yield
    vars: [ (var v_measure_coefficient -2.0643649)
               (var repayment-coefficient -0.3394)
               (var v_measure_lifetime 20)
               (var v_in_use_factors 0.9) ]
    name: m_solid_wall_external
	update-flags: [solid solid-external] 
    delegate: (measure.wall-insulation
                   type: External))
(yield
    vars: [ (var v_measure_coefficient -0.8612969)
               (var repayment-coefficient -0.1230)
               (var v_measure_lifetime 20)
               (var v_in_use_factors 0.9) ]
    name: m_loft
    update-flags: [loft]
    delegate: (measure.loft-insulation
                   top-up: true
                   thickness: 150))
(yield
    vars: [ (var v_measure_coefficient -1.3865649)
               (var repayment-coefficient -0.1230)
               (var v_measure_lifetime 20)
               (var v_in_use_factors 0.9) ]
    name: m_loft_and_cavity
    update-flags: [loft cavity]
    delegate: (do all:true
                   (measure.loft-insulation
                       top-up: true
                       thickness: 150)
                   (measure.wall-insulation
                       type: Cavity)))
)