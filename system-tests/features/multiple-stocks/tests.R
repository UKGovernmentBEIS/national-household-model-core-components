data <- load.probe("p");

## there should be two rows

fail.test.if(nrow(data) != 2,
             "There should be exactly two cases in the combined stocks.")
