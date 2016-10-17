package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Doc(
	value = {
		"This action sets the various heating temperature controls for houses to which it is applied.",
		"There are four possible attributes which can be specified - for those which are left unspecified,",
		"no change will be made to the corresponding aspect of the house's temperature settings."
		})
@Bind("action.set-heating-temperatures")
public class XHeatingTemperaturesAction extends XFlaggedDwellingAction {
	public static final class P {
		public static final String livingAreaTemperature = "livingAreaTemperature";
		public static final String thresholdExternalTemperature = "thresholdExternalTemperature";
		public static final String temperatureDifference = "temperatureDifference";
		public static final String restofDwellingTemperature = "restOfDwellingTemperature";
		public static final String restOfDwellingHeatedProportion = "restOfDwellingHeatedProportion";
		public static final String desiredHeatingMonths = "desiredHeatingMonths";
	}
	private XNumber livingAreaTemperature = null;
	private XNumber thresholdExternalTemperature = null;
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
	@Doc("If specified, this action will set the living area demand temperature to this value (in celsius). This is the heating temperature in the main part of the house.")
	public XNumber getLivingAreaTemperature() {
		return livingAreaTemperature;
	}
	public void setLivingAreaTemperature(final XNumber livingAreaTemperature) {
		this.livingAreaTemperature = livingAreaTemperature;
	}
	
	@BindNamedArgument("threshold-external-temperature")
	@Doc({
		"If specified, this action will set the outside temperature threshold for heating to come on to this value (in celsius).",
		"The house will only be heated when the outside temperature is less than or equal to this value.",
		"SAP does not have an equivalent value, but instead defines the summer months to be the months in which heating is",
		"off, but because the NHM allows you to vary the external temperature the heating months must be derived instead."
	})
	public XNumber getThresholdExternalTemperature() {
		return thresholdExternalTemperature;
	}
	public void setThresholdExternalTemperature(final XNumber thresholdExternalTemperature) {
		this.thresholdExternalTemperature = thresholdExternalTemperature;
	}
	
	@BindNamedArgument("temperature-difference")
	@Doc(
			{
				"If specified, this action will set the living area / rest of house temperature difference to the given amount (in celsius).",
				"This is different from, and exclusive of, setting the rest-of-dwelling-temperature; when a temperature difference is specified",
				"SAP determines the rest of dwelling temperature using the responsiveness of the house's heating systems.",
				"When the rest of dwelling temperature is set explicitly, SAP will not compute it but will take the given value."
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
		"If at least one month is specified, and threshold external temperature is not specified, this will attempt to set a threshold external temperature",
		"such that the current weather would result in the heating being on during as many of the given months as possible and off otherwise."
	})
	public List<XMonth> getDesiredHeatingMonths() {
		return desiredHeatingMonths;
	}
	public void setDesiredHeatingMonths(final List<XMonth> desiredHeatingMonths) {
		this.desiredHeatingMonths = desiredHeatingMonths;
	}
}
