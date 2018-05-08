package uk.org.cse.nhm.documentation;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.reflections.Reflections;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimap;

import net.sf.cglib.asm.Type;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.wrap.SavVariableMapping;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.spss.SurveyEntry;
import uk.org.cse.stockimport.spss.elementreader.ISpssReader;

/**
 * Finds uses of SPSS variables in the codebase. This is not a lovely bit of
 * code.
 *
 * @author hinton
 *
 */
public class VariableUsageGenerator {

    private final String version;
    private final String output;
    private final String standardStockFile;

    public VariableUsageGenerator(final String version, final String output, final String standardStockFile) {
        this.version = version;
        this.output = output;
        this.standardStockFile = standardStockFile;
    }

    public static void main(final String[] args) throws Exception {
        final Reflections r = new Reflections("uk.org.cse.stockimport");

        // get stockimport version
        String version = IBasicDTO.class.getPackage().getImplementationVersion();

        if (version == null) {
            version = "unknown";
        }

        Files.createDirectories(Paths.get(args[0]).getParent());

        final VariableUsageGenerator vug = new VariableUsageGenerator(version, args[0], args[1]);

        // look at all the SPSS readers' read methods, and traverse into them
        for (final Class<?> clazz : r.getSubTypesOf(ISpssReader.class)) {
            vug.methodsToTrace.add(new MethodToTrace(clazz.getName(), "read", Type.getMethodDescriptor(
                    Type.getType(java.util.List.class),
                    new Type[]{Type.getType(IHouseCaseSources.class)}
            ), true
            ));
        }

        vug.run();
    }

    private static <X extends Annotation> X getAnnotationSearchingInterfaces(final Method m, final Class<X> annotationClass) {
        if (m.isAnnotationPresent(annotationClass)) {
            return m.getAnnotation(annotationClass);
        } else {
            // search interfaces?
            final Class<?> clazz = m.getDeclaringClass();
            for (final Class<?> iface : clazz.getInterfaces()) {
                try {
                    final X x = getAnnotationSearchingInterfaces(iface.getMethod(m.getName(), m.getParameterTypes()), annotationClass);;
                    if (x != null) {
                        return x;
                    }
                } catch (NoSuchMethodException | SecurityException e) {
                }
            }

            try {
                final X x = getAnnotationSearchingInterfaces(clazz.getSuperclass().getMethod(m.getName(), m.getParameterTypes()), annotationClass);;
                if (x != null) {
                    return x;
                }
            } catch (final Exception e) {
            }

            return null;
        }

    }

    private static <X extends Annotation> X getAnnotationSearchingInterfaces(final Class<?> c, final Class<X> annotationClass) {
        if (c.isAnnotationPresent(annotationClass)) {
            return c.getAnnotation(annotationClass);
        } else {
            for (final Class<?> iface : c.getInterfaces()) {
                final X val = getAnnotationSearchingInterfaces(iface, annotationClass);
                if (val != null) {
                    return val;
                }
            }

            if (c.getSuperclass() != null) {
                return getAnnotationSearchingInterfaces(c.getSuperclass(), annotationClass);
            }

            return null;
        }
    }

    private static SavVariableMapping getMappingAnnotation(final Class<?> clazz, final String methodName, final String methodDescriptor) {
        if (clazz == null) {
            return null;
        }
        for (final Method m : clazz.getMethods()) {
            if (m.getName().equals(methodName) && methodDescriptor.equals(Type.getMethodDescriptor(m))) {
                if (m.isAnnotationPresent(SavVariableMapping.class)) {
                    return m.getAnnotation(SavVariableMapping.class);
                }
            }
        }

        for (final Class<?> inter : clazz.getInterfaces()) {
            final SavVariableMapping mapping = getMappingAnnotation(inter, methodName, methodDescriptor);
            if (mapping != null) {
                return mapping;
            }
        }

        return getMappingAnnotation(clazz.getSuperclass(), methodName, methodDescriptor);
    }

