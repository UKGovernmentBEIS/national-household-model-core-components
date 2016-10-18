package uk.org.cse.nhm.simulator.integration.tests.guice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IEmissions;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class IntegrationTestOutput {
	public final List<ISimulationLogEntry> log = new ArrayList<ISimulationLogEntry>();
	
	public final IDimension<StructureModel> structure;

	public final IDimension<IPowerTable> power;

	public final IDimension<IPowerTable> uncalibratedPower;
	
	public final IDimension<ITechnologyModel> technology;

	public final IDimension<IWeather> weather;

	public final IDimension<IEmissions> fuelCosts;

	public final IDimension<ICarbonFactors> fuelPricing;

	public final IDimension<BasicCaseAttributes> basicAttributes;
	
	public final IDimension<IObligationHistory> obligationHistory;
	public final IDimension<DwellingTransactionHistory> transactionHistory;
	
	public final IDimension<IFlags> flags;
	public final IDimension<People> people;
	public final IDimension<FinancialAttributes> finacialAttributes;
	
	public final Map<String, IFunctionAssertion> asserts = new HashMap<>();
	
	public final ICanonicalState state;
	public final ISimulator simulator;
	
	@Inject	
	public IntegrationTestOutput(final IDimension<StructureModel> structure,
			final IDimension<IPowerTable> power,
			@Named("uncalibrated") final IDimension<IPowerTable> ucpower,
			final IDimension<ITechnologyModel> technology,
			final IDimension<IWeather> weather, final IDimension<IEmissions> fuelCosts,
			final IDimension<ICarbonFactors> fuelPricing,
			final IDimension<BasicCaseAttributes> basicAttributes,
			final IDimension<IFlags> flags,
			final IDimension<IObligationHistory> obligationHistory, 
			final ICanonicalState state,
			final ISimulator simulator,
			final IDimension<DwellingTransactionHistory> transactionHistory,
			final IDimension<People> people,
			final IDimension<FinancialAttributes> financialAttributes) {
		this.structure = structure;
		this.power = power;
		uncalibratedPower = ucpower;
		this.technology = technology;
		this.weather = weather;
		this.fuelCosts = fuelCosts;
		this.fuelPricing = fuelPricing;
		this.basicAttributes = basicAttributes;
		this.flags = flags;
		this.obligationHistory = obligationHistory;
		this.state = state;
		this.simulator = simulator;
		this.transactionHistory = transactionHistory;
		this.people = people;
		this.finacialAttributes = financialAttributes;
	}

	public static class MeasureInvocation {
		public final DateTime when;
		public final int count;
		public MeasureInvocation(final DateTime when, final int count) {
			super();
			this.when = when;
			this.count = count;
		}
	}
	
	private final ListMultimap<String, MeasureInvocation> measureInvocationsById = ArrayListMultimap.create();
	
	public List<MeasureInvocation> getInvocationsForId(final String id) {
		return measureInvocationsById.get(id);
	}
	
	public Iterable<IDwelling> dwellingsWithFlag(final String string) {
		// I really wish java had yield()
		final ImmutableSet.Builder<IDwelling> b = ImmutableSet.builder();
		for (final IDwelling d : state.getDwellings()) {
			if (state.get(flags, d).testFlag(string)) {
				b.add(d);
			}
		}
		return b.build();
	}

	public String getAACode(final IDwelling d) {
		return state.get(basicAttributes, d).getAacode();
	}
}
