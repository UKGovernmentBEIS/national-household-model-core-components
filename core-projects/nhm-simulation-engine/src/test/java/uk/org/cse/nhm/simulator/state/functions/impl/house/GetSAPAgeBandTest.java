package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetSAPAgeBandTest {

	@SuppressWarnings("unchecked")
	@Test
	public void calculatesCorrectly() {
		final IDimension<BasicCaseAttributes> dim = mock(IDimension.class);
		final IComponentsScope scope = mock(IComponentsScope.class);
		final BasicCaseAttributes basic = mock(BasicCaseAttributes.class);
		when(scope.get(dim)).thenReturn(basic);
		final GetSAPAgeBand fun = new GetSAPAgeBand(dim, 2010);

		when(basic.getRegionType()).thenReturn(RegionType.SouthWest);
		when(basic.getBuildYear()).thenReturn(0);
		Assert.assertEquals("Age Band", "A", fun.compute(scope, ILets.EMPTY).toString());
		
		when(basic.getBuildYear()).thenReturn(2014);
		Assert.assertEquals("Age Band", "K", fun.compute(scope, ILets.EMPTY).toString());
	}
}
