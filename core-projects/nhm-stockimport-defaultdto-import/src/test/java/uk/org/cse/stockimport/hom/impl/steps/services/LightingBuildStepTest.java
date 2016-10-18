package uk.org.cse.stockimport.hom.impl.steps.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.ILowEnergyLightingDTO;
import uk.org.cse.stockimport.hom.impl.steps.BasicAttributesBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * LightingBuilderStepTest.
 *
 * @author richardt
 * @version $Id: LightingBuilderStepTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(MockitoJUnitRunner.class)
public class LightingBuildStepTest extends Mockito {

    private LightingBuildStep step;

    @Mock
    SurveyCase model;
    @Mock
    IHouseCaseSources<IBasicDTO> dtoProvider;
    @Mock
    ILowEnergyLightingDTO lowEnergyDTO;

    /**
     * TODO.
     * 
     * @throws java.lang.Exception
     * @since 0.0.1-SNAPSHOT
     */
    @Before
    public void setUp() throws Exception {
        step = new LightingBuildStep();
    }

    /**
     * Test method for {@link uk.org.cse.stockimport.hom.impl.steps.services.LightingBuildStep#getIdentifier()}.
     */
    @Test
    public void testGetIdentifier() {
        assertEquals("getIdentifier()", LightingBuildStep.IDENTIFIER, step.getIdentifier());
    }

    /**
     * Test method for {@link uk.org.cse.stockimport.hom.impl.steps.services.LightingBuildStep#getDependencies()}.
     */
    @Test
    public void testGetDependencies() {
        final Set<String> dependencies = step.getDependencies();
        assertTrue("Builder needs step to run first", dependencies.contains(BasicAttributesBuildStep.IDENTIFIER));
        assertTrue("Builder needs step to run first", dependencies.contains(StructureInitializingBuildStep.IDENTIFIER));
    }

    /**
     * Test method for {@link uk.org.cse.stockimport.hom.impl.steps.services.LightingBuildStep#build(uk.org.cse.nhm.hom.SurveyCase, uk.org.cse.stockimport.repository.IHouseCaseSources)}.
     */
    @Test
    public void testBuildLightsAddCorrectProportionOfEnergyEfficientLightsIf50PercentLowEnergy() {
        final ITechnologyModel technologies = ITechnologiesFactory.eINSTANCE.createTechnologyModel();

        when(model.getTechnologies()).thenReturn(technologies);
        when(this.dtoProvider.getOne(ILowEnergyLightingDTO.class)).thenReturn(Optional.of(lowEnergyDTO));
        when(lowEnergyDTO.getLowEnergyLightsFraction()).thenReturn(0.5);

        // Execute Method under test
        step.build(model, dtoProvider);

        // Run tests against the output
        verify(model).getTechnologies();
        assertEquals("Expected 2 lighting types for 50% low energy fraction", 2, technologies.getLights().size());
        final ILight lightOne = technologies.getLights().get(0);
        final ILight lightTwo = technologies.getLights().get(1);
        assertEquals("Expected 0.5 (50%)", 0.5, lightOne.getProportion(), 0.00);
        assertEquals("Expected 0.5 (50%)", 0.5, lightTwo.getProportion(), 0.00);
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.hom.impl.steps.services.LightingBuildStep#build(uk.org.cse.nhm.hom.SurveyCase, uk.org.cse.stockimport.repository.IHouseCaseSources)}
     * .
     */
    @Test
    public void testBuildLightsAddCorrectProportionOfEnergyEfficientLightsIf100PercentLowEnergy() {
        final ITechnologyModel technologies = ITechnologiesFactory.eINSTANCE.createTechnologyModel();

        when(model.getTechnologies()).thenReturn(technologies);
        when(this.dtoProvider.getOne(ILowEnergyLightingDTO.class)).thenReturn(Optional.of(lowEnergyDTO));
        when(lowEnergyDTO.getLowEnergyLightsFraction()).thenReturn(1.00);

        // Execute Method under test
        step.build(model, dtoProvider);

        // Run tests against the output
        verify(model).getTechnologies();
        assertEquals("Expected 2 lighting types for 100% low energy fraction", 1, technologies.getLights().size());
        final ILight lightOne = technologies.getLights().get(0);
        assertEquals("Light name", LightingBuildStep.EFFICIENT_LIGHT, lightOne.getName());
        assertEquals("Expected 1.5 (100%)", 1.00, lightOne.getProportion(), 0.00);
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.hom.impl.steps.services.LightingBuildStep#build(uk.org.cse.nhm.hom.SurveyCase, uk.org.cse.stockimport.repository.IHouseCaseSources)}
     * .
     */
    @Test
    public void testBuildLightsAddCorrectProportionOfEnergyEfficientLightsIfZeroPercentLowEnergy() {
        final ITechnologyModel technologies = ITechnologiesFactory.eINSTANCE.createTechnologyModel();

        when(model.getTechnologies()).thenReturn(technologies);
        when(this.dtoProvider.getOne(ILowEnergyLightingDTO.class)).thenReturn(Optional.of(lowEnergyDTO));
        when(lowEnergyDTO.getLowEnergyLightsFraction()).thenReturn(0.00);

        // Execute Method under test
        step.build(model, dtoProvider);

        // Run tests against the output
        verify(model).getTechnologies();
        assertEquals("Expected 1 lighting types for 100% std energy fraction", 1, technologies.getLights().size());
        final ILight lightOne = technologies.getLights().get(0);
        assertEquals("Light name", LightingBuildStep.STDLIGHT_LIGHT, lightOne.getName());
        assertEquals(1.00, lightOne.getProportion(), 0.00);
    }
}
