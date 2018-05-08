package uk.org.cse.stockimport.hom.impl.steps;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.types.DTOFloorConstructionType;
import uk.org.cse.stockimport.imputation.house.IHousePropertyImputer;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * StructureInitializingBuildStepTest.
 *
 * @author richardt
 * @version $Id: StructureInitializingBuildStepTest.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(MockitoJUnitRunner.class)
public class StructureInitializingBuildStepTest extends Mockito {

    private StructureInitializingBuildStep step;

    @Mock
    HouseCaseDTO dto;

    @Mock
    IHousePropertyImputer housePropertyImputer;

    @Mock
    IHouseCaseSources<IBasicDTO> dtoProvider;

    @Before
    public void initaliseTests() {
        step = new StructureInitializingBuildStep(housePropertyImputer);
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep#getIdentifier()}.
     */
    @Test
    public void testGetIdentifier() {
        assertEquals("getIdentifier()", StructureInitializingBuildStep.IDENTIFIER, step.getIdentifier());
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep#getDependencies()}.
     */
    @Test
    public void testGetDependencies() {
        final Set<String> dependencies = step.getDependencies();
        assertEquals(0, dependencies.size());
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep#getLivingAreaFaction(uk.org.cse.stockimport.domain.impl.HouseCaseDTO)}.
     */
    @Test
    public void testGetLivingAreaFactionReturnsLivingAreaFactionFromHouseCaseDTOIfNotNull() {
        final double livingAreaFaction = 0.75;
        when(dto.getLivingAreaFaction()).thenReturn(livingAreaFaction);
        assertEquals(livingAreaFaction, step.getLivingAreaFaction(dto), 0.00);
    }

    @Test
    public void testGetLivingAreaFactionReturnsLivingAreaFactionFromImputerIfNoAreaForLivingRoom() throws Exception {
        final double livingAreaFaction = 0.00;
        final int numOfHabitableRooms = 1;
        final double expectedLivingAreaFaction = 0.75;

        when(dto.getLivingAreaFaction()).thenReturn(livingAreaFaction);
        when(dto.getNumOfHabitalRooms()).thenReturn(numOfHabitableRooms);
        when(housePropertyImputer.getLivingAreaFraction(numOfHabitableRooms)).thenReturn(expectedLivingAreaFaction);

        assertEquals(expectedLivingAreaFaction, step.getLivingAreaFaction(dto), 0.00);
    }

    @Test
    public void testDraftLobyIsSet() throws Exception {
        final SurveyCase surveyCase = new SurveyCase();

        when(dtoProvider.requireOne(HouseCaseDTO.class)).thenReturn(dto);
        when(dto.getBuiltFormType()).thenReturn(BuiltFormType.PurposeBuiltHighRiseFlat);
        when(dtoProvider.getOne(IVentilationDTO.class)).thenReturn(Optional.<IVentilationDTO>absent());
        when(dto.isHasDraftLoby()).thenReturn(true);
        when(dto.getBuildYear()).thenReturn(2008);
        when(dto.getRegionType()).thenReturn(RegionType.EastOfEngland);
        when(dto.getFloorConstructionType()).thenReturn(DTOFloorConstructionType.Solid);

        step.build(surveyCase, dtoProvider);

        final StructureModel sm = surveyCase.getStructure();
        Assert.assertNotNull("structure model not null", sm);
        assertEquals("draft lobby not set", true, sm.hasDraughtLobby());
    }
}
