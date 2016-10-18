package uk.org.cse.stockimport.ehcs2010.spss.builders;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;

public class SpaceHeatingFromSpssElementBuilderTest {

    private SpaceHeatingFromSpssElementBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new SpaceHeatingFromSpssElementBuilder();
    }

    private ISpaceHeatingDTO dummyDTOWithMinimalData() {
        final ISpaceHeatingDTO element = mock(ISpaceHeatingDTO.class);

        when(element.getAacode()).thenReturn("dummyAACode");
        when(element.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.STANDARD);
        when(element.getHeatingSystemControlTypes()).thenReturn(new ArrayList<HeatingSystemControlType>());
        when(element.getMainHeatingFuel()).thenReturn(FuelType.MAINS_GAS);
        when(element.getFlueType()).thenReturn(Optional.<FlueType> absent());
        when(element.getElectricTariff()).thenReturn(Optional.<ElectricityTariffType> absent());
        when(element.getBasicEfficiency()).thenReturn(0.0);
        when(element.getSummerEfficiency()).thenReturn(Optional.<Double> absent());
        when(element.getWinterEfficiency()).thenReturn(Optional.<Double> absent());
        when(element.getStorageHeaterType()).thenReturn(Optional.<StorageHeaterType> absent());
        when(element.getStorageHeaterControlType()).thenReturn(Optional.<StorageHeaterControlType> absent());
        when(element.getCommunityChargingUsageBased()).thenReturn(Optional.<Boolean> absent());
        when(element.getChpFraction()).thenReturn(Optional.<Double> absent());
        when(element.getSecondaryHeatingSystemType()).thenReturn(SecondaryHeatingSystemType.NOT_KNOWN);
        when(element.getStorageCombiCylinderVolume()).thenReturn(Optional.<Double> absent());
        when(element.getStorageCombiSolarVolume()).thenReturn(Optional.<Double> absent());
        when(element.getStorageCombiCylinderInsulationThickness()).thenReturn(Optional.<Double> absent());
        when(element.getStorageCombiCylinderThemostatPresent()).thenReturn(Optional.<Boolean> absent());
        when(element.getStorageCombiCylinderFactoryInsulated()).thenReturn(Optional.<Boolean> absent());
        when(element.getCondensing()).thenReturn(Optional.<Boolean> absent());
        when(element.getInstallationYear()).thenReturn(Optional.<Integer> absent());

        return element;
    }

    @Test
    public void testBuildRowHasSensibleDefaults() {
        assertArrayEquals(new String[] {
                "dummyAACode",
                "0.0",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "mains_gas",
                "not_known",
                "standard",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        }, builder.buildRow(dummyDTOWithMinimalData()));
    }

    @Test
    public void testBuilderShouldOutputCorrectResultsForHouseCaseH0011103IntegrationTest() {
        final ISpaceHeatingDTO element = mock(ISpaceHeatingDTO.class);

        when(element.getAacode()).thenReturn("H0011103");
        when(element.getSpaceHeatingSystemType()).thenReturn(SpaceHeatingSystemType.COMBI);
        when(element.getHeatingSystemControlTypes()).thenReturn(ImmutableList.of(HeatingSystemControlType.PROGRAMMER));
        when(element.getMainHeatingFuel()).thenReturn(FuelType.BULK_LPG);
        when(element.getFlueType()).thenReturn(Optional.of(FlueType.FAN_ASSISTED_BALANCED_FLUE));
        when(element.getElectricTariff()).thenReturn(Optional.<ElectricityTariffType> absent());
        when(element.getBasicEfficiency()).thenReturn(0.898);
        when(element.getSummerEfficiency()).thenReturn(Optional.of(0.806));
        when(element.getWinterEfficiency()).thenReturn(Optional.of(0.907));
        when(element.getStorageHeaterType()).thenReturn(Optional.<StorageHeaterType> absent());
        when(element.getStorageHeaterControlType()).thenReturn(Optional.<StorageHeaterControlType> absent());
        when(element.getCommunityChargingUsageBased()).thenReturn(Optional.<Boolean> absent());
        when(element.getChpFraction()).thenReturn(Optional.<Double> absent());
        when(element.getSecondaryHeatingSystemType()).thenReturn(SecondaryHeatingSystemType.ELECTRIC_ROOM_HEATERS);

        when(element.getStorageCombiCylinderVolume()).thenReturn(Optional.<Double> absent());
        when(element.getStorageCombiSolarVolume()).thenReturn(Optional.<Double> absent());
        when(element.getStorageCombiCylinderInsulationThickness()).thenReturn(Optional.<Double> absent());
        when(element.getStorageCombiCylinderThemostatPresent()).thenReturn(Optional.<Boolean> absent());
        when(element.getStorageCombiCylinderFactoryInsulated()).thenReturn(Optional.<Boolean> absent());
        when(element.getCondensing()).thenReturn(Optional.<Boolean> absent());
        when(element.getInstallationYear()).thenReturn(Optional.<Integer> absent());

        assertArrayEquals(new String[] {
                "H0011103",
                "0.898",
                "",
                "",
                "",
                "fan_assisted_balanced_flue",
                "0.806",
                "0.907",
                "",
                "programmer",
                "",
                "bulk_lpg",
                "electric_room_heaters",
                "combi",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        }, builder.buildRow(element));
    }

    @Test
    public void testWritingOutHeatingSystemControlTypes() {
        final ISpaceHeatingDTO element = dummyDTOWithMinimalData();
        when(element.getHeatingSystemControlTypes()).thenReturn(ImmutableList.copyOf(HeatingSystemControlType.VALUES));

        final int indexOfHeatingSystemControlTypes = Arrays.asList(builder.buildHeader(null))
                .indexOf("heatingsystemcontroltypes");
        final String result = builder.buildRow(element)[indexOfHeatingSystemControlTypes];

        for (final HeatingSystemControlType type : HeatingSystemControlType.VALUES) {
            assertTrue(type + " should be included in the built heatingSystemControlTypes column. "+result,
                    result.contains(type.name().toLowerCase()));
        }

        assertTrue(
                "heatingSystemControlTypes column should match a regex of word characters followed by optional semicolons",
                result.matches("(\\w+;?)+"));
    }
}
