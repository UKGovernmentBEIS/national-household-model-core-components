package uk.org.cse.nhm.language.builder.convert;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.logic.LogicalCombination;

public class TestToSetConverterTest {

    @Test
    public void isBooleanComponentsFunctionWorks() {
        Assert.assertFalse(TestToSetConverter.isBooleanComponentsFunction(Object.class));
        Assert.assertFalse(TestToSetConverter.isBooleanComponentsFunction(IComponentsFunction.class));
        System.err.println("Test logical combination");
        Assert.assertTrue(TestToSetConverter.isBooleanComponentsFunction(LogicalCombination.class));
    }
}
