package uk.org.cse.nhm.simulation.measure.renewables;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
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

/**
 * The measure which will install solar water heating. Current assumptions:
 *
 * <ol>
 * <li>Suitability is defined to be
 * <ol>
 * <li>There is no existing solar water heating system</li>
 * <li>There is an external heat loss roof whose orientation is between the minimum and maximum
 * values set on the measure, and whose area less the total area of PV solar systems is greater or
 * equal to the minimum roof area set</li>
 * </ol>
 * </li>
 * <li>When applied, a SolarSystem and SolarWaterHeatingSystem is created with the following presets:
 * <ol>
 * <li>The overshading is average/unknown</li>
 * <li>The collector tilt value is 30 degrees</li>
 * <li>The collector orientation is due south (180 degrees)</li>
 * <li>A fixed area is installed (installedArea), at a fixed cost (installationCost)</li>
 * <li>A hot water cylinder is installed, with the given volume and insulation,
 * set to be in heated space, has prim. pipework insulation, has a thermostat, and factory insulation</li>
 * </ol>
 * </li>
 * </ol>
 *
 * @author hinton
 *
 */
public class SolarHotWaterMeasure extends AbstractMeasure {
	private static final Logger log = LoggerFactory.getLogger(SolarHotWaterMeasure.class);
	private static final double DEFAULT_PITCH = 30d * Math.PI/180d;
	private static final double DEFAULT_ORIENTATION = Math.PI/2;

	private final IComponentsFunction<Number> lhlc;
    private final IComponentsFunction<Number> zle;
    private final IComponentsFunction<Number> installedArea;
	private final IComponentsFunction<Number> installationCost;
	private final IComponentsFunction<Number> cylinderVolume;
	private final IDimension<ITechnologyModel> technologies;
    private final IDimension<StructureModel> structure;

	@Inject
	public SolarHotWaterMeasure(
			final IDimension<ITechnologyModel> technologies,
            final IDimension<StructureModel> structure,
			@Assisted("area")   final IComponentsFunction<Number> installedArea,
			@Assisted("cost")   final IComponentsFunction<Number> installationCost,
			@Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("lhlc")   final IComponentsFunction<Number> lhlc,
            @Assisted("zle")    final IComponentsFunction<Number> zle
			) {
		this.installedArea = installedArea;
		this.installationCost = installationCost;
		this.cylinderVolume = cylinderVolume;
		this.technologies = technologies;
        this.structure = structure;
        this.lhlc = lhlc;
        this.zle = zle;
	}

    static class Modifier implements IModifier<ITechnologyModel> {
        final double area, lhlc, zle, cylinderVolume;

        public Modifier(final double area,final double lhlc,final double zle, final double cylinderVolume) {
            this.area = area;
            this.lhlc = lhlc;
            this.zle = zle;
            this.cylinderVolume = cylinderVolume;
        }

        @Override
        public boolean modify(final ITechnologyModel newTechnologies) {
            final ICentralWaterSystem centralHotWaterSystem = newTechnologies.getCentralWaterSystem();
            final ISolarWaterHeater solar = ITechnologiesFactory.eINSTANCE.createSolarWaterHeater();

            solar.setArea(area);
            solar.setUsefulAreaRatio(1d);
            solar.setPitch(DEFAULT_PITCH);
            solar.setOrientation(DEFAULT_ORIENTATION);
            solar.setLinearHeatLossCoefficient(lhlc);
            solar.setPumpPhotovolatic(true);
            solar.setZeroLossEfficiency(zle);
            solar.setPreHeatTankVolume(cylinderVolume);
            centralHotWaterSystem.setSolarWaterHeater(solar);

            return true;
        }
    }

	@Override
	public boolean apply(final ISettableComponentsScope components, final ILets lets) throws NHMException {
		if (!isSuitable(components, lets)) {
			return false;
		}

		final ITechnologyModel technologyModel = components.get(technologies);

		if (technologyModel.getCentralWaterSystem() != null) {
            final double area = this.installedArea.compute(components, lets).doubleValue();
            components.addNote(SizingResult.suitable(area, Units.SQUARE_METRES));

            final double installationCost = this.installationCost.compute(components, lets).doubleValue();
            final double tankVolume = this.cylinderVolume.compute(components, lets).doubleValue();
            final double lhlc = this.lhlc.compute(components, lets).doubleValue();
            final double zle = this.zle.compute(components, lets).doubleValue();

			components.modify(technologies, new Modifier(area, lhlc, zle, tankVolume));

			components.addNote(new TechnologyInstallationDetails(this, TechnologyType.solarHotWater(), area, Units.SQUARE_METRES, installationCost, 0));
			components.addTransaction(Payment.capexToMarket(installationCost));
			return true;
		} else {
			log.warn("No central hot water system in {} - not suitable for solar", components);
		}
		return false;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public boolean isSuitable(final IComponentsScope components, final ILets lets) {
		final ITechnologyModel technologyModel = components.get(technologies);
		final StructureModel structureModel = components.get(structure);

		if (technologyModel.getCentralWaterSystem() != null
            && technologyModel.getCentralWaterSystem().getSolarWaterHeater() == null
            && structureModel.hasExternalRoof()) {
            final double roofArea = structureModel.getExternalRoofArea(true);

			return roofArea >= installedArea.compute(components, lets).doubleValue();
		} else {
			return false;
		}
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
