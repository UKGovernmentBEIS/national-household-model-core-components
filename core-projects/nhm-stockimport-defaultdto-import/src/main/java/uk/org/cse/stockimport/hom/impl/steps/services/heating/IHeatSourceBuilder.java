package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.hom.impl.steps.services.SpaceHeatingBuildStep;

/**
 * A subsidiary of the {@link SpaceHeatingBuildStep} which makes boilers
 *
 * @author hinton
 * @since 1.0
 */
public interface IHeatSourceBuilder {

    /**
     * @since 1.0
     */
    public IHeatSource createHeatSource(int constructionYear, ISpaceHeatingDTO dto);

    /**
     * @since 1.0
     */
    public IHeatSource createHeatSource(int constructionYear, IWaterHeatingDTO dto);
}
