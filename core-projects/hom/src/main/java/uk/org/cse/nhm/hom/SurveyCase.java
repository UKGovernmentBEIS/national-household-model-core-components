package uk.org.cse.nhm.hom;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;

/**
 * This is the representation of a survey case, suitable for serialization into mongo.
 * 
 * It contains representations for the various different dimensions into which a house is sliced
 * for the simulation's purposes.
 * 
 * All of the actual data are stored in objects within this; it's just a big container.
 * 
 * @author hinton
 *
 */
@JsonTypeInfo(use=Id.CLASS, property="_class", defaultImpl=SurveyCase.class)
public class SurveyCase implements IEnergyCalculatorHouseCase {
	private BasicCaseAttributes basicAttributes;
	private ITechnologyModel technologies;
	private StructureModel structure;
	private People people;
    private FinancialAttributes financialAttributes;
    private Set<String> importLog;
    
    Map<String, String> additionalProperties;
	
	public SurveyCase() {
		
	}

	public BasicCaseAttributes getBasicAttributes() {
		return basicAttributes;
	}

	public void setBasicAttributes(final BasicCaseAttributes basicAttributes) {
		this.basicAttributes = basicAttributes;
	}

	public ITechnologyModel getTechnologies() {
		return technologies;
	}

	public void setTechnologies(final ITechnologyModel technologies) {
		this.technologies = technologies;
	}

	public StructureModel getStructure() {
		return structure;
	}

	public void setStructure(final StructureModel structure) {
		this.structure = structure;
	}
	
	public People getPeople() {
		return people;
	}

	public void setPeople(final People people) {
		this.people = people;
	}

	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor) {
		structure.accept(visitor);
        if (technologies != null)
            technologies.accept(constants, parameters, visitor, new AtomicInteger(), null);
	}

	@Override
	public double getFloorArea() {
		return structure.getFloorArea();
	}

	@Override
	public double getLivingAreaProportionOfFloorArea() {
		return structure.getLivingAreaProportionOfFloorArea();
	}

	@Override
	public double getInterzoneSpecificHeatLoss() {
		return structure.getInterzoneSpecificHeatLoss();
	}

	@Override
	public double getHouseVolume() {
		return structure.getVolume();
	}

	@Override
	public int getNumberOfStoreys() {
		return structure.getNumberOfStoreys();
	}

	@Override
	public boolean hasDraughtLobby() {
		return structure.hasDraughtLobby();
	}

	@Override
	public double getZoneTwoHeatedProportion() {
		return structure.getZoneTwoHeatedProportion();
	}

	@Override
	public int getBuildYear() {
		return basicAttributes.getBuildYear();
	}

	@Override
	public double getDraughtStrippedProportion() {
		return structure.getDraughtStrippedProportion();
	}

	@Override
	public int getNumberOfShelteredSides() {
		return structure.getNumberOfShelteredSides();
	}
	
	@Override
	public SiteExposureType getSiteExposure() {
		return basicAttributes.getSiteExposure();
	}

    /**
     * Return the financialAttributes.
     * 
     * @return the financialAttributes
     * @since 1.1.0
     */
    public FinancialAttributes getFinancialAttributes() {
        return financialAttributes;
    }

    /**
     * Set the financialAttributes.
     * 
     * @param financialAttributes the financialAttributes
     * @since 1.1.0
     */
    public void setFinancialAttributes(final FinancialAttributes financialAttributes) {
        this.financialAttributes = financialAttributes;
    }
    
    /**
     * @since 3.5.0
     */
    public Set<String> getImportLog() {
    	return importLog;
    }

    /**
     * @since 3.5.0
     */
    public void setImportLog(final Set<String> importLog) {
    	this.importLog = importLog;
    }

    /**
     * @since 3.6.0
     */
	public Map<String, String> getAdditionalProperties() {
		return additionalProperties;
	}

	/**
    * @since 3.6.0
    */
	public void setAdditionalProperties(final Map<String, String> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	/**
	 * @since 6.4.0
	 */
	@Override
	public double getThermalBridgingCoefficient() {
		return structure.getThermalBridgingCoefficient();
	}
}
