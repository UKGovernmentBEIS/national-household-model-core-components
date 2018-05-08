package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.AdditionalHousePropertiesDTO;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * Adds the static additional house properties to the SurveyCase.
 *
 * @since 2.0
 */
public class AdditionalHouseCasePropertyStep implements ISurveyCaseBuildStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElevationBuildStep.class);

    @Override
    public String getIdentifier() {
        return AdditionalHouseCasePropertyStep.class.getCanonicalName();
    }

    @Override
    public Set<String> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final Optional<AdditionalHousePropertiesDTO> dto = dtoProvider.getOne(AdditionalHousePropertiesDTO.class);
        if (dto.isPresent()) {
            model.setAdditionalProperties(dto.get().getValuesByProperty());
        } else {
            LOGGER.debug("No additional properties found for ref:{}", dtoProvider.getAacode());
        }
    }
}
