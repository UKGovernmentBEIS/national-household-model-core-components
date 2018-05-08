package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.impl.OccupantDetailsDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * FinancialAttributesBuilderStep.
 *
 * @author richardt
 * @version $Id: FinancialAttributesBuilderStep.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 0.0.1-SNAPSHOT
 */
public class FinancialAttributesBuilderStep implements ISurveyCaseBuildStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinancialAttributesBuilderStep.class);
    /**
     * @since 1.0
     */
    public static final String IDENTIFIER = FinancialAttributesBuilderStep.class.getCanonicalName();
    private final AtomicInteger ctNullOccupantDetails = new AtomicInteger(0);

    /**
     * @return @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    /**
     * @return @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#getDependencies()
     */
    @Override
    public Set<String> getDependencies() {
        return Collections.emptySet();
    }

    /**
     * @param model
     * @param dtoProvider
     * @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#build(uk.org.cse.nhm.hom.SurveyCase,
     * uk.org.cse.stockimport.repository.IHouseCaseSources)
     */
    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final Optional<OccupantDetailsDTO> maybeDto = dtoProvider.getOne(OccupantDetailsDTO.class);

        final FinancialAttributes attributes;
        if (maybeDto.isPresent()) {
            final OccupantDetailsDTO dto = maybeDto.get();
            attributes = new FinancialAttributes(
                    dto.getChiefIncomeEarnersAge().orNull(),
                    dto.getHouseHoldIncomeBeforeTax().orNull());
        } else {
            ctNullOccupantDetails.incrementAndGet();
            LOGGER.warn("OccupantDetailsDTO null for hcase:{}, financial attributes not set, totalfound={}",
                    dtoProvider.getAacode(), ctNullOccupantDetails);
            // TODO: Handling of null values for house cases
            attributes = new FinancialAttributes(null, null);
        }

        model.setFinancialAttributes(attributes);
    }
}
