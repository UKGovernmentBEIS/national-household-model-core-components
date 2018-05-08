package uk.org.cse.nhm.spss.impl;

import java.io.IOException;
import java.util.HashSet;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.SavVariableType;

@AutoProperty
public class SavVariableImpl implements SavVariable {

    private static final int NAME_LENGTH = 8;
    private String label;
    private String name;
    private SavVariableType variableType;
    private SavVariableValues values;
    private final int variableTypeCode;
    private final int index;
    private final HashSet<Double> missingValueSet = new HashSet<Double>();
    private boolean restricted;
    private boolean nominal;

    public SavVariableImpl(final SavDataStream stream, final int index) throws IOException {
        this.variableTypeCode = stream.readInt();
        this.index = index;
        if (variableTypeCode < 0) {
            variableType = SavVariableType.STRING_CONTINUATION;
        } else if (variableTypeCode == 0) {
            variableType = SavVariableType.NUMBER;
        } else {
            variableType = SavVariableType.STRING;
        }

        final int hasLabel = stream.readInt();
        final int missingValueCode = stream.readInt();
        if (Math.abs(missingValueCode) > 3) {
            throw new IOException("Invalid missing value code : " + missingValueCode);
        }

        final int printFormatCode = stream.readInt();
        final int writeFormatCode = stream.readInt();
        name = stream.readString(NAME_LENGTH).trim();
        if (hasLabel == 1) {
            final int labelLength = stream.readInt();
            label = stream.readString(labelLength);
            // skip 4 byte of padding
            if (labelLength % 4 != 0) {
                stream.skip(4 - (labelLength % 4));
            }
        } else {
            label = "";
        }

        for (int i = 0; i < Math.abs(missingValueCode); i++) {
            final double missingValue = stream.readDouble();
            missingValueSet.add(missingValue);
        }
    }

    @Override
    public void rename(final String newName) {
        this.name = newName;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public SavVariableType getType() {
        return variableType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setValues(final SavVariableValues values) {
        this.values = values;
    }

    @Override
    public int getVariableTypeCode() {
        return variableTypeCode;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String decode(final double value) {
        if (values == null) {
            return value + "";
        } else {
            return values.decode(value);
        }
    }

    @Override
    public boolean isMissingValue(final double value) {
        //TODO do we also need to compare to system missing value?
        return missingValueSet.contains(value);
    }

    @Override
    public String getValueLabel(final Double value) {
        if (values == null) {
            return null;
        } else {
            return values.decode(value);
        }
    }

    @Override
    public SavVariableValues getVariableValues() {
        return values;
    }

    @Override
    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(final boolean restricted) {
        this.restricted = restricted;
    }

    @Override
    public boolean isNominal() {
        return nominal;
    }

    public void setNominal(final boolean nominal) {
        this.nominal = nominal;
    }

}
