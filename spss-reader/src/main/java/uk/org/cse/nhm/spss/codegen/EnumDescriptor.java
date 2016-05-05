package uk.org.cse.nhm.spss.codegen;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.spss.SavVariable;

@AutoProperty
public class EnumDescriptor implements TypeDescriptor {
	private Set<String> valueLabels = new HashSet<String>();
	private Set<String> missingValueLabels = new HashSet<String>();
	private String pkg;
	private String name;
	private boolean used = false;

	public EnumDescriptor(final String pkg, final String name, final SavVariable var) {
		this.pkg = pkg;
		this.name = name;
		
		for (final Double d : var.getVariableValues().getAntiValues().keySet()) {
			if (var.isMissingValue(d)) {
				missingValueLabels.add(var.getVariableValues().getAntiValues().get(d));
			} else {
				valueLabels.add(var.getVariableValues().getAntiValues().get(d));
			}
		}
	}
	
	protected boolean isYesNo() {
		return valueLabels.size() == 2 && valueLabels.contains("Yes") && valueLabels.contains("No");
	}
	
	/**
	 * return true if this enumeration's variables are all in the other enumeration's variables
	 */
	public boolean isSubEnumeration(final EnumDescriptor descriptor) {
		if (this.isYesNo()) return descriptor.isYesNo();

		return descriptor.valueLabels.containsAll(valueLabels);
	}
	
	public String getPackage() {
		return pkg;
	}

	public String getName() {
		return name;
	}
	
	public void use() {
		used = true;
	}

	public boolean isUsed() {
		return used;
	}

	public void generate(final File outputDirectory, final CodeGenerator codeGenerator) throws FileNotFoundException {
		if (!used) return;
		final PrintStream stream = codeGenerator.createStream(outputDirectory, pkg, name);
		
		stream.println(String.format("package %s;", pkg));
		stream.println();
		stream.println("import uk.org.cse.nhm.spss.wrap.SavEnumMapping;");
		stream.println();
		stream.println(String.format("public enum %s {", name));
		
		if (missingValueLabels.isEmpty() == false) {
			final StringBuilder values = new StringBuilder();
			for (final String s : missingValueLabels) {
				values.append("\"" + s.replace("\"", "\\\"") + "\", ");
			}
			stream.println(String.format("\t@SavEnumMapping({%s})", values));
			stream.println("\t__MISSING,");
			stream.println();
		}
		
		for (final String s : valueLabels) {
			stream.println(String.format("\t@SavEnumMapping(\"%s\")", s.replace("\"", "\\\"")));
			stream.println(String.format("\t%s,", makeEnumConstant(s)));
			stream.println();
		}
		
		stream.println("}");
		stream.close();
	}

	private String makeEnumConstant(String s) {
		return CodeGenerator.makeNiceName(s);
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	public void merge(final EnumDescriptor descriptor) {
		missingValueLabels.addAll(descriptor.missingValueLabels);
		valueLabels.addAll(descriptor.valueLabels);
	}
}
