package uk.org.cse.hom.housepropertystore;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestHousePropertyStoreConstruction {

    @Test
    public void HousePropertyStoreShouldBeConstructedEvenIfAdditionalPropertyHasNullValue() throws Exception {
        Map<String, String> properties = new HashMap<>();
        properties.put("Key", null);

        HouseProperties houseProperties = new HouseProperties(properties);
        Assert.assertThat(houseProperties, notNullValue());
        Assert.assertThat(houseProperties.get("Key"), equalTo(HouseProperties.EMPTY_VALUE));
    }
}
