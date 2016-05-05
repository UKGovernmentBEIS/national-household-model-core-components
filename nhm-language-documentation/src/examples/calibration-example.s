(example
    caption: "Calibrating energy use for all houses with a polynomial."
    description: "The NHM's energy calculator calibration rules allow you to modify the results of the energy calculation.
This example shows how you can use a polynomial calibration rule to scale or offset energy use."

    (context.calibration ; this belongs with a scenario at the top level
        ;; Each thing within context.calibration is a calibration rule.
        ;; Rules are applied sequentially, each transforming the results
        ;; from the previous rule.

        ;; this polynomial rule is equivalent to saying y = 100 * 0.8x
        ;; for gas and electricity consumption.
        (polynomial
            fuels: [MainsGas PeakElectricity OffPeakElectricity]
            100 ;; add 100kWh
            80% ;; and scale original by 80%
            ;; if you wrote more terms here, they would be higher order
            ;; in the polynomial. This one is linear, as it has only
            ;; two terms.
        )

        ;; This would add another 50 to mains gas for detached houses,
        ;; on top of the previous adjustment (a 0th order polynomial
        ;; having only 1 term). Note how you can use a function which
        ;; varies for different houses if you want, so the terms
        ;; need not be constant.

        (polynomial
            fuels: MainsGas
            (function.case (when (house.built-form-is detached) 50) default:0))

        ;; so the final result for a detached house using 500 units of gas
        ;; would be (100 + 80% * 500) + 50 = 550. A non-detached house using
        ;; 500 would still use 500. A non-detached house using 1000 units would
        ;; be adjusted to 900 and so on.
    )
)
