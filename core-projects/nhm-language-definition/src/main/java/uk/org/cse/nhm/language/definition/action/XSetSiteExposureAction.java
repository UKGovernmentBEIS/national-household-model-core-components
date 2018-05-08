package uk.org.cse.nhm.language.definition.action;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc({
    "Change the site exposure of a dwelling.",
    "In the BREDEM 2012 calculator, this is used to modify the air change rate of the house (see BREDEM 2012 Table 21 and equation 3E).",
    "In the SAP 2012 calculator, it has no effect."
})
@Bind("action.set-site-exposure")
@Category(CategoryType.RESETACTIONS)
public class XSetSiteExposureAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String siteExposure = "siteExposure";
    }

    private XSiteExposureType siteExposure;

    @BindPositionalArgument(0)
    @Prop(P.siteExposure)
    @NotNull(message = "action.set-site-exposure must specify a site exposure to set on the dwelling.")
    public XSiteExposureType getSiteExposure() {
        return siteExposure;
    }

    public void setSiteExposure(XSiteExposureType siteExposure) {
        this.siteExposure = siteExposure;
    }

    public enum XSiteExposureType {
        @Doc("Coastal and hill top sites. Any dwelling on the 10th floor or above in a high rise block.")
        Exposed,
        @Doc("Open sites not in the exposed category. Dwellings on the 6th to 9th floor of tower blocks.")
        AboveAverage,
        @Doc("Most rural and sub-urban sites. Dwellings on the 4th and 5th floors, or on the 3rd floor in an urban location. City centre sites close to high rise developments.")
        Average,
        @Doc("Partially sheltered urban and rural sites where there is some geographical reduction in local wind speed. Three storey dwellings on sheltered sites.")
        BelowAverage,
        @Doc("Sites where the local geography provides shelter from prevailing winds (e.g. valley or local hollow). City centre sites that are not close to high rise developments.")
        Sheltered
    }
}
