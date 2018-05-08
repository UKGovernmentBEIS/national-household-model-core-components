package uk.org.cse.nhm.simulation.measure.boilers;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.EfficiencySourceType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Measure to install a gas condensing combi boiler; handles both the storage
 * and instant cases, as they are very nearly identical.
 *
 * @author hinton
 */
public class CombiBoilerMeasure extends AbstractBoilerMeasure {

    final IComponentsFunction<Number> storageVolume;
    final IDimension<ITechnologyModel> technologies;
    final IDimension<StructureModel> structure;
    final ITechnologyOperations operations;

    @Inject
    public CombiBoilerMeasure(
            final ITimeDimension time,
            final IWetHeatingMeasureFactory factory,
            final IDimension<ITechnologyModel> technologies,
            final IDimension<StructureModel> structure,
            final ITechnologyOperations operations,
            @Assisted final FuelType fuelType,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("winterEfficiency") final IComponentsFunction<Number> winterEfficiency,
            @Assisted("summerEfficiency") final IComponentsFunction<Number> summerEfficiency,
            @Assisted("storageVolume") final IComponentsFunction<Number> storageVolume) {
        super(time,
                technologies,
                operations,
                factory,
                TechnologyType.combiBoiler(fuelType),
                sizingFunction,
                capitalCostFunction,
                operationalCostFunction,
                wetHeatingCostFunction,
                winterEfficiency,
                summerEfficiency,
                fuelType);
        this.storageVolume = storageVolume;
        this.technologies = technologies;
        this.structure = structure;
        this.operations = operations;
    }

    private class Modifier implements IModifier<ITechnologyModel> {

        final double opex;
        final double winterEfficiency;
        final double summerEfficiency;
        final double volume;

        public Modifier(final double opex, final double winterEfficiency, final double summerEfficiency, final double volume) {
            super();
            this.opex = opex;
            this.winterEfficiency = winterEfficiency;
            this.summerEfficiency = summerEfficiency;
            this.volume = volume;
        }

        @Override
        public boolean modify(final ITechnologyModel newCase) {
            final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;
            final ICombiBoiler heating;

            if (volume > 0) {
                final IStorageCombiBoiler combi = IBoilersFactory.eINSTANCE.createStorageCombiBoiler();
                final IWaterTank tank = factory.createWaterTank();
                //TODO set other tank properties here
                tank.setVolume(volume);
                combi.setStore(tank);
                heating = combi;
            } else {
                heating = IBoilersFactory.eINSTANCE.createInstantaneousCombiBoiler();
            }

            heating.setWinterEfficiency(Efficiency.fromDouble(winterEfficiency));
            heating.setSummerEfficiency(Efficiency.fromDouble(summerEfficiency));
            heating.setFuel(getFuelType());
            heating.setPumpInHeatedSpace(true);
            heating.setFlueType(getFlueType());
            heating.setCondensing(isCondensing());
            heating.setAnnualOperationalCost(opex);
            heating.setEfficiencySource(EfficiencySourceType.MANUFACTURER_DECLARED);

            operations.installHeatSource(newCase, heating, true, true, EmitterType.RADIATORS, getHeatingSystemControlTypes(), 0, 0);

            return true;
        }
    }

    private double getStorageVolume(final IComponentsScope components, final ILets lets) {
        return Math.max(0, this.storageVolume.compute(components, lets).doubleValue());
    }

    @Override
    protected boolean doApply(final ISettableComponentsScope components, final ILets lets, final double size, final double cap, final double op) throws NHMException {
        components.modify(technologies, new Modifier(op,
                getWinterEfficiency(components, lets),
                getSummerEfficiency(components, lets),
                getStorageVolume(components, lets)
        ));
        return true;
    }

    @Override
    protected boolean doIsSuitable(final IComponents components) {
        return GasBoilerSuitability.isSuitableIfGas(getFuelType(), operations, components.get(structure), components.get(technologies));
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    private final Set<HeatingSystemControlType> heatingSystemControlTypes = Sets.newHashSet(new HeatingSystemControlType[]{HeatingSystemControlType.PROGRAMMER,
        HeatingSystemControlType.ROOM_THERMOSTAT, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE});

    @Override
    protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
        return heatingSystemControlTypes;
    }
}
