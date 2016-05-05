package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IElectricShower;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XHeatingSystem;
import uk.org.cse.nhm.language.definition.function.num.XEfficiencyMeasurement;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HeatingEfficiencyFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private static final double ELECTRIC_RESISTIVE = 1.0;
	private static final double NO_MATCHING_SYSTEM = 0.0;
	
	private final XHeatingSystem system;
	private final XEfficiencyMeasurement measurement;
	private final IDimension<IPowerTable> energy;
	private final IDimension<ITechnologyModel> techDimension;

	@AssistedInject
	public HeatingEfficiencyFunction(
			@Assisted final XHeatingSystem system,
			@Assisted final XEfficiencyMeasurement measurement,
			final IDimension<IPowerTable> energy,
			final IDimension<ITechnologyModel> techDimension) {
				this.system = system;
				this.measurement = measurement;
				this.energy = energy;
				this.techDimension = techDimension;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(techDimension);
		
		if (measurement == XEfficiencyMeasurement.InSitu) {
			return insituEfficiency(scope, tech, system);
		}
		
		switch(system) {
		case AuxiliaryHotWater:
			return waterHeaterEfficiency(tech.getSecondaryWaterHeater(), measurement);
			
		case CentralHotWater:
			if (tech.getCentralWaterSystem() == null) {
				return NO_MATCHING_SYSTEM;
			} else {
				return centralWaterEfficiency(tech.getCentralWaterSystem(), measurement);
			}
			
		case PrimarySpaceHeating:
			return getPrimarySpaceHeatingEfficiency(tech, measurement);
		case SecondarySpaceHeating:
			return getSecondarySpaceHeatingEfficiency(tech);
			
		default:
			throw new IllegalArgumentException("Unknown heating system type " + system);
		}
	}
	
	private static double getFuelUseByService(IPowerTable power, ServiceType service) {
		double accum = 0d;
		
		for (FuelType fuel : FuelType.values()) {
			accum += power.getFuelUseByEnergyService(service, fuel);
		}
	
		return accum;
	}
	
	/**
	 * Work out the in-situ efficiency of a system based on the demand it satisfied divided by the fuel it used to do so.
	 */
    private double insituEfficiency(IComponentsScope scope, ITechnologyModel tech, XHeatingSystem system) {
    	IPowerTable power = scope.get(energy);
    	
    	switch(system) {
    	case PrimarySpaceHeating:
    		return power.getPrimaryHeatDemand() / getFuelUseByService(power, ServiceType.PRIMARY_SPACE_HEATING);
    	case SecondarySpaceHeating:
    		return power.getSecondaryHeatDemand() / getFuelUseByService(power, ServiceType.SECONDARY_SPACE_HEATING);
    	case CentralHotWater:
    		return inSituHotWaterEfficiency(power, tech, true);
    	case AuxiliaryHotWater:
    		return inSituHotWaterEfficiency(power, tech, false);
		default:
			throw new RuntimeException("Unknown heating system type " + system.toString());
    	}
	}

    /**
     * We only used one water heating system to meet the whole hot water demand.
     * 
     * If the primary system is present and not broken, we used that. Otherwise, we used the secondary system.
     * 
     * If a system is not used, its efficiency will come out as NaN.
     */
	private double inSituHotWaterEfficiency(IPowerTable power, ITechnologyModel tech, boolean isPrimary) {
		final boolean usedPrimary = !(tech.getCentralWaterSystem() == null || tech.getCentralWaterSystem().isBroken());
		
		return isPrimary == usedPrimary ? power.getHotWaterDemand() / getFuelUseByService(power, ServiceType.WATER_HEATING) : Double.NaN;
	}

	public static double getAnySpaceHeatingEfficiency(final ITechnologyModel tech, XEfficiencyMeasurement measurement) {
        final double primary = getPrimarySpaceHeatingEfficiency(tech, measurement);
        if (primary == NO_MATCHING_SYSTEM) {
            return getSecondarySpaceHeatingEfficiency(tech);
        } else {
            return primary;
        }
    }

    public static double getSecondarySpaceHeatingEfficiency(final ITechnologyModel tech) {
        if (tech.getSecondarySpaceHeater() == null) {
            return NO_MATCHING_SYSTEM;
        } else {
            return tech.getSecondarySpaceHeater().getEfficiency().value;
        }
    }
        
	public static double getPrimarySpaceHeatingEfficiency(final ITechnologyModel tech, XEfficiencyMeasurement measurement) {
		final IPrimarySpaceHeater primarySpaceHeater = tech.getPrimarySpaceHeater();
		if (primarySpaceHeater instanceof ICentralHeatingSystem) {
			return heatSourceEfficiency(((ICentralHeatingSystem) primarySpaceHeater).getHeatSource(), measurement)
					.or(NO_MATCHING_SYSTEM);
		} else if (primarySpaceHeater instanceof IStorageHeater) {
			return ELECTRIC_RESISTIVE;
		} else if (primarySpaceHeater instanceof IWarmAirSystem) {
			return ((IWarmAirSystem) primarySpaceHeater).getEfficiency().value;
		} else {
			return NO_MATCHING_SYSTEM;
		}
	}

	private double centralWaterEfficiency(final ICentralWaterSystem centralWaterSystem, XEfficiencyMeasurement measurement) {
		
		return centralWaterHeaterEfficiency(centralWaterSystem.getPrimaryWaterHeater(), measurement).or(
				centralWaterHeaterEfficiency(centralWaterSystem.getSecondaryWaterHeater(), measurement).or(
					solarWaterHeaterEfficiency(centralWaterSystem.getSolarWaterHeater()).or(
							NO_MATCHING_SYSTEM)));
	}
		
	private Optional<Double> centralWaterHeaterEfficiency(final ICentralWaterHeater primaryWaterHeater, XEfficiencyMeasurement measurement) {
		if (primaryWaterHeater instanceof IImmersionHeater) {
			return Optional.of(ELECTRIC_RESISTIVE);
			
		} else if (primaryWaterHeater instanceof IMainWaterHeater) {
			return heatSourceEfficiency(((IMainWaterHeater) primaryWaterHeater).getHeatSource(), measurement);
			
		} else if (primaryWaterHeater instanceof ISolarWaterHeater) {
			return solarWaterHeaterEfficiency((ISolarWaterHeater) primaryWaterHeater);
			
		} else if (primaryWaterHeater instanceof IWarmAirCirculator) {
			final IWarmAirSystem warmAirSystem = ((IWarmAirCirculator) primaryWaterHeater).getWarmAirSystem();
			if (warmAirSystem == null) {
				return Optional.absent();
				
			} else {
				return Optional.of(warmAirSystem.getEfficiency().value);
			}
			
		} else {
			return Optional.absent();
		}
	}
	
	private static Optional<Double> heatSourceEfficiency(final IHeatSource heatSource, XEfficiencyMeasurement measurement) {
		if (heatSource instanceof ICommunityHeatSource) {
			return Optional.of(((ICommunityHeatSource) heatSource).getHeatEfficiency().value);
			
		} else if (heatSource instanceof IBoiler) {
			final IBoiler boiler = (IBoiler) heatSource;
			
			if (measurement == XEfficiencyMeasurement.Winter) {
				return Optional.of(boiler.getWinterEfficiency().value);
				
			} else {
				return Optional.of(boiler.getSummerEfficiency().value);
			}
			
		} else if (heatSource instanceof IHeatPump) {
			return Optional.of(((IHeatPump) heatSource).getCoefficientOfPerformance().value);
			
		} else {
			return Optional.absent();
		}
	}

	private Optional<Double> solarWaterHeaterEfficiency(final ISolarWaterHeater solarWaterHeater) {
		return Optional.absent(); // Don't know how to get this.
	}

	private double waterHeaterEfficiency(final IWaterHeater secondaryWaterHeater, XEfficiencyMeasurement measurement) {
		if (secondaryWaterHeater instanceof ICentralWaterSystem) {
			return centralWaterEfficiency((ICentralWaterSystem) secondaryWaterHeater, measurement);
			
		} else if (secondaryWaterHeater instanceof IElectricShower) {
			return ELECTRIC_RESISTIVE;
			
		} else if (secondaryWaterHeater instanceof IPointOfUseWaterHeater) {
			return ((IPointOfUseWaterHeater) secondaryWaterHeater).getEfficiency().value;
			
		} else  {
			return NO_MATCHING_SYSTEM;
		}
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(techDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return ImmutableSet.of();
	}
}
