## I have turned this test off, because it is not a test.
## (tom)

if (FALSE) {
    trybrary(ggplot2)
    trybrary(dplyr)

    probe <- load.probe("report") %>%
        select(
            aacode = aacode..Before.,
            occupancy = occupancy..Before.,
            type = type..Before.,
            water.demand = water.demand..Before.,

            water1.label = water1.label..Before.,
            water2.label = water2.label..Before.,
            space1.label = space1.label..Before.,
            space2.label = space2.label..Before.,

            water1 = water1..Before.,
            water2 = water2..Before.,
            space1 = space1..Before.,
            space2 = space2..Before.)

    if (nrow(probe) > length(probe$aacode)) {
        fail.test("There were duplicate rows in the output.")
    }

    ## Filter heat pumps out since they're not very interesting.
    probe <- probe %>% filter(type != "HeatPump")

    my.points <- geom_point(shape = 3, alpha = 0.01)
    facet.by.type <- facet_wrap("type")
    facet.by.fuel <- facet_wrap("fuel")

    ## ## Space Heating
    ## ## Primary Spacing Heating

    ## Plot label vs in-situ
    ggplot(aes(x = space1.label, y = space1), data = probe) + facet.by.type + my.points
    ggsave("space-primary-label-vs-in-situ.png")

    ## ## Secondary Space Heating
    ## Plot label vs in-situ
    ggplot(aes(x = space2.label, y = space2), data = probe) + facet.by.type + my.points
    ggsave("space-secondary-label-vs-in-situ.png")

    ## ## Hot Water
    ## Demand is linearly correlated with occupancy
    ggplot(aes(x = occupancy, y = water.demand), data = probe) + my.points
    ggsave("water-demand-vs-occupancy.png")

    ## ## Central Hot Water
    ## Label vs in-situ efficiency: these are not well correlated.
    ggplot(aes(x = water1.label, y = water1), data = probe) + facet.by.type + my.points
    ggsave("water-central-label-vs-in-situ.png")

    ## Efficiency is slightly correlated with demand.
    ggplot(aes(x = water.demand, y = water1), data = probe) + my.points
    ggsave("water-central-in-situ-vs-demand.png")

    ## ## Auxiliary Hot Water
    ## There are no auxiliary water heaters in the stock.
    all(probe$water2.label == 0)
    all(is.nan(probe$water2))
}
