package uk.org.cse.nhm.language.sexp;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.reflect.TypeToken;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.XScenarioElement;
import uk.org.cse.nhm.language.parse.LanguageElements;

/**
 * A main method which generates the code for a scenario converter,
 * which can then be tweaked by hand
 * @author hinton
 *
 */
public class ScenarioConverterGenerator {
	private final Set<Class<?>> elements;
	private final PrintWriter trueout;
	private final PrintWriter out;
	private final PrintWriter prefixOut;
	private final StringWriter convertorDefs;
	private StringWriter assignables = new StringWriter();

	public static void main(final String[] args) throws IOException {
		new ScenarioConverterGenerator(LanguageElements.get().allAsSet(),
				new PrintWriter(Files.newOutputStream(Paths.get(args[0]))))
				.run();
	}
	
	public ScenarioConverterGenerator(final Set<Class<?>> elements, final PrintWriter out) {
		this.elements = elements;
		this.trueout = out;
		
		this.convertorDefs = new StringWriter();
		this.assignables = new StringWriter();

		this.out = new PrintWriter(convertorDefs);
		this.prefixOut = new PrintWriter(assignables);
	}
	
	public void run() {
		for (final Class<?> clazz : elements) {
			if (XScenarioElement.class.isAssignableFrom(clazz)) {
				if (clazz.isAnnotationPresent(XmlRootElement.class)) {
					System.err.println(clazz.getAnnotation(XmlRootElement.class).name());
				}
			}
		}
		
		trueout.println("public class ScenarioConverterImpl extends ScenarioConverter {");
		trueout.println("public ScenarioConverterImpl() {");
		for (final Class<?> clazz : elements) {
			if (clazz.isAnnotationPresent(XmlRootElement.class)) {		
				out.println("// " + clazz.getCanonicalName());
				generateConversionRule(clazz.getAnnotation(XmlRootElement.class).name(), clazz, elements);
				out.println();
				out.println();
			}
		}
		
		prefixOut.flush();
		out.flush();
		
		trueout.println(assignables.toString());
		trueout.println(convertorDefs.toString());
		trueout.println("sortMappings();");
		trueout.println("}");
		trueout.println("}");
		trueout.flush();
		trueout.close();
	}
	
