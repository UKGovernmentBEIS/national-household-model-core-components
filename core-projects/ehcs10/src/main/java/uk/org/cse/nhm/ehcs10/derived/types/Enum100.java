package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum100 {
    @SavEnumMapping({ "Question not applicable - owner occupier" })
    __MISSING,

    @SavEnumMapping({ "Raw rent/HB data used", "raw rent/HB data used" })
    RawRent_HBDataUsed,

    @SavEnumMapping("Rent/HB overwritten as value implausible")
    Rent_HBOverwrittenAsValueImplausible,

    @SavEnumMapping("Rent/HB imputed as value missing")
    Rent_HBImputedAsValueMissing,

}
