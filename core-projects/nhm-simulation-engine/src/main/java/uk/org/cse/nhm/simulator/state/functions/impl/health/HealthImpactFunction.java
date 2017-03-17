package uk.org.cse.nhm.simulator.state.functions.impl.health;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import uk.ac.ucl.hideem.CumulativeHealthOutcome;
import uk.ac.ucl.hideem.Disease;
import uk.ac.ucl.hideem.HealthOutcome;
import uk.ac.ucl.hideem.IHealthModule;
import uk.ac.ucl.hideem.Person;
import uk.ac.ucl.hideem.Person.Sex;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.SexType;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XDisease;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XImpact;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

public class HealthImpactFunction extends AbstractNamed implements IComponentsFunction<Number> {
	private static final Map<XDisease, Set<Disease.Type>> DISEASES =
			ImmutableMap.
					<XDisease, Set<Disease.Type>>
					builder()
					.put(XDisease.CaV, 	    EnumSet.of(Disease.Type.cerebrovascular))
				    .put(XDisease.CP,	    EnumSet.of(Disease.Type.cardiopulmonary))
				    .put(XDisease.LC,	    EnumSet.of(Disease.Type.lungcancer))
				    .put(XDisease.MI,	    EnumSet.of(Disease.Type.myocardialinfarction))
				    .put(XDisease.WCaV,	    EnumSet.of(Disease.Type.wincerebrovascular))
				    .put(XDisease.WCV,	    EnumSet.of(Disease.Type.wincardiovascular))
				    .put(XDisease.WMI,	    EnumSet.of(Disease.Type.winmyocardialinfarction))
				    .put(XDisease.CMD,	    EnumSet.of(Disease.Type.commonmentaldisorder))
				    .put(XDisease.Asthma,   EnumSet.of(Disease.Type.asthma1, Disease.Type.asthma2, Disease.Type.asthma3))
				    .build()
			;


	private final IHealthModule healthModule;
	private final IDimension<StructureModel> structure;
	private final IDimension<People> people;
	private final IDimension<BasicCaseAttributes> attributes;

	private final IComponentsFunction<Number> h1, h2, p1, p2, t1, t2, g1, g2;
	private final IComponentsFunction<Number> horizon, offset;
    private final IComponentsFunction<Boolean> fans, vents;
	private final Set<XDisease> diseases;
	private final XImpact impact;

	@Inject
	public HealthImpactFunction(
			final IHealthModule healthModule,
			final IDimension<StructureModel> structure,
			final IDimension<People> people,
			final IDimension<BasicCaseAttributes> attributes,

			// these are required for overheating risk
            @Assisted("h1") 	 final IComponentsFunction<Number> h1,
            @Assisted("h2") 	 final IComponentsFunction<Number> h2,

            // delta T for health impacts
            @Assisted("t1") 	 final IComponentsFunction<Number> t1,
            @Assisted("t2") 	 final IComponentsFunction<Number> t2,

            // delta permeability
			@Assisted("p1") 	 final IComponentsFunction<Number> p1,
			@Assisted("p2") 	 final IComponentsFunction<Number> p2,

			@Assisted("horizon") final IComponentsFunction<Number> horizon,
			@Assisted("offset")  final IComponentsFunction<Number> offset,
            @Assisted("fans")    final IComponentsFunction<Boolean> fans,
            @Assisted("vents")   final IComponentsFunction<Boolean> vents,

            @Assisted("g1") 	 final IComponentsFunction<Number> g1,
            @Assisted("g2")	     final IComponentsFunction<Number> g2,

			@Assisted			 final List<XDisease> diseases,
			@Assisted			 final XImpact impact
			) {
		super();
		this.healthModule = healthModule;
		this.structure = structure;
        this.people = people;
        this.attributes = attributes;

		this.h1 = h1;
		this.h2 = h2;

		this.t1 = t1;
		this.t2 = t2;

		this.p1 = p1;
		this.p2 = p2;

		this.g1 = g1;
		this.g2 = g2;

		this.horizon = horizon;
		this.offset = offset;
		this.diseases = EnumSet.copyOf(diseases);
		this.impact = impact;

        this.fans = fans;
        this.vents = vents;
	}

	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		final BasicCaseAttributes basic = scope.get(attributes);
		final StructureModel structureModel = scope.get(structure);
		final BuiltFormType builtFormType = structureModel.getBuiltFormType();
		final List<Person> people = getPeople(scope);
		final int horizon = this.horizon.compute(scope, lets).intValue();
		final int offset = this.offset.compute(scope, lets).intValue();

		final int mainFloorLevel = structureModel.getMainFloorLevel();

		final boolean hasWorkingExtractors = this.fans.compute(scope, lets);
		final boolean hasTrickleVents = this.vents.compute(scope, lets);

        final double _h1 = h1.compute(scope, lets).doubleValue();
        final double _h2 = h2.compute(scope, lets).doubleValue();

        final double _t1 = t1.compute(scope, lets).doubleValue();
        final double _t2 = t2.compute(scope, lets).doubleValue();

        final double _p1 = p1.compute(scope, lets).doubleValue();
        final double _p2 = p2.compute(scope, lets).doubleValue();

        final boolean _g1 = g1.compute(scope, lets).doubleValue() >= 0.8;
        final boolean _g2 = g2.compute(scope, lets).doubleValue() >= 0.8;

        // work out some health impacts
        final CumulativeHealthOutcome outcome = healthModule.effectOf(
        	CumulativeHealthOutcome.factory(horizon),
            _t1, _t2,
            _p1, _p2,
            _h1, _h2,

            builtFormType,
            structureModel.getFloorArea(),
            basic.getRegionType(),
            mainFloorLevel,

            hadWorkingExtractors,
            hadTrickleVents,

            hasWorkingExtractors,
            hasTrickleVents,

            _g1, _g2, // has double glazing, had double glazing?

            people
            );

		return totalImpact(outcome, offset, horizon);
	}

	private List<Person> getPeople(final IComponentsScope scope) {
		final People people = scope.get(this.people);

        // this data may not be available in all stocks, so there ought to be a warning of some sort.
        final ImmutableList.Builder<Person> builder = ImmutableList.builder();
        for (final People.Occupant o : people.getOccupants()) {
            builder.add(new Person(o.getAge(),
                                   o.getSex() == SexType.MALE ? Sex.MALE : Sex.FEMALE,
                        		   o.isSmoker()));
        }

		return builder.build();
	}

	private double totalImpact(final CumulativeHealthOutcome outcome, final int offset, final int horizon) {
		// TODO support discounting in some efficient way.
		double acc = 0;
		for (final XDisease disease : diseases) {
			for (final Disease.Type d : DISEASES.get(disease)) {
				for (int i = offset; i<horizon; i++) {
					switch (impact) {
					case Cost:
						acc += outcome.cost(d, i);
						break;
					case Morbidity:
						acc += outcome.morbidity(d, i);
						break;
					case Mortality:
						acc += outcome.mortality(d, i);
						break;
					}
				}
			}
		}
		return acc;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		// parts of the house that are important for health impact
		return ImmutableSet.<IDimension<?>>of(structure, people);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		// This is not a function of the simulation year, so it has no change dates.
		return Collections.emptySet();
	}
}