package uk.org.cse.commons;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

public class GlobTest {
    static void testGlob(
        final String glob,
        final String[] pass,
        final String[] fail,
        final boolean wild,
        final Optional<String> literal) {
        
        final Glob g = Glob.of(glob);
        
        for (final String s : pass) {
            Assert.assertTrue(
                String.format("%s should pass %s", glob, s),
                g.matches(s));
        }

        for (final String s : fail) {
            Assert.assertFalse(
                String.format("%s should not pass %s", glob, s),
                g.matches(s));

            if (g.isNegated()) {
                Assert.assertTrue(
                    String.format("%s should remove %s [%s]", glob, s, g.matches(s, false)),
                    g.removes(s));
            }
        }

        Assert.assertEquals("Wildcard status of glob " + g, wild, g.isWild());
        Assert.assertEquals("Negation status of glob " + g, glob.startsWith("!"), g.isNegated());
        Assert.assertEquals("Glob literal value " + g, literal, g.getLiteralValue());

        if (!g.isNegated()) {
            testGlob("!" + glob, fail, pass, wild, literal);
        }
    }

    @Test
    public void star() {
        testGlob("*",
                 new String[] {"", "a", "asdf", "120938", "*"},
                 new String[] {},
                 true,
                 Optional.<String>absent());
    }

    @Test
    public void question() {
        testGlob("?",
                 new String[] {"a", "b", "c", "*", "?", "1"},
                 new String[] {"", "ab", "abc"},
                 true,
                 Optional.<String>absent());
    }

    @Test
    public void starts() {
        testGlob("start*",
                 new String[] {"start1", "start", "start-blam"},
                 new String[] {"star", "foo-start-x", " start"},
                 true,
                 Optional.<String>absent());
    }

    @Test
    public void ends() {
        testGlob("*end",
                 new String[] {"end", "asdfend", "qwwerend"},
                 new String[] {"and", "bendy", "end8"},
                 true,
                 Optional.<String>absent());
    }

    @Test
    public void literals() {
        testGlob("\\*",
                 new String[] {"*"},
                 new String[] {"a"},
                 false,
                 Optional.<String>of("*"));

        testGlob("got-widgets",
                 new String[] {"got-widgets"},
                 new String[] {"not-got-widgets"},
                 false,
                 Optional.<String>of("got-widgets"));
    }

    @Test
    public void alternatives() {
        testGlob("<a,b>",
                 new String[] {"a", "b"},
                 new String[] {"ab", "ba"},
                 true,
                 Optional.<String>absent());
    }

    @Test
    public void notC() {
        final Glob g = Glob.of("!c");
        Assert.assertTrue(g.removes("c"));
    }
    
    
    public static void requireForbid(final String[][] trues, final String[][] falses, final Glob... globs) {
    	final Predicate<Collection<String>> pred = Glob.requireAndForbid(Arrays.asList(globs));
    	
    	final String globS = Arrays.toString(globs);
    	for (final String[] S : trues) {
    		Assert.assertTrue(
    				Arrays.toString(S) + " should pass " + globS,
    				pred.apply(Arrays.asList(S)));
    	}
    	
    	for (final String[] S : falses) {
    		Assert.assertFalse(
    				Arrays.toString(S) + " should fail " + globS,
    				pred.apply(Arrays.asList(S)));
    	}
    }
    
    @Test
    public void requireForbidNothing() {
    	requireForbid(new String[][] {
    			{},
    			{"any", "thing", "should", "pass"},
    			{"whatever", "I", "write", "here"}
    	} , new String[][] {});
    }
    
    @Test
    public void forbidEverything() {
    	requireForbid(new String[][] {
    			{} // allowed
    	} , new String[][] {
    			{"any", "thing", "should", "fail"},
    			{"whatever", "I", "write", "here"}
    	},
    	Glob.of("!*"));
    }
    
    @Test
    public void forbidCase() {
    	requireForbid(new String[][] {
    			{},
    			{"blahblah"}
    	} , new String[][] {
    			{"LOWERCASE", "thing", "should", "fail"},
    			{"whatever", "lowercase", "write", "here"}
    	},
    	Glob.of("!lowercase"));
    }
    
    @Test
    public void requireBlah() {
    	requireForbid(new String[][] {
    			{"blah", "and", "foo"}
    	} , new String[][] {
    			{},
    			{"blah-things"},
    			{"LOWERCASE", "thing", "should", "fail"},
    			{"whatever", "lowercase", "write", "here"}
    	},
    	Glob.of("blah"));
    }
    
    @Test
    public void requireBlahStar() {
    	requireForbid(new String[][] {
    			{"blah", "and", "foo"},
    			{"blah-things"},
    			{"blah-whatsits"},
    			{"blah"}
    	} , new String[][] {
    			{},
    			{"LOWERCASE", "thing", "should", "fail"},
    			{"whatever", "lowercase", "write", "here"}
    	},
    	Glob.of("blah*"));
    }
    
    @Test
    public void requireBlahStarButForbidStarThings() {
    	requireForbid(new String[][] {
    			{"blah", "and", "foo"},
    			{"blah-whatsits"},
    			{"blah"}
    	} , new String[][] {
    			{},
    			{"blah-things"},
    			{"LOWERCASE", "thing", "should", "fail"},
    			{"whatever", "lowercase", "write", "here"}
    	},
    	Glob.of("blah*"), Glob.of("!*-things"));
    }
}