    private void run() throws Exception {
        while (!methodsToTrace.isEmpty()) {
            final MethodToTrace method = methodsToTrace.pop();

            if (tracedMethods.contains(method)) {
                continue;
            }
            tracedMethods.add(method);

            final Class<?> clazz = Class.forName(method.owner);
            try {
                final InputStream stream = clazz.getResourceAsStream('/' + clazz.getName().replace('.', '/') + ".class");
                if (stream == null) {
                    System.err.println("Could not find " + method.owner);
                    continue;
                }
                final byte[] bytecode = IOUtils.toByteArray(stream);
                final ClassReader reader = new ClassReader(bytecode);
                reader.accept(new Tracer(method), 0);
            } catch (final IOException e) {
            }
        }

        // generate output here
        final Multimap<String, MethodToTrace> dtoVariableToDirectSetter = HashMultimap.create();
        final Multimap<String, MethodToTrace> variableToDirectGetter = HashMultimap.create();

        for (final MethodToTrace traced : tracedMethods) {
            final Class<?> clazz = Class.forName(traced.owner);
            if (SurveyEntry.class.isAssignableFrom(clazz)) {
                // we have a hit on a survey entry! yay!
                final SavVariableMapping mapping = getMappingAnnotation(clazz, traced.name, traced.descriptor);
                if (mapping != null) {
                    for (final String s : mapping.value()) {
                        variableToDirectGetter.put(s, traced);
                    }
                } else {
                    System.out.println("A method invoked on a sav entry did not have a mapping annotation (" + traced.name + " on " + traced.owner + ")");
                }
            } else if (IBasicDTO.class.isAssignableFrom(clazz)) {
                for (final PropertyDescriptor property : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
                    if (property.getWriteMethod() != null && property.getReadMethod() != null) {
                        final DTOField field = getAnnotationSearchingInterfaces(property.getReadMethod(), DTOField.class);
                        if (property.getWriteMethod().getName().equals(traced.name) && traced.descriptor.equals(Type.getMethodDescriptor(property.getWriteMethod()))) {
                            if (field != null) {
                                final DTO dto = getAnnotationSearchingInterfaces(clazz, DTO.class);

                                final String s;
                                if (field.value().length == 1) {
                                    s = field.value()[0];
                                } else {
                                    s = "{" + Joiner.on(", ").join(field.value()) + "}";
                                }

                                dtoVariableToDirectSetter.put(String.format("%s.%s", dto.value(), s), traced);
                            }
                        }
                    }
                }
            }
        }

        for (final String s : variableToDirectGetter.keySet()) {
            System.out.println(s);
        }
        final SavMetadataIndex meta = new SavMetadataIndex(standardStockFile);

        try (final PrintWriter pw = new PrintWriter(Paths.get(output).toFile())) {
            final DocbookWriter writer = new DocbookWriter(pw);

            writer.startSection("Variables in the stock import");

            writer.startSection("SPSS Import Variable Usage");

            writer.writeParagraph("This section of the manual shows where in the code survey variables are consumed, and what metadata are expected for them.");

            writer.writeParagraph("All categorical values are mapped according to their labels, not according to the SPSS coding scheme chosen.");

            writer.startVariableList("Required SPSS variables");

            for (final String variable : ImmutableSortedSet.copyOf(variableToDirectGetter.keySet())) {
                writer.startVarListEntry(variable);

                try {
                    final SavVariable var = meta.getVariable(variable);
                    writer.writeParagraph(String.format("This variable is expected to be provided in the survey file <code>%s</code>. Its original label is <![CDATA[%s]]>.",
                            meta.getFileContaining(variable),
                            var.getLabel()));

                    switch (var.getType()) {
                        case NUMBER:
                            if (var.isNominal()) {
                                writer.writeParagraph("The variable should be categorical, taking the following values:");

                                writer.startItemizedList();

                                for (final String s : var.getVariableValues().getValues().keySet()) {
                                    writer.startListItem();
                                    writer.writeParagraph(s);
                                    writer.endListItem();
                                }

                                writer.endItemizedList();
                            } else {
                                writer.writeParagraph("The variable should be a number.");
                            }

                            final StringBuffer sb = new StringBuffer();

                            for (final double d : var.getVariableValues().getAntiValues().keySet()) {
                                if (var.isMissingValue(d)) {
                                    if (sb.length() > 0) {
                                        sb.append(", ");
                                    }
                                    sb.append(String.format("%f (%s)", d, var.decode(d)));
                                }
                            }

                            if (sb.length() > 0) {
                                writer.writeParagraph("The following values will be treated as missing data: " + sb);
                            }
                            break;
                        case STRING:
                            writer.writeParagraph("The variable should be a string.");
                            break;
                        default:
                            break;

                    }
                } catch (final Exception ex) {
                    System.err.println("Suspicious variable: " + variable);
                }

                writer.writeParagraph("This variable is used in the following locations:");

                writer.startItemizedList();

                for (final MethodToTrace call : variableToDirectGetter.get(variable)) {
                    final List<String> parts = getCallsTo(call);
                    for (final String s : parts) {
                        writer.startListItem();
                        writer.writeParagraph(s);
                        writer.endListItem();
                    }
                }

                writer.endItemizedList();

                writer.endVarListEntry();
            }

            writer.endVariableList();

            writer.endSection();

            writer.startSection("Creation of DTO variables");

            writer.writeParagraph("This section shows where in the code particular DTO fields are set.");

            writer.startVariableList("Places where DTO variables are set");

            for (final String variable : ImmutableSortedSet.copyOf(dtoVariableToDirectSetter.keySet())) {
                writer.startVarListEntry(variable);
                writer.writeParagraph("This field is computed from the following locations:");
                writer.startItemizedList();
                for (final MethodToTrace call : dtoVariableToDirectSetter.get(variable)) {
                    final List<String> parts = getCallsTo(call);

                    for (final String s : parts) {
                        writer.startListItem();
                        writer.writeParagraph(s);
                        writer.endListItem();
                    }
                }
                writer.endItemizedList();
                writer.endVarListEntry();
            }

            writer.endVariableList();

            writer.endSection();

            writer.endSection();
        }

    }

