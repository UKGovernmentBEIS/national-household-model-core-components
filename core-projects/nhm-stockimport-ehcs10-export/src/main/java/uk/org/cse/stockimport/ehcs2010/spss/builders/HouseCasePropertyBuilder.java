package uk.org.cse.stockimport.ehcs2010.spss.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import uk.org.cse.stockimport.domain.AdditionalHousePropertiesDTO;
import uk.org.cse.stockimport.ehcs2010.spss.AbstractCSVHomElementBuilder;

public class HouseCasePropertyBuilder extends AbstractCSVHomElementBuilder<AdditionalHousePropertiesDTO> {

    private final List<String> headers = new ArrayList<>();

    @Override
    public String getFileName() {
        return "houseCaseProperties";
    }

    @Override
    public String[] buildHeader(AdditionalHousePropertiesDTO exampleElement) {
        this.headers.clear();

        this.headers.addAll(exampleElement.getValuesByProperty().keySet());
        Collections.sort(this.headers);

        this.headers.add(0, "aacode");

        return this.headers.toArray(new String[0]);
    }

    @Override
    public String getBuiltClassName() {
        return AdditionalHousePropertiesDTO.class.getName();
    }

    @Override
    public String[] buildRow(AdditionalHousePropertiesDTO element) {
        final List<String> row = new ArrayList<String>();
        final Map<String, String> valuesByProperty = element.getValuesByProperty();

        row.add(element.getAacode());

        for (int i = 1; i < this.headers.size(); i++) {
            row.add(valuesByProperty.get(headers.get(i)));
        }

        return row.toArray(new String[0]);
    }
}
