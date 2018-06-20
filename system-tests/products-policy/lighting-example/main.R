trybrary(dplyr)
## verify lighting split was as intended at the end of each year

agg <- load.aggregate("proportion-of-lighting-in-stock-summary")

# this is just the table typed out from the scenario:
inputs <- read.csv(sep="\t",
                  textConnection("year	cfl	hal	incan	led
2014	0.28	0.44	0.24	0.04
2015	0.29	0.47	0.17	0.07
2016	0.29	0.49	0.12	0.1
2017	0.28	0.52	0.08	0.12
2018	0.26	0.54	0.05	0.15
2019	0.24	0.52	0.03	0.21
2020	0.21	0.50	0.02	0.27
2021	0.18	0.47	0.01	0.34
2022	0.15	0.43	0.00	0.42
2023	0.12	0.38	0.00	0.50
2024	0.10	0.33	0.00	0.57
2025	0.07	0.30	0.00	0.63
2026	0.05	0.26	0.00	0.69
2027	0.04	0.23	0.00	0.73
2028	0.03	0.20	0.00	0.77
2029	0.03	0.20	0.00	0.79
2030	0.03	0.17	0.00	0.80"))

clean.agg <- agg %>%
    transmute(year = as.integer(substring(Date, 1, 4)),
              cfl = cfl..After.,
              hal = hal..After.,
              led = led..After.,
              incan = inc..After.
              )

both <- clean.agg %>% left_join(inputs, by="year")

print(both)

with(both,
     fail.test.unless(
         all(abs(cfl.x - cfl.y) < 0.01) &
         all(abs(hal.x - hal.y) < 0.01) &
         all(abs(led.x - led.y) < 0.01) &
         all(abs(incan.x - incan.y) < 0.01),
         sprintf("CFL: %d HAL:%d LED:%d Incan:%d",
                 sum(cfl.x != cfl.y),
                 sum(hal.x != hal.y),
                 sum(led.x != led.y),
                 sum(incan.x != incan.y)
                 )))


## and that measure was always suitable for all dwellings

probe <- load.probe("proportion-of-lighting-in-stock")

fail.test.unless(
    all(probe$suitable == "true"),
    "some unsuitable rows"
)
