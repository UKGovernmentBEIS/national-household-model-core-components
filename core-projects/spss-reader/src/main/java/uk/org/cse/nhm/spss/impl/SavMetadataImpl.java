package uk.org.cse.nhm.spss.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.impl.format.SavFloatTypeInformation;
import uk.org.cse.nhm.spss.impl.format.SavIntegerTypeInformation;

@AutoProperty
@SuppressWarnings("unused") // The fields are used by the @AutoProperty annotation.
public class SavMetadataImpl implements SavMetadata {
	private static final String RECORD_TYPE_CODE = "$FL2";
	private static final int PRODUCT_ID_LENGTH = 60;
	private static final int TIME_LENGTH = 8;
	private static final int DATE_LENGTH = 9;
	private static final int LABEL_LENGTH = 64;
	private static final int DOC_RECORD_LINE_LENGTH = 80;
	
	
	private final int obsPerObservation;
	private final int compression;
	private final int weightSwitch;
	private final int caseCount;
	private final double compressionBias;
	private final String creationDate;
	private final String creationTime;
	private final String fileLabel;
	
	private final List<SavVariableValues> variableValues = new ArrayList<SavVariableValues>();
	private final List<SavVariableImpl> variables = new ArrayList<SavVariableImpl>();
	private final Map<String, SavVariable> variablesByName = new HashMap<String, SavVariable>();
	private SavIntegerTypeInformation integerTypeInformation;
	private SavFloatTypeInformation floatTypeInformation;
	
	public SavMetadataImpl(final SavDataStream stream) throws IOException {
		this(stream, false);
	}
	
	public SavMetadataImpl(final SavDataStream stream, final boolean useLongNames) throws IOException {
		final String recordTypeCode = stream.readString(RECORD_TYPE_CODE.length());
		
		if (!recordTypeCode.equals(RECORD_TYPE_CODE)) {
			throw new IOException("Record type code is " + recordTypeCode);
		}
		
		final String productID = stream.readString(PRODUCT_ID_LENGTH);
		
		final int layoutCode = stream.readInt();
		
		if (!(layoutCode == 2 || layoutCode == 3)) {
			stream.setBigEndian();
			// fix up layout code (reverse its byte order)
		}
		
		obsPerObservation = stream.readInt();
		compression = stream.readInt();
		weightSwitch = stream.readInt();
		caseCount = stream.readInt();
		compressionBias = stream.readDouble();
		creationDate = stream.readString(DATE_LENGTH);
		creationTime = stream.readString(TIME_LENGTH);
		fileLabel = stream.readString(LABEL_LENGTH);
		stream.skip(3);
		
		stream.setCompressedData(isCompressed(), compressionBias);
		
		int nextTypeCode;
		while ((nextTypeCode = stream.readInt()) == 2) {
			final SavVariableImpl var = new SavVariableImpl(stream, variables.size());
			variables.add(var);
			variablesByName.put(var.getName(), var);
		}
		
		stream.pushBack(nextTypeCode);
		
		while ((nextTypeCode = stream.readInt()) != 999) {
			switch (nextTypeCode) {
			case 3:
				// type 3 variable is a value label set
				// for a previously read variable(s) (type code 2)
				final SavVariableValues values = new SavVariableValuesImpl(stream);
				
				variableValues.add(values);
				
				// there should be a type 4 record after the type 3
				final int shouldBe4 = stream.readInt();
				
				if (shouldBe4 != 4) {
					throw new IOException("Got a type " + shouldBe4 + " record after a type 3!");
				}
				
				final int numberOfVariables = stream.readInt();
				
				for (int i = 0; i<numberOfVariables; i++) {
					final int variableIndex = stream.readInt();
					variables.get(variableIndex - 1).setValues(values);
				}
				
				break;
			case 6:
				// type 7 variable is a "document record"
				// not sure exactly what that is for
				// so I am just going to read and ignore it.
				
				final int lineCount = stream.readInt();
				for (int i = 0; i<lineCount; i++) {
					stream.readString(DOC_RECORD_LINE_LENGTH);
				}
				break;
			case 7:
				// type 7 variable has various subtypes]
				final int subType = stream.readInt();
				switch (subType) {
				case 3:
					// integer bit format
					this.integerTypeInformation = new SavIntegerTypeInformation(stream);
					break;
				case 4:
					// float bit format
					this.floatTypeInformation = new SavFloatTypeInformation(stream);
					break;
				case 5:
					// variable sets information
				{
					final int dataElementLength = stream.readInt();
					assert dataElementLength == 1;
					final int dataElementCount = stream.readInt();
					stream.readString(dataElementCount);
				}
					break;
				case 11:
				{
					// more datatype information
					final int dataElementLength = stream.readInt();
					final int dataElementCount = stream.readInt();
					
					final int variableCount = dataElementCount / 3;
					for (int i = 0; i<variableCount; i++) {
						final int measure = stream.readInt();
						final int width = stream.readInt();
						final int alignment = stream.readInt();

						switch (measure) {
						case 1: //nominal
							variables.get(i).setNominal(true);
						case 2://ordinal
							variables.get(i).setRestricted(true);
							break;
						case 3: //scale
							
							break;
						}
					}
				}
				break;
				case 13:
				{
					final int dataElementLength = stream.readInt();
					final int numberOfElements = stream.readInt();
					if (dataElementLength != 1) {
						throw new IOException("Reading subtype 13, expected data element length to be 1, but was " + dataElementLength); 
					}
					
					final byte[] data = stream.readBytes(numberOfElements);
					if (useLongNames) {
						final String[] parts = new String(data, StandardCharsets.US_ASCII).split("\t");
						for (final String part : parts) {
							final String[] map = part.split("=");
							if (map.length != 2) {
								throw new IOException("Long names section contains invalid renaming " + part);
							}
							final SavVariable variable = variablesByName.get(map[0]);
							if (variable == null) throw new IOException("Long names section renames nonexistent variable " + map[0]);
							variable.rename(map[1]);
							variablesByName.put(variable.getName(), variable);
						}
					}
				}
					break;
				default:
					// wind over other type 7 subtypes
				{
					final int dataElementLength = stream.readInt();
					final int numberOfElements = stream.readInt();
					
					for (int i = 0; i<numberOfElements; i++) {
						stream.skip(dataElementLength);
					}
				}
					break;
				}
				break;
			case 999:
				break;
			default:
				throw new IOException("Unknown SAV record type " + nextTypeCode);	
			}
		}
		
		final int terminator = stream.readInt();
		if (terminator != 0) throw new IOException("Metadata block should end with 999 followed by 0");
	}

	@Override
	public List<? extends SavVariable> getVariables() {
		return variables;
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	public boolean isCompressed() {
		return compression == 1;
	}

	@Override
	public int getEntryCount() {
		return caseCount;
	}

	@Override
	public SavVariable getVariableIgnoreCase(final String string) {
		for (final String name : variablesByName.keySet()) {
			if (name.equalsIgnoreCase(string)) {
				return variablesByName.get(name);
			}
		}
		return null;
	}
	
	@Override
	public SavVariable getVariable(final String varname) {
		return variablesByName.get(varname);
	}

	@Override
	public List<SavVariableValues> getVariableValues() {
		return variableValues;
	}
}
