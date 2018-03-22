package uk.org.cse.nhm.hom.emf.util.impl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;

public class TechnologyOperations implements ITechnologyOperations {
	private static final Logger log = LoggerFactory.getLogger(TechnologyOperations.class);

	@Override
	public boolean setInstallationYears(final ITechnologyModel technologies, final int year) {
		final TreeIterator<EObject> iterator = technologies.eAllContents();
		boolean result = false;
		while (iterator.hasNext()) {
			final EObject thing = iterator.next();
			if (thing instanceof IHasInstallationYear) {
				final IHasInstallationYear iy = (IHasInstallationYear) thing;
				if (!iy.isSetInstallationYear()) {
					iy.setInstallationYear(year);
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public boolean hasCommunitySpaceHeating(final ITechnologyModel technologies) {
		return technologies.getCommunityHeatSource() != null &&
				technologies.getCommunityHeatSource().getSpaceHeater() != null;
	}

	@Override
	public boolean hasCommunityWaterHeating(final ITechnologyModel technologies) {
		return technologies.getCommunityHeatSource() != null &&
				technologies.getCommunityHeatSource().getWaterHeater() != null;
	}

	@Override
	public void removeHeatSource(final IHeatSource heatSource) {
		if (heatSource == null) return;

		if (heatSource.getSpaceHeater() != null) {
			heatSource.getSpaceHeater().setHeatSource(null);
		}

		if (heatSource.getWaterHeater() != null) {
			if (heatSource.getWaterHeater().eContainer() != null)
				heatSource.getWaterHeater().eContainer().eSet(heatSource.getWaterHeater().eContainingFeature(), null);
		}
	}

	@Override
	public void installHeatSource(final ITechnologyModel technologies, final IHeatSource newHeatSource, final boolean forSpace, final boolean forWater, final EmitterType emitterType, final Set<HeatingSystemControlType> heatingControls, final double insulation, final double volume) {
		if (newHeatSource instanceof ICommunityHeatSource) {
			log.debug("replacing {} with {}", technologies.getCommunityHeatSource(), newHeatSource);
			removeHeatSource(technologies.getCommunityHeatSource());
			technologies.setCommunityHeatSource((ICommunityHeatSource) newHeatSource);
		} else if (newHeatSource instanceof IIndividualHeatSource) {
			log.debug("replacing {} with {}", technologies.getIndividualHeatSource(), newHeatSource);
			removeHeatSource(technologies.getIndividualHeatSource());
			technologies.setIndividualHeatSource((IIndividualHeatSource) newHeatSource);
		} else {
			throw new RuntimeException(newHeatSource + " is not a community heat source or an individual heat source!");
		}

		if (forSpace) {
			final ICentralHeatingSystem system = getOrInstallCentralHeating(technologies);
			system.setHeatSource(newHeatSource);
			system.setEmitterType(emitterType);
			addAllToEList(system.getControls(), heatingControls);
		}

		if (forWater) {
			final ICentralWaterSystem system = getOrInstallCentralHotWater(technologies);
			final IMainWaterHeater mwh = ITechnologiesFactory.eINSTANCE.createMainWaterHeater();
			mwh.setHeatSource(newHeatSource);
			system.setPrimaryWaterHeater(mwh);
			if (newHeatSource instanceof ICombiBoiler) {
				removeWaterTank(system);
			} else {
				installWaterTank(system, true, insulation, volume);
			}
		}

		ensureConsistency(technologies);
	}

	private void ensureConsistency(final ITechnologyModel technologies) {
		if (technologies.getIndividualHeatSource() != null) {
			if (heatSourceIsDisconnected(technologies.getIndividualHeatSource())) {
				log.debug("removing unused individual heat source");
				technologies.setIndividualHeatSource(null);
			}
		}

		if (technologies.getCommunityHeatSource() != null) {
			if (heatSourceIsDisconnected(technologies.getCommunityHeatSource())) {
				log.debug("removing unused community heat source");
				technologies.setCommunityHeatSource(null);
			}
		}

		if (technologies.getCentralWaterSystem() instanceof ICentralWaterSystem) {
			final ICentralWaterSystem cws = technologies.getCentralWaterSystem();
			removeDisconnectedCirculator(cws.getPrimaryWaterHeater());
			removeDisconnectedCirculator(cws.getSecondaryWaterHeater());
		}

		installImmersionHeaterAsFallback(technologies);
	}

	private void removeDisconnectedCirculator(final ICentralWaterHeater primaryWaterHeater) {
		if (primaryWaterHeater instanceof IWarmAirCirculator) {
			if (((IWarmAirCirculator) primaryWaterHeater).getWarmAirSystem() == null) {
				EcoreUtil.delete(primaryWaterHeater);
			}
		}
	}

	private void installImmersionHeaterAsFallback(final ITechnologyModel technologies) {
		if (technologies.getCentralWaterSystem() instanceof ICentralWaterSystem) {
			final ICentralWaterSystem cws = technologies.getCentralWaterSystem();
			if (cws.getPrimaryWaterHeater() == null &&
					cws.getSecondaryWaterHeater() == null &&
						(cws.getStore() == null || cws.getStore().getImmersionHeater() == null)) {
				final IImmersionHeater immersionHeater = ITechnologiesFactory.eINSTANCE.createImmersionHeater();
				immersionHeater.setDualCoil(false);

				installWaterTank(cws, true, 50, 100);
				cws.getStore().setImmersionHeater(immersionHeater);
			}
		}
	}

	private boolean heatSourceIsDisconnected(final IHeatSource heatSource) {
		return heatSource.getSpaceHeater() == null && heatSource.getWaterHeater() == null;
	}

	private void removeWaterTank(final ICentralWaterSystem system) {
		system.setStore(null);
	}

	private <T> void addAllToEList(final EList<T> eList, final Set<T> toAdd) {
		final Set<T> existing = new HashSet<T>(eList);
		toAdd.removeAll(existing);
		eList.addAll(toAdd);
	}

	private ICentralWaterSystem getOrInstallCentralHotWater(final ITechnologyModel technologies) {
		if (technologies.getCentralWaterSystem() != null) {
			return technologies.getCentralWaterSystem();
		}
		final ICentralWaterSystem water = ITechnologiesFactory.eINSTANCE.createCentralWaterSystem();

		technologies.setCentralWaterSystem(water);

		water.setPrimaryPipeworkInsulated(true);
		water.setSeparatelyTimeControlled(true);

		return water;
	}

	@Override
	public ICentralHeatingSystem getOrInstallCentralHeating(final ITechnologyModel technologies) {
		if (technologies.getPrimarySpaceHeater() instanceof ICentralHeatingSystem) {
			return (ICentralHeatingSystem) technologies.getPrimarySpaceHeater();
		} else {
			final ICentralHeatingSystem newCentralHeatingSystem = ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem();
			newCentralHeatingSystem.setEmitterType(EmitterType.RADIATORS);

			doReplacePrimarySpaceHeater(technologies, newCentralHeatingSystem);
			return newCentralHeatingSystem;
		}
	}


	@Override
	public void replacePrimarySpaceHeater(final ITechnologyModel technologies, final IPrimarySpaceHeater newHeater) {
		doReplacePrimarySpaceHeater(technologies, newHeater);
		ensureConsistency(technologies);
	}

	public void doReplacePrimarySpaceHeater(final ITechnologyModel technologies, final IPrimarySpaceHeater newHeater) {
		removePrimarySpaceHeater(technologies);
		technologies.setPrimarySpaceHeater(newHeater);
	}

	@Override
	public void removePrimarySpaceHeater(final ITechnologyModel technologies) {
		if (technologies.getPrimarySpaceHeater() != null) {
			EcoreUtil.delete(technologies.getPrimarySpaceHeater(), true);
		}
	}

	@Override
	public IWaterTank installWaterTank(final ICentralWaterSystem system, final boolean retainExisting, final double insulationThickness, final double cylinderVolume) {
		if (retainExisting && system.getStore() != null) {
			return system.getStore();
		}

		final IWaterTank tank = ITechnologiesFactory.eINSTANCE.createWaterTank();

		tank.setThermostatFitted(true);
		tank.setInsulation(insulationThickness);
		tank.setFactoryInsulation(true);
		tank.setVolume(cylinderVolume);

		system.setPrimaryPipeworkInsulated(true);
		system.setStore(tank);

		return tank;
	}

	@Override
	public void installHeatSource(final ITechnologyModel technologies, final IHeatSource heatSource, final double cylinderInsulation,
			final double cylinderVolume) {
		installHeatSource(technologies, heatSource, false, true, null, null, cylinderInsulation, cylinderVolume);
	}
	@Override
	public void installHeatSource(final ITechnologyModel technologies, final IHeatSource heatSource, final EmitterType emitterType,
			final Set<HeatingSystemControlType> controls) {
		installHeatSource(technologies, heatSource, true, false, emitterType, controls, 0, 0);
	}

	@Override
	public Optional<IBoiler> getBoiler(final ITechnologyModel technologies) {
		final IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();
		if(primarySpaceHeater instanceof ICentralHeatingSystem) {
			final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
			if(heatSource instanceof IBoiler) {
				return Optional.of((IBoiler) heatSource);
			}
		}
		return Optional.absent();
	}

	@Override
	public boolean changeHeatingEfficiency(final ITechnologyModel technologies, final double winterTarget, final double summerTarget, final boolean up, final boolean down) {
		final IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();
		if(primarySpaceHeater instanceof ICentralHeatingSystem) {
			final IHeatSource heatSource = ((ICentralHeatingSystem) primarySpaceHeater).getHeatSource();
			if (heatSource instanceof IBoiler) {
				return changeBoilerEfficiency((IBoiler) heatSource, winterTarget, summerTarget, up, down);
			} else if (heatSource instanceof IHeatPump) {
				return changeHeatPumpPerformance((IHeatPump) heatSource, winterTarget, up, down);
			}
		} else if (primarySpaceHeater instanceof IWarmAirSystem) {
			return changeWarmAirEfficiency((IWarmAirSystem) primarySpaceHeater, winterTarget, up, down);
		}
		return false;
	}

	private boolean changeWarmAirEfficiency(final IWarmAirSystem primarySpaceHeater,
			final double target, final boolean up, final boolean down) {
		if (up && primarySpaceHeater.getEfficiency().value < target || down
				&& primarySpaceHeater.getEfficiency().value > target) {
			primarySpaceHeater.setEfficiency(Efficiency.fromDouble(target));
			return true;
		}
		return false;
	}

	private boolean changeHeatPumpPerformance(
			final IHeatPump heatSource, final double target,
			final boolean up, final boolean down) {
		if (up && heatSource.getCoefficientOfPerformance().value < target || down && heatSource.getCoefficientOfPerformance().value > target) {
			heatSource.setCoefficientOfPerformance(Efficiency.fromDouble(target));
			return true;
		}
		return false;
	}

	private boolean changeBoilerEfficiency(
			final IBoiler heatSource, final double winterTarget, final double summerTarget,
			final boolean up, final boolean down) {
		boolean change = false;

		if (up && heatSource.getSummerEfficiency().value < summerTarget || down && heatSource.getSummerEfficiency().value > summerTarget) {
			heatSource.setSummerEfficiency(Efficiency.fromDouble(summerTarget));
			change = true;
		}

		if (up && heatSource.getWinterEfficiency().value < winterTarget || down && heatSource.getWinterEfficiency().value > winterTarget) {
			heatSource.setWinterEfficiency(Efficiency.fromDouble(winterTarget));
			change = true;
		}

		return change;
	}

	@Override
	/**
	 * @assumption Secondary heating fraction set based on SAP 2009 table 11: Fraction of heat supplied by secondary heating systems. If the primary heater is a storage heater without fans, the secondary heating fraction is 0.15. Otherwise it is 0.1.
	 */
	public double getSAPSecondaryHeatingProportion(final ITechnologyModel technologies) {
		final IPrimarySpaceHeater primarySpaceHeater = technologies.getPrimarySpaceHeater();
		if(primarySpaceHeater instanceof IStorageHeater && ((IStorageHeater) primarySpaceHeater).getType() == StorageHeaterType.OLD_LARGE_VOLUME) {
			return 0.15;
		} else {
			return 0.1;
		}
	}

	@Override
	public FuelType getMainHeatingFuel(final ITechnologyModel technologies) {
		FuelType result = technologies.getPrimaryHeatingFuel();
		if (result == null) {
			result = technologies.getSecondaryHeatingFuel();
		}
		return result;
	}
}