	private void generateConversionRule(final String prefix, final Class<?> clazz, final Set<Class<?>> elements) {
		final Bind bind = clazz.getAnnotation(Bind.class);

		try {
			out.printf("map(\"%s\", \"%s\")\n", prefix, bind.value()); 
			
			for (final PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
				if (pd.getReadMethod() == null) continue;
				final Annotations annotations = annotations(out, pd);
				
				if (annotations.hasBindAnnotation() && annotations.hasXmlAnnotation()) {
					annotations.generateConversionRule(elements);
				}
			}
			
			out.println("\n\t.build();");
			
			for (final PropertyDescriptor pd : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
				if (pd.getReadMethod() == null) continue;
				final Annotations annotations = annotations(out, pd);
				
				if (annotations.hasBindAnnotation() && annotations.hasXmlAnnotation()) {
					// figure out about annotations
					if (annotations.element != null) {
						// TODO we need to put in some other kind of rule here
						// for target classes which do not have Bind because it
						// was a wrapper?
						if (annotations.clazz.isAnnotationPresent(Bind.class)) {
							if (annotations.clazz.isAnnotationPresent(XmlRootElement.class)) {
								
							} else {
								generateConversionRule(
										prefix + "/" + annotations.getEffectiveName(),
										annotations.clazz, elements);
							}
						}
					}
				}
			}
		} catch (final IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Class<?> getListTypeParameter(final java.lang.reflect.Type genericReturnType) {
		try {
			return TypeToken.of(genericReturnType)
					.resolveType(
							List.class.getMethod("get", int.class)
							.getGenericReturnType()).getRawType();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Cannot see list.get");
		}
	}
	
	private Class<?> getPropertyType(final PropertyDescriptor pd) {
		if (List.class.isAssignableFrom(pd.getPropertyType())) {
			return getListTypeParameter(pd.getReadMethod().getGenericReturnType());
		} else {
			return pd.getPropertyType();
		}
	}
	
	final HashSet<Class<?>> doneAssignables = new HashSet<>();
	
	public Annotations annotations(final PrintWriter out, final PropertyDescriptor pd) {
		final Method method = pd.getReadMethod();
		return new Annotations(
				out,
				getPropertyType(pd),
				pd.getName(),
				method.getAnnotation(XmlAttribute.class), 
				method.getAnnotation(XmlElement.class), 
				method.getAnnotation(XmlElementRef.class), 
				method.getAnnotation(BindNamedArgument.class), 
				method.getAnnotation(BindPositionalArgument.class), 
				method.getAnnotation(BindRemainingArguments.class));
	}
	
	class Annotations {
		public final XmlAttribute attribute;
		public final XmlElement element;
		public final XmlElementRef ref;
		
		public final BindNamedArgument named;
		public final BindPositionalArgument positional;
		public final BindRemainingArguments remaining;
		private final String name;
		private final Class<?> clazz;

		Annotations(final PrintWriter out,final Class<?> clazz, final String name, final XmlAttribute attribute, final XmlElement element,
				final XmlElementRef ref, final BindNamedArgument named,
				final BindPositionalArgument positional,
				final BindRemainingArguments remaining) {
			super();
			this.clazz = clazz;
			this.name = name;
			this.attribute = attribute;
			this.element = element;
			this.ref = ref;
			this.named = named;
			this.positional = positional;
			this.remaining = remaining;
		}
		
		public String nameOrPropertyName(final String name) {
			if (name.startsWith("#")) return this.name;
			else return name;
		}
		
		public String getEffectiveName() {
			if (this.attribute != null) {
				return nameOrPropertyName(this.attribute.name());
			} else if (this.element != null) {
				return nameOrPropertyName(element.name());
			}
			throw new UnsupportedOperationException();
		}

		public void generateConversionRule(final Set<Class<?>> elements) {
			if (attribute != null) {
				out.printf("\t.attribute(\"%s\", %s)\n",
						getEffectiveName(),
						generateOutputSide());
				
			} else if (ref != null) {
				generateRefRule(elements);
			} else if (element != null) {
				out.printf("\t.element(new String[]{\"%s\"}, %s)\n",
						getEffectiveName(), generateOutputSide());
			}
		}

		private void generateRefRule(final Set<Class<?>> elements) {
			final String setName = clazz.getCanonicalName().replace('.', '_');
			
			if (!doneAssignables.contains(clazz)) {
				final Set<String> names = new HashSet<String>();
				for (final Class<?> c : elements) {
					if (clazz.isAssignableFrom(c) && c.isAnnotationPresent(XmlRootElement.class)) {
						final String name = c.getAnnotation(XmlRootElement.class).name();
						names.add('"' + name + '"');
					}
				}
				
				final String allNames = names.toString();
				
				prefixOut.printf(
						"\tfinal String[] %s = {%s};\n",
						setName,
						allNames.substring(1, allNames.length()-1).replace(",", ",\n\t\t")
						);
				
				doneAssignables.add(clazz);
			}
			
			out.printf("\t.element(%s, %s)\n",
					setName,
					generateOutputSide());
		}

		private String generateOutputSide() {
			if (named != null) {
				return String.format("named(\"%s\")", 
						named.value().startsWith("#") ? name : named.value());
			} else if (positional != null) {
				return String.format("positional(%d)", positional.value());
			}
			
			return "remainder()";
		}

		public boolean hasXmlAnnotation() {
			return attribute != null || element != null || ref != null;
		}
		
		public boolean hasBindAnnotation() {
			return named != null || positional != null || remaining != null;
		}
	}
}
