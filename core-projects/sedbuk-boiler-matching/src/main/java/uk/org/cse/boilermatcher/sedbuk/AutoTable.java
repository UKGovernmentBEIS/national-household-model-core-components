package uk.org.cse.boilermatcher.sedbuk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * Utility class which implements versions of {@link ITable},
 * overriding/implementing any getters annotated with {@link Column}
 *
 * @author hinton
 * @since 1.0
 */
public class AutoTable {

    /**
     * Instantiate an implementation of the given interface, based off the given
     * base class.
     *
     * All methods annotated with {@link Column} that are invoked on the
     * resulting class will be handled by lookup up in the sedbuk table with the
     * given column number. All other methods will be handled on the base class.
     *
     * @param baseClass the base class to extend
     * @param iface the interface extending {@link ITable} to implement
     * @return an implementation
     * @since 1.0
     */
    public static <T extends ITable> T forClass(final Class<? extends BaseTable> baseClass, final Class<T> iface) {
        return iface.cast(Enhancer.create(baseClass, new Class[]{iface},
                new MethodInterceptor() {
            final Map<Method, Multimap<Object, Object>> caches = new HashMap<Method, Multimap<Object, Object>>();

            @Override
            public Object intercept(Object instance, Method method, Object[] arguments, MethodProxy proxy) throws Throwable {
                Column annotation = method.getAnnotation(Column.class);
                if (annotation != null) {
                    if (annotation.search()) {
                        if (!caches.containsKey(method)) {
                            final BaseTable base = (BaseTable) instance;
                            final int col = annotation.value();
                            final Multimap<Object, Object> cache = ArrayListMultimap.create();
                            caches.put(method, cache);
                            final Class<?> type = method.getParameterTypes()[0];
                            for (int i = 0; i < base.getNumberOfRows(); i++) {
                                final Object value = base.get(type, i, col);
                                cache.put(value, i);
                            }
                        }
                        return caches.get(method).get(arguments[0]);
                    } else {
                        if (arguments.length == 1 && arguments[0] instanceof Integer && arguments[0] != null) {
                            final int row = (Integer) arguments[0];
                            final Class<?> returnType = method.getReturnType();
                            return ((BaseTable) instance).get(returnType, row, annotation.value());
                        } else {
                            throw new RuntimeException("Invalid method : " + method + ", does not have exactly 1 integer argument");
                        }
                    }
                }
                return proxy.invokeSuper(instance, arguments);
            }
        }));
    }
}
