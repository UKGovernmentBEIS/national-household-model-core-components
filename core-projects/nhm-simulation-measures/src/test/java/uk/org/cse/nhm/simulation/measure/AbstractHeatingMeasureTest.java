package uk.org.cse.nhm.simulation.measure;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.WetHeatingMeasure;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class AbstractHeatingMeasureTest {

    private IDimension<IPowerTable> en;
    private ITimeDimension time;
    private IWetHeatingMeasureFactory wetHeatingMeasureFactory;
    private ITechnologyOperations operations;
    private IDimension<ITechnologyModel> technologies;

    @Before
    @SuppressWarnings("unchecked")
    public void mockDimensions() {
        time = mock(ITimeDimension.class);
        en = mock(IDimension.class);
        technologies = mock(IDimension.class);
        operations = mock(ITechnologyOperations.class);

        wetHeatingMeasureFactory = new IWetHeatingMeasureFactory() {
            @Override
            public WetHeatingMeasure create(final IComponentsFunction<? extends Number> capex) {
                return new WetHeatingMeasure(null, null) {
                    @Override
                    public boolean applyMayInstallWetHeating(
                            final ISettableComponentsScope scopeOfHeatingMeasure,
                            final ILets lets, final HeatingMeasureApplication measure) {
                        return measure.apply(scopeOfHeatingMeasure, lets);
                    }
                };
            }
        };
    }

    @Test
    public void testAbstractHeatingMeasureSuitabilityFromSizing() {
        final ISizingFunction sf = mock(ISizingFunction.class);
        when(sf.computeSize(any(IComponentsScope.class), any(ILets.class), any(Units.class))).thenReturn(SizingResult.unsuitable(0, Units.KILOWATTS));
        final AbstractHeatingMeasure ahm = new AbstractHeatingMeasure(
                time,
                technologies,
                operations,
                wetHeatingMeasureFactory,
                null,
                sf,
                null, null, null
        ) {

            @Override
            protected boolean doApply(final ISettableComponentsScope components,
                    final ILets lets, final double size, final double capex, final double opex)
                    throws NHMException {
                return false;
            }

            @Override
            protected boolean doIsSuitable(final IComponents components) {
                return true;
            }

            @Override
            protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
                return null;
            }
        };

        final IPowerTable e = mock(IPowerTable.class);

        final IComponentsScope components = mock(IComponentsScope.class);
        when(components.get(en)).thenReturn(e);

        Assert.assertFalse("Not suitable as no size is available", ahm.isSuitable(components, ILets.EMPTY));
    }

    @Test
    public void testAbstractHeatingMeasureSuitabilityFromSubclass() {
        final boolean[] wasCalled = new boolean[1];

        final ISizingFunction sf = mock(ISizingFunction.class);
        when(sf.computeSize(any(IComponentsScope.class), any(ILets.class), any(Units.class))).thenReturn(SizingResult.suitable(1, Units.KILOWATTS));
        final AbstractHeatingMeasure ahm = new AbstractHeatingMeasure(
                time,
                technologies,
                operations,
                wetHeatingMeasureFactory,
                null,
                sf,
                null, null, null
        ) {

            @Override
            protected boolean doApply(final ISettableComponentsScope components,
                    final ILets lets, final double size, final double capex, final double opex)
                    throws NHMException {
                return false;
            }

            @Override
            protected boolean doIsSuitable(final IComponents components) {
                wasCalled[0] = true;
                return false;
            }

            @Override
            protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
                return null;
            }
        };

        final IComponentsScope components = mock(IComponentsScope.class);
        Assert.assertFalse("Not suitable because of inner method", ahm.isSuitable(components, ILets.EMPTY));
        Assert.assertTrue("Inner was called", wasCalled[0]);
    }

    @Test
    public void testAbstractHeatingMeasureInstallationDetails() throws NHMException {
        final boolean[] wasCalled = new boolean[1];

        final double[] sco = new double[3];

        final ISizingFunction sf = mock(ISizingFunction.class);
        when(sf.computeSize(any(IComponentsScope.class), any(ILets.class), any(Units.class))).thenReturn(SizingResult.suitable(1, Units.KILOWATTS));

        final AbstractHeatingMeasure ahm = new AbstractHeatingMeasure(
                time,
                technologies,
                operations,
                wetHeatingMeasureFactory,
                TechnologyType.airSourceHeatPump(),
                sf,
                ConstantComponentsFunction.<Number>of(Name.of("test"), 1d), ConstantComponentsFunction.<Number>of(Name.of("test"), 2d), ConstantComponentsFunction.<Number>of(Name.of("test"), 3d)) {

            @Override
            protected boolean doApply(final ISettableComponentsScope components,
                    final ILets lets, final double size, final double capex, final double opex)
                    throws NHMException {
                sco[0] = size;
                sco[1] = capex;
                sco[2] = opex;
                return true;
            }

            @Override
            protected boolean doIsSuitable(final IComponents components) {
                wasCalled[0] = true;
                return true;
            }

            @Override
            protected Set<HeatingSystemControlType> getHeatingSystemControlTypes() {
                return Collections.emptySet();
            }
        };

        final ISettableComponentsScope components = mock(ISettableComponentsScope.class);

        Assert.assertTrue("Suitable", ahm.isSuitable(components, ILets.EMPTY));
        Assert.assertTrue("Inner was called", wasCalled[0]);

        when(components.get(time)).thenReturn(new ITime() {

            @Override
            public DateTime get(final XForesightLevel key) {
                return new DateTime(0);
            }

            @Override
            public DateTime get(final ILets lets) {
                return new DateTime(0);
            }

            @Override
            public DateTime get(final Optional<XForesightLevel> foresight, final ILets lets) {
                return new DateTime(0);
            }

            @Override
            public Set<XForesightLevel> predictableLevels() {
                throw new UnsupportedOperationException();
            }
        });

        ahm.apply(components, ILets.EMPTY);
        Assert.assertTrue(Arrays.toString(sco), Arrays.equals(sco, new double[]{1, 1, 2}));

        final ArgumentCaptor<ITechnologyInstallationDetails> tidCaptor = ArgumentCaptor.forClass(ITechnologyInstallationDetails.class);
        verify(components, atLeastOnce()).addNote(tidCaptor.capture());
        final List<ITechnologyInstallationDetails> details = tidCaptor.getAllValues();

        Assert.assertEquals(2, details.size());

        final ITechnologyInstallationDetails tid = details.get(1);
        Assert.assertEquals(TechnologyType.airSourceHeatPump(),
                tid.getInstalledTechnology());
        Assert.assertEquals(1, tid.getInstalledQuantity(), 0.01);
        Assert.assertEquals(Units.KILOWATTS, tid.getUnits());
    }
}
