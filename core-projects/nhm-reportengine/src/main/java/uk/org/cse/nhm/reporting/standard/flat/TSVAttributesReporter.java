package uk.org.cse.nhm.reporting.standard.flat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.BasicAttributesLogEntry;
import uk.org.cse.nhm.logging.logentry.BasicAttributesLogEntry.Details;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

public class TSVAttributesReporter extends SimpleTabularReporter<BasicAttributesLogEntry> {
	private final ArrayList<Method> getters;
	
	public TSVAttributesReporter(final IOutputStreamFactory factory) {
		super(factory, BasicAttributesLogEntry.class, TableType.DWELLING, "housing-stock-attributes", ReportHeaderLogEntry.Type.State);
		this.getters = new ArrayList<Method>();
		
		final ImmutableList.Builder<Field> fields = ImmutableList.builder();
		
		fields.add(Field.of("attributes-id",
				"The unique ID for this set of attributes",
				"ID"
				));

		
		for (final Method m : BasicAttributesLogEntry.Details.class.getDeclaredMethods()) {
			if (m.getReturnType() != Void.TYPE && m.getParameterTypes().length == 0) {
				if (m.getName().startsWith("get") || m.getName().startsWith("is")) {
					getters.add(m);
					String name = m.getName();
					if (name.startsWith("get")) name = name.substring(3);
					else if (name.startsWith("is")) name = name.substring(2);
					
					fields.add(Field.of(name, 
							"The value of " +name + " for houses with these attributes",
							m.getReturnType().getSimpleName()
							));
				}
			}
		}
		
		start(fields.build());
	}

	
	@Override
	protected void doHandle(final BasicAttributesLogEntry entry) {
		final List<String> values = new ArrayList<String>();
		values.add(entry.getAttributesID() + "");
		try {
			final Details details = entry.getDetails();
			for (final Method m : getters) {
				Object o;
					o = m.invoke(details);
				if (o == null) values.add("");
				else values.add("" + o);
			}
		} catch (final IllegalAccessException e) {
		} catch (final IllegalArgumentException e) {
		} catch (final InvocationTargetException e) {
		}
		
		write(values.toArray(new String[0]));
	}
	
	@Override
	public String getDescription() {
		return "This table is a normalisation of 'structure'. Each row just describes a unique set of "+
				"properties that have pertained to a house or some houses at some point in the simulation. " +
				"It cannot tell you about the properties of a particular house at a particular time; for this " +
				"you need to join it onto 'struture' with attributes-id.";
	}
}
