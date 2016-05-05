package uk.org.cse.nhm.spss.wrap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavInputStream;
import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.SavVariableType;

/**
 * Pass in an interface, and this will return an iterator that intelligently extracts implementations from the sav input
 * stream. Use {@link SavVariableMapping} to indicate on the interface what accessors go with what fields
 *
 * @author hinton
 */
public class SavStreamWrapperBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(SavStreamWrapperBuilder.class);
    Map<String, String> reportedMissingVars = new HashMap<>();
    Map<String, Map<String, String>> nonEnumMatches = new HashMap<>();
    Set<String> varsWeUsed = new HashSet<>();

    public SavStreamWrapperBuilder() {
        varsWeUsed.add("AACODE");
        varsWeUsed.add("AAGPD111");
        varsWeUsed.add("AAGPD910");
        varsWeUsed.add("AGEHRPX");
        varsWeUsed.add("ATTIC");
        varsWeUsed.add("BASEMENT");
        varsWeUsed.add("DBLGLAZ2");
        varsWeUsed.add("DBLGLAZ4");
        varsWeUsed.add("DWTYPE8X");
        varsWeUsed.add("DWTYPENX");
        varsWeUsed.add("FDFBCKOF");
        varsWeUsed.add("FDFFLOOR");
        varsWeUsed.add("FDFFROOF");
        varsWeUsed.add("FDFLFTOF");
        varsWeUsed.add("FDFMAIND");
        varsWeUsed.add("FDFMAINW");
        varsWeUsed.add("FDFNEXTD");
        varsWeUsed.add("FDFNEXTW");
        varsWeUsed.add("FDFRIGOF");
        varsWeUsed.add("FDFSAMED");
        varsWeUsed.add("FDHADEP1");
        varsWeUsed.add("FDHADEP2");
        varsWeUsed.add("FDHADEP3");
        varsWeUsed.add("FDHALEV1");
        varsWeUsed.add("FDHALEV2");
        varsWeUsed.add("FDHALEV3");
        varsWeUsed.add("FDHAWID1");
        varsWeUsed.add("FDHAWID2");
        varsWeUsed.add("FDHAWID3");
        varsWeUsed.add("FDHMDEP1");
        varsWeUsed.add("FDHMDEP2");
        varsWeUsed.add("FDHMDEP3");
        varsWeUsed.add("FDHMLEV1");
        varsWeUsed.add("FDHMWID1");
        varsWeUsed.add("FDHMWID2");
        varsWeUsed.add("FDHMWID3");
        varsWeUsed.add("FELCAVBF");
        varsWeUsed.add("FELCAVFF");
        varsWeUsed.add("FELCAVLF");
        varsWeUsed.add("FELCAVRF");
        varsWeUsed.add("FELEXTBF");
        varsWeUsed.add("FELEXTFF");
        varsWeUsed.add("FELEXTLF");
        varsWeUsed.add("FELEXTRF");
        varsWeUsed.add("FELFENBW");
        varsWeUsed.add("FELFENFW");
        varsWeUsed.add("FELFENLW");
        varsWeUsed.add("FELFENRW");
        varsWeUsed.add("FEXCS1NO");
        varsWeUsed.add("FEXCS2NO");
        varsWeUsed.add("FEXDF1NO");
        varsWeUsed.add("FEXDF2NO");
        varsWeUsed.add("FEXP1FDP");
        varsWeUsed.add("FEXP2FDP");
        varsWeUsed.add("FEXPLOTF");
        varsWeUsed.add("FEXPLOTR");
        varsWeUsed.add("FEXPLTYP");
        varsWeUsed.add("FEXWIDTH");
        varsWeUsed.add("FINBATCL");
        varsWeUsed.add("FINBEDEX");
        varsWeUsed.add("FINCHACC");
        varsWeUsed.add("FINCHBAG");
        varsWeUsed.add("FINCHBCD");
        varsWeUsed.add("FINCHBMA");
        varsWeUsed.add("FINCHBMO");
        varsWeUsed.add("FINCHCTC");
        varsWeUsed.add("FINCHDST");
        varsWeUsed.add("FINCHROM");
        varsWeUsed.add("FINCHTIM");
        varsWeUsed.add("FINCHTRV");
        varsWeUsed.add("FINCHTZC");
        varsWeUsed.add("FINEX1EX");
        varsWeUsed.add("FINEX1FU");
        varsWeUsed.add("FINEX2EX");
        varsWeUsed.add("FINEX2FU");
        varsWeUsed.add("FINEX3EX");
        varsWeUsed.add("FINEX3FU");
        varsWeUsed.add("FINEX4EX");
        varsWeUsed.add("FINEX4FU");
        varsWeUsed.add("FINEX5EX");
        varsWeUsed.add("FINEX5FU");
        varsWeUsed.add("FINEX6EX");
        varsWeUsed.add("FINEX6FU");
        varsWeUsed.add("FINEX7EX");
        varsWeUsed.add("FINEX7FU");
        varsWeUsed.add("FINFLRSF");
        varsWeUsed.add("FINGASMS");
        varsWeUsed.add("FINHTGLG");
        varsWeUsed.add("FINKITCL");
        varsWeUsed.add("FINKITLE");
        varsWeUsed.add("FINLIVCL");
        varsWeUsed.add("FINLIVDE");
        varsWeUsed.add("FINLIVLE");
        varsWeUsed.add("FINLIVWI");
        varsWeUsed.add("FINLOPOS");
        varsWeUsed.add("FINMHBOI");
        varsWeUsed.add("FINMHFUE");
        varsWeUsed.add("FINOHAGE");
        varsWeUsed.add("FINOHEAT");
        varsWeUsed.add("FINOHTYP");
        varsWeUsed.add("FINROOMS");
        varsWeUsed.add("FINWDIPR");
        varsWeUsed.add("FINWHCPR");
        varsWeUsed.add("FINWHCYL");
        varsWeUsed.add("FINWHINS");
        varsWeUsed.add("FINWHLPR");
        varsWeUsed.add("FINWHLTY");
        varsWeUsed.add("FINWHMMS");
        varsWeUsed.add("FINWHOAG");
        varsWeUsed.add("FINWHOPR");
        varsWeUsed.add("FINWHOTY");
        varsWeUsed.add("FINWHSIZ");
        varsWeUsed.add("FINWHTHE");
        varsWeUsed.add("FINWHXAG");
        varsWeUsed.add("FINWHXPR");
        varsWeUsed.add("FINWHXTY");
        varsWeUsed.add("FINWMPPR");
        varsWeUsed.add("FINWMPTY");
        varsWeUsed.add("FINWOTFU");
        varsWeUsed.add("FINWSIPR");
        varsWeUsed.add("FINWSPPR");
        varsWeUsed.add("FINWSPTY");
        varsWeUsed.add("FLITHICK");
        varsWeUsed.add("FLOORARE");
        varsWeUsed.add("FODCONAC");
        varsWeUsed.add("FODCONST");
        varsWeUsed.add("FPFULLIN");
        varsWeUsed.add("FSHADDIT");
        varsWeUsed.add("FVWSPEBF");
        varsWeUsed.add("FVWSPELF");
        varsWeUsed.add("FVWSPERF");
        varsWeUsed.add("FVWTENBF");
        varsWeUsed.add("FVWTENFF");
        varsWeUsed.add("FVWTENLF");
        varsWeUsed.add("FVWTENRF");
        varsWeUsed.add("GOREHCS");
        varsWeUsed.add("HHLTSICK");
        varsWeUsed.add("HHSIZEX");
        varsWeUsed.add("HHVULX");
        varsWeUsed.add("HRP");
        varsWeUsed.add("LENRES");
        varsWeUsed.add("NBEDSX");
        varsWeUsed.add("NDEPCHIL");
        varsWeUsed.add("NOOFHRSR");
        varsWeUsed.add("OWNTYPE");
        varsWeUsed.add("PERSNO");
        varsWeUsed.add("RUMORPH");
        varsWeUsed.add("STOREYX");
        varsWeUsed.add("TENURE8X");
        varsWeUsed.add("TYPE");
        varsWeUsed.add("TYPERCOV");
        varsWeUsed.add("TYPERSTR");
        varsWeUsed.add("TYPEWIN ");
        varsWeUsed.add("TYPEWSTR");
    }

    private static SavVariable findVariable(final SavMetadata metadata, final SavVariableMapping mapping) {
        for (final String variableName : mapping.value()) {
            if (variableName.length() > 0 && variableName.charAt(0) == '~') {
                // handle as a regex, rather than a direct lookup
                for (final SavVariable variable : metadata.getVariables()) {
                    if (variable.getName().matches(variableName.substring(1))) {
                        return variable;
                    }
                }
            } else {
                final SavVariable variable = metadata.getVariable(variableName);
                if (variable != null) return variable;
            }
        }
        return null;
    }

    private void collectAllAnnotatedGetters(final Class<?> clazz, final Set<Method> out) {
        if (clazz == null) {
            return;
        }

        for (final Method m : clazz.getMethods()) {
            if (m.getParameterAnnotations().length == 0) {
                SavVariableMapping annotation = m.getAnnotation(SavVariableMapping.class);
                if (annotation != null) {
                    out.add(m);
                }
            }
        }

        collectAllAnnotatedGetters(clazz.getSuperclass(), out);
        for (final Class<?> face : clazz.getInterfaces()) {
            collectAllAnnotatedGetters(face, out);
        }
    }

    /**
     * Wraps a bean class around a stream, using the same rules for stuff as
     * {@link #wrapInterface(SavInputStream, Class)} that is to say, the getter methods ought to be annotated as though
     * it was an interface, and this will use no-arg constructor + setter to create your bean for you. If you have a
     * method setSavEntry which expects an instance of {@link SavEntry} on your bean, that will get called with the
     * original entry for the bean as well.
     *
     * @param stream
     * @param beanClass
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public <W> Iterator<W> wrapBean(final SavInputStream stream, final Class<W> beanClass)
            throws NoSuchMethodException, SecurityException {
        final Constructor<W> constructor = beanClass.getConstructor();
        final SavMetadata metadata = stream.getMetadata();

        if (constructor == null) {
            throw new RuntimeException("No zero-arg constructor on " + beanClass);
        }

        Map<String, Method> setters = new HashMap<String, Method>();

        final List<Method> usedSetters = new ArrayList<Method>();
        final List<SavVariableMapping> mappings = new ArrayList<SavVariableMapping>();

        HashSet<Method> annotatedGetters = new HashSet<Method>();

        collectAllAnnotatedGetters(beanClass, annotatedGetters);

        for (final Method m : beanClass.getMethods()) {
            if (m.getName().startsWith("set") && (m.getReturnType() == Void.TYPE)
                    && (m.getParameterTypes().length == 1)) {
                setters.put(m.getName().substring(3), m);
            }
        }

        for (final Method m : annotatedGetters) {
            final String propertyName;

            if (m.getName().startsWith("get")) {
                propertyName = m.getName().substring(3);
            } else if (m.getName().startsWith("is")) {
                propertyName = m.getName().substring(2);
            } else {
                continue;
            }

            final Method setter = setters.get(propertyName);
            if (setter != null) {
                // there is a setter, hooray
                usedSetters.add(setter);
                // we know this method has the annotation, because that's why we put it in the annotated getters set.
                mappings.add(m.getAnnotation(SavVariableMapping.class));
            }
        }

        Method maybeSetSavEntry = null;
        try {
            maybeSetSavEntry = beanClass.getDeclaredMethod("setSavEntry", SavEntry.class);
        } catch (NoSuchMethodException ex) {

        }

        final Method setSavEntry = maybeSetSavEntry;

        return new Iterator<W>() {
            public boolean hasNext() {
                return stream.hasNext();
            }

            public W next() {
                final SavEntry entry = stream.next();
                final W bean;
                try {
                    bean = constructor.newInstance();
                } catch (final Throwable e) {
                    throw new RuntimeException("Exception constructing bean", e);
                }

                populate(bean, entry);

                if (setSavEntry != null) {
                    try {
                        setSavEntry.invoke(bean, entry);
                    } catch (Throwable e) {
                        throw new RuntimeException("Caught this while setting the entry on bean", e);
                    }
                }

                return bean;
            }

            public void remove() {
                stream.remove();
            }

            private void populate(final Object bean, final SavEntry entry) {
                final Iterator<SavVariableMapping> annotationIterator = mappings.iterator();
                final Iterator<Method> setterIterator = usedSetters.iterator();

                while (annotationIterator.hasNext() && setterIterator.hasNext()) {
                    final SavVariableMapping mapping = annotationIterator.next();

                    final Method setter = setterIterator.next();

                    final SavVariable variable = findVariable(metadata, mapping);

                    final Class<?> argType = setter.getParameterTypes()[0];

                    final Object propertyValue = transform(argType, entry, metadata, variable);

                    if ((variable == null) || (propertyValue == null)) {
                        final String savFile = bean.getClass().getName();
                        final String varName = mapping.value().length == 1 ? mapping.value()[0] :
                            Arrays.toString(mapping.value());

                        final String missingVarReport = savFile + varName;

                        if (reportedMissingVars.containsKey(missingVarReport) == false) {
                            reportedMissingVars.put(missingVarReport, "");
                            LOG.error("{},{},not found.", savFile, varName);
                        }
                    }

                    try {
                        setter.invoke(bean, propertyValue);
                    } catch (final Throwable th) {
                        throw new RuntimeException("Caught this while setting bean property " + setter.getName(), th);
                    }
                }
            }
        };
    }

    /**
     * Caches return for {@link #getEnumValue(Class, SavVariable, SavEntry)}
     */
    private final Map<Class<?>, Map<SavVariable, Map<Double, Object>>> enumLookup = new HashMap<Class<?>, Map<SavVariable, Map<Double, Object>>>();

    private Object getEnumValue(final Class<? extends Enum<?>> returnType, final SavVariable variable,
            final SavEntry entry) {
        if (variable == null) {
            LOG.error("SPSS Enum was null {} ", returnType);
            return null;
        }

        if (variable.getType() != SavVariableType.NUMBER) {
            throw new RuntimeException("Cannot map non-numeric variable " + variable + " to enum type " + returnType);
        }

        Map<SavVariable, Map<Double, Object>> map = enumLookup.get(returnType);

        if (map == null) {
            map = new HashMap<SavVariable, Map<Double, Object>>();
            enumLookup.put(returnType, map);
        }

        // Create a smaller map just holding the double value and enum for the variable if we can find it.
        Map<Double, Object> subMap = map.get(variable);

        if (subMap == null) {
            subMap = new HashMap<Double, Object>();
            map.put(variable, subMap);
        }

        final Double value = entry.getValue(variable, Double.class);

        if (subMap.containsKey(value)) {
            return subMap.get(value);
        } else {
            // Match on value lable here
            final String valueLabel = variable.getValueLabel(value).trim();
            List<String> expectedLabels = new ArrayList<>();

            for (final Enum<?> e : returnType.getEnumConstants()) {
                Field f;
                try {
                    f = returnType.getField(e.name());
                } catch (Throwable e1) {
                    throw new RuntimeException("This should never happen", e1);
                }
                final SavEnumMapping mapping = f.getAnnotation(SavEnumMapping.class);

                final StringBuilder matchedValues = new StringBuilder();
                if (mapping != null) {
                    for (final String s : mapping.value()) {
                        matchedValues.append("|").append(s);

                        if (s.trim().equalsIgnoreCase((valueLabel))) {
                            subMap.put(value, e);
                            return e;
                        }
                    }
                } else {
                    if (e.name().equalsIgnoreCase(valueLabel)) {
                        subMap.put(value, e);
                        return e;
                    }
                }
                expectedLabels.add(matchedValues.toString());
            }
            subMap.put(value, null);

            if (varsWeUsed.contains(variable.getName())) {
                Map<String, String> errors;
                if (nonEnumMatches.containsKey(variable.getName())) {
                    errors = nonEnumMatches.get(variable.getName());

                    if (errors.containsKey(valueLabel) == false) {
                        errors.put(valueLabel, expectedLabels.toString());

                        nonEnumMatches.put(variable.getName(), errors);

                        LOG.error("RecognizedValues={},Actual=|{}|", expectedLabels, valueLabel);
                        LOG.error("{},value={},lable={},{},not mapped", variable.getName(), value, valueLabel,
                                variable.getVariableValues());
                    }
                } else {
                    errors = new HashMap<>();
                    errors.put(valueLabel, expectedLabels.toString());
                    nonEnumMatches.put(variable.getName(), errors);

                    LOG.error("RecognizedValues={},Actual=|{}|", expectedLabels, valueLabel);
                    LOG.error("{},value={},label={},{},{},not mapped", variable.getName(), value, valueLabel,
                            variable.getVariableValues(), returnType.getClass().getName());
                }
            }
            if (!nonEnumMatches.isEmpty()) {
                LOG.error("Enum error ct={}", nonEnumMatches.keySet().size());
            }
            
            return null;
        }
    }

    protected Object transform(final Class<?> type, final SavEntry entry, final SavMetadata meta,
            final SavVariable variable) {
        if (variable == null) {
            // LOG.error("Expected SPSS variable was null {} ", type);
            return null;
        }
        else if (type.isEnum()) {
            return getEnumValue((Class<? extends Enum<?>>) type, variable, entry);
        } else {
            if (entry.isMissing(variable)) {
                if (type.isPrimitive()) {
                    // zero / false / etc;
                    if (type == Boolean.TYPE) {
                        return false;
                    } else if (type == Integer.TYPE) {
                        return 0;
                    } else if (type == Double.TYPE) {
                        return 0d;
                    } else {
                        throw new RuntimeException(
                                "Cannot return missing values to any primitive type that isn't boolean, int or double");
                    }
                } else {
                    return null;
                }
            }

            if (type.isAssignableFrom(String.class)) {
                return entry.getValue(variable, String.class);
            } else if (type == Boolean.TYPE) {
                final String s = variable.getValueLabel(entry.getValue(variable, Double.class));
                if (s.equalsIgnoreCase("Yes")) {
                    return true;
                }
                return false;
            } else if ((type == Integer.class) || (type == Integer.TYPE)) {
                // if (entry.isPredefinedValue(variable)) return type == Integer.class ? null : 0;
                return entry.getValue(variable, Double.class).intValue();
            } else if ((type == Double.class) || (type == Double.TYPE)) {
                if (entry.isPredefinedValue(variable)) {
                    return type == Double.class ? null : 0;
                }
                return entry.getValue(variable, Double.class);
            } else {
                throw new RuntimeException("Don't know how to make a " + type);
            }
        }
    }

    /**
     * Wraps stream to return an iterator for things which implement the interface class passed in.
     *
     * @param stream the stream to wrap
     * @param clazz the type of object to return
     * @return an iterator where each entry will be a row from the sav file, represented using the class
     */
    public <W> Iterator<W> wrapInterface(final SavInputStream stream, final Class<W> clazz) {
        final SavMetadata metadata = stream.getMetadata();

        final Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(SavEntryWrapper.class);

        enhancer.setInterfaces(new Class[] { clazz });

        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy)
                    throws Throwable {
                final SavEntryWrapper self = (SavEntryWrapper) obj;

                if ((method.getParameterTypes().length == 0) &&
                        (method.getName().equals("getEntry") || method.getName().equals("getMetadata"))) {
                    return proxy.invokeSuper(obj, args);
                }

                final SavVariableMapping mapping = method.getAnnotation(SavVariableMapping.class);

                if (mapping == null) {
                    return null;
                }

                final SavEntry entry = self.getEntry();
                final SavMetadata meta = self.getMetadata();

                final SavVariable variable = findVariable(meta, mapping);

                if (variable == null) {
                    return null;
                }

                return transform(method.getReturnType(), entry, metadata, variable);
            }

        });

        return new Iterator<W>() {
            public boolean hasNext() {
                return stream.hasNext();
            }

            public W next() {
                return (W) enhancer.create(new Class[] { SavMetadata.class, SavEntry.class }, new Object[] { metadata,
                        stream.next() });
            }

            public void remove() {
                stream.remove();
            }
        };
    }
}
