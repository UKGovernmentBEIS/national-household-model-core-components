package uk.org.cse.nhm.simulator.state.dimensions.energy;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.dimensions.impl.DerivedDimensionWithCache;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

/**
 * An {@link IDimension} which handles {@link IPowerTable} by using an {@link IEnergyCalculatorBridge}.
 * 
 * @author hinton
 *
 */
public class PowerDimension extends DerivedDimensionWithCache<IPowerTable> {
	private final IState state;
	private final IDimension<IWeather> weather;
	private final IDimension<People> people;
	private final IDimension<ITechnologyModel> technology;
	private final IDimension<StructureModel> structure;
	private final IEnergyCalculatorBridge bridge;
	private final IDimension<BasicCaseAttributes> attributes;
	private final IDimension<IHeatingBehaviour> heatingBehaviour;
	
	@Inject
	public PowerDimension(
			final DimensionCounter dc,
			final IState state,
			final IDimension<IWeather> weather,
			final IDimension<People> people,
			final IDimension<ITechnologyModel> technology,
			final IDimension<StructureModel> structure,
			final IDimension<BasicCaseAttributes> attributes,
			final IDimension<IHeatingBehaviour> heatingBehaviour,
			final IEnergyCalculatorBridge bridge) {
		this(dc.next(), state, weather, people, technology, structure, attributes, heatingBehaviour, bridge, null, IInternalDimension.DEFAULT_CAPACITY);
	}

	private PowerDimension(
			final int index, final IState state, final IDimension<IWeather> weather,
			final IDimension<People> people,
			final IDimension<ITechnologyModel> technology,
			final IDimension<StructureModel> structure,
			final IDimension<BasicCaseAttributes> attributes,
			final IDimension<IHeatingBehaviour> heatingBehaviour,
			final IEnergyCalculatorBridge bridge, 
			final PowerDimension energyDimension,
			final int capacity) {
		super(index, energyDimension, capacity);
		
		this.state = state;
		this.weather = weather;
		this.people = people;
		this.technology = technology;
		this.structure = structure;
		this.attributes = attributes;
		this.bridge = bridge;
		this.heatingBehaviour = heatingBehaviour;
	}

	@Override
	protected IPowerTable doGet(final IDwelling instance) {
		return bridge.evaluate(
				state.get(weather, instance), 
				state.get(structure, instance), 
				state.get(technology, instance), 
				state.get(attributes, instance), 
				state.get(people, instance),
				state.get(heatingBehaviour, instance));
	}
	
	@Override
	public int getGeneration(final IDwelling instance) {
		final int genW = state.getGeneration(weather, instance);
		final int genP = state.getGeneration(people, instance);
		final int genT = state.getGeneration(technology, instance);
		final int genS = state.getGeneration(structure, instance);
		final int genH = state.getGeneration(heatingBehaviour, instance);
		return genW + genP + genT + genS + genH;
	}
	
	@Override
	public IInternalDimension<IPowerTable> branch(final IBranch forkingState, final int capacity) {
		return new PowerDimension(index, forkingState, weather, people, technology, structure, attributes,heatingBehaviour, bridge, this, capacity);
	}
	
	@Override
	public String toString() {
		return "Energy";
	}
}
