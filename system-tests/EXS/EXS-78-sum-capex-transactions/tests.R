agg <- load.aggregate("sum-transactions")
probe <- load.probe("probe")

print("aggregate report:")
print(agg)
print("summary of probe report:")
print(summary(probe))

probed.total.capex <- sum(probe$weight * probe$capex..after.)

fail.test.if(probed.total.capex <= 0,
             "probed total capex is not positive")

agg.total.capex <- agg$capex1[[1]]
agg.total.CAPEX <- agg$capex2[[1]]
agg.total.spend <- agg$all[[1]]

fail.test.if(probed.total.capex != agg.total.CAPEX,
             paste("aggregated value for :CAPEX was", agg.total.CAPEX, "not", probed.total.capex))

fail.test.if(probed.total.capex != agg.total.capex,
             paste("aggregated value for capex was", agg.total.capex, "not", probed.total.capex))

fail.test.if(probed.total.capex != agg.total.spend,
             paste("aggregate value for all trans. was", agg.total.spend, "not", probed.total.capex))

fail.test.if(agg$none[[1]] != 0,
             paste("the sum of no transactions should be zero!"))
