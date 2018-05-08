package uk.org.cse.nhm.simulator.state.components.impl;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.Glob;

public class FlagsTest {

    private Flags flags;

    @Before
    public void setup() {
        flags = new Flags(Collections.<String, Object>emptyMap());
    }

    @Test
    public void testUntouchedFlag() {
        Assert.assertFalse("Flag should not be used by default.", flags.testFlag("nope"));
    }

    @Test
    public void testSetFlag() {
        Assert.assertTrue("Can set a flag", flags.addFlag("flag"));
        Assert.assertTrue("Set flag is visible", flags.testFlag("flag"));
        Assert.assertFalse("Setting an already set flag does nothing", flags.addFlag("flag"));
    }

    @Test
    public void canClearFlag() {
        Assert.assertFalse("Clearing an unset flag does nothing.", flags.removeFlag("flag"));
        flags.addFlag("flag");
        Assert.assertTrue("Can clear a set flag", flags.removeFlag("flag"));
        Assert.assertFalse("Cleared flag is not visible", flags.testFlag("flag"));
    }

    @Test
    public void clearWithGlobs() {
        flags.addFlag("a");
        flags.addFlag("b");
        flags.addFlag("c");
        flags.modifyFlagsWith(ImmutableList.of(Glob.of("!?"), Glob.of("x")));
        Assert.assertEquals(Collections.singleton("x"), flags.getFlags());
    }
}
