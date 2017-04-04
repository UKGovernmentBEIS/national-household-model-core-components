package uk.org.cse.stockimport.domain.services;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.DTOField;

/**
 * The superinterface for space heating and water heating DTOs, containing common fields
 * 
 * @author hinton
 * @since 1.0
 */
public interface IHeatingDTO extends IBasicDTO {
	public static final String TARIFF_FIELD = "ElectricTariff";
	public static final String COMMUNUTYBASEDCHARGE_FIELD = "CommunityChargingUsageBased";
	public static final String CHPFRACTION_FIELD = "CHPFraction";
	
	public static final String WINNTEREFFICIENCY_FIELD = "WinterEfficiency";
	public static final String SUMMEREFFICIENCY_FIELD = "SummerEfficiency";
	public static final String BASICEFFICIENCY_FIELD = "BasicEfficiency";
	public static final String FLUETYPE_FIELD = "flueType";
		   
    public static final String IS_PCDB_MATCH_FIELD = "PcdbMatch";

	@DTOField(value=FLUETYPE_FIELD)
	public Optional<FlueType> getFlueType() ;
	public void setFlueType(final Optional<FlueType> flueType);

	@DTOField(value=CHPFRACTION_FIELD)
	public Optional<Double> getChpFraction();
	public void setChpFraction(Optional<Double> chpFraction);
	
	@DTOField(value=TARIFF_FIELD)
	public Optional<ElectricityTariffType> getElectricTariff();
	public void setElectricTariff(Optional<ElectricityTariffType> electricTariff);
	
	@DTOField(value=COMMUNUTYBASEDCHARGE_FIELD)
	public Optional<Boolean> getCommunityChargingUsageBased();
	public void setCommunityChargingUsageBased(final Optional<Boolean> value);
	
	@DTOField(value=SUMMEREFFICIENCY_FIELD)
	public Optional<Double> getSummerEfficiency();
	public void setSummerEfficiency(Optional<Double> summerEfficiency);
	
	@DTOField(value=WINNTEREFFICIENCY_FIELD)
	public Optional<Double> getWinterEfficiency();
	public void setWinterEfficiency(Optional<Double> winterEfficiency);
	
	@DTOField(BASICEFFICIENCY_FIELD)
	public double getBasicEfficiency();
	public void setBasicEfficiency(double basicEfficiency);
	
	@DTOField(value=IS_PCDB_MATCH_FIELD, description="Indicates whether boiler detailed came from a PCDB match.")
    public Optional<Boolean> getPcdbMatch();
    public void setPcdbMatch(final Optional<Boolean> isMatch);
}
