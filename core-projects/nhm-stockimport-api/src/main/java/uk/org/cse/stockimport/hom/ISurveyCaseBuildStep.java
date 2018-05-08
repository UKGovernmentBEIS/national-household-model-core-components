package uk.org.cse.stockimport.hom;

import java.util.Set;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * Something which builds a part of the object model from some DTOs
 *
 * @author hinton
 * @since 1.0
 */
public interface ISurveyCaseBuildStep {

    /**
     * @return the identifying name of this build step
     * @since 1.0
     */
    public String getIdentifier();

    /**
     * @return the names of other build steps which need already to have
     * happened for this to work.
     * @since 1.0
     */
    public Set<String> getDependencies();

    /**
     * Run this build step; update the given object model using the associated
     * DTO provider.
     *
     * @param model
     * @param dtoProvider
     * @since 1.0
     */
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider);
}
