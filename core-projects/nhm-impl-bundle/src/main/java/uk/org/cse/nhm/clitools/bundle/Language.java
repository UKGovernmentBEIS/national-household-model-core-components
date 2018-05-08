package uk.org.cse.nhm.clitools.bundle;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Sets;
import com.larkery.jasb.io.IModel;
import com.larkery.jasb.io.IModel.IArgument;
import com.larkery.jasb.io.IModel.IAtomModel;
import com.larkery.jasb.io.IModel.IElement;
import com.larkery.jasb.io.IModel.IInvocationModel;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.MacroModel;

import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IDefinition.DefinitionType;
import uk.org.cse.nhm.bundle.api.ILanguage;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.sexp.Defaults;
import uk.org.cse.nhm.macros.ExtraMacros;

public class Language implements ILanguage {

    enum SType {
        Argument,
        Constant,
        Builtin,
        Template,
        Macro,
        Name
    }

    static class S implements ISuggestion, Comparable<S> {

        private final SType type;
        private final String text, description, category;
        private final int offset, leftOffset;
        private final boolean spaced;

        protected S(
                final SType type,
                final String text, final String description, final String category,
                final int offset, final int leftOffset, final boolean spaced) {
            super();
            this.type = type;
            this.text = text;
            this.description = description;
            this.category = category;
            this.offset = offset;
            this.leftOffset = leftOffset;
            this.spaced = spaced;
        }

        @Override
        public String category() {
            return this.category;
        }

        @Override
        public String description() {
            return this.description;
        }

        @Override
        public int cursorOffset() {
            return this.offset;
        }

        @Override
        public int leftOffset() {
            return this.leftOffset;
        }

        @Override
        public boolean spaced() {
            return this.spaced;
        }

        @Override
        public String text() {
            return this.text;
        }

        public static S command(final ICursor cursor, final IInvocationModel e) {
            return cmd(cursor, SType.Builtin, e.getName(), describe(e.getJavaType()), category(e.getJavaType()));
        }

        private static String category(final Class<?> javaType) {
            final Category cat = javaType.getAnnotation(Category.class);
            if (cat == null) {
                return "general";
            } else {
                return cat.value().display;
            }
        }

        public static String describe(final AnnotatedElement e) {
            final Doc d = e.getAnnotation(Doc.class);
            if (d == null) {
                return "";
            } else {
                return Joiner.on(" ").join(d.value());
            }
        }

        public static String describe(final Class<?> javaType, final String lit) {
            if (javaType.isEnum()) {
                for (final Object o : javaType.getEnumConstants()) {
                    if (lit.equals(String.valueOf(o))) {
                        for (final Field f : javaType.getFields()) {
                            try {
                                if (o == f.get(null)) {
                                    return describe(f);
                                }
                            } catch (final Exception e) {
                            }
                        }
                    }
                }
            }
            return describe(javaType);
        }

        public static S literal(final ICursor cursor, final IAtomModel e, final String lit) {
            return new S(
                    SType.Constant,
                    lit,
                    describe(e.getJavaType(), lit),
                    category(e.getJavaType()),
                    0, cursor.left().length(),
                    true);
        }

        public static S macroKeyword(final ICursor cursor, final MacroModel macro, final String argument) {
            return new S(
                    SType.Argument,
                    argument + ": ",
                    (macro.allowedKeys.containsKey(argument))
                    ? macro.allowedKeys.get(argument) : macro.requiredKeys.get(argument),
                    "keywords",
                    0, cursor.left().length(),
                    true);
        }

        public static S keyword(final ICursor cursor, final IArgument arg) {
            return new S(
                    SType.Argument,
                    arg.getName().get() + ": ",
                    describe(arg.getReadMethod()),
                    "keywords",
                    0, cursor.left().length(),
                    true);
        }

