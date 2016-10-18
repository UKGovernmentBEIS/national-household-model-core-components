package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Collections;
import java.util.Set;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * Sets up the basic attributes of a survey case - this is very simple, and doesn't have to do an imputation
 * @author hinton
 * @since 1.0
 */
public class BasicAttributesBuildStep implements ISurveyCaseBuildStep {
	/** @since 1.0 */
    public static final String IDENTIFIER = BasicAttributesBuildStep.class.getCanonicalName();

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return Collections.emptySet();
	}

	@Override
	public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
		final HouseCaseDTO dto = dtoProvider.requireOne(HouseCaseDTO.class);
		
		final BasicCaseAttributes attributes = new BasicCaseAttributes(
				dto.getAacode(),
				dto.getDwellingCaseWeight(),
				dto.getHouseholdCaseWeight(),
				dto.getRegionType(),
				dto.getMorphologyType(),
				dto.getTenureType(),
				dto.getBuildYear());
		
		model.setBasicAttributes(attributes);
	}
}