    private List<String> getCallsTo(final MethodToTrace traced) {
        if (traced.isReaderMethod()) {
            return Collections.singletonList("");
        } else {
            final ImmutableList.Builder<String> b = ImmutableList.builder();
            for (final Call mtt : calledBy.get(traced)) {
                final String xref = generateLink(mtt);

                final String at;
                if (xref.isEmpty()) {
                    at = String.format("%s:%d (%s)", mtt.source, mtt.lineNumber, mtt.callingMethod.name);
                } else {
                    at = String.format("<ulink url=\"%s\">%s:%d</ulink> (%s)", xref, mtt.source, mtt.lineNumber, mtt.callingMethod.name.replace("<init>", "constructor"));
                }
                for (final String source : getCallsTo(mtt.callingMethod)) {
                    if (source.isEmpty()) {
                        b.add(at);
                    } else {
                        b.add(source + " calls " + at);
                    }
                }
            }
            return b.build();
        }
    }

    private String generateLink(final Call mtt) {
        String xref = "";

        try {
            final Class<?> clazz = Class.forName(mtt.callingMethod.owner);
            final URL url = clazz.getProtectionDomain().getCodeSource().getLocation();

            final Path p = Paths.get(url.getFile());

            final String filename = p.getFileName().toString();

            if (filename.endsWith(".jar")) {
                final String moduleName = filename.substring(0, filename.length() - ".jar".length());
                if (moduleName.endsWith(version)) {
                    final String subName = moduleName.substring(0, version.length());
                    final String path
                            = String.format("%s/src/main/java/%s.java",
                                    subName,
                                    mtt.callingMethod.owner.replace('.', '/')
                            );

                    xref
                            = String.format("https://csesvn.beanstalkapp.com/nhmstockimport/browse/git/%s?ref=t-nhm-stockimport-parent-%s",
                                    path, version
                            );

                    // annoyingly it is not possible to link to a fricking line number in beanstalk, because beanstalk gives all the lines some kind of UID
                }
            }
        } catch (final Exception e) {
        }
        return xref;
    }

