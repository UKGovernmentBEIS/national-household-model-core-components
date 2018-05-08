package uk.org.cse.nhm.language.validate.contents;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.parse.LanguageElements;
import uk.org.cse.nhm.language.validate.NoCyclesValidatorWithDelegates.IDelegate;
import uk.org.cse.nhm.language.visit.IPropertyVisitor;

public class ContainmentValidator implements IPropertyVisitor<XElement>, IDelegate {

    final Multiset<Class<?>> parentTypes = HashMultiset.create();
    final Multiset<Class<?>> disallowedElements = HashMultiset.create();

    private final ImmutableList.Builder<IError> problems = ImmutableList.builder();

    private static final LoadingCache<Class<?>, Info> classInfo
            = CacheBuilder.newBuilder().build(new CacheLoader<Class<?>, Info>() {
                @Override
                public Info load(final Class<?> key) throws Exception {
                    return Info.forClass(key);
                }
            });

    private static final LoadingCache<Method, Info> methodInfo
            = CacheBuilder.newBuilder().build(new CacheLoader<Method, Info>() {
                @Override
                public Info load(final Method key) throws Exception {
                    return Info.forMethod(key);
                }
            });

    int depthFromScenario = -1;

    static class Info {

        public static Info forMethod(final Method m) {
            if (m.isAnnotationPresent(ActsAsParent.class)) {
                return new Info(
                        ImmutableSet.copyOf(
                                m.getAnnotation(ActsAsParent.class).value()
                        ),
                        Collections.<Class<?>>emptySet(),
                        Collections.<Class<?>>emptySet()
                );
            } else {
                return EMPTY;
            }
        }

        public static Info forClass(final Class<?> c) {
            final Set<Class<?>> requiredParents;
            final Set<Class<?>> forbiddenChildren;

            if (c.isAnnotationPresent(ForbidChild.class)) {
                forbiddenChildren = ImmutableSet.copyOf(
                        c.getAnnotation(ForbidChild.class).value()
                );
            } else {
                forbiddenChildren = Collections.emptySet();
            }

            if (c.isAnnotationPresent(RequireParent.class)) {
                requiredParents = ImmutableSet.copyOf(
                        c.getAnnotation(RequireParent.class).value()
                );
            } else {
                requiredParents = Collections.emptySet();
            }

            return new Info(
                    ImmutableSet.<Class<?>>of(c),
                    requiredParents,
                    forbiddenChildren
            );
        }

        private static final Info EMPTY = new Info(
                Collections.<Class<?>>emptySet(),
                Collections.<Class<?>>emptySet(),
                Collections.<Class<?>>emptySet()
        );

        private final Set<Class<?>> effectiveTypes;
        private final Set<Class<?>> requiredParents;
        private final Set<Class<?>> forbiddenChildren;

        private Info(final Set<Class<?>> effectiveTypes, final Set<Class<?>> requiredParents, final Set<Class<?>> forbiddenChildren) {
            super();
            this.effectiveTypes = effectiveTypes;
            this.requiredParents = requiredParents;
            this.forbiddenChildren = forbiddenChildren;
        }

        public Set<Class<?>> getEffectiveTypes() {
            return effectiveTypes;
        }

        public Set<Class<?>> getRequiredParents() {
            return requiredParents;
        }

        public Set<Class<?>> getForbiddenChildren() {
            return forbiddenChildren;
        }
    }

    @Override
    public boolean doEnter(final XElement v) {
        if (v instanceof XScenario) {
            depthFromScenario = 0;
        } else if (depthFromScenario >= 0) {
            depthFromScenario++;
        }

        if (depthFromScenario == 1 && v.getClass().isAnnotationPresent(Declaration.class)) {
            return false;
        }

        try {
            final Info info = classInfo.get(v.getClass());

            Set<Class<?>> requiredParents = info.getRequiredParents();

            if (v instanceof ISpecialContentsForValidation) {
                final ISpecialContentsForValidation special = (ISpecialContentsForValidation) v;
                requiredParents = ImmutableSet.<Class<?>>builder()
                        .addAll(requiredParents)
                        .addAll(special.getAdditionalRequirements())
                        .build();
            }

            checkForbiddenChildren(v);
            checkRequiredParents(v, requiredParents);

            addInfo(info);
            addSpecial(v);
        } catch (final ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return true;
    }

    private void checkForbiddenChildren(final XElement v) {
        for (final Class<?> disallowed : disallowedElements.elementSet()) {
            if (disallowed.isAssignableFrom(v.getClass())) {
                forbidden(v, disallowed);
            }
        }
    }

    private void checkRequiredParents(final XElement v, final Set<Class<?>> requiredParents) {
        for (final Class<?> required : requiredParents) {
            boolean found = false;
            for (final Class<?> parent : parentTypes.elementSet()) {
                if (required.isAssignableFrom(parent)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                required(v, required);
            }
        }
    }

    @Override
    public void doLeave(final XElement v) {
        if (depthFromScenario >= 0) {
            depthFromScenario--;
        }
        if (depthFromScenario == 0 && v.getClass().isAnnotationPresent(Declaration.class)) {
            return;
        }
        try {
            removeInfo(classInfo.get(v.getClass()));
            removeSpecial(v);
        } catch (final ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void addInfo(final Info info) {
        parentTypes.addAll(info.getEffectiveTypes());
        disallowedElements.addAll(info.getForbiddenChildren());
    }

    private void removeInfo(final Info info) {
        for (final Class<?> c : info.getEffectiveTypes()) {
            parentTypes.remove(c);
        }
        for (final Class<?> c : info.getForbiddenChildren()) {
            disallowedElements.remove(c);
        }
    }

    private void addSpecial(final Object o) {
        if (o instanceof ISpecialContentsForValidation) {
            parentTypes.addAll(((ISpecialContentsForValidation) o).getAdditionalProvisions());
        }
    }

    private void removeSpecial(final Object o) {
        if (o instanceof ISpecialContentsForValidation) {
            for (final Class<?> c : ((ISpecialContentsForValidation) o).getAdditionalProvisions()) {
                parentTypes.remove(c);
            }
        }
    }

    @Override
    public void enterProperty(final XElement element, final Method name) {
        try {
            addInfo(methodInfo.get(name));
        } catch (final ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void leaveProperty(final XElement element, final Method name) {
        try {
            removeInfo(methodInfo.get(name));
        } catch (final ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void required(final XElement v, final Class<?> required) {
        problems.add(BasicError.at(
                v.getLocation(),
                nameOf(v.getClass()) + " can only be used within "
                + nameOf(required)
        ));
    }

    private void forbidden(final XElement v, final Class<?> disallowed) {
        problems.add(BasicError.at(
                v.getLocation(),
                nameOf(v.getClass()) + " is forbidden within "
                + nameOf(disallowed)
        ));
    }

    private final LanguageElements le = LanguageElements.get();

    protected String nameOf(final Class<?> clazz) {
        return clazz.isAnnotationPresent(Bind.class)
                ? clazz.getAnnotation(Bind.class).value()
                : nameOfSubclasses(clazz);
    }

    private String nameOfSubclasses(final Class<?> clazz) {
        final StringBuffer sb = new StringBuffer();
        for (final Class<?> c : le.getSubTypesOf(clazz)) {
            if (c.isAnnotationPresent(Bind.class)) {
                if (sb.length() > 0) {
                    sb.append(", or ");
                }
                sb.append(c.getAnnotation(Bind.class).value());
            }
        }
        return sb.toString();
    }

    public Iterable<? extends IError> getProblems() {
        return problems.build();
    }

    @Override
    public void visit(final XElement v) {
    }
}
