package uk.org.cse.stockimport.imputation.walls;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band;

/**
 * Delegates to {@link WallUValueImputer} and {@link WallThicknessImputer} with some use of {@link AgeBandInterpolator} to handle
 * the EHS / SAP age band differences.
 * 
 * @author hinton
 * @since 1.0
 */
public class WallPropertyImputer implements IWallPropertyImputer {
	private static final Logger log = LoggerFactory.getLogger(WallPropertyImputer.class);
	
	/**
	 * The default thickness of insulation, in mm.
	 */
	private final double defaultInsulationThickness = 50;
	
	private IWallUValueImputer uValues = new WallUValueImputer();
	
	private IWallThicknessImputer thickness = new WallThicknessImputer();
	
	private IWallKValueImputer kValues = new WallKValueImputer();
	
	private IWallInfiltrationImputer infiltration = new WallInfiltrationImputer();
	
	public WallPropertyImputer(){
		
	}
	
	public WallPropertyImputer(final IWallPropertyTables wallPropertyTables){
		uValues = wallPropertyTables.getWallUValueImputer();
		thickness = wallPropertyTables.getWallThicknessImputer();
		kValues = wallPropertyTables.getWallKValueImputer();
		infiltration = wallPropertyTables.getWallInfiltrationImputer();
	}
	
	@Override
	public double getInternalWallKValue() {
		return kValues.getInternalWallKValue();
	}
	
	@Override
	public double getUValue(final SAPAgeBandValue.Band ageBand,
			final RegionType region, final WallConstructionType constructionType,
			final Map<WallInsulationType, Double> insulationThicknesses,
			final double wallThickness) {
		log.trace("U value band : {}", ageBand);
			return uValues.getUValue(ageBand, region, constructionType, insulationThicknesses, wallThickness);
	}
	
	@Override
	public double getUValue(final Band ageBand,
			final RegionType region, final WallConstructionType constructionType,
			final Set<WallInsulationType> insulations) {
		log.trace("Get U value for {}, {}, {}, {}", new Object[] {ageBand, region, constructionType, insulations});
		return getUValue(ageBand, region, constructionType, getInsulationThicknesses(insulations),
				getWallThickness(ageBand, region, constructionType, insulations));
	}
	
	private Map<WallInsulationType, Double> getInsulationThicknesses(
			final Set<WallInsulationType> insulations) {
		final EnumMap<WallInsulationType, Double> result = new EnumMap<WallInsulationType, Double>(WallInsulationType.class);
		
		for (final WallInsulationType type : WallInsulationType.values()) {
			result.put(type, 0d);
		}
		
		for (final WallInsulationType type : insulations) {
			result.put(type, defaultInsulationThickness);
		}
		
		return result;
	}

	@Override
	public double getWallThickness(final SAPAgeBandValue.Band ageBand,
			final RegionType region, final WallConstructionType construction,
			final Set<WallInsulationType> insulations) {
			return thickness.getWallThickness(ageBand, region, construction, insulations);
	}

	@Override
	public double getKValue(final WallConstructionType constructionType, final Set<WallInsulationType> insulationTypes) {
		return kValues.getKValue(constructionType, insulationTypes);
	}

	@Override
	public double getAirChangeRate(final WallConstructionType constructionType) {
		return infiltration.getAirChangeRate(constructionType);
	}
}