        static S cmd(final ICursor cursor, final SType type, final String name, final String desc, final String cat) {
            final String insert;
            final int offset;

            if (!cursor.previous().isPresent() || cursor.argument().name().isPresent() || cursor.argument().position().isPresent()) {
                insert = "(" + name + ")";
                offset = -1;
            } else {
                insert = name;
                offset = 0;
            }

            return new S(
                    type,
                    insert,
                    desc, cat,
                    offset,
                    cursor.left().length(),
                    false);
        }

        public static S definition(final ICursor cursor, final IDefinition<?> d) {
            if (d.type() == DefinitionType.Template) {
                return cmd(cursor, SType.Template, d.name(), "a user-defined template", "templates");
            } else {
                return new S(SType.Name,
                        d.name(),
                        "a user-defined " + d.type().toString().toLowerCase(),
                        "user-defined commands",
                        0,
                        cursor.left().length(),
                        false);
            }
        }

        public static S keyword(final ICursor cursor,
                final uk.org.cse.nhm.bundle.api.IArgument arg) {
            return new S(
                    SType.Argument,
                    arg.name().get() + ": ",
                    "template argument",
                    "keywords",
                    0,
                    cursor.left().length(),
                    true);
        }

        @Override
        public int compareTo(final S arg0) {
            final int i = Integer.compare(this.type.ordinal(), arg0.type.ordinal());
            if (i == 0) {
                return this.text.compareTo(arg0.text);
            } else {
                return i;
            }
        }

        public static S macro(final ICursor cursor, final String name, final MacroModel next) {
            return cmd(cursor, SType.Macro, name, next.description, "macros");
        }
    }

    final ListMultimap<String, IInvocationModel> byName = LinkedListMultimap.create();
    final ListMultimap<String, MacroModel> macroByName = LinkedListMultimap.create();

    public Language() {
        final IModel model = Defaults.CONTEXT.getModel();
        for (final IInvocationModel inv : model.getInvocations()) {
            this.byName.put(inv.getName(), inv);
        }

        for (final IMacro macro : ExtraMacros.CURRENT) {
            this.macroByName.put(macro.getName(), macro.getModel());
        }

        this.macroByName.put("include-modules",
                MacroModel.builder()
                        .desc("Include the contents of any ~module declarations in another file.")
                        .desc("This also transitively includes any other files included through include-modules.")
                        .desc("Transitive inclusion will stop if there is a cycle (A includes B includes A).")
                        .pos()
                        .require("The relative path to the file to include. If it contains spaces, it should be quoted.")
                        .and()
                        .build());

        this.macroByName.put("~init-modules",
                MacroModel.builder()
                        .desc("Initialize all included modules.")
                        .desc("This macro expands out to the init templates from any modules that have been included.")
                        .desc("The init templates are produced in the order that their modules were encountered.")
                        .build()
        );
    }

    /**
     * We couldn't guess what's around the cursor, so just guess other stuff may
     * still be able to guess the thing above the cursor
     *
     * @param cursor
     * @param definitions
     * @param tmp
     * @return
     */
    private void guess(final ICursor cursor, final Set<? extends IDefinition<?>> definitions, final List<S> result) {
        for (final IDefinition<?> d : definitions) {
            if (d.name().equals(cursor.command()) && d.type() == DefinitionType.Template) {
                // if we exactly match the template, suggest things about it as well
                for (final uk.org.cse.nhm.bundle.api.IArgument arg : d.arguments()) {
                    if (arg.name().isPresent() && arg.name().get().startsWith(cursor.left())) {
                        result.add(S.keyword(cursor, arg));
                    }
                }
            } else if (d.name().startsWith(cursor.left())) {
                result.add(S.definition(cursor, d));
            }
        }

        for (final MacroModel mm : this.macroByName.get(cursor.command())) {
            // this is a macro which matches where we are
            // we ought to suggest keywords for it
            for (final String s : mm.allowedKeys.keySet()) {
                if (s.startsWith(cursor.left())) {
                    result.add(S.macroKeyword(cursor, mm, s));
                }
            }
            for (final String s : mm.requiredKeys.keySet()) {
                if (s.startsWith(cursor.left())) {
                    result.add(S.macroKeyword(cursor, mm, s));
                }
            }
        }

        for (final String s : this.byName.keySet()) {
            if (s.startsWith(cursor.left())) {
                // suggest completing the command
                result.add(S.command(cursor, this.byName.get(s).iterator().next()));
            }
        }
    }

