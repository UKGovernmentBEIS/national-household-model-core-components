package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.types.MainHeatingSystemType;

/**
 * GetMainHeatingSystemTypeTest.
 *
 * @author richardTiffin
 * @version $Id$
 */
@RunWith(MockitoJUnitRunner.class)
public class GetMainHeatingSystemTypeTest {

    GetMainHeatingSystemType func;

    @Mock
    IDimension<ITechnologyModel> techDimension;
    @Mock
    ITechnologyModel technologies;
    @Mock
    ICentralHeatingSystem centralHeatingSystem;
    @Mock
    IBoiler boiler;

    @Before
    public void initialiseTests() {
        func = new GetMainHeatingSystemType(techDimension);
    }

    @Test
    public void IsCondensingReturnsTrueOnlyIfHeatSourceIsBoilderAndIsCondensing() throws Exception {
        when(technologies.getPrimarySpaceHeater()).thenReturn(centralHeatingSystem);
        when(centralHeatingSystem.getHeatSource()).thenReturn(boiler);
        when(boiler.isCondensing()).thenReturn(true);

        Assert.assertTrue(func.isCondensing(technologies));
    }

    @Test
    public void IsCondensingReturnsFalseIfHeatSourceIsNotBoiler() throws Exception {
        when(technologies.getPrimarySpaceHeater()).thenReturn(centralHeatingSystem);
        when(centralHeatingSystem.getHeatSource()).thenReturn(mock(IHeatPump.class));

        Assert.assertFalse(func.isCondensing(technologies));
    }

    @Test
    public void WillReturnACondensingBoilerType() throws Exception {
        when(technologies.getPrimarySpaceHeater()).thenReturn(centralHeatingSystem);
        when(centralHeatingSystem.getHeatSource()).thenReturn(boiler);
        when(boiler.isCondensing()).thenReturn(true);

        Assert.assertEquals(MainHeatingSystemType.Condensing, func.getTypeOfMainHeatingSystem(technologies));
    }

    @Test
    public void WillReturnACondensingCombiBoilerType() throws Exception {
        final ICombiBoiler combi = mock(ICombiBoiler.class);

        when(technologies.getPrimarySpaceHeater()).thenReturn(centralHeatingSystem);
        when(centralHeatingSystem.getHeatSource()).thenReturn(combi);
        when(combi.isCondensing()).thenReturn(true);

        Assert.assertEquals(MainHeatingSystemType.CondensingCombiBoiler, func.getTypeOfMainHeatingSystem(technologies));
    }

    @Test
    public void WillStillReturnStandardNonCondensingBoiler() {
        when(technologies.getPrimarySpaceHeater()).thenReturn(centralHeatingSystem);
        when(centralHeatingSystem.getHeatSource()).thenReturn(boiler);

        Assert.assertEquals(MainHeatingSystemType.StandardBoiler, func.getTypeOfMainHeatingSystem(technologies));
    }
}
