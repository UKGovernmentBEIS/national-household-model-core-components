package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

/**
 * @since 4.2.0
 */
public class DraughtProofingMeasureTest {
	private IDimension<StructureModel> structureDimension;
	
	@SuppressWarnings("unchecked")
	@Before
	public void createMocks() {
		structureDimension = mock(IDimension.class);
	}

	public DraughtProofingMeasure createDraughtProofingMeasure() {
		return new DraughtProofingMeasure(
				structureDimension, 
				ConstantComponentsFunction.<Number>of(Name.of("test"), 0d)
				, 1d);
	}
	
	private IComponentsScope components(final StructureModel sm) {
		final IComponentsScope c = mock(IComponentsScope.class);
		when(c.get(structureDimension)).thenReturn(sm);
		return c;
	}
	
	private static class Proportion {
		private double stripped;

		public double getStripped() {
			return stripped;
		}
		
		public void setStripped(final double stripped) {
			this.stripped = stripped;
		}
	}
	
	@Test
	public void applyDraughtProofingTest() {
		final DraughtProofingMeasure m = createDraughtProofingMeasure();
		final StructureModel sm = mock(StructureModel.class);
		final Proportion stripped = new Proportion();
		when(sm.getDraughtStrippedProportion()).then(new Answer<Double>() {
			@Override
			public Double answer(final InvocationOnMock invocation) throws Throwable {
				return stripped.getStripped();
			}
		});
		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(final InvocationOnMock invocation) throws Throwable {
				final Object[] arguments = invocation.getArguments();
				stripped.setStripped((Double) arguments[0]);
				return null;
			}
		}).when(sm).setDraughtStrippedProportion(any(Double.class));
		
		Assert.assertTrue("Glazing should successfully modify a given structure model.", m.modify(sm));
		Assert.assertEquals("StructureModel.draughtStrippedProportion should match that of the installed measure.", sm.getDraughtStrippedProportion(), m.getProportion(), 0d);
	}
	
	@Test
	public void isSuitableForDraughProofingTest() {
		final DraughtProofingMeasure m = createDraughtProofingMeasure();
		final StructureModel sm = mock(StructureModel.class);
		Assert.assertTrue("Any house should be suitable for draught proofing", m.isSuitable(components(sm), ILets.EMPTY));
	}
}
