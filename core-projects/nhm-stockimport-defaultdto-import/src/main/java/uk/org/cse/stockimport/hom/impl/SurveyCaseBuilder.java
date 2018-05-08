package uk.org.cse.stockimport.hom.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.ISurveyCaseBuilder;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * @since 1.0
 */
public class SurveyCaseBuilder implements ISurveyCaseBuilder {

    private static final Logger logger = LoggerFactory.getLogger(SurveyCaseBuilder.class);

    private final List<ISurveyCaseBuildStep> steps = new ArrayList<ISurveyCaseBuildStep>();
    private boolean initialized = false;

    @Override
    public void addStep(final ISurveyCaseBuildStep step) {
        if (initialized) {
            throw new UnsupportedOperationException("Steps should not be added after initialize() has been called");
        }
        steps.add(step);
        if (logger.isDebugEnabled()) {
            logger.debug("addStep(): added:{}", step.getIdentifier());
        }
    }

    public Set<String> getExistingStepIdentifiers() {
        final Set<String> identifiers = new HashSet<String>();
        for (final ISurveyCaseBuildStep step : steps) {
            identifiers.add(step.getIdentifier());
        }
        return identifiers;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public SurveyCase build(final IHouseCaseSources provider) {
        final SurveyCase c = new SurveyCase();

        for (final ISurveyCaseBuildStep step : steps) {
            step.build(c, provider);
        }

        return c;
    }

    @Override
    public void initialize() {
        if (initialized) {
            throw new UnsupportedOperationException("Should not be initialized twice");
        }
        initialized = true;

        sortSteps();

        final StringBuffer s = new StringBuffer();
        for (final ISurveyCaseBuildStep step : steps) {
            s.append(step.getIdentifier() + " ");
        }
        logger.debug("Sorted steps: {}", s);
    }

    /**
     * Put the {@link #steps} list in dependency order.
     */
    private void sortSteps() {
        final Map<String, ISurveyCaseBuildStep> stepsByName = new HashMap<String, ISurveyCaseBuildStep>();
        for (final ISurveyCaseBuildStep step : steps) {
            stepsByName.put(step.getIdentifier(), step);
        }

        steps.clear();

        while (stepsByName.isEmpty() == false) {
            final ISurveyCaseBuildStep step = stepsByName.values().iterator().next();
            insert(step, stepsByName);
        }
    }

    private void insert(final ISurveyCaseBuildStep step, final Map<String, ISurveyCaseBuildStep> stepsByName) {
        for (final String dependencyName : step.getDependencies()) {
            if (stepsByName.containsKey(dependencyName)) {
                final ISurveyCaseBuildStep dependency = stepsByName.get(dependencyName);
                insert(dependency, stepsByName);
            }
            //TODO detect cycles or missing dependencies
        }
        stepsByName.remove(step.getIdentifier());
        steps.add(step);
    }

}
