package uk.org.cse.nhm.language.validate;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.validate.NoCyclesValidatorWithDelegates.IDelegate;

public class ObsolesenceValidator implements IDelegate {

    private final ImmutableList.Builder<IError> problems = ImmutableList.builder();
    private static final String NO_ERROR = "NO_ERROR";
    private final LoadingCache<Class<?>, String> cache
            = CacheBuilder.newBuilder().build(new CacheLoader<Class<?>, String>() {
                @Override
                public String load(final Class<?> clazz) throws Exception {
                    if (clazz.isAnnotationPresent(Obsolete.class)) {
                        final Obsolete o = clazz.getAnnotation(Obsolete.class);
                        final String elementName = clazz.isAnnotationPresent(Bind.class) ? clazz.getAnnotation(Bind.class).value() : "this element";
                        final StringBuffer error = new StringBuffer();
                        error.append(elementName);
                        error.append(" is obsolete");
                        if (o.version().isEmpty() == false) {
                            error.append(" since version " + o.version());
                        }

                        error.append(".");

                        if (!o.reason().isEmpty()) {
                            error.append(" " + o.reason());
                        }

                        if (o.inFavourOf().length > 0) {
                            error.append(" ");

                            for (int i = 0; i < o.inFavourOf().length; i++) {
                                final Class<?> replacement = o.inFavourOf()[i];
                                if (replacement.isAnnotationPresent(Bind.class)) {
                                    if (i > 0) {
                                        error.append(", ");
                                    }
                                    error.append(replacement.getAnnotation(Bind.class).value());
                                }
                            }

                            if (o.inFavourOf().length > 1) {
                                error.append(" are replacements.");
                            } else {
                                error.append(" is a replacement.");
                            }
                        }
                        return error.toString();
                    } else {
                        return NO_ERROR;
                    }
                }
            });

    @Override
    public void visit(final XElement v) {
        final Class<? extends XElement> clazz = v.getClass();
        try {
            final String error = cache.get(clazz);
            if (error != NO_ERROR) {
                problems.add(BasicError.warningAt(
                        v.getLocation(),
                        error.toString()));
            }
        } catch (final ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public Iterable<? extends IError> getProblems() {
        return problems.build();
    }

    @Override
    public boolean doEnter(final XElement v) {
        return true;
    }

    @Override
    public void doLeave(final XElement v) {
    }
}
