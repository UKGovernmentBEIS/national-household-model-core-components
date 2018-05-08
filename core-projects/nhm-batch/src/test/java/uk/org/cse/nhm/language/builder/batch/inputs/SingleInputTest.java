package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class SingleInputTest {

    protected String placeholder;
    protected SingleInput input;

    @Before
    public void setup() {
        this.placeholder = "placeholder";
        this.input = getInput(placeholder);
    }

    abstract protected SingleInput getInput(String placeholder);

    @Test
    public void testPlaceholder() {
        Assert.assertEquals("Should only be one placeholder.", 1, input.getPlaceholders().size());
        Assert.assertEquals("Correct placeholder should be included.", placeholder, input.getPlaceholders().get(0));
    }

    protected void assertValues(SingleInput input, String message, Object... values) {
        Iterator<List<Object>> iterator = input.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            if (i >= values.length) {
                Assert.assertTrue(String.format("Input produced more values (%d) than expected (%d).", i + 1, values.length), i < values.length);
            }

            List<Object> next = iterator.next();
            Assert.assertEquals("Each entry in a single input should contain only one value.", 1, next.size());
            Assert.assertEquals(message, values[i], next.get(0));
            i += 1;
        }

        if (i < values.length) {
            Assert.fail("Input produced fewer values than expected.");
        }
    }
}
