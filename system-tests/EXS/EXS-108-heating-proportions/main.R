
## Test to compare the energy use of the stock to a previous known
## version.

## If this fails, the energy use has failed. We'll output some graphs
## and a table of numbers to help find out why.

## If, following investigation, you decided that the changes are
## correct (e.g. we have fixed a bug in the energy calculator),
## replace output-old.zip with the contents of the new output.zip
## file.

old.energy <- read.csv(unz("output-old.zip",
                           encoding="UTF-8",
                           filename="dwelling/probe-energy.tab"),
                       header=TRUE, sep="\t")

new.energy <- load.probe("energy")

comparison.0 <- merge(old.energy, new.energy,
                      by="dwelling.id", all=TRUE)

bad <- any(comparison.0$energy..Before.x. !=
                 comparison.0$energy..Before.y.)
fail.test.if(bad, "some energy use has changed - I will try and make charts, but who knows how that will go")

if (bad) {
    trybrary(ggplot2)
    trybrary(dplyr)

    comparison <-
        old.energy %>%
        select(dwelling.id,
               weight,
               old = energy..Before.,
               old.temp = temp..Before.,
               old.water = water.heating..Before.,
               old.space = space.heating..Before.,
               fuel = fuel..Before.,
               heater = heater..Before.) %>%
        left_join(new.energy, by="dwelling.id") %>%
        select(old ,
               old.temp ,
               old.water ,
               old.space ,
               fuel ,
               heater,
               aacode = aacode..Before.,
               new = energy..Before.,
               new.temp = temp..Before.,
               new.water = water.heating..Before.,
               new.space = space.heating..Before.) %>%
        mutate(delta.temp = new.temp - old.temp,
               delta.E = new - old)

    ## Change in background temperature
    qplot(new.temp - old.temp, data = comparison)
    ggsave("temperature-change.svg")
    

    ## Change in water heating
    qplot(old.water, new.water, data=comparison) + geom_hex()
    ggsave("water energy use before and after.svg")
    
    qplot(new.water / old.water, data = comparison)
    ggsave("change in water energy use.svg")
    
    qplot(new.water / old.water, heater, data = comparison)
    ggsave("change in water energy use by primary space heater.svg")
    
    qplot(new.water / old.water, fuel, data = comparison)
    ggsave("change in water energy use by main heating fuel.svg")

    ## Change in space heating by heater and fuel
    qplot(old.space, new.space, data = comparison) + geom_hex()
    ggsave("space heating energy use before and after.svg")
    
    qplot(new.space / old.space, data = comparison)
    ggsave("change in space heating energy use.svg")
    
    qplot(new.space / old.space, heater, data = comparison)
    ggsave("change in space heating energy use by primary space heater.svg")
    
    qplot(new.space / old.space, fuel, data = comparison)
    ggsave("change in space heating energy use by main heating fuel.svg")

    ## Change in water heating vs change in space heating.
    qplot(new.water / old.water, new.space / old.space, data = comparison) + geom_hex()
    ggsave("change in water vs change in space heating.svg")    

    write.csv(comparison, "comparison.csv")
    
    fail.test("Energy has changed, charts and tables have been created to explain the difference.")
}
