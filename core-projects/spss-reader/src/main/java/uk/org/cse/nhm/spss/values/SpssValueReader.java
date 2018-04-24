package uk.org.cse.nhm.spss.values;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.impl.SavInputStreamImpl;

/**
 * Class to load values from an SPSS file. Wraps the InputStream in
 * SavInputStream, then goes through all the variables for every entry in the
 * SPSS file and makes a table of values by key and variable.
 * 
 * Currently EHS specific since it keyed on aacode.
 * 
 * @since 1.2.0
 * @author glenns
 */
public class SpssValueReader {

	/**
	 * @since 1.2.0
	 * 
	 * @param stream
	 *            An inputstream containing an SPSS .sav file.
	 * @return A Table of values with variable as the row key and aacode as the
	 *         column key.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Optional<Table<String, String, String>> load(InputStream stream, Set<String> aacodesToLookup) throws FileNotFoundException, IOException {
		return load(new SavInputStreamImpl(stream), aacodesToLookup);
	}

	private Optional<Table<String, String, String>> load(SavInputStreamImpl input, Set<String> aacodesToLookup) throws FileNotFoundException, IOException {
		Table<String, String, String> valuesByVariableThenAACode = HashBasedTable.create();

		SavMetadata metaData = input.getMetadata();
		List<? extends SavVariable> variables = metaData.getVariables();

		SavVariable aaCodeVariable = getAACodeVariable(variables);

		if (aaCodeVariable == null) {
			throw new IllegalArgumentException("Could not find aa code variable in stream.");
		}

		Set<String> aaCodes = new HashSet<String>();
		while (input.hasNext()) {
			SavEntry entry = input.next();
			String aaCode = entry.getValue(aaCodeVariable, String.class);
			if (aaCodes.contains(aaCode)) {
				return Optional.absent(); // The file being processed contains multiple entries
											// per aacode, so we'll skip it.
			}

			aaCodes.add(aaCode);

			if (aacodesToLookup.contains(aaCode)) {
				getPropertiesForAACode(valuesByVariableThenAACode, variables, entry, aaCode);
			}
		}

		return Optional.of(valuesByVariableThenAACode);
	}

	private SavVariable getAACodeVariable(List<? extends SavVariable> variables) {
		SavVariable aaCodeVariable = null;
		for (SavVariable variable : variables) {
			if (variable.getName().toLowerCase().equals("aacode")) {
				aaCodeVariable = variable;
			}
		}
		return aaCodeVariable;
	}

	private void getPropertiesForAACode(Table<String, String, String> valuesByVariableThenAACode, List<? extends SavVariable> variables, SavEntry entry, String aaCode) {
		for (SavVariable variable : variables) {
			String variableName = variable.getName();
			switch (variable.getType()) {
			case NUMBER:
				Double value = entry.getValue(variable, Double.class);
				if (variable.isMissingValue(value)) {
					valuesByVariableThenAACode.put(variableName, aaCode, "");
				} else if (variable.isRestricted()) {
					valuesByVariableThenAACode.put(variableName, aaCode, variable.getValueLabel(value));
				} else {
					valuesByVariableThenAACode.put(variableName, aaCode, value.toString());
				}

				break;
			case STRING:
				valuesByVariableThenAACode.put(variableName, aaCode, stripNewlines(entry.getValue(variable, String.class)));
				break;
			case STRING_CONTINUATION:
				break;
			default:
				throw new RuntimeException(String.format("Unknown variable type %s, please add a new case in the program.", variable.getName()));
			}
		}
	}

	private String stripNewlines(String value) {
		return value.replace('\n', ' ').replace("\r", "");
	}
}
