package uk.org.cse.nhm.simulation.measure.util;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.WetHeatingMeasure;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulation.measure.HeatingMeasureApplication;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class Util {
	public static final double ERROR_DELTA = 0.001;
	
	public static class MockDimensions {
		public final IDimension<IPowerTable> energy;
		public final IDimension<ITechnologyModel> technology;
		public final IDimension<StructureModel> structure;
		public final IDimension<BasicCaseAttributes> basic;
		public final ITimeDimension time;
		
		@SuppressWarnings("unchecked")
		public MockDimensions() {
			energy = mock(IDimension.class);
			technology = mock(IDimension.class);
			structure = mock(IDimension.class);
			basic = mock(IDimension.class);
			time = mock(ITimeDimension.class);
		}
	}

	public static MockDimensions getMockDimensions() {
		return new MockDimensions();
	}
	
	public static IWetHeatingMeasureFactory mockWetHeatingMeasureFactory() {
		return new IWetHeatingMeasureFactory(){
		
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
	
	/*
	 * return a sizing function that yields the given value.
	 * 
	 * This is not a mockito mock, because (a) it is simple and (b) if we add any methods
	 * we probably want to think about them.
	 */
	public static ISizingFunction mockSizingFunction(final Optional<Double> result) {
		return new ISizingFunction() {
			@Override
			public ISizingResult computeSize(final IComponentsScope scope, final ILets lets, final Units units) {
				if (result.isPresent()) {
					return SizingResult.suitable(result.get(), units);
				} else {
					return SizingResult.unsuitable(0, units);
				}
			}
		};
	}
	
	public static ISettableComponentsScope mockComponents(final MockDimensions dims, final StructureModel structure, final ITechnologyModel technologies, final IPowerTable ecr) {
		final ISettableComponentsScope mock = mockComponents(dims, structure, technologies);
		when(mock.get(dims.energy)).thenReturn(ecr);
		return mock;
	}
	
	public static ISettableComponentsScope mockComponents(final MockDimensions dims, final StructureModel structure, final ITechnologyModel technologies) {
		final ISettableComponentsScope mock = mock(ISettableComponentsScope.class);
		when(mock.get(dims.structure)).thenReturn(structure);
		when(mock.get(dims.technology)).thenReturn(technologies);
		
		final ITime mockTime = mock(ITime.class);
		final DateTime date = new DateTime(1983, 01, 01, 01, 01, 01, 01);
		when(mockTime.get(any(ILets.class))).thenReturn(date);
		when(mockTime.get(any(XForesightLevel.class))).thenReturn(date);
		
		when(mock.get(dims.time)).thenReturn(mockTime);
		
		return mock;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static StructureModel runCallbacksForStructure(
			final ISettableComponentsScope settable,
			final IDimension<StructureModel> dimension
			) {
		final ArgumentCaptor<IModifier> captor = ArgumentCaptor.forClass(IModifier.class);
		verify(settable).modify(eq(dimension), captor.capture());
		final StructureModel sm = settable.get(dimension).copy();
		for (final IModifier mod : captor.getAllValues()) {
			mod.modify(sm);
		}
		return sm;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static ITechnologyModel runCallbacksForTech(
			final ISettableComponentsScope settable,
			final IDimension<ITechnologyModel> dimension
			) {
		final ArgumentCaptor<IModifier> captor = ArgumentCaptor.forClass(IModifier.class);
		verify(settable, atLeastOnce()).modify(eq(dimension), captor.capture());
		final ITechnologyModel sm = EcoreUtil.copy(settable.get(dimension));
		for (final IModifier mod : captor.getAllValues()) {
			mod.modify(sm);
		}
		return sm;
	}
	
	public static StructureModel applyAndGetStructure(final MockDimensions dims, final IComponentsAction act, final ISettableComponentsScope components) throws NHMException {
		final boolean apply = act.apply(components, ILets.EMPTY);
		Assert.assertTrue(apply);
		
		return runCallbacksForStructure(components, dims.structure);
	}
	
	public static ITechnologyModel applyAndGetTech(final MockDimensions dims, final IComponentsAction act, final ISettableComponentsScope components) throws NHMException {
		final boolean apply = act.apply(components, ILets.EMPTY);
		Assert.assertTrue(apply);
		return runCallbacksForTech(components, dims.technology);
	}
	
	public static void setupCommunityHeating(final ITechnologyModel technologies) {
		final ICommunityHeatSource communityHeatSource = new CommunityHeatSourceImpl() {
		};

		technologies.setCommunityHeatSource(communityHeatSource);
		technologies.setPrimarySpaceHeater(new CentralHeatingSystemImpl() {
			{
				setHeatSource(communityHeatSource);
			}
		});
		technologies.setCentralWaterSystem(new CentralWaterSystemImpl() {
			{
				setPrimaryWaterHeater(new MainWaterHeaterImpl() {
					{
						setHeatSource(communityHeatSource);
					}
				});
			}
		});
	}

	public static IComponentsFunction<Number> mockCapexFunction(final double in, final double out) {
		return new IComponentsFunction<Number>() {

			@Override
			public Double compute(final IComponentsScope components, final ILets lets) {
				final List<ISizingResult> annotations = components.getLocalNotes(ISizingResult.class);
				if (annotations.isEmpty() == false) {
					if (annotations.get(annotations.size()-1).getSize() == in) return out;
				} 
				
				
				return out;
			}

			@Override
			public Name getIdentifier() {
				return Name.of("test");
			}
			
			@Override
			public Set<IDimension<?>> getDependencies() {
				return Collections.emptySet();
			}

			@Override
			public Set<DateTime> getChangeDates() {
				return Collections.emptySet();
			}
		};
	}

	public static IComponentsFunction<Number> mockOpexFunction(final double d, final double e, final double f) {
		return new IComponentsFunction<Number>() {

			@Override
			public Double compute(final IComponentsScope components, final ILets lets) {
				final List<ISizingResult> annotations = components.getLocalNotes(ISizingResult.class);
				if (annotations.isEmpty() == false) {
					if (annotations.get(annotations.size()-1).getSize() == d) return f;
				} 
				return f;
			}

			@Override
			public Name getIdentifier() {
				return Name.of("test");
			}
			
			@Override
			public Set<IDimension<?>> getDependencies() {
				return Collections.emptySet();
			}

			@Override
			public Set<DateTime> getChangeDates() {
				return Collections.emptySet();
			}
		};
	}
}
