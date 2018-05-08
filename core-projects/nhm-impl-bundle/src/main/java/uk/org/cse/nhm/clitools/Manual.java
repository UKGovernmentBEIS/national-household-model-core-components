package uk.org.cse.nhm.clitools;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;

import com.google.common.base.Joiner;
import com.larkery.jasb.io.IModel;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.sexp.Defaults;

public class Manual {

    public static final String USAGE = "<model command>\n\tProduces the manual page for the given model command.";

    public static void main(final String[] args) {
        Validator.disableLogging();
        if (args.length != 1) {
            throw new IllegalArgumentException(USAGE);
        }
        System.out.println(help(args[0]));
    }

    public static String help(final String term) {
        final Pattern p = Pattern.compile(term, Pattern.CASE_INSENSITIVE);
        final IModel model = Defaults.CONTEXT.getModel();

        final StringBuffer sb = new StringBuffer();

        for (final IModel.IElement element : model.getElements()) {
            if (p.matcher(element.getName()).matches()) {
                sb.append(helpFor(element));
            }
        }

        return sb.toString();
    }

    private static String dot(final String s) {
        if (s.endsWith(".")) {
            return s;
        } else {
            return s + ".";
        }
    }

    private static boolean hasDoc(final AnnotatedElement e) {
        if (e instanceof Class) {
            final Class<?> eClass = (Class<?>) e;
            if (eClass.isEnum()) {
                return true; // all enums get docs
            }
        }
        return e.isAnnotationPresent(Doc.class);
    }

    private static String doc(final AnnotatedElement e) {
        final StringBuffer out = new StringBuffer();

        if (e.isAnnotationPresent(Doc.class)) {
            out.append(dot(Joiner.on("\n").join(e.getAnnotation(Doc.class).value())));
        }

        if (e instanceof Class) {
            final Class<?> clazz = (Class<?>) e;

            if (clazz.isEnum()) {
                out.append("<variablelist>\n");
                out.append("<title>Allowed Values</title>\n");

                for (final Field f : clazz.getFields()) {
                    if (f.isEnumConstant()) {
                        // it is an enum constant?		
                        if (hasDoc(e)) {
                            out.append("<varlistentry>");
                            out.append("<term>");
                            try {
                                out.append(f.get(null).toString());
                            } catch (IllegalArgumentException
                                    | IllegalAccessException e1) {
                                throw new IllegalArgumentException("Could not get enum field " + f, e1);
                            }
                            out.append("</term>");
                            out.append("<listitem>");
                            out.append(doc(f));
                            out.append("</listitem>");
                            out.append("</varlistentry>");
                        }
                    }
                }

                out.append("</variablelist>");
            }
        }
        return out.toString();
    }

    private static String wrap(final String in, final int tabs, final int width) {
        final String indent = new String(new char[tabs]).replaceAll("\0", "  ");
        return indent + WordUtils.wrap(in.replaceAll("[\r\n]", " "),
                width,
                "\n" + indent,
                true);
    }

    public static String helpFor(final IModel.IElement element) {
        final StringBuffer result = new StringBuffer();

        if (element instanceof IModel.IAtomModel) {
            final IModel.IAtomModel atom = (IModel.IAtomModel) element;
            result.append(element.getName() + " (unstructured input):\n\n");
            result.append(wrap(doc(element.getJavaType()), 1, 80));

            if (atom.getLiterals().size() > 0) {
                result.append(wrap("Legal Values:", 1, 80) + "\n");
            }
            for (final String s : atom.getLiterals()) {
                result.append(wrap("* " + s, 2, 80) + "\n");
            }
        } else if (element instanceof IModel.IInvocationModel) {
            final IModel.IInvocationModel inv = (IModel.IInvocationModel) element;

            result.append("(" + element.getName() + ") :\n\n");
            result.append(wrap(doc(element.getJavaType()), 1, 80));

            result.append("\n");

            final StringBuffer summary = new StringBuffer();

            for (final IModel.IArgument arg : inv.getNamedArguments()) {
                if (summary.length() > 0) {
                    summary.append(", ");
                }
                summary.append(arg.getName().get() + ":");
            }

            if (inv.getRemainderArgument().isPresent()) {
                if (summary.length() > 0) {
                    summary.append(", and ");
                }
                summary.append("arbitrarily many positional arguments");
            } else {
                if (inv.getPositionalArguments().size() > 0) {
                    if (summary.length() > 0) {
                        summary.append(", and ");
                    }

                    summary.append(inv.getPositionalArguments().size() + " positional arguments");
                }
            }

            if (summary.length() > 0) {
                result.append(wrap("\nArguments:", 1, 80) + "\n\n");
                result.append(wrap(summary.toString(), 2, 80) + "\n\n");
            }

            for (final IModel.IArgument argument : inv.getArguments()) {
                if (argument.isPositionalArgument()) {

                } else if (argument.isNamedArgument()) {

                } else if (argument.isRemainderArgument()) {

                }
            }
        }

        result.append("\n");
        return result.toString();
    }
}
