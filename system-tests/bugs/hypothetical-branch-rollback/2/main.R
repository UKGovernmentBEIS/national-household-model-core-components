result <- load.probe("in-order")

fail.test.if(any(result$D..before. != 9),
             "Before value should be 9 in all cases")

fail.test.if(any(result$D..after. != 2),
             "After value should be 2 in all cases")
