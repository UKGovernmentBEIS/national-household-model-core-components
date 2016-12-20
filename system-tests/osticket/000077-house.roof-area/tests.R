#!/usr/bin/Rscript

## Floating point error
EPSILON <- 0.01

roofs <- load.probe("roofs");

## 3. other dwellings with pitched roofs have lower corrected roof area

is.flat <- roofs$built.form..Before. %in% c("ConvertedFlat", "PurposeBuiltLowRiseFlat", "PurposeBuiltHighRiseFlat")
is.pitched <- roofs$type..Before. == "PitchedSlateOrTiles"
no.roof <- roofs$area..Before. == 0

## 1. Flats have no roof area
flats.with.roofs <- roofs[is.flat & (!no.roof),]
fail.test.if(nrow(flats.with.roofs) > 0, "Flats should not have roof area.")

## 2. Houses have roof area
houses.without.roofs <- roofs[!is.flat & no.roof,]
fail.test.if(nrow(houses.without.roofs) > 0, "Houses should have roof area.")

## 3. Adjustment should apply to pitched roofs
expected.adjustment <- cos(35 * pi / 180)

pitched <- roofs[is.pitched,]
pitched$expected <- pitched$area..Before. / expected.adjustment
adjustment.wrong <- abs(pitched$area.pitch.corrected..Before. - pitched$expected) > EPSILON
pitched.adjustment.wrong <- roofs[adjustment.wrong,]

fail.test.if(
    nrow(pitched.adjustment.wrong) > 0,
    sprintf("Houses with pitched roofs should get the pitch adjustment but %s did not.", nrow(pitched.adjustment.wrong)))

## 4. Adjustment should not apply to other houses
adjusted.wrong.roof <- roofs[!is.pitched & (roofs$area.pitch.corrected..Before != roofs$area..Before.),]
fail.test.if(nrow(adjusted.wrong.roof) > 0, "Houses without pitched roofs should not get the pitch adjustment.")
