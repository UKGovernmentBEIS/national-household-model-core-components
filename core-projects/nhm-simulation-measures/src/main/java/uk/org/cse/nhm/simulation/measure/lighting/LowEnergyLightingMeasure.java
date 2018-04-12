package uk.org.cse.nhm.simulation.measure.lighting;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
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

public class LowEnergyLightingMeasure extends AbstractMeasure {
	private final IDimension<ITechnologyModel> techDimension;
	private final IDimension<StructureModel> structureDimension;
	private final ITechnologiesFactory techFactory;
	private final IComponentsFunction<Number> capex;
	private EnumSet<LightType> from;
	private LightType to;

	@AssistedInject
	public LowEnergyLightingMeasure(
			@Assisted final List<LightType> from,
			@Assisted final LightType to,
			@Assisted final IComponentsFunction<Number> capex,
			final IDimension<ITechnologyModel> techDimension,
			final IDimension<StructureModel> structureDimension
			) {
		this.structureDimension = structureDimension;
		this.techFactory = ITechnologiesFactory.eINSTANCE;;
		this.capex = capex;
		this.techDimension = techDimension;
		
		this.to = to;
		this.from = EnumSet.copyOf(from);
	}

	@Override
	public boolean doApply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {

		final double floorArea = scope.get(structureDimension).getFloorArea();

		scope.modify(techDimension, new Modifier(scope, lets, floorArea));
		return true;
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final ITechnologyModel tech = scope.get(techDimension);

		for (final ILight l : tech.getLights()) {
			if (l.getProportion() > 0 && this.from.contains(l.getType())) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	/*
	 * This method just exists for testing.
	 */
	public Modifier createModifier(final ISettableComponentsScope scope, final ILets lets, final double floorArea) {
		return new Modifier(scope, lets, floorArea);
	}

	public final class Modifier implements IModifier<ITechnologyModel> {
		private final ISettableComponentsScope scope;
		private final ILets lets;
		private final double floorArea;

		public Modifier(final ISettableComponentsScope scope, final ILets lets, final double floorArea) {
			this.scope = scope;
			this.lets = lets;
			this.floorArea = floorArea;
		}

		@Override
		public boolean modify(final ITechnologyModel tech) {
			ILight existing = null;
			double missing = 0;
			double total = 0;
			Iterator<ILight> iterator = tech.getLights().iterator();
			
			while (iterator.hasNext()) {
				final ILight l = iterator.next();
				if (from.contains(l.getType())) {
					iterator.remove();
					missing += l.getProportion();
				} else if (l.getType() == to) {
					existing = l;
				}
				total += l.getProportion();
			}
			
			if (existing == null) {
				existing = techFactory.createLight();
				existing.setName(to.name());
				existing.setType(to);
				existing.setProportion(missing);
				tech.getLights().add(existing);
			} else {
				existing.setProportion(existing.getProportion() + missing);
			}

			final double size = (missing / total) * floorArea;
			final ISizingResult sizingResult = SizingResult.suitable(size, Units.SQUARE_METRES);
			scope.addNote(sizingResult);

			final double capexResult = capex.compute(scope, lets).doubleValue();

			scope.addNote(new TechnologyInstallationDetails(LowEnergyLightingMeasure.this, TechnologyType.lowEnergyLighting(), sizingResult.getSize(), sizingResult.getUnits(), capexResult, 0));
			scope.addTransaction(Payment.capexToMarket(capexResult));
			
			if (tech.getLights().isEmpty()) {
				throw new RuntimeException("measure.replace-lighting has left a house with no lights; this should never happen");
			}
			
			return true;
		}
	}
}
