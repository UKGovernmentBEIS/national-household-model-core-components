package uk.org.cse.nhm.spss.codegen;

import java.util.Set;

import uk.org.cse.nhm.spss.SavVariable;

public class FieldDescriptor {
	private String niceName;

	public FieldDescriptor(SavVariable variable, TypeDescriptor string, final Set<String> names) {
		this.variableName = variable.getName();
		this.variableLabel = variable.getLabel();
		this.type = string;
		String niceName = CodeGenerator.makeNiceName(variableLabel);

		if (niceName.isEmpty()) niceName += variableName;
		
		if (names.contains(niceName)) {
			niceName += "_" + variableName;
		}

		names.add(niceName);
		this.niceName = niceName;
	}
	private String variableName;
	private String variableLabel;
	private TypeDescriptor type;
	
	public String generateDefinition() {
		type.use();
		return 
				String.format("\t@SavVariableMapping(\"%s\")\n", variableName) +
				String.format("\tpublic %s get%s();\n", type.getName(), niceName);
	}
	
	public String getImport() {
		return type.getPackage() + "." + type.getName();
	}

	public String generateGetter() {
		return
				String.format("\tpublic %s get%s() {\n", type.getName(), niceName) +
				String.format("\t\treturn %s;", niceName) +
				String.format("\t}\n");
	}

	public String generateSetter() {
		return
				String.format("\tpublic void set%s(final %s %s) {\n", niceName, type.getName(), niceName) +
				String.format("\t\tthis.%s = %s;", niceName, niceName) +
				String.format("\t}\n");
	}
	
	public String generateField() {
		return String.format("\tprivate %s %s;", type.getName(), niceName);
	}

	public String getVariableName() {
		return variableName;
	}
}
