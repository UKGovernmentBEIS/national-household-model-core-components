package uk.org.cse.nhm.spss.codegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.spss.SavVariable;

public class RecordDescriptor implements TypeDescriptor {

    private List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    private String pkg;
    private String name;
    private String interfaceName;
    private String className;
    private HashSet<String> names = new HashSet<String>();
    private String orginalFileName;

    public RecordDescriptor(String currentPackage, String name, String originalFileName) {
        this.pkg = currentPackage;
        this.name = name;
        this.interfaceName = CodeGenerator.makeNiceName(name.substring(0, 1).toUpperCase() + name.substring(1) + "Entry");
        this.className = CodeGenerator.makeNiceName(name.substring(0, 1).toUpperCase() + name.substring(1) + "EntryImpl");
        this.orginalFileName = originalFileName;
    }

    public void addField(final SavVariable variable, final TypeDescriptor string) {
        fields.add(new FieldDescriptor(variable, string, names));
    }

    public String getPackage() {
        return pkg;
    }

    public String getName() {
        return name;
    }

    public void use() {

    }

    public void generate(final File outputDirectory, final CodeGenerator codeGenerator, String superClass, String superInterface, Set<String> set) throws FileNotFoundException {
        generateInterface(outputDirectory, codeGenerator, superInterface, set);
        generateImplementation(outputDirectory, codeGenerator, superClass, set);
    }

    private void generateImplementation(File outputDirectory, CodeGenerator codeGenerator, String superClass, Set<String> set) throws FileNotFoundException {
        final PrintStream stream = codeGenerator.createStream(outputDirectory, getPackage() + ".impl", className);

        stream.println(
                String.format("package %s;", getPackage() + ".impl"));

        stream.println();

        final HashSet<String> imports = new HashSet<String>();
        imports.add("uk.org.cse.nhm.spss.wrap.SavVariableMapping");
        imports.add(getPackage() + "." + interfaceName);

        final StringBuffer sb = new StringBuffer();

        if (superClass != null && superClass.isEmpty() == false) {
            sb.append(String.format("public class %s extends %s implements %s {\n", className, superClass, interfaceName));
        } else {
            sb.append(String.format("public class %s implements %s {\n", className, interfaceName));
        }

        for (final FieldDescriptor fd : fields) {
            if (set.contains(fd.getVariableName())) {
                continue;
            }
            sb.append(fd.generateField() + "\n");
        }

        for (final FieldDescriptor fd : fields) {
            if (set.contains(fd.getVariableName())) {
                continue;
            }
            sb.append(fd.generateGetter() + "\n");
            sb.append(fd.generateSetter() + "\n");
            imports.add(fd.getImport());
        }

        sb.append("}\n");

        for (final String s : imports) {
            stream.println(String.format("import %s;", s));
        }

        stream.println();

        stream.println(sb);

        stream.close();
    }

    private void generateInterface(File outputDirectory, CodeGenerator codeGenerator, String superInterface, Set<String> set) throws FileNotFoundException {
        final PrintStream stream = codeGenerator.createStream(outputDirectory, getPackage(), interfaceName);

        stream.println(String.format("package %s;", getPackage()));

        stream.println();

        final HashSet<String> imports = new HashSet<String>();
        imports.add("uk.org.cse.nhm.spss.wrap.SavVariableMapping");

        final StringBuffer sb = new StringBuffer();

        if (superInterface != null && superInterface.isEmpty() == false) {
            sb.append(String.format("public interface %s extends %s {\n", interfaceName, superInterface));
        } else {
            sb.append(String.format("public interface %s {\n", interfaceName));
        }

        for (final FieldDescriptor fd : fields) {
            if (set.contains(fd.getVariableName())) {
                continue;
            }
            sb.append(fd.generateDefinition() + "\n");
            imports.add(fd.getImport());
        }

        sb.append("}\n");

        for (final String s : imports) {
            stream.println(String.format("import %s;", s));
        }

        stream.println();

        stream.println(sb);

        stream.close();
    }

    /**
     * Return the orginalFileName.
     *
     * @return the orginalFileName
     */
    public String getOrginalFileName() {
        return orginalFileName;
    }

    public String getGeneratedClassName() {
        return className;
    }
}
