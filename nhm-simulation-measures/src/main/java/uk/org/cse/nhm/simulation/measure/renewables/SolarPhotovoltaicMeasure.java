package uk.org.cse.nhm.simulation.measure.renewables;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.impl.TechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.Payment;

public class SolarPhotovoltaicMeasure extends AbstractMeasure {

	private final IComponentsFunction<Number> efficiency;
	private final IComponentsFunction<Number> roofCoverage;
	private final IComponentsFunction<Number> capex;
	private final IDimension<ITechnologyModel> techDimension;
	private final IDimension<StructureModel> structureDimension;
	private final ILogEntryHandler log;
	private final ITechnologiesFactory techFactory;
	private final IComponentsFunction<Number> ownUse;

	@AssistedInject
	public SolarPhotovoltaicMeasure(
			@Assisted("efficiency") final IComponentsFunction<Number> efficiency,
			@Assisted("roofCoverage") final IComponentsFunction<Number> roofCoverage,
			@Assisted("capex") final IComponentsFunction<Number> capex,
			@Assisted("ownUse") final IComponentsFunction<Number> ownUse,
			final IDimension<ITechnologyModel> techDimension,
			final IDimension<StructureModel> structureDimension,
			final ILogEntryHandler log) {
		this.efficiency = efficiency;
		this.roofCoverage = roofCoverage;
		this.capex = capex;
		this.ownUse = ownUse;
		this.techDimension = techDimension;
		this.structureDimension = structureDimension;
		this.log = log;
		
		techFactory = ITechnologiesFactory.eINSTANCE;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
		final ITechnologyModel tech = scope.get(techDimension);
		
		if (tech.getSolarPhotovoltaic() != null) {
			return false;
		}
		
		final double remainingArea = calcRemainingArea(scope, tech);
		if (remainingArea > 0) {
			scope.modify(techDimension, new IModifier<ITechnologyModel>(){
				
				int dID = scope.getDwellingID();

				@Override
				public boolean modify(final ITechnologyModel modifiableTech) {
					final double roofProportion = clampProportion(roofCoverage.compute(scope, lets).doubleValue(), dID);
					final double areaToInstall = roofProportion * remainingArea;
					final double _ownUse = clampProportion(ownUse.compute(scope, lets).doubleValue(), dID);
					final ISolarPhotovoltaic pv = techFactory.createSolarPhotovoltaic();
					
					pv.setArea(areaToInstall);
					pv.setEfficiency(Efficiency.fromDouble(clampEfficiency(efficiency.compute(scope, lets).doubleValue(), dID)));
					pv.setOwnUseProportion(_ownUse);
					modifiableTech.setSolarPhotovoltaic(pv);
					
					scope.addNote(SizingResult.suitable(areaToInstall, Units.SQUARE_METRES));
					
					final double cost = capex.compute(scope, lets).doubleValue();
					scope.addTransaction(Payment.capexToMarket(cost));
					
					scope.addNote(new TechnologyInstallationDetails(
							SolarPhotovoltaicMeasure.this, 
							TechnologyType.solaPhotovoltaic(), 
							areaToInstall, 
							Units.SQUARE_METRES, 
							cost, 
							0));
					
					
					return true;
				}
				
				private double clampProportion(final double proportion, final int dwellingID) {
					return clampAndWarn(proportion, dwellingID, "roof-coverage", 1.0, 0.0);
				}
				
				private double clampEfficiency(final double efficiency, final int dwellingID) {
					return clampAndWarn(efficiency, dwellingID, "efficiency", 1.0, 0.01);
				}

				private double clampAndWarn(final double value, final int dwellingID, final String name, final double upper, final double lower) {
					if (value < lower) {
						log.acceptLogEntry(new WarningLogEntry(
								String.format("Attempted to install photovoltaic panels with a %s which was out of bounds. Using fallback value instead.", name),
								ImmutableMap.<String, String>builder()
										.put("measure", SolarPhotovoltaicMeasure.this.getIdentifier().getName())
										.put("dwellingID", Integer.toString(dwellingID))
										.put("invalid", Double.toString(value))
										.put("fallback", Double.toString(lower))
										.build()));
						return lower;
						
					} else if (value > upper) {
						log.acceptLogEntry(new WarningLogEntry(
								String.format("Attempted to install photovoltaic panels with a %s which was out of bounds. Using fallback value instead.", name),
								ImmutableMap.<String, String>builder()
										.put("measure", SolarPhotovoltaicMeasure.this.getIdentifier().getName())
										.put("dwellingID", Integer.toString(dwellingID))
										.put("invalid", Double.toString(value))
										.put("fallback", Double.toString(upper))
										.build()));
								
						return upper;
						
					} else {
						return value;
					}
				}
			});
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(techDimension);
		
		if (tech.getSolarPhotovoltaic() != null) {
			return false;
		}
		
		return calcRemainingArea(scope, tech) > 0;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
	
	private double calcRemainingArea(final IComponentsScope scope, final ITechnologyModel tech) {
		final double roofArea = scope.get(structureDimension).getExternalRoofArea();
		final ICentralWaterSystem centralHotWater = tech.getCentralWaterSystem();
		if (centralHotWater != null) {
			final ISolarWaterHeater solarHotWater = centralHotWater.getSolarWaterHeater();
			if (solarHotWater != null) {
				return (roofArea - solarHotWater.getArea());
			}
		}
		
		return roofArea;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
