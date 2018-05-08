package uk.org.cse.nhm.spss.codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavInputStream;
import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.impl.SavInputStreamImpl;

public class CodeGenerator {

    final List<RecordDescriptor> records = new ArrayList<RecordDescriptor>();
    final List<EnumDescriptorWrapper> enums = new ArrayList<EnumDescriptorWrapper>();

    int enumCounter = 0;

    final Stack<String> packageStack = new Stack<String>();
    private final String packagePrefix;

    private class EnumDescriptorWrapper implements TypeDescriptor {

        public EnumDescriptor descriptor;

        @Override
        public String getPackage() {
            return descriptor.getPackage();
        }

        @Override
        public String getName() {
            return descriptor.getName();
        }

        @Override
        public void use() {
            descriptor.use();
        }
    }

    public CodeGenerator(final String packagePrefix) {
        this.packagePrefix = packagePrefix;
    }

    public void loadDirectory(final File directory) throws FileNotFoundException, IOException {
        packageStack.push(directory.getName().toLowerCase());
        for (final File f : directory.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".sav")) {
                loadFile(f);
            } else if (f.isDirectory()) {
                loadDirectory(f);
            }
        }
        packageStack.pop();
    }

    @SuppressWarnings("incomplete-switch")
    private void loadFile(final File f) throws FileNotFoundException, IOException {
        System.err.println("Load " + f);
        final SavInputStream sis = new SavInputStreamImpl(new FileInputStream(f));

        final SavMetadata metadata = sis.getMetadata();
        final RecordDescriptor rd = new RecordDescriptor(getCurrentPackage(), f.getName().substring(0,
                f.getName().length() - 4), f.toString());
        records.add(rd);

        final HashSet<SavVariable> enumVariables = new HashSet<SavVariable>();
        final HashSet<SavVariable> intVariables = new HashSet<SavVariable>();
        final HashSet<SavVariable> doubleVariables = new HashSet<SavVariable>();

        for (final SavVariable variable : metadata.getVariables()) {
            switch (variable.getType()) {
                case STRING:
                    rd.addField(variable, TypeDescriptor.STRING);
                    break;
                case NUMBER:
                    enumVariables.add(variable);
                    intVariables.add(variable);
                    doubleVariables.add(variable);
                    break;
            }
        }

        while (sis.hasNext()) {
            final SavEntry entry = sis.next();

            for (final Iterator<SavVariable> enumVariableIterator = enumVariables.iterator(); enumVariableIterator.hasNext();) {
                final SavVariable v = enumVariableIterator.next();

                if (v.getVariableValues() == null || !v.getVariableValues().isPredefined(entry.getValue(v, Double.class))) {
                    enumVariableIterator.remove();
                }
            }

            for (final Iterator<SavVariable> intVariableIterator = intVariables.iterator(); intVariableIterator.hasNext();) {
                final SavVariable v = intVariableIterator.next();

                final Double d = entry.getValue(v, Double.class);
                if (d.intValue() != d.doubleValue()) {
                    intVariableIterator.remove();
                }
            }
        }

        intVariables.removeAll(enumVariables);
        doubleVariables.removeAll(enumVariables);
        doubleVariables.removeAll(intVariables);

        for (final SavVariable v : intVariables) {
            rd.addField(v, TypeDescriptor.INTEGER);
        }

        for (final SavVariable v : doubleVariables) {
            rd.addField(v, TypeDescriptor.DOUBLE);
        }

        for (final SavVariable v : enumVariables) {
            rd.addField(v, getEnumTypeDescriptor(new EnumDescriptor(getCurrentPackage() + ".types", "Enum" + (enumCounter++), v)));
        }
    }

    private String getCurrentPackage() {
        final StringBuffer sb = new StringBuffer();
        sb.append(packagePrefix);
        for (final String s : packageStack.subList(1, packageStack.size())) {
            sb.append(".");
            sb.append(s);
        }
        return sb.toString();
    }

    public TypeDescriptor getEnumTypeDescriptor(final EnumDescriptor descriptor) {
        for (final EnumDescriptorWrapper wrapper : enums) {
            if (wrapper.descriptor.isSubEnumeration(descriptor) || descriptor.isSubEnumeration(wrapper.descriptor)) {
                wrapper.descriptor.merge(wrapper.descriptor);
                return wrapper;
            }
        }
        final EnumDescriptorWrapper result = new EnumDescriptorWrapper();
        result.descriptor = descriptor;
        enums.add(result);
        return result;
    }

    public void generate(final File outputDirectory,
            final String superClass,
            final String superInterface,
            final Set<String> set) throws FileNotFoundException {

//        FileOutputStream stream = new FileOutputStream(outputDirectory.getAbsolutePath() + "\\output.xml");
//        final SpssImportRequest request = new SpssImportRequest();
        for (final RecordDescriptor rd : records) {
            System.err.println("Generate code for " + rd.getName());
            rd.generate(outputDirectory, this, superClass, superInterface, set);
            //updateImportLog(request, rd);
        }

        for (final EnumDescriptorWrapper edw : enums) {
            edw.descriptor.generate(outputDirectory, this);
        }

//        try {
//            //saveInputLog(request, stream);
//            //stream.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public PrintStream createStream(final File outputDirectory, final String pkg, final String name) throws FileNotFoundException {
        final File d = new File(outputDirectory.getAbsolutePath() + "/" + pkg.replace(".", "/"));
        d.mkdirs();
        return new PrintStream(new File(d, name + ".java"));
    }

//    public void saveInputLog(final SpssImportRequest request, FileOutputStream stream) throws Exception {
//        final JAXBContext context = JAXBContext.newInstance(SpssImportRequest.class);
//        final Marshaller marshaller = context.createMarshaller();
//        marshaller.marshal(request, stream);
//    }
//    public void updateImportLog(final SpssImportRequest request, RecordDescriptor rd) throws FileNotFoundException {
//        StockImportItem item = new StockImportItem(rd.getOrginalFileName(), rd.getPackage() + ".impl."
//                + rd.getGeneratedClassName());
//        request.getImportItems().add(item);
//    }
    /**
     * Make a label string into a nice java identifier
     *
     * removes whitespace, camelcases text, and replaces a few special
     * characters with ascii equivalents
     *
     * @param string
     * @return
     */
    public static String makeNiceName(String string) {
        string = string.replace("<", "LessThan").replace(">", "GreaterThan").replace("=", "Equals").replace("\"", "Inches");
        final StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = true;
        for (final char c : string.toCharArray()) {
            if (Character.isLetter(c)) {
                if (nextUpperCase) {
                    nextUpperCase = false;
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            } else if (Character.isDigit(c)) {
                if (sb.length() == 0) {
                    sb.append("_");
                }
                sb.append(c);
            } else {
                nextUpperCase = true;
                if (!Character.isWhitespace(c)) {
                    sb.append("_");
                }
            }
        }
        return sb.toString();
    }
}
