package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class RoofInsulationMeasureTest {
	private static final boolean NO_TOPUP = false;
	private static final boolean WITH_TOPUP = true;
	private static final double INSULATION_THICKNESS = 50d;
	private static final RoofConstructionType ACCEPTABLE = RoofConstructionType.PitchedSlateOrTiles;

	private IDimension<StructureModel> dimension;

	private ISettableComponentsScope settable(final StructureModel sm, final StructureModel out) {
		final ISettableComponentsScope result = mock(ISettableComponentsScope.class);

		when(result.get(dimension)).thenReturn(sm);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void createMocks() {
		dimension = mock(IDimension.class);
	}

	private RoofInsulationMeasure createMeasure(final boolean topup) {
		return new RoofInsulationMeasure(
				dimension,
				INSULATION_THICKNESS, ConstantComponentsFunction.<Number>of(Name.of("test"), 5d/INSULATION_THICKNESS),
				Optional.<IComponentsFunction<Number>>absent(),
				ConstantComponentsFunction.<Number>of(Name.of("Test"), 0d),
				EnumSet.of(ACCEPTABLE), topup);
	}

	private StructureModel mockStructure(final double externalRoofArea, final RoofConstructionType constructionType, final boolean hasExternalRoof, final double existingInsulation) {
		final StructureModel sm = mock(StructureModel.class);
		when(sm.getExternalRoofArea()).thenReturn(externalRoofArea);
		when(sm.getRoofConstructionType()).thenReturn(constructionType);
		when(sm.getRoofInsulationThickness()).thenReturn(existingInsulation);
		when(sm.getHasLoft()).thenReturn(true);
		when(sm.hasExternalRoof()).thenReturn(hasExternalRoof);
		return sm;
	}

	private IComponentsScope components(final StructureModel sm) {
		final IComponentsScope c = mock(IComponentsScope.class);
		when(c.get(dimension)).thenReturn(sm);
		return c;
	}

	@Test
	public void unsuitableForHousesWithNoRoof() {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);

		final StructureModel sm = mockStructure(0d, ACCEPTABLE, true, 0d);

		Assert.assertFalse("Should not be suitable for houses with no external roof area",
				m.isSuitable(components(sm), ILets.EMPTY));
	}

	@Test
	public void unsuitableForHousesWithNoLoft() {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);

		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, INSULATION_THICKNESS - 10);
		when(sm.getHasLoft()).thenReturn(false);
		Assert.assertFalse("Should not be suitable for houses with no external roof area",
				m.isSuitable(components(sm), ILets.EMPTY));
	}

	@Test
	public void unsuitableForHousesWithWrongRoofType() {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);

		final StructureModel sm = mockStructure(100d, RoofConstructionType.Thatched, true, 0d);

		Assert.assertFalse("Should not be suitable for houses with wrong construction type",
				m.isSuitable(components(sm), ILets.EMPTY
						));
	}

	@Test
	public void unsuitableWithoutTopupIfInsulated() {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);

		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, 1d);

		Assert.assertFalse("Should not be suitable for houses which have insulation, if topup is false",
				m.isSuitable(components(sm), ILets.EMPTY
						));
	}

	@Test
	public void unsuitableWithTopupIfVeryInsulated() {
		final RoofInsulationMeasure m = createMeasure(WITH_TOPUP);

		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, INSULATION_THICKNESS);

		Assert.assertFalse("Should not be suitable with topup if insulation is as thick as intended topup value",
				m.isSuitable(components(sm), ILets.EMPTY
						));
	}

	@Test
	public void suitableWithTopupIfInsufficientlyInsulated() {
		final RoofInsulationMeasure m = createMeasure(WITH_TOPUP);

		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, INSULATION_THICKNESS - 10);

		Assert.assertTrue("Should be suitable for topup if existing < topup and external area > 0 and construction type is OK",
				m.isSuitable(components(sm), ILets.EMPTY
						));
	}

	@Test
	public void suitableWithoutTopupIfUninsulated() {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);

		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, 0);

		Assert.assertTrue("Should be suitable for topup if existing < topup and external area > 0 and construction type is OK",
				m.isSuitable(components(sm), ILets.EMPTY
						));
	}

	@Test
	public void suitableIfNoExternalRoof() {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);
		final StructureModel sm = mockStructure(100d, ACCEPTABLE, false, 0);
		Assert.assertFalse(
				"Should be suitable for topup if existing < topup and external area > 0 and construction type is OK",
				m.isSuitable(components(sm), ILets.EMPTY)
			);
	}

	@Test
	public void correctAmountIsInstalledWithoutTopup() throws NHMException {
		final RoofInsulationMeasure m = createMeasure(NO_TOPUP);
		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, 0);
		final StructureModel out = mock(StructureModel.class);
		final Storey storey = mock(Storey.class);

		when(out.getStoreys()).thenReturn(Collections.singletonList(storey));

		final ISettableComponentsScope settable = settable(sm, out);
		m.apply(settable, ILets.EMPTY);
		runCallbacks(settable, out);

		verify(out).setRoofInsulationThickness(INSULATION_THICKNESS);
		verify(storey).addCeilingInsulation(5d);
	}



	@Test
	public void correctAmountIsInstalledWithTopup() throws NHMException {
		final RoofInsulationMeasure m = createMeasure(WITH_TOPUP);
		final StructureModel sm = mockStructure(100d, ACCEPTABLE, true, INSULATION_THICKNESS/2);
		final StructureModel out = mockStructure(100d, ACCEPTABLE, true, INSULATION_THICKNESS/2);
		final Storey storey = mock(Storey.class);

		when(out.getStoreys()).thenReturn(Collections.singletonList(storey));

		final ISettableComponentsScope settable = settable(sm, out);
		m.apply(settable, ILets.EMPTY);
		runCallbacks(settable, out);

		verify(out).setRoofInsulationThickness(INSULATION_THICKNESS);
		verify(storey).addCeilingInsulation(2.5d);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void runCallbacks(final ISettableComponentsScope settable, final StructureModel out) {
		final ArgumentCaptor<IModifier> captor = ArgumentCaptor.forClass(IModifier.class);
		verify(settable).modify(eq(dimension), captor.capture());
		for (final IModifier mod : captor.getAllValues()) {
			mod.modify(out);
		}
	}
}
