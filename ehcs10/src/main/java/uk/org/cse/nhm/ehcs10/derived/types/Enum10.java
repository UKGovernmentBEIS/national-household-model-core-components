package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum10 {
    @SavEnumMapping({ "Not Applicable", "not applicable - owner occupier", "Question Not Applicable",
            "Section Not Applicable", "Unknown" })
    __MISSING,

    @SavEnumMapping({ "Yes", "yes" })
    Yes,

    @SavEnumMapping({ "No", "no" })
    No,

}