    private void addMacros(final ICursor cursor, final List<S> result) {
        for (final String s : this.macroByName.keySet()) {
            if (s.startsWith(cursor.left())) {
                result.add(S.macro(cursor, s, this.macroByName.get(s).iterator().next()));
            }
        }
    }

    /**
     * Given a cursor position, try and find the model for the thing around the
     * cursor.
     */
    private Optional<IInvocationModel> resolve(final ICursor cursor) {
        final Set<IInvocationModel> list = new HashSet<IInvocationModel>(this.byName.get(cursor.command()));
        if (list.size() > 1) {
            if (cursor.previous().isPresent()) {
                final ICursor previous = cursor.previous().get();
                final Optional<IInvocationModel> pm = resolve(previous);
                if (pm.isPresent()) {
                    final IInvocationModel im = pm.get();
                    final Optional<IArgument> matched = matchArgument(im, previous.argument());
                    if (matched.isPresent()) {
                        list.retainAll(matched.get().getLegalValues());
                    } else {
                        list.clear();
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            return Optional.of(list.iterator().next());
        } else {
            return Optional.absent();
        }
    }

    @Override
    public List<? extends ISuggestion> suggest(final ICursor cursor,
            final Set<? extends IDefinition<?>> definitions) {
        final ArrayList<S> tmp = new ArrayList<>();
        if (cursor.argument().position().isPresent() || cursor.argument().name().isPresent()) {
            final Optional<IInvocationModel> inv = resolve(cursor); // this resolves the thing we are in right now, if it is finished
            if (inv.isPresent()) {
                suggest(cursor, inv.get(), definitions, tmp);
            } else {
                guess(cursor, definitions, tmp);
            }
        } else {
            // we are at the head of a command, so we should suggest sensible things for that place
            suggestHead(cursor, definitions, tmp);
        }

        addMacros(cursor, tmp);

        Collections.sort(tmp);
        return ImmutableList.copyOf(tmp);
    }

    private void suggestHead(
            final ICursor cursor,
            final Set<? extends IDefinition<?>> definitions,
            final ArrayList<S> tmp) {
        if (cursor.previous().isPresent()) {
            final ICursor pc = cursor.previous().get();
            final Optional<IInvocationModel> inv = resolve(pc);
            if (inv.isPresent()) {
                final Optional<IArgument> arg = matchArgument(inv.get(), pc.argument());
                if (arg.isPresent()) {
                    // we are an argument of a known thing, so suggest all prefix matches there?
                    suggest(cursor, arg.get(), tmp);
                }
            }
        } else {
            // we are at the top level - maybe this is scenario or batch
            if (cursor.command().isEmpty()) {
                for (final IInvocationModel im : byName.get("scenario")) {
                    tmp.add(S.command(cursor, im));
                }
                for (final IInvocationModel im : byName.get("batch")) {
                    tmp.add(S.command(cursor, im));
                }
            } else {
                for (final String s : byName.keySet()) {
                    if (s.startsWith(cursor.left())) {
                        for (final IInvocationModel im : byName.get(s)) {
                            tmp.add(S.command(cursor, im));
                        }
                    }
                }
            }
        }

        for (final IDefinition<?> d : definitions) {
            if (d.type() == DefinitionType.Template) {
                if (d.name().startsWith(cursor.left())) {
                    tmp.add(S.definition(cursor, d));
                }
            }
        }
    }

    private Optional<IArgument> matchArgument(
            final IInvocationModel inv,
            final uk.org.cse.nhm.bundle.api.IArgument argument) {
        if (argument.position().isPresent()) {
            if (argument.position().get() >= inv.getPositionalArguments().size()) {
                return inv.getRemainderArgument();
            } else {
                for (final IArgument a : inv.getPositionalArguments()) {
                    if (a.getPosition().equals(argument.position())) {
                        return Optional.of(a);
                    }
                }
            }
        } else if (argument.name().isPresent()) {
            for (final IArgument a : inv.getNamedArguments()) {
                if (a.getName().equals(argument.name())) {
                    return Optional.of(a);
                }
            }
        }
        return Optional.absent();
    }

    private void suggest(final ICursor cursor, final IArgument a, final List<S> suggestions) {
        for (final IElement e : a.getLegalValues()) {
            // don't suggest anything obsolete
            if (e.getJavaType().isAnnotationPresent(Obsolete.class)) {
                continue;
            }
            if (e instanceof IInvocationModel) {
                if (e.getName().startsWith(cursor.left())) {
                    // suggest it
                    suggestions.add(S.command(cursor, (IInvocationModel) e));
                }
            } else if (e instanceof IAtomModel) {
                for (final String lit : ((IAtomModel) e).getLiterals()) {
                    if (lit.startsWith(cursor.left())) {
                        suggestions.add(S.literal(cursor, (IAtomModel) e, lit));
                    }
                }
            }
        }
    }

    /**
     * Produce suggestions given a cursor and a guess at what's around the
     * cursor
     *
     * @param cursor
     * @param inv
     * @param definitions
     * @param tmp
     * @return
     */
    private void suggest(
            final ICursor cursor,
            final IInvocationModel inv,
            final Set<? extends IDefinition<?>> definitions,
            final List<S> suggestions) {
        if (cursor.argument().position().isPresent()) {
            // we may be wanting to insert a keyword
            for (final IArgument arg : inv.getNamedArguments()) {
                final String name = arg.getName().get();
                if (name.startsWith(cursor.left())) {
                    suggestions.add(S.keyword(cursor, arg));
                }
            }
        }

        final Optional<IArgument> arg = matchArgument(inv, cursor.argument());
        if (arg.isPresent()) {
            suggest(cursor, arg.get(), suggestions);
        }

        for (final IDefinition<?> d : definitions) {
            if (d.name().startsWith(cursor.left())) {
                suggestions.add(S.definition(cursor, d));
            }
        }
    }

    @Override
    public Set<String> elements() {
        return this.byName.keySet();
    }

    @Override
    public Set<String> macros() {
        return Sets.union(
                // these are kind of macros, but not really
                ImmutableSet.of("~module", "~local", "template", "include", "include-modules", "no-include"),
                this.macroByName.keySet());
    }

    @Override
    public Optional<String> describe(final ICursor cursor) {
        final Optional<IInvocationModel> resolve = resolve(cursor);
        if (resolve.isPresent()) {
            final IInvocationModel model = resolve.get();
            final Optional<IArgument> arg = matchArgument(model, cursor.argument());
            if (arg.isPresent()) {
                // TODO enum values need detecting
                return Optional.of(S.describe(arg.get().getReadMethod()));
            } else {
                return Optional.of(S.describe(model.getJavaType()));
            }
        } else if (this.macroByName.containsKey(cursor.command())) {
            final MacroModel mm = this.macroByName.get(cursor.command()).iterator().next();
            if (cursor.argument().name().isPresent()) {
                return Optional.fromNullable(mm.allowedKeys.get(cursor.argument().name().get()))
                        .or(Optional.fromNullable(mm.requiredKeys.get(cursor.argument().name().get())));
            } else if (cursor.argument().position().isPresent()) {
                final int i = cursor.argument().position().get();
                if (i >= mm.allowedPos.size()) {
                    return mm.restNum;
                } else {
                    return Optional.fromNullable(mm.allowedPos.get(i));
                }
            } else {
                return Optional.fromNullable(mm.description);
            }
        }
        return Optional.absent();
    }
}
