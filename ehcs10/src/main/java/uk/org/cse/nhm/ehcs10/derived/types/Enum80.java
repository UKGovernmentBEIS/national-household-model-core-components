package uk.org.cse.nhm.ehcs10.derived.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum80 {
    @SavEnumMapping({ "Was below basic IS - imputed using basic IS",
            "was below basic IS/PC - imputed using basic IS/PC" })
    WasBelowBasicIS_ImputedUsingBasicIS,

    @SavEnumMapping("Some private sources imputed")
    SomePrivateSourcesImputed,

    @SavEnumMapping({ "None, all data OK", "none, all data OK" })
    None_AllDataOK,

    @SavEnumMapping("Some bens imputed or changed")
    SomeBensImputedOrChanged,

    @SavEnumMapping("Some priv and some bens imputed")
    SomePrivAndSomeBensImputed,

    @SavEnumMapping({ "Was below basic IS - imputed using group median",
            "was below basic IS/PC - imputed using group median" })
    WasBelowBasicIS_ImputedUsingGroupMedian,

    @SavEnumMapping({ "HHold total imputed using group median", "household total imputed using group median" })
    HHoldTotalImputedUsingGroupMedian,

    @SavEnumMapping({ "Was below basic IS - imputed using basic IS plus disab prems",
            "was below basic IS/PC - imputed using basic IS/PC plus disab prems" })
    WasBelowBasicIS_ImputedUsingBasicISPlusDisabPrems,

}