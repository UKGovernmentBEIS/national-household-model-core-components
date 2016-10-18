package uk.org.cse.nhm.spss.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.SavVariableType;

@AutoProperty
public class SavEntryImpl implements SavEntry {
	private List<Object> values = new ArrayList<Object>();
	private SavMetadata metadata;
	
	public SavEntryImpl(final SavMetadata metadata, final SavDataStream stream) throws IOException {
		this.metadata = metadata;
		for (final SavVariable variable : metadata.getVariables()) {
			switch (variable.getType()) {
			case NUMBER:
				values.add(readNumber(stream));
				break;
			case STRING:
				values.add(readString(stream, variable));
				break;
			case STRING_CONTINUATION:
				values.add(null);
				break;
			}
		}
	}
	
	private String readString(SavDataStream stream, SavVariable variable) throws IOException {
		return stream.readStringData(variable.getVariableTypeCode());
	}

	private Double readNumber(final SavDataStream stream) throws IOException {
		return stream.readNumericData();
	}

	public <T> T getValue(SavVariable variable, Class<T> clazz) {
		return getValue(variable.getIndex(), clazz);
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		
		for (final SavVariable var : metadata.getVariables()) {
			switch (var.getType()) {
			case NUMBER:
				sb.append(String.format("%s (%s): %s (missing:%s)\n", var.getName(), var.getLabel(), var.decode(getValue(var, Double.class)),
						var.isMissingValue(getValue(var, Double.class))
						));
				break;
			case STRING:
				sb.append(String.format("%s (%s): %s\n", var.getName(), var.getLabel(), getValue(var, String.class)));
				break;
			case STRING_CONTINUATION:
				break;
			}
		}
		
		return sb.toString();
	}
	
	public <T> T getValue(int variable, Class<T> clazz) {
		return clazz.cast(values.get(variable));
	}

	public boolean isMissing(int variable) {
		Object object = values.get(variable);
		if (object instanceof Double) {
			return metadata.getVariables().get(variable).isMissingValue((Double) object);
		} else {
			return false;
		}
	}

	public boolean isMissing(SavVariable variable) {
		return isMissing(variable.getIndex());
	}

	public boolean isPredefinedValue(SavVariable variable) {
		if (variable.getType() == SavVariableType.NUMBER && 
				variable.getVariableValues() != null && 
				variable.getVariableValues().isPredefined(getValue(variable, Double.class))) {
			return true;
		}
		return false;
	}
}