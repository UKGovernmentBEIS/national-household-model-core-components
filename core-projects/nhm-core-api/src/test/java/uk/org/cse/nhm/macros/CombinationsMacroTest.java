package uk.org.cse.nhm.macros;

import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.JoinMacro;
import com.larkery.jasb.sexp.parse.MacroExpander;
import com.larkery.jasb.sexp.parse.Parser;

public class CombinationsMacroTest {

    @Test
    public void simpleCombinationsWork() throws Exception {
        final Seq node = (Seq) Node.copy(
                Parser.source(
                        ("test:test"),
                        new InputStreamReader(
                                getClass().
                                        getClassLoader().
                                        getResourceAsStream("plain-combinations.s")),
                        IErrorHandler.RAISE));

        final Node input = node.get(0);
        final Node output = node.get(1);

        final Node expanded
                = Node.copy(
                        MacroExpander.expand(
                                ImmutableList.<IMacro>of(new CombinationsMacro()),
                                input,
                                IErrorHandler.RAISE)
                );

        Assert.assertEquals(output, expanded);
    }

    @Test
    public void callingTemplateWorks() throws Exception {
        validate("templated-combinations.s");
    }

    private void validate(final String name) throws UnfinishedExpressionException {
        final Seq node = (Seq) Node.copy(
                Parser.source(
                        ("test:test"),
                        new InputStreamReader(
                                getClass().
                                        getClassLoader().
                                        getResourceAsStream(name)),
                        IErrorHandler.RAISE));

        final Node input = node.get(0);
        final Node output = node.get(1);

        final Node expanded
                = Node.copy(
                        MacroExpander.expand(
                                ImmutableList.<IMacro>of(new CombinationsMacro()),
                                input,
                                IErrorHandler.RAISE)
                );

        Assert.assertEquals(output, expanded);
    }

    @Test
    public void combinationsWithWrapperWork() throws Exception {
        final Seq node = (Seq) Node.copy(
                Parser.source(
                        ("test:test"),
                        new InputStreamReader(
                                getClass().
                                        getClassLoader().
                                        getResourceAsStream("wrapped-combinations.s")),
                        IErrorHandler.RAISE));

        final Node input = node.get(0);
        final Node output = node.get(1);

        final Node expanded
                = Node.copy(
                        MacroExpander.expand(
                                ImmutableList.<IMacro>of(new CombinationsMacro()),
                                input,
                                IErrorHandler.RAISE)
                );

        Assert.assertEquals(output, expanded);
    }

    @Test
    public void combinationsWithInnerMacroWork() throws Exception {
        final Seq node = (Seq) Node.copy(
                Parser.source(
                        ("test:test"),
                        new InputStreamReader(
                                getClass().
                                        getClassLoader().
                                        getResourceAsStream("join-wrapped-combinations.s")),
                        IErrorHandler.RAISE));

        final Node input = node.get(0);
        final Node output = node.get(1);

        final Node expanded
                = Node.copy(
                        MacroExpander.expand(
                                ImmutableList.<IMacro>of(new CombinationsMacro(), new JoinMacro()),
                                input,
                                IErrorHandler.RAISE)
                );

        Assert.assertEquals(output, expanded);
    }
}
