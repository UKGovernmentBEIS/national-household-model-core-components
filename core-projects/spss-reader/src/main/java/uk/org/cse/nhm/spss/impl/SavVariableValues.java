package uk.org.cse.nhm.spss.impl;

import java.util.Map;

public interface SavVariableValues {

    public abstract String decode(double value);

    public abstract boolean isPredefined(Double value);

    public abstract Map<String, Double> getValues();

    public abstract Map<Double, String> getAntiValues();
}
