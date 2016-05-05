package uk.org.cse.nhm.language.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;

import uk.org.cse.nhm.language.adapt.impl.Prop;

import com.google.common.base.Optional;

class Util {
	public static Class<?> getEffectiveParameterType(Method m, int i) {
		Type propType = m.getGenericParameterTypes()[i];
		
		if (TypeUtils.isAssignable(propType, Optional.class)) {
			propType = ((ParameterizedType) propType).getActualTypeArguments()[0];
		} else if (TypeUtils.isAssignable(propType, List.class)) {
			propType = ((ParameterizedType) propType).getActualTypeArguments()[0];
		}
		
		return TypeUtils.getRawType(propType, null);
	}


	public static Class<?> getEffectivePropertyType(Class<?> adapted, String value) {
		for (final Method m : adapted.getMethods()) {
			if (m.getParameterTypes().length == 0 && m.getReturnType() != Void.TYPE) {
				if (m.getName().toLowerCase().endsWith(value.toLowerCase())) {
					final Type t = m.getGenericReturnType();
					if (TypeUtils.isAssignable(t, List.class)) {
						return TypeUtils.getRawType(((ParameterizedType) t).getActualTypeArguments()[0], null);
					} else {
						return TypeUtils.getRawType(t, null);
					}
				}
			}
		}
		return null;
	}


	public static Prop getProp(Annotation[] a) {
		for (final Annotation e : a) {
			if (e instanceof Prop) return (Prop) e;
		}
		
		return null;
	}


	public static boolean isConcreteAndPublic(Class<?> parameterType) {
		return parameterType.isInterface() == false &&
				Modifier.isAbstract(parameterType.getModifiers()) == false
				&&
				Modifier.isPublic(parameterType.getModifiers());
	}
}
