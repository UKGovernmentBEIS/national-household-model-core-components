deltas <- load.probe("delta-ins")

print("delta-ins probe:")
print(summary(deltas))

fail.test.if(any(deltas$original != deltas$stored.original),
             "the value stored in x should equal (original)")

fail.test.if(any(deltas$rise != - deltas$fall),
             "the rise should always be the opposite of the fall")

fail.test.if(any(abs((deltas$current - deltas$original) - deltas$rise) > 0.01),
             "the rise should be the the current minus original ")

xs <- load.probe("delta-xs")


print("delta-xs probe:")
print(summary(xs))

fail.test.if(any(xs$current != 4), "current should be 4")
fail.test.if(any(xs$prior != 2), "prior should be 4")
fail.test.if(any(xs$prior.prior != 1), "prior^2 should be 1")
fail.test.if(any(xs$rise != 2), "rise-here should be 2")
fail.test.if(any(xs$original.rise != 1), "original rise should be 1")
