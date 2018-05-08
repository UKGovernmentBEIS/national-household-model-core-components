package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;

public class MatchTariffTest {

    private static final FuelType OIL = FuelType.OIL;
    private IDimension<ITariffs> tariffsDimension;
    private ITariff match;
    private MatchTariff matcher;
    private IComponentsScope scope;
    private ITariffs tariffs;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        tariffsDimension = mock(IDimension.class);
        match = mock(ITariff.class);
        when(match.getFuelTypes()).thenReturn(Collections.singleton(OIL));
        matcher = new MatchTariff(match, tariffsDimension);
        scope = mock(IComponentsScope.class);
        tariffs = mock(ITariffs.class);
        when(scope.get(tariffsDimension)).thenReturn(tariffs);
    }

    @Test
    public void testDependencies() {
        Assert.assertEquals("Depends on tariffs", Collections.singleton(tariffsDimension), matcher.getDependencies());
    }

    @Test
    public void testMatching() {
        when(tariffs.getTariff(OIL)).thenReturn(match, mock(ITariff.class));
        Assert.assertTrue("Should match same tariff.", matcher.compute(scope, ILets.EMPTY));
        Assert.assertFalse("Should not match different tariff.", matcher.compute(scope, ILets.EMPTY));
    }
}
