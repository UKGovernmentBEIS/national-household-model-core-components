package uk.org.cse.nhm.language.builder.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test makes sure that {@link MapEnum} works for all its mapping methods.
 * 
 * If this test fails, it's because you've changed an internal enum
 * but not amended the language enum to correspond to it.
 * 
 * At some point it might be reasonable to relax the 1-1 correspondence in the test
 * 
 * @author hinton
 *
 */
public class MapEnumTest {
	/**
	 * For all the static methods in {@link MapEnum}, this checks that
	 * (a) they map from an enum to an enum and
	 * (b) they are a 1-1 exhaustive mapping.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testMapEnumAllSides() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (final Method m : MapEnum.class.getMethods()) {
			if (m.getName().equals("energyConstant")) continue;
			if (Modifier.isStatic(m.getModifiers())) {
				final String mn = m.getName();
				Assert.assertTrue(mn + " should return an enum", m.getReturnType().isEnum());
				Assert.assertTrue(mn + " should have 1 argument", m.getParameterTypes().length == 1);
				Assert.assertTrue(mn + "'s first arg should be an enum",
						m.getParameterTypes()[0].isEnum());
				
				final Class<?> outType = m.getReturnType();
				final Class<?> inputType = m.getParameterTypes()[0];
				
				final Set<Object> seenOutputs = new HashSet<Object>();
				
				for (final Object i : inputType.getEnumConstants()) {
					final Object o = m.invoke(null, i);
					Assert.assertFalse("in " + mn + ", " + o + " should not be produced by two inputs", seenOutputs.contains(o));
					seenOutputs.add(o);
				}
				
				
				final HashSet<Object> required = new HashSet<Object>(Arrays.asList(outType.getEnumConstants()));
				
				if (m.getAnnotation(MapEnum.Unmapped.class) != null) {
					for (final String s : m.getAnnotation(MapEnum.Unmapped.class).value()) {
						required.remove(Enum.valueOf((Class) outType, s));
					}
				}
				
				Assert.assertEquals(mn + " should map to required outputs",
						required, seenOutputs);
			}
		}
	}
}
