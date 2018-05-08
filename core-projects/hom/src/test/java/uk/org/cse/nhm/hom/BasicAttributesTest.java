package uk.org.cse.nhm.hom;

import org.junit.Assert;
import org.junit.Test;

public class BasicAttributesTest {

    @Test
    public void equalsIsEqualityNotIdentity() {
        final BasicCaseAttributes bca = new BasicCaseAttributes();
        Assert.assertEquals(bca.copy(), bca);
    }
}
