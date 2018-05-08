package uk.org.cse.nhm.ehcs10.physical.types;

import uk.org.cse.nhm.spss.wrap.SavEnumMapping;

public enum Enum1186 {
    @SavEnumMapping({"Question Not Applicable", "Don't Know"})
    __MISSING,
    @SavEnumMapping({"No private plot or shared plot", "No private or shared plot"})
    NoPrivatePlotOrSharedPlot,
    @SavEnumMapping({"Private plot", "Private plot exist"})
    PrivatePlot,
    @SavEnumMapping("Shared plot only")
    SharedPlotOnly,

}
