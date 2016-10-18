package uk.org.cse.nhm.spss.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class SavVariableValuesImpl implements SavVariableValues {
	private final Map<String, Double> values = new HashMap<String, Double>();
	private final Map<Double, String> antiValues = new HashMap<Double, String>();
	
	public Map<String, Double> getValues() {
		return values;
	}

	public Map<Double, String> getAntiValues() {
		return antiValues;
	}

	public SavVariableValuesImpl(final SavDataStream stream) throws IOException {
		// read a type 3 record (already read a 3 in the caller)
		final int numberOfLabels = stream.readInt();
		for (int i = 0; i<numberOfLabels; i++) {
			double value = stream.readDouble();
			final String label = stream.readVarString(8);
			values.put(label, value);
			antiValues.put(value, label);
		}
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.spss.impl.SavVariableValues#decode(double)
	 */
	public String decode(double value) {
		if (antiValues.containsKey(value)) {
			return antiValues.get(value);
		} else {
			return "" + value;
		}
	}

	public boolean isPredefined(Double value) {
		return antiValues.containsKey(value);
	}
}
