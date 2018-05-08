package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum63 {
    @SavEnumMapping({"Question not applicable - owner occupier"})
    __MISSING,
    @SavEnumMapping("Rent excluding services - value overwritten as implausible")
    RentExcludingServices_ValueOverwrittenAsImplausible,
    @SavEnumMapping("Rent excluding services - value provided")
    RentExcludingServices_ValueProvided,
    @SavEnumMapping("Rent excluding services - value modelled")
    RentExcludingServices_ValueModelled,
    @SavEnumMapping({"Value same as rentwkx - rent does not include services",
        "value same as rentwkx - rent does not include services"})
    ValueSameAsRentwkx_RentDoesNotIncludeServices,

}
