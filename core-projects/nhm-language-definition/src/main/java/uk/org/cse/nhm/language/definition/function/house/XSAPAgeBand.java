package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("house.sap-age-band")
@Doc("The SAP age band of the house, derived from its build year.")
@ReturnsEnum(SAPAgeBandValue.Band.class)
public class XSAPAgeBand extends XCategoryFunction {
    public static class P {
        public static final String year = "year";
    }

    private int year = 2009;

    @Prop(P.year)
    @BindNamedArgument
    @Doc("The sap version to use; if this is less than 2012, then SAP age band K will be the maximum.")
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
}
