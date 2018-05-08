package uk.org.cse.stockimport.hom.impl.steps.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.ILowEnergyLightingDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.BasicAttributesBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * LightingBuilderStep.
 *
 * @author richardt
 * @version $Id: LightingBuilderStep.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class LightingBuildStep implements ISurveyCaseBuildStep {

    protected static final Logger log = LoggerFactory.getLogger(LightingBuildStep.class);
    /**
     * @since 1.0
     */
    public static final String IDENTIFIER = LightingBuildStep.class.getCanonicalName();

    /**
     * Name given to installed efficient lights
     *
     * @since 1.0
     */
    public static final String EFFICIENT_LIGHT = "efficient";
    /**
     * Name given to installed incandescent lights
     *
     * @since 1.0
     */
    public static final String STDLIGHT_LIGHT = "incandescent";
    /**
     * @since 1.0
     */
    public static final double ONE = 1.00;
    /**
     * @since 1.0
     */
    public static final double ZERO = 0.00;

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
        return ImmutableSet.of(BasicAttributesBuildStep.IDENTIFIER,
                StructureInitializingBuildStep.IDENTIFIER);
    }

    /**
     * @param model
     * @param dtoProvider
     * @see
     * uk.org.cse.stockimport.hom.ISurveyCaseBuildStep#build(uk.org.cse.nhm.hom.SurveyCase,
     * uk.org.cse.stockimport.repository.IHouseCaseSources)
     */
    @Override
    public void build(SurveyCase model, IHouseCaseSources<IBasicDTO> dtoProvider) {
        ITechnologyModel technologies = model.getTechnologies();
        Optional<ILowEnergyLightingDTO> lightingDTO = dtoProvider.getOne(ILowEnergyLightingDTO.class);

        double lowEnergyLightsFraction;
        if (lightingDTO.isPresent()) {
            lowEnergyLightsFraction = lightingDTO.get().getLowEnergyLightsFraction();
        } else {
            lowEnergyLightsFraction = ZERO;
        }

        /*
		BEISDOC
		NAME: Lighting energy demand
		DESCRIPTION: Light objects consume Watts of electricity and output Watts of light
		TYPE: Technology model object containing efficiency and proportion
		UNIT: Dimensionless efficiency, Dimensionless proportion
		SAP: (232, L1, L2)
                SAP_COMPLIANT: Yes
		BREDEM: 1B, 1C
                BREDEM_COMPLIANT: Yes
		DEPS: cfl-energy-consumption,incandescent-energy-consumption,adjusted-light-demand
		SET: measure.low-energy-lighting
		STOCK: lighting.csv (fraction)
		ID: lighting-energy-demand
		CODSIEB
         */
        if (lowEnergyLightsFraction > ZERO) {
            ILight light = ITechnologiesFactory.eINSTANCE.createLight();
            light.setType(LightType.CFL);
            light.setName(EFFICIENT_LIGHT);
            light.setProportion(lowEnergyLightsFraction);
            technologies.getLights().add(light);
        }
        if (lowEnergyLightsFraction < ONE) {
            ILight light = ITechnologiesFactory.eINSTANCE.createLight();
            light.setType(LightType.Incandescent);
            light.setName(STDLIGHT_LIGHT);
            light.setProportion(ONE - lowEnergyLightsFraction);
            technologies.getLights().add(light);
        }
    }
}
