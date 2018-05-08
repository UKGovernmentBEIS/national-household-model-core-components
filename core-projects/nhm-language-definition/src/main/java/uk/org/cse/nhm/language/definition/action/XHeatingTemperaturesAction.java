package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc(
        value = {
            "This action sets the various heating temperature controls for houses to which it is applied.",
            "For attributes which are left unspecified,",
            "no change will be made to the corresponding aspect of the house's temperature settings.",
            "These settings have no effect if the energy calculator is in SAP 2012 mode."
        })
@Bind("action.set-heating-temperatures")
public class XHeatingTemperaturesAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String livingAreaTemperature = "livingAreaTemperature";
        public static final String temperatureDifference = "temperatureDifference";
        public static final String restofDwellingTemperature = "restOfDwellingTemperature";
        public static final String restOfDwellingHeatedProportion = "restOfDwellingHeatedProportion";
        public static final String desiredHeatingMonths = "desiredHeatingMonths";
    }
    private XNumber livingAreaTemperature = null;
    private XNumber temperatureDifference = null;
    private XNumber restOfDwellingTemperature = null;
    private XNumber restOfDwellingHeatedProportion = null;
    private List<XMonth> desiredHeatingMonths = new ArrayList<>();

    public enum XMonth {
        January,
        February,
        March,
        April,
        May,
        June,
        July,
        August,
        September,
        October,
        November,
        December
    }

    @BindNamedArgument("living-area-temperature")
    @Doc({"If specified, this action will set the living area demand temperature to this value (in celsius). This is the heating temperature in the main part of the house.",
        "If no value for living-area-temperature has been set on a dwelling, then the defaul value of 19\u2103 from the Cambridge Household Model is used."})
    public XNumber getLivingAreaTemperature() {
        return livingAreaTemperature;
    }

    public void setLivingAreaTemperature(final XNumber livingAreaTemperature) {
        this.livingAreaTemperature = livingAreaTemperature;
    }

    @BindNamedArgument("temperature-difference")
    @Doc(
            {
                "If specified, this action will set the living area / rest of house temperature difference to the given amount (in celsius).",
                "This is different from, and exclusive of, setting the rest-of-dwelling-temperature; when a temperature difference is specified",
                "SAP determines the rest of dwelling temperature using the responsiveness of the house's heating systems.",
                "When the rest of dwelling temperature is set explicitly, SAP will not compute it but will take the given value.",
                "If no value is present for this on the dwelling, then the default value of 3\u2103 from the Cambridge Household Model is used."
            }
    )
    public XNumber getTemperatureDifference() {
        return temperatureDifference;
    }

    public void setTemperatureDifference(final XNumber temperatureDifference) {
        this.temperatureDifference = temperatureDifference;
    }

    @BindNamedArgument("rest-of-dwelling-temperature")

    @Doc({
        "If specified, this action will set the demand temperature in the rest of the house (in celsius). See temperature-difference for more detail",
        "on this. If temperature-difference is also specified, this value will be ignored (because they are exclusive)."
    })
    public XNumber getRestOfDwellingTemperature() {
        return restOfDwellingTemperature;
    }

    public void setRestOfDwellingTemperature(final XNumber restOfDwellingTemperature) {
        this.restOfDwellingTemperature = restOfDwellingTemperature;
    }

    @BindNamedArgument("rest-of-dwelling-heated-proportion")
    @Doc({
        "If specified, this action will set the heated proportion in the rest of the house (a number from 0 to 1).",
        "This is the parameter fz2htd used in calculation 7E of BREDEM 2012.",
        "This will have no effect if the energy calculator is set to SAP 2012 mode (when the proportion is always 1, or 100%)."
    })
    public XNumber getRestOfDwellingHeatedProportion() {
        return restOfDwellingHeatedProportion;
    }

    public void setRestOfDwellingHeatedProportion(final XNumber restOfDwellingHeatedProportion) {
        this.restOfDwellingHeatedProportion = restOfDwellingHeatedProportion;
    }

    @BindNamedArgument("desired-heating-months")
    @Doc({
        "If at least one month is specified, this will enable the heating during the specified months.",
        "Has no effect in SAP 2012 mode."

    })
    public List<XMonth> getDesiredHeatingMonths() {
        return desiredHeatingMonths;
    }

    public void setDesiredHeatingMonths(final List<XMonth> desiredHeatingMonths) {
        this.desiredHeatingMonths = desiredHeatingMonths;
    }
}
