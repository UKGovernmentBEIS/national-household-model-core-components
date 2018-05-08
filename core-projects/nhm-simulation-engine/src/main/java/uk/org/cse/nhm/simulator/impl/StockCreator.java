package uk.org.cse.nhm.simulator.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import uk.org.cse.commons.names.Name;
import uk.org.cse.hom.housepropertystore.HouseProperties;
import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.logging.logentry.SurveyCaseLogEntry;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HouseWeightFunction;

public class StockCreator implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(StockCreator.class);

    private static final IStateChangeSource NO_REASON = new IStateChangeSource() {

        @Override
        public Name getIdentifier() {
            return null;
        }

        @Override
        public StateChangeSourceType getSourceType() {
            return null;
        }
    };

    private final Set<String> importIDs;

    private final RequestedHouseProperties requestedHouseProperties;
    private final IStockService surveyCaseDataService;

    private final IDimension<StructureModel> structure;
    private final IDimension<BasicCaseAttributes> attributes;
    private final IDimension<ITechnologyModel> technologies;
    private final IDimension<People> people;
    private final IDimension<FinancialAttributes> finance;
    private final IDimension<IHouseProperties> additional;

    private final ICanonicalState state;
    private final ISimulator simulator;
    private final StockLogger stockLogger;
    private final Function<Double, List<Double>> weighting;
    private final IComponentsFunction<Number> surveyWeightFunction;

    @Inject
    public StockCreator(
            @Named(SimulatorConfigurationConstants.STOCK_ID) final List<String> importIDs,
            @Named(SimulatorConfigurationConstants.WEIGHTING) final Function<Double, List<Double>> weighting,
            @Named(SimulatorConfigurationConstants.SURVEY_WEIGHT_FUNCTION) final IComponentsFunction<Number> surveyWeightFunction,
            final RequestedHouseProperties requestedHouseProperties,
            final IStockService surveyCaseDataService,
            final StockLogger stockLogger,
            final IDimension<StructureModel> structure,
            final IDimension<BasicCaseAttributes> attributes,
            final IDimension<ITechnologyModel> technologies,
            final IDimension<People> people, final IDimension<FinancialAttributes> finance,
            final IDimension<IHouseProperties> additional,
            final ICanonicalState state,
            final ISimulator simulator) {
        super();

        this.importIDs = ImmutableSet.copyOf(importIDs);
        this.weighting = weighting;
        this.surveyWeightFunction = surveyWeightFunction;

        this.requestedHouseProperties = requestedHouseProperties;
        this.surveyCaseDataService = surveyCaseDataService;
        this.stockLogger = stockLogger;
        this.structure = structure;
        this.attributes = attributes;
        this.technologies = technologies;
        this.people = people;
        this.finance = finance;
        this.additional = additional;
        this.state = state;
        this.simulator = simulator;
    }

    private class ConstructionEvent implements IDateRunnable, IStateAction {

        final Collection<SurveyCase> cases;
        final Name identifier;

        ConstructionEvent(final int year,
                final Collection<SurveyCase> cases) {
            this.cases = ImmutableList.copyOf(cases);
            this.identifier = Name.of(String.format("New Build (%d)", year));
        }

        @Override
        public void run(final DateTime date) {
            state.apply(this, this, Collections.<IDwelling>emptySet(), ILets.EMPTY);
        }

        @Override
        public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
            // This branch will be discarded at the end.
            final IBranch temp = scope.branch(NO_REASON).getState();

            // This state will become real.
            final IBranch change = scope.getState();

            final ImmutableSet.Builder<IDwelling> builder = ImmutableSet.builder();

            for (final SurveyCase hc : cases) {
                stockLogger.acceptCase(new SurveyCaseLogEntry(hc));

                final double surveyWeight;
                if (surveyWeightFunction instanceof HouseWeightFunction) {
                    surveyWeight = hc.getBasicAttributes().getDwellingCaseWeight();
                } else {
                    /*
                     * Make a temporary dwelling which we'll end up throwing away.
                     * Compute the survey weight from it.
                     */
                    IComponentsScope tempDwellingScope = temp.detachedScope(
                            createInState(temp, (float) hc.getBasicAttributes().getDwellingCaseWeight(), hc)
                    );
                    surveyWeight = surveyWeightFunction.compute(tempDwellingScope, lets);
                }

                /*
				 * Obviously a house can't have a negative weight...
                 */
                if (surveyWeight > 0) {
                    /*
		             * Based on the survey weight and the weighting type, 
		             * cut the case up into multiple dwellings with smaller weights.
                     */
                    for (final double d : weighting.apply(surveyWeight)) {
                        builder.add(createInState(change, (float) d, hc));
                    }
                }
            }

            return builder.build();
        }

        private IDwelling createInState(IBranch branch, float weight, SurveyCase hc) {
            final IDwelling dwelling = branch.createDwelling(weight);

            branch.set(structure, dwelling, hc.getStructure().copy());
            branch.set(attributes, dwelling, hc.getBasicAttributes().copy());
            branch.set(technologies, dwelling, hc.getTechnologies().copy());
            branch.set(people, dwelling, hc.getPeople().copy());
            branch.set(finance, dwelling, hc.getFinancialAttributes().copy());
            branch.set(additional, dwelling, new HouseProperties(hc.getAdditionalProperties()));

            return dwelling;
        }

        @Override
        public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
            return dwellings;
        }

        @Override
        public StateChangeSourceType getSourceType() {
            return StateChangeSourceType.CREATION;
        }

        @Override
        public Name getIdentifier() {
            return identifier;
        }

        @Override
        public String toString() {
            return String.valueOf(getIdentifier());
        }
    }

    private static final Function<SurveyCase, Integer> BY_BUILD_YEAR
            = new Function<SurveyCase, Integer>() {
        @Override
        public Integer apply(final SurveyCase input) {
            return input.getBasicAttributes().getBuildYear();
        }
    };

    @Override
    public void initialize() throws NHMException {
        log.debug("Loading stocks {}", importIDs);
        final List<SurveyCase> allHouses = new LinkedList<>();

        for (final String importID : importIDs) {
            log.debug("Loading stock {}", importID);
            final List<SurveyCase> stockHouses = surveyCaseDataService.getSurveyCases(importID, requestedHouseProperties.getRequested());
            log.debug("Loaded stock {} with {} cases", importID, stockHouses.size());
            allHouses.addAll(stockHouses);
        }

        // 1: group houses by their build year
        final Multimap<Integer, SurveyCase> byBuildYear = Multimaps.index(allHouses, BY_BUILD_YEAR);

        // 2: put years in order
        final SortedSet<Integer> buildYears = ImmutableSortedSet.copyOf(byBuildYear.keySet());

        final int startYear = simulator.getCurrentDate().getYear();

        // 3: schedule immediate construction for all houses up to the current year
        {
            final Set<Integer> buildYearsToNow = buildYears.headSet(startYear + 1);

            final ImmutableList.Builder<SurveyCase> existingCases = ImmutableList.builder();
            for (final int year : buildYearsToNow) {
                existingCases.addAll(byBuildYear.get(year));
            }

            final List<SurveyCase> cases = existingCases.build();

            if (!cases.isEmpty()) {
                simulator.schedule(simulator.getCurrentDate(),
                        Priority.ofStockCreator(),
                        new ConstructionEvent(startYear, cases));
            }
        }

        // 4: schedule deferred construction for the later houses
        for (final int year : buildYears.tailSet(startYear + 1)) {
            final Collection<SurveyCase> cases = byBuildYear.get(year);
            final DateTime when = new DateTime(year, DateTimeConstants.JANUARY,
                    1, 0, 0,
                    DateTimeZone.UTC);

            if (!cases.isEmpty()) {
                simulator.schedule(when,
                        Priority.ofStockCreator(),
                        new ConstructionEvent(year, cases));
            }
        }
    }
}
