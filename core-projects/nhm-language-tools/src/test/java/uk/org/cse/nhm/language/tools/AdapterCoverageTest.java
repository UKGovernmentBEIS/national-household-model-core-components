package uk.org.cse.nhm.language.tools;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;

import uk.org.cse.nhm.language.adapt.IAdaptable;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * This test ensures that there is no situation where a scenario simply cannot be built, because there is a potential
 * requirement that A is adapted to B somewhere, but no possible adapter that can make an A into a B anywhere.
 * 
 * @author hinton
 *
 */
public class AdapterCoverageTest {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(AdapterCoverageTest.class);

	
	private static class CoverageTester {
		final Reflections reflections = new Reflections("uk.org.cse", new MethodAnnotationsScanner(), new SubTypesScanner());
		final Set<Method> adapterMethods = reflections.getMethodsAnnotatedWith(Adapt.class);
		final Set<Method> verifiedMethods = new HashSet<Method>();
		
		public CoverageTester(final Class<? extends IAdaptable> in, final Class<?> out) {
			verify(in, out, false);
		}

		private boolean verify(final Class<? extends IAdaptable> in, final Class<?> out, final boolean outerHasAlt) {
			try {
				final HashSet<Method> validAdapters = new HashSet<Method>();
				
				for (final Method m : adapterMethods) {
					final Adapt a = m.getAnnotation(Adapt.class);
					if (a.value().isAssignableFrom(in) && 
							isAssignableOrConvertable(m.getReturnType(), out)) {
						validAdapters.add(m);
					}
				}
				
				final Iterator<Method> miter = validAdapters.iterator();
				
				if (!miter.hasNext()) {
					log.error("No adapters from {} to {}", in.getSimpleName(), out.getSimpleName());
				}
				
				while (miter.hasNext()) {
					if (verify(miter.next(), miter.hasNext())) {
						return true;
					}
				}
			} catch (final RuntimeException ex) {
				throw new RuntimeException("While adapting " + in.getSimpleName() + " to " + out.getSimpleName(), ex);
			}
			
//			if (!outerHasAlt) {
				throw new RuntimeException("Could not adapt " + in.getSimpleName() + " to " + out.getSimpleName());
//			} else {
//				return false;
//			}
		}

		private boolean isAssignableOrConvertable(final Class<?> from, final Class<?> to) {
			if (TypeUtils.isAssignable(from, to)) return true;

			return
					(IComponentsAction.class.isAssignableFrom(from) && to.isAssignableFrom(IStateAction.class)) ||
					(IComponentsFunction.class.isAssignableFrom(from) && to.isAssignableFrom(ISizingFunction.class));
		}

		@SuppressWarnings("unchecked")
		private boolean verify(final Method m, final boolean outerHasNext) {
			if (verifiedMethods.contains(m)) return true;
			verifiedMethods.add(m);
			
			final Class<IAdaptable> adapted = (Class<IAdaptable>) m.getAnnotation(Adapt.class).value();
			
			for (int i = 0; i<m.getParameterTypes().length; i++) {
				final Prop p = Util.getProp(m.getParameterAnnotations()[i]);
				if (p == null) continue;
				
				final Class<?> propertyType = Util.getEffectivePropertyType(adapted, p.value());
				final Class<?> parameterType = Util.getEffectiveParameterType(m, i);
				if (TypeUtils.isAssignable(propertyType, parameterType)) continue;
				
				if (IAdaptable.class.isAssignableFrom(propertyType)) {
					final Iterator<Class<?>> citer = getConcreteSubtypes(propertyType).iterator();
				
					while (citer.hasNext()) {
						
						final Class<?> next = citer.next();
						
						if (!verify((Class<? extends IAdaptable>) next, parameterType, citer.hasNext())) {
							log.warn("Maybe a problem; for {}, {} cannot be adapted to {}", new Object[] {m.getName(), next.getSimpleName(), parameterType.getSimpleName()});
						}
					}
				} else {
					throw new RuntimeException("Adapter method " + m + " has property " + p + " which is neither assignable or adaptable");
				}
			}
			
			return true;
		}

		private Set<Class<?>> getConcreteSubtypes(final Class<?> propertyType) {
			final HashSet<Class<?>> result = new HashSet<Class<?>>();
			
			if (Util.isConcreteAndPublic(propertyType)) {
				result.add(propertyType);
			}
			
			for (final Class<?> c : reflections.getSubTypesOf(propertyType)) {
				if (Util.isConcreteAndPublic(c)) result.add(c);
			}
			
			return result;
		}
	}
	
	@Test
	@Ignore("Currently broken by changing to use numbers; need to support generics above.")
	public void testCoverage() throws Throwable {
		try {
			new CoverageTester(XScenario.class, ISimulator.class);
		} catch (Throwable ex) {
			while (ex.getCause() != ex && ex.getCause() != null) {
				log.error(ex.getMessage());
				ex = ex.getCause();
			}
			throw ex;
		}
	}
}
