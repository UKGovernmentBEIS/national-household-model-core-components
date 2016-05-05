package uk.org.cse.nhm.simulation.measure;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class StorageHeaterMeasureTest {
	@Mock
	private ITimeDimension time;
	@Mock
	private IDimension<ITechnologyModel> techs;
	@Mock
	private ITechnologyOperations operations;
	@Mock
	private ISizingFunction sizingFunction;
	@Mock
	private ISettableComponentsScope components;
	@Mock
	IComponentsFunction<Number> responsiveness;

	@Mock
	private IWetHeatingMeasureFactory whmf;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void isSuitable() {
		final StorageHeaterMeasure measure = 
				new StorageHeaterMeasure(
						time, 
						whmf,
						techs, 
						operations,
                        mock(IProfilingStack.class),
						StorageHeaterType.OLD_LARGE_VOLUME, 
						StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 
						sizingFunction, 
						null, 
						null, 
						null);
		
		when(sizingFunction.computeSize(components, ILets.EMPTY, Units.KILOWATTS)).thenReturn(
				SizingResult.unsuitable(0, Units.KILOWATTS));
		
		Assert.assertFalse("Not suitable because of sizing", measure.isSuitable(components, ILets.EMPTY));
		

		when(sizingFunction.computeSize(components, ILets.EMPTY, Units.KILOWATTS)).thenReturn(
				SizingResult.suitable(22, Units.KILOWATTS));
		
		Assert.assertTrue("Is suitable otherwise", measure.isSuitable(components, ILets.EMPTY));
	}
	
	@Test
	public void modifierInstallsAStorageHeater() {
		final StorageHeaterMeasure.Modifier mod = 
				new StorageHeaterMeasure.Modifier(operations, 
				StorageHeaterType.FAN, 
				0.5, 
				StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, 
				123);
		
		final ITechnologyModel modifiable = mock(ITechnologyModel.class);
		Assert.assertTrue(mod.modify(modifiable));
		
		final ArgumentCaptor<IPrimarySpaceHeater> ac = ArgumentCaptor.forClass(IPrimarySpaceHeater.class);
		verify(operations).replacePrimarySpaceHeater(same(modifiable), ac.capture());
		
		final IPrimarySpaceHeater value = ac.getValue();
		Assert.assertTrue(value instanceof IStorageHeater);
		final IStorageHeater sh = (IStorageHeater) value;
		
		Assert.assertEquals(StorageHeaterType.FAN, sh.getType());
		Assert.assertEquals(StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, sh.getControlType());
		Assert.assertEquals(0.5, sh.getResponsiveness(), 0);
		//TODO opex is ignored
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void responsivenessOverrideIsUsed() {
		final StorageHeaterMeasure measure = 
				new StorageHeaterMeasure(
						time, 
						whmf,
						techs, 
						operations,
                        mock(IProfilingStack.class),
						StorageHeaterType.OLD_LARGE_VOLUME, 
						StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 
						sizingFunction, 
						ConstantComponentsFunction.<Number>of(Name.of("test"), 0d), 
						ConstantComponentsFunction.<Number>of(Name.of("test"), 0d), 
						Optional.of(responsiveness));
		
		
		when(responsiveness.compute(components, ILets.EMPTY)).thenReturn(1234d);
		
		@SuppressWarnings("rawtypes")
		final ArgumentCaptor<IModifier> modifier = ArgumentCaptor.forClass(IModifier.class);
		measure.doApply(components, ILets.EMPTY, 1, 2, 3);
		
		verify(components).modify(same(techs), modifier.capture());
		
		final IModifier<ITechnologyModel> mod = modifier.getValue();
		
		final ITechnologyModel modifiable = mock(ITechnologyModel.class);
		Assert.assertTrue(mod.modify(modifiable));
		
		final ArgumentCaptor<IPrimarySpaceHeater> ac = ArgumentCaptor.forClass(IPrimarySpaceHeater.class);
		verify(operations).replacePrimarySpaceHeater(same(modifiable), ac.capture());
		
		final IPrimarySpaceHeater value = ac.getValue();
		Assert.assertTrue(value instanceof IStorageHeater);
		final IStorageHeater sh = (IStorageHeater) value;
		
		Assert.assertEquals(1234d, sh.getResponsiveness(), 0);
	}
	
	private void check(final StorageHeaterType type, final StorageHeaterControlType control, final double ex) {
		Assert.assertEquals(ex, StorageHeaterMeasure.getSapResponsiveness(type, control), 0.01);
	}
	
	@Test
	public void responsivenessLookupIsCorrect() {
		check(StorageHeaterType.OLD_LARGE_VOLUME, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, 0);
		check(StorageHeaterType.OLD_LARGE_VOLUME, StorageHeaterControlType.CELECT_CHARGE_CONTROL, 0);
		check(StorageHeaterType.OLD_LARGE_VOLUME, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 0);
		
		check(StorageHeaterType.CONVECTOR, StorageHeaterControlType.CELECT_CHARGE_CONTROL, 0.5);
		check(StorageHeaterType.CONVECTOR, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, 0.25);
		check(StorageHeaterType.CONVECTOR, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 0.25);
		
		check(StorageHeaterType.FAN, StorageHeaterControlType.CELECT_CHARGE_CONTROL, 0.75);
		check(StorageHeaterType.FAN, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, 0.5);
		check(StorageHeaterType.FAN, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 0.5);
		
		check(StorageHeaterType.INTEGRATED_DIRECT_ACTING, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, 0.75);
		check(StorageHeaterType.INTEGRATED_DIRECT_ACTING, StorageHeaterControlType.CELECT_CHARGE_CONTROL, 0.75);
		check(StorageHeaterType.INTEGRATED_DIRECT_ACTING, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 0.75);
		
		check(StorageHeaterType.SLIMLINE, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL, 0.25);
		check(StorageHeaterType.SLIMLINE, StorageHeaterControlType.CELECT_CHARGE_CONTROL, 0.5);
		check(StorageHeaterType.SLIMLINE, StorageHeaterControlType.MANUAL_CHARGE_CONTROL, 0.25);
	}
}
