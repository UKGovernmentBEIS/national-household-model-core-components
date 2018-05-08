package uk.org.cse.nhm.spss.values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.org.cse.nhm.spss.SavVariable;

public class VariablesAndValues {

    private List<String> variableNames;
    private Map<String, List<String>> propertyListsByAACode;

    public VariablesAndValues(List<? extends SavVariable> variables, Map<String, List<String>> propertyListsByAACode) {
        variableNames = new ArrayList<String>();
        for (SavVariable var : variables) {
            variableNames.add(var.getName());
        }
        this.propertyListsByAACode = propertyListsByAACode;
    }

    public List<String> getVariableNames() {
        return variableNames;
    }

    public Map<String, List<String>> getPropertyListsByAACode() {
        return propertyListsByAACode;
    }
}
