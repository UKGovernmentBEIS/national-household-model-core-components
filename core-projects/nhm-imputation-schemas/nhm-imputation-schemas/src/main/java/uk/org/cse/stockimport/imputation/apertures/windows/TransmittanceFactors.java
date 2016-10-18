package uk.org.cse.stockimport.imputation.apertures.windows;

import java.util.EnumMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;

import com.google.common.collect.ImmutableMap;

/**
 * Transmittance factors, as per the CHM spreadsheet.
 * @since 1.0
 */
public class TransmittanceFactors implements ITransmittanceFactors {
	private static final Logger log = LoggerFactory.getLogger(TransmittanceFactors.class);
	
	private double singleGlazingGainsTransmittance;
	private double singleGlazingLightTransmittance;
	private double secondaryGlazingGainsTransmittance;
	private double secondaryGlazingLightTransmittance;
	
	private Map<WindowInsulationType, Double> doubleGlazingGainsTransmittance = new EnumMap<WindowInsulationType, Double>(WindowInsulationType.class);
	private Map<WindowInsulationType, Double> tripleGlazingGainsTransmittance = new EnumMap<WindowInsulationType, Double>(WindowInsulationType.class);
				
	private Map<WindowInsulationType, Double> doubleGlazingLightTransmittance = new EnumMap<WindowInsulationType, Double>(WindowInsulationType.class);
	private Map<WindowInsulationType, Double> tripleGlazingLightTransmittance = new EnumMap<WindowInsulationType, Double>(WindowInsulationType.class);
	
	/**
	 * Uses RdSAP/CHM data assumptions by default
	 */
	public TransmittanceFactors(){
		this(true);
	}
	
	public TransmittanceFactors(boolean useRdSAPValues){
		if (useRdSAPValues){
			singleGlazingGainsTransmittance = 0.85;
			singleGlazingLightTransmittance = 0.90;
			secondaryGlazingGainsTransmittance = 0.76;
			secondaryGlazingLightTransmittance = 0.8;
			
			doubleGlazingGainsTransmittance.putAll(ImmutableMap.of(
							WindowInsulationType.Air, 0.76,
							WindowInsulationType.LowESoftCoat, 0.63,
							WindowInsulationType.LowEHardCoat, 0.72));
			
			tripleGlazingGainsTransmittance.putAll(ImmutableMap.of(
							WindowInsulationType.Air, 0.68,
							WindowInsulationType.LowESoftCoat, 0.57,
							WindowInsulationType.LowEHardCoat, 0.64));
			
			doubleGlazingLightTransmittance.putAll(ImmutableMap.of(
							WindowInsulationType.Air, 0.8,
							WindowInsulationType.LowESoftCoat, 0.8,
							WindowInsulationType.LowEHardCoat, 0.8));
			
			tripleGlazingLightTransmittance.putAll(ImmutableMap.of(
							WindowInsulationType.Air, 0.7,
							WindowInsulationType.LowESoftCoat, 0.7,
							WindowInsulationType.LowEHardCoat, 0.7));
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.apertures.windows.ITransmittanceFactors#getGainsTransmittance(uk.org.cse.nhm.hom.components.fabric.types.GlazingType, uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType)
	 */
    @Override
	public double getGainsTransmittance(final GlazingType glazingType, final WindowInsulationType insulation) {
		switch (glazingType) {
		case Secondary:
			return secondaryGlazingGainsTransmittance;
		case Single:
			return singleGlazingGainsTransmittance;
		case Double:
			return doubleGlazingGainsTransmittance.get(insulation);
		case Triple:
			return tripleGlazingGainsTransmittance.get(insulation);
		default:
			log.error("Unknown glazing type {}", glazingType);
			return 0;
		}
	}
	
    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.apertures.windows.ITransmittanceFactors#getLightTransmittance(uk.org.cse.nhm.hom.components.fabric.types.GlazingType, uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType)
	 */
    @Override
	public double getLightTransmittance(final GlazingType glazingType, final WindowInsulationType insulation) {
		switch (glazingType) {
		case Secondary:
			return secondaryGlazingLightTransmittance;
		case Single:
			return singleGlazingLightTransmittance;
		case Double:
			return doubleGlazingLightTransmittance.get(insulation);
		case Triple:
			return tripleGlazingLightTransmittance.get(insulation);
		default:
			log.error("Unknown glazing type {}", glazingType);
			return 0;
		}
	}

	public void setSingleGlazingGainsTransmittance(
			double singleGlazingGainsTransmittance) {
		this.singleGlazingGainsTransmittance = singleGlazingGainsTransmittance;
	}

	public void setSingleGlazingLightTransmittance(
			double singleGlazingLightTransmittance) {
		this.singleGlazingLightTransmittance = singleGlazingLightTransmittance;
	}

	public void setSecondaryGlazingGainsTransmittance(
			double secondaryGlazingGainsTransmittance) {
		this.secondaryGlazingGainsTransmittance = secondaryGlazingGainsTransmittance;
	}

	public void setSecondaryGlazingLightTransmittance(
			double secondaryGlazingLightTransmittance) {
		this.secondaryGlazingLightTransmittance = secondaryGlazingLightTransmittance;
	}
	
	public void addDoubleGlazingGainsTransimittance(WindowInsulationType insulationType, Double value){
		doubleGlazingGainsTransmittance.put(insulationType, value);
	}
	
	public void addDoubleGlazingLightTransimittance(WindowInsulationType insulationType, Double value){
		doubleGlazingLightTransmittance.put(insulationType, value);
	}
	
	public void addTripleGlazingGainsTransimittance(WindowInsulationType insulationType, Double value){
		tripleGlazingGainsTransmittance.put(insulationType, value);
	}
	
	public void addTripleGlazingLightTransimittance(WindowInsulationType insulationType, Double value){
		tripleGlazingLightTransmittance.put(insulationType, value);
	}
}
