package uk.org.cse.stockimport.hom;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * A composing device for {@link ISurveyCaseBuildStep}s. Add several steps with
 * {@link #addStep(ISurveyCaseBuildStep)}, and then run
 * {@link #build(IHouseCaseSources)} to create a case from the given
 * {@link IHouseCaseSources}.
 *
 *
 * @author hinton
 * @since 1.0
 */
public interface ISurveyCaseBuilder {

    /**
     * Add a step to the build steps this builder will perform
     *
     * @param step
     * @since 1.0
     */
    public void addStep(final ISurveyCaseBuildStep step);

    /**
     * Do any required setup after using {@link #addStep(ISurveyCaseBuildStep)}
     * to add any steps (for example, putting the steps in dependency order)
     *
     * @since 1.0
     */
    public void initialize();

    /**
     * @param provider
     * @return a survey case produced using the steps previously added with
     * {@link #addStep(ISurveyCaseBuildStep)}
     * @since 1.0
     */
    public SurveyCase build(final IHouseCaseSources<?> provider);
}
