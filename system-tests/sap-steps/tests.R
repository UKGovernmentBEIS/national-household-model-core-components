trybrary(dplyr)
trybrary(tidyr)

model.out <- load.probe("sap-steps") %>%
    mutate(property = gsub(".+-(.+)$", "\\1", aacode)) %>%
    gather(variable, actual, starts_with("v")) %>%
    select(property, variable, actual)

expected <- read.csv("./expectations.tab", sep="\t") %>%
    mutate(property = gsub(".+-(.+)$", "\\1", aacode)) %>%
    gather(variable, expectation, starts_with("v")) %>%
    select(property, variable, expectation)

joined <- full_join(model.out, expected, by=c("property", "variable")) %>%
    filter(expectation != actual)

print(joined)

fail.test.if(nrow(joined) > 0,
             sprintf("Differences in steps for %s",
                     paste0(collapse=", ",
                            sprintf("%s %s %s %s", joined$property, joined$variable,
                                    joined$expectation, joined$value))))
