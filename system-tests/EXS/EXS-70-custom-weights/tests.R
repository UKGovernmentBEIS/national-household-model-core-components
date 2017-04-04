probe <- load.probe("report")

fail.test.if(
    nrow(probe) <= 1,
    "There should be houses in the stock"
)

fail.test.if(
    !all(probe$weight..Before. == 1),
    "All dwelling weights should be 1."
)
