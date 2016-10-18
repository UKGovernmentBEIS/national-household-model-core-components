package uk.org.cse.nhm.simulation.measure.scaling;

import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XHeatingResponsivenessScaling;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XSpaceHeatingSystem;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ResponsivenessScalingAction extends ScalingAction {

	private static final String RESPONSIVENESS_OUT_OF_BOUNDS = "Action attempted to set responsiveness to an invalid value. Set to fallback value instead.";
	private final Set<XSpaceHeatingSystem> systems;
	private final IDimension<ITechnologyModel> techDimension;
	private final IConstants constants;

	@AssistedInject
	public ResponsivenessScalingAction(
			@Assisted final IComponentsFunction<Number> scalingFunction,
			@Assisted final Set<XSpaceHeatingSystem> systems,
			final IConstants constants,
			final IDimension<ITechnologyModel> techDimension,
			final ILogEntryHandler loggingService) {
				super(loggingService, scalingFunction);
				this.systems = systems;
				this.constants = constants;
				this.techDimension = techDimension;
	}
	
	@Override
	public void doApply(final double scaling, final ISettableComponentsScope scope)
			throws NHMException {
		
		final int dwellingID = scope.getDwellingID();
		
		scope.modify(techDimension, new IModifier<ITechnologyModel>(){

			@Override
			public boolean modify(final ITechnologyModel tech) {
				
				
				for (final XSpaceHeatingSystem s : systems) {
					switch(s) {
					case PrimarySpaceHeating:
						final IPrimarySpaceHeater primarySpaceHeater = tech.getPrimarySpaceHeater();
						
						if (primarySpaceHeater instanceof ICentralHeatingSystem) {
							final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
							
							if (heatSource != null) {
								heatSource.setOverrideResponsiveness(
										newResponsiveness(primarySpaceHeater.getDerivedResponsiveness(constants)));
							}
							
						} else if (primarySpaceHeater instanceof IStorageHeater) {
							final IStorageHeater storageHeater = (IStorageHeater) primarySpaceHeater;
							storageHeater.setResponsiveness(
									newResponsiveness(storageHeater.getResponsiveness()));
							
						} else if (primarySpaceHeater instanceof IWarmAirSystem) {
							((IWarmAirSystem) primarySpaceHeater).setOverrideResponsiveness(
									newResponsiveness(primarySpaceHeater.getDerivedResponsiveness(constants)));
						}
						
						break;
						
					case SecondarySpaceHeating:
						final IRoomHeater secondarySpaceHeater = tech.getSecondarySpaceHeater();
						
						if (secondarySpaceHeater != null) {
							secondarySpaceHeater.setResponsiveness(
									newResponsiveness(secondarySpaceHeater.getResponsiveness()));
						}
						
						break;
					default:
						break;
					
					}
				}
				
				return true;
			}
			
			private double newResponsiveness(final double oldResponsiveness) {
				final double newReponsiveness = (1 + scaling) * oldResponsiveness;
				
				if (newReponsiveness < XHeatingResponsivenessScaling.Min) {
					
					warn(RESPONSIVENESS_OUT_OF_BOUNDS, 
							ResponsivenessScalingAction.this,
							dwellingID,
							oldResponsiveness,
							newReponsiveness,
							XHeatingResponsivenessScaling.Min);
					
					return XHeatingResponsivenessScaling.Min;
					
				} else if (newReponsiveness > XHeatingResponsivenessScaling.Max) {
					
					warn(RESPONSIVENESS_OUT_OF_BOUNDS, 
							ResponsivenessScalingAction.this,
							dwellingID,
							oldResponsiveness,
							newReponsiveness,
							XHeatingResponsivenessScaling.Max);
					
					return XHeatingResponsivenessScaling.Max;
					
				} else {
					return newReponsiveness;
				}
			}
		});
	}
}
