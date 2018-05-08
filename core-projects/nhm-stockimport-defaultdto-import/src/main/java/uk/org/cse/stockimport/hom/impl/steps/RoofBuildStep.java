package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.geometry.IRoofDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * RoofBuilderStep.
 *
 * @author richardt
 * @version $Id: RoofBuilderStep.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class RoofBuildStep implements ISurveyCaseBuildStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElevationBuildStep.class);
    /**
     * @since 1.0
     */
    public static final String IDENTIFIER = RoofBuildStep.class.getCanonicalName();

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
        return ImmutableSet.of(
                BasicAttributesBuildStep.IDENTIFIER,
                StructureInitializingBuildStep.IDENTIFIER);
    }

    /**
     * Simply sets {@link StructureModel#getRoofConstructionType()} to
     * {@link IRoofDTO#getRoofConstructionType()}
     *
     * @param model
     * @param dtoProvider
     * @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#build(uk.org.cse.nhm.hom.SurveyCase,
     * uk.org.cse.stockimport.repository.IHouseCaseSources)
     */
    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("building roof");
        }
        final IHouseCaseDTO houseCaseDto = dtoProvider.requireOne(IHouseCaseDTO.class);
        final IRoofDTO roofDTO = dtoProvider.requireOne(IRoofDTO.class);

        final StructureModel structure = model.getStructure();
        structure.setRoofConstructionType(roofDTO.getRoofType());
        structure.setRoofInsulationThickness(roofDTO.getInsulationThickness().or(0d));
        structure.setHasLoft(houseCaseDto.isHasLoft());
    }
}
