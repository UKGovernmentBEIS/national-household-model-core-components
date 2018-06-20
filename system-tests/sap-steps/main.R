trybrary(dplyr)
trybrary(tidyr)

model.out <- load.probe("sap-steps") %>%
    mutate(property = gsub(".+-(.+)$", "\\1", aacode)) %>%
    gather(variable, actual, starts_with("v")) %>%
    select(property, variable, actual)

expected <- read.csv("./expectations.tab", sep="\t")
## in case it's actually now more correct
write.table(model.out, "./actuals.tab", sep="\t", row.names = FALSE, col.names = TRUE)

joined <- full_join(model.out, expected, by=c("property", "variable")) %>%
    filter(expectation != actual) %>%
    arrange(property, variable)

fail.test.if(nrow(joined) > 0,
             with(joined,
                  sprintf("%-10s %-6s %8.1f vs %-8.1f", property, variable, expectation, actual)))
