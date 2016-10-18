package uk.org.cse.nhm.language.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;

import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * This suite of tests does static analysis of all the adapters in the classpath, and checks that they
 * are not doing anything silly
 * 
 * @author hinton
 *
 */
public class BuilderIntegrityTest {



	@Test
	public void testThatAdapterMethodsAreReasonable() {
		final Reflections r = new Reflections("uk.org.cse", new MethodAnnotationsScanner(), new SubTypesScanner());
		
		final Set<Method> adapterMethods = r.getMethodsAnnotatedWith(Adapt.class);
		
		final Multimap<Class<?>, Class<?>> adaptedTypes = HashMultimap.create();
		
		for (final Method m : adapterMethods) {
			Class<?> value = m.getAnnotation(Adapt.class).value();
			
			adaptedTypes.put(value, m.getReturnType());
			
			// This stuff here is a hack to handle the use of IConverters, without newing up
			// the IConverters here and doing the closure.
			
			// If you add an IConverter to the system, you may need to add its mapping behaviour here.
			
			if (IComponentsAction.class.isAssignableFrom(m.getReturnType())) {
				adaptedTypes.put(value, IStateAction.class);
			}
			
			if (IComponentsFunction.class.isAssignableFrom(m.getReturnType())) {
				if (m.getGenericReturnType() instanceof ParameterizedType) {
					if (
							TypeUtils.isAssignable(
							((ParameterizedType) m.getGenericReturnType()).getActualTypeArguments()[0],
							Double.class)) {
						adaptedTypes.put(value, ISizingFunction.class);
					}
				}
			}
		}

		for (final Method m : adapterMethods) {
			System.out.println(String.format("verify %s.%s", m.getDeclaringClass().getSimpleName(), m.getName()));
			verifyMethodParametersAreMappedToUniqueProperties(m);
			verifyPropertiesHaveGetters(m);
		}
	}
	
	/**
	 * There should be getters in all the adapted types for the values being adapted
	 * @param m
	 */
	public void verifyPropertiesHaveGetters(Method m) {
		Annotation[][] parameterAnnotations = m.getParameterAnnotations();
		
		final Class<?> adapted = m.getAnnotation(Adapt.class).value();
		
		final HashSet<String> getters = new HashSet<String>();
		
		for (final Method g : adapted.getMethods()) {
			if (g.getReturnType() != Void.TYPE && g.getParameterTypes().length == 0) {
				final String mn = g.getName();
				if (mn.startsWith("get")) {
					getters.add(mn.substring(3).toLowerCase());
				} else if (mn.startsWith("is")) {
					getters.add(mn.substring(2).toLowerCase());
				}
			}
		}
		
		for (final Annotation[] as : parameterAnnotations) {
			for (final Annotation a : as) {
				if (a instanceof Prop) {
					final Prop p = (Prop) a;
					Assert.assertTrue("Adapted type " + adapted.getSimpleName() + " should have a getter called " + 
							p.value(),
							getters.contains(p.value().toLowerCase()));
				}
			}
		}
	}

	/**
	 * If you have things annotated with the same @Prop annotation, it is probably a mistake.
	 * @param m
	 */
	private void verifyMethodParametersAreMappedToUniqueProperties(Method m) {
		final HashSet<String> mappedParameters = new HashSet<String>();
		Annotation[][] parameterAnnotations = m.getParameterAnnotations();
		for (final Annotation[] as : parameterAnnotations) {
			for (final Annotation a : as) {
				if (a instanceof Prop) {
					final Prop p = (Prop) a;
					Assert.assertFalse("in " + m.getDeclaringClass().getSimpleName() + "."+m.getName() + " property " + p + " should not be mapped more than once", mappedParameters.contains(p.value()));
					mappedParameters.add(p.value());
				}
			}
		}
	}
}
