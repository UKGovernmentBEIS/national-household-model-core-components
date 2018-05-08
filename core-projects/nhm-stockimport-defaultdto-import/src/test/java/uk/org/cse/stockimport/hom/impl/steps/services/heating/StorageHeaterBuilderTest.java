package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;

public class StorageHeaterBuilderTest {

    @Test
    public void testBuildRubbishOldStorageHeater() {
        final IStorageHeaterBuilder shb = new StorageHeaterBuilder();
        final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);

        when(dto.getStorageHeaterType()).thenReturn(Optional.of(StorageHeaterType.FAN));
        when(dto.getElectricTariff()).thenReturn(Optional.of(ElectricityTariffType.FLAT_RATE));
        when(dto.getStorageHeaterControlType()).thenReturn(Optional.<StorageHeaterControlType>absent());
        final IStorageHeater sh = shb.buildStorageHeater(dto);

        Assert.assertEquals(StorageHeaterType.FAN, sh.getType());
        Assert.assertEquals(StorageHeaterControlType.CELECT_CHARGE_CONTROL, sh.getControlType());
    }

    @Test
    public void testBuildFancyNewStorageHeater() {
        final IStorageHeaterBuilder shb = new StorageHeaterBuilder();
        final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
        when(dto.getElectricTariff()).thenReturn(Optional.of(ElectricityTariffType.FLAT_RATE));
        when(dto.getStorageHeaterType()).thenReturn(Optional.of(StorageHeaterType.SLIMLINE));
        when(dto.getStorageHeaterControlType()).thenReturn(Optional.<StorageHeaterControlType>absent());
        final IStorageHeater sh = shb.buildStorageHeater(dto);

        Assert.assertEquals(StorageHeaterType.SLIMLINE, sh.getType());
        Assert.assertEquals(StorageHeaterControlType.MANUAL_CHARGE_CONTROL, sh.getControlType());
    }

    @Test
    public void testFlatRateResponsivness() {
        final IStorageHeaterBuilder shb = new StorageHeaterBuilder();
        final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
        when(dto.getElectricTariff()).thenReturn(Optional.of(ElectricityTariffType.FLAT_RATE));
        when(dto.getStorageHeaterType()).thenReturn(Optional.of(StorageHeaterType.SLIMLINE));
        when(dto.getStorageHeaterControlType()).thenReturn(Optional.<StorageHeaterControlType>absent());
        final IStorageHeater sh = shb.buildStorageHeater(dto);

        Assert.assertEquals(StorageHeaterType.SLIMLINE, sh.getType());
        Assert.assertEquals(StorageHeaterControlType.MANUAL_CHARGE_CONTROL, sh.getControlType());
    }

    @Test
    public void testNotFlatRateResponsivness() {
        final IStorageHeaterBuilder shb = new StorageHeaterBuilder();
        final ISpaceHeatingDTO dto = mock(ISpaceHeatingDTO.class);
        when(dto.getElectricTariff()).thenReturn(Optional.of(ElectricityTariffType.ECONOMY_10));
        when(dto.getStorageHeaterType()).thenReturn(Optional.of(StorageHeaterType.SLIMLINE));
        when(dto.getStorageHeaterControlType()).thenReturn(Optional.<StorageHeaterControlType>absent());
        final IStorageHeater sh = shb.buildStorageHeater(dto);

        Assert.assertEquals(StorageHeaterType.SLIMLINE, sh.getType());
        Assert.assertEquals(StorageHeaterControlType.MANUAL_CHARGE_CONTROL, sh.getControlType());
    }
}
