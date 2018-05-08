package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.types.VentilationSystem;
import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.domain.impl.VentilationDTO;

public class VentilationMapperTest extends AbsMapperTest {

    final int numIntermittentFans = 1;
    final int numPassiveVents = 2;
    final double windowsAndDoorsDraughtStrippedProportion = 0.75;
    final VentilationSystem ventilationSystem = VentilationSystem.MechanicalBalancedWholeHouseWithHeatRecovery;

    final int numMainHeatingChimneys = 3;
    final int numSecondaryHeatingChimneys = 5;
    final int numChimneysOther = 200;

    @Before
    public void initiateTests() {
        //Assertions
        fields()
                .add(IBasicDTO.AACODE, aacode)
                .add(IVentilationDTO.FANS_FIELD, String.valueOf(numIntermittentFans))
                .add(IVentilationDTO.PASSIVEVENTS_FIELD, String.valueOf(numPassiveVents))
                .add(IVentilationDTO.DRAUGHTSTRIPPED_FIELD, String.valueOf(windowsAndDoorsDraughtStrippedProportion))
                .add(IVentilationDTO.VENTSYSTEMS_FIELD, String.valueOf(ventilationSystem.toString()))
                .add(IVentilationDTO.CHIMNEYSMAIN_FIELD, String.valueOf(numMainHeatingChimneys))
                .add(IVentilationDTO.CHIMNEYSSECONDARY_FIELD, String.valueOf(numSecondaryHeatingChimneys))
                .add(IVentilationDTO.CHIMNEYSOTHER_FIELD, String.valueOf(numChimneysOther));
    }

    @Test
    public void testMapFieldSet() throws Exception {
        final IVentilationDTO ventDto = new MappableDTOReader<>(VentilationDTO.class).read(fieldSet);
        testBuildReferenceData(ventDto, aacode);
        testVentilationData(ventDto, numIntermittentFans, numPassiveVents, windowsAndDoorsDraughtStrippedProportion, ventilationSystem);
        testChimeyData(ventDto, numMainHeatingChimneys, numSecondaryHeatingChimneys, numChimneysOther);
    }

    public static final void testVentilationData(final IVentilationDTO ventilationDTO, final int numIntermittentFans,
            final int numPassiveVents, final double windowsAndDoorsDraughtStrippedProportion, final VentilationSystem ventilationSystem) {
        assertEquals("passive vents", numPassiveVents, ventilationDTO.getPassiveVents());
        assertEquals("intermittent fans", numIntermittentFans, ventilationDTO.getIntermittentFans());
        assertEquals("draught stripped", windowsAndDoorsDraughtStrippedProportion, ventilationDTO.getWindowsAndDoorsDraughtStrippedProportion(), 0.0d);
        assertEquals("ventilation system", ventilationSystem, ventilationDTO.getVentilationSystem());
    }

    public static final void testChimeyData(final IVentilationDTO ventilationDTO,
            final int numMainHeatingChimneys, final int numSecondaryHeatingChimneys, final int numChimneysOther) {
        assertEquals("main chimneys", numMainHeatingChimneys, ventilationDTO.getChimneysMainHeating());
        assertEquals("secondary chimneys", numSecondaryHeatingChimneys, ventilationDTO.getChimneysSecondaryHeating());
        assertEquals("other chimneys", numChimneysOther, ventilationDTO.getChimneysOther());
    }
}
