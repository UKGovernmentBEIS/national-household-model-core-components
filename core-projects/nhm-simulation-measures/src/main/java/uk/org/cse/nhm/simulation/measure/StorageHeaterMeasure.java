package uk.org.cse.nhm.simulation.measure;

import java.util.Collections;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.Undefined;

public class StorageHeaterMeasure extends AbstractHeatingMeasure {
	private final IDimension<ITechnologyModel> techs;
	private final ITechnologyOperations operations;
	private final StorageHeaterType type;
	private final StorageHeaterControlType controlType;
	private final Optional<IComponentsFunction<Number>> responsivenessFunction;

	@AssistedInject
	protected StorageHeaterMeasure(
			final ITimeDimension time,
			final IWetHeatingMeasureFactory wetHeatingFactory,
			final IDimension<ITechnologyModel> techs,
			final ITechnologyOperations operations,
            final IProfilingStack stack,
			@Assisted final StorageHeaterType type,
			@Assisted final StorageHeaterControlType controlType,
			@Assisted final ISizingFunction sizingFunction,
			@Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
			@Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
			@Assisted("responsiveness") final Optional<IComponentsFunction<Number>> responsivenessFunction) {
		super(time,
				techs,
				operations,
				wetHeatingFactory,
				TechnologyType.storageHeater(),
				sizingFunction,
              capitalCostFunction, operationalCostFunction, Undefined.<Number>get(stack, "Storage heaters should not install wet central heating"));
		this.techs = techs;
		this.operations = operations;
		this.type = type;
		this.controlType = controlType;
		this.responsivenessFunction = responsivenessFunction;
	}

	static class Modifier implements IModifier<ITechnologyModel> {
		private final double opex;
		private final ITechnologyOperations operations;
		private final StorageHeaterType type;
		private final Optional<Double> responsiveness;
		private final StorageHeaterControlType controlType;

		public Modifier(final ITechnologyOperations operations,
				final StorageHeaterType type,
				final Optional<Double> responsiveness,
				final StorageHeaterControlType controlType,
				final double opex) {
			this.operations = operations;
			this.type = type;
			this.controlType = controlType;
			this.responsiveness = responsiveness;
			this.opex = opex;
		}

		@Override
		public boolean modify(final ITechnologyModel modifiable) {
			final IStorageHeater storageHeater = ITechnologiesFactory.eINSTANCE.createStorageHeater();

			storageHeater.setControlType(controlType);

			if (responsiveness.isPresent()) {
				storageHeater.setHasResponsivenessOverride(true);
				storageHeater.setResponsivenessOverride(responsiveness.get());
			}
			storageHeater.setType(type);
			storageHeater.setAnnualOperationalCost(opex);

			operations.replacePrimarySpaceHeater(modifiable, storageHeater);

			return true;
		}
	}

	@Override
	protected boolean doApply(
			final ISettableComponentsScope components,
			final ILets lets,
			final double size,
			final double capex, final double opex) throws NHMException {
		final Optional<Double> responsiveness;
		if (responsivenessFunction.isPresent()) {
			responsiveness = Optional.of(
					responsivenessFunction.get().compute(components, lets).doubleValue());

			if (responsiveness.get() < 0 || responsiveness.get() > 1) {
				throw new RuntimeException("Responsiveness should be between 0 and 1, but was " + responsiveness.get());
			}
		} else {
			responsiveness = Optional.absent();
		}
		components.modify(techs,
				new Modifier(operations,
						type,
						responsiveness,
						controlType,
						opex
						));
		return true;
	}

	@Override
	protected boolean doIsSuitable(final IComponents components) {
		return true;
	}

	@Override
	protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
		return Collections.emptySet();
	}

	@Override
	protected boolean isCentralHeatingSystemRequired() {
		return false;
	}
}