    static class Call {

        public final MethodToTrace callingMethod;
        public final int lineNumber;
        private final String source;

        public Call(final MethodToTrace callingMethod, final String source, final int lineNumber) {
            super();
            this.callingMethod = callingMethod;
            this.source = source;
            this.lineNumber = lineNumber;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((callingMethod == null) ? 0 : callingMethod.hashCode());
            result = prime * result + lineNumber;
            result = prime * result + ((source == null) ? 0 : source.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Call other = (Call) obj;
            if (callingMethod == null) {
                if (other.callingMethod != null) {
                    return false;
                }
            } else if (!callingMethod.equals(other.callingMethod)) {
                return false;
            }
            if (lineNumber != other.lineNumber) {
                return false;
            }
            if (source == null) {
                if (other.source != null) {
                    return false;
                }
            } else if (!source.equals(other.source)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Call [callingMethod=" + callingMethod + ", lineNumber=" + lineNumber + ", source=" + source + "]";
        }
    }

    static class MethodToTrace {

        public final String owner;
        public final String name;
        public final String descriptor;
        private final boolean isReaderMethod;

        public MethodToTrace(final String owner, final String name, final String descriptor, final boolean isReaderMethod) {
            super();
            this.owner = owner;
            this.name = name;
            this.descriptor = descriptor;
            this.isReaderMethod = isReaderMethod;
        }

        public boolean isReaderMethod() {
            return this.isReaderMethod;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((descriptor == null) ? 0 : descriptor.hashCode());
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((owner == null) ? 0 : owner.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MethodToTrace other = (MethodToTrace) obj;
            if (descriptor == null) {
                if (other.descriptor != null) {
                    return false;
                }
            } else if (!descriptor.equals(other.descriptor)) {
                return false;
            }
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }
            if (owner == null) {
                if (other.owner != null) {
                    return false;
                }
            } else if (!owner.equals(other.owner)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "MethodToTrace [owner=" + owner + ", name=" + name + ", descriptor=" + descriptor + "]";
        }
    }

    final Set<MethodToTrace> tracedMethods = new HashSet<>();
    final Deque<MethodToTrace> methodsToTrace = new LinkedList<>();
    final Multimap<MethodToTrace, Call> calledBy = HashMultimap.create();

    private void push(final MethodToTrace mtt) {
        if (!tracedMethods.contains(mtt)) {
            methodsToTrace.push(mtt);
        }
    }

    class MethodTracer extends MethodVisitor {

        private final MethodToTrace caller;
        private int lineNumber;
        private final String source;

        public MethodTracer(final MethodToTrace mtt, final String source) {
            super(Opcodes.ASM4);
            this.caller = mtt;
            this.source = source;
        }

        @Override
        public void visitLineNumber(final int line, final Label start) {
            this.lineNumber = line;
        }

        @Override
        public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
            if (owner.contains("/cse/")) {
                final MethodToTrace mtt = new MethodToTrace(owner.replace('/', '.'), name, desc, false);
                calledBy.put(mtt, new Call(caller, source, lineNumber));
                push(mtt);
            }
        }
    }

    class Tracer extends ClassVisitor {

        private final MethodToTrace mtt;
        private String source;

        public Tracer(final MethodToTrace mtt) {
            super(Opcodes.ASM4);
            this.mtt = mtt;
        }

        @Override
        public void visitSource(final String source, final String debug) {
            this.source = source;
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
            if (name.equals(mtt.name) && desc.equals(mtt.descriptor)) {
                return new MethodTracer(mtt, source);
            }
            return null;
        }
    }
}
