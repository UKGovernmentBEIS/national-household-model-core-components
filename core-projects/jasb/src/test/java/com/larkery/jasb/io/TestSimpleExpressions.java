package com.larkery.jasb.io;

import java.io.StringReader;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.io.testmodel.Arithmetic;
import com.larkery.jasb.io.testmodel.Div;
import com.larkery.jasb.io.testmodel.GetNode;
import com.larkery.jasb.io.testmodel.ListOfListsOfString;
import com.larkery.jasb.io.testmodel.ListOfStrings;
import com.larkery.jasb.io.testmodel.Plus;
import com.larkery.jasb.io.testmodel.Times;
import com.larkery.jasb.io.testmodel.Value;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.Parser;

public class TestSimpleExpressions extends JasbIOTest {

    public <T> T read(final String s, final Class<T> out) throws InterruptedException, ExecutionException {
        Node node;
        try {
            node = Node.copy(Parser.source("test", new StringReader(s), IErrorHandler.SLF4J));
        } catch (final UnfinishedExpressionException e) {
            throw new RuntimeException(e);
        }

        final T result = context.getReader().readNode(out, node, IErrorHandler.RAISE).get();

        if (result instanceof Arithmetic) {
            Assert.assertSame(node, ((Arithmetic) result).node);
        }

        return result;
    }

    @Test
    public void readsValue() throws InterruptedException, ExecutionException {
        final Value read = read("(value)", Value.class);
        Assert.assertNotNull(read);
    }

    @Test
    public void readsValueWithDouble() throws InterruptedException, ExecutionException {
        final Value read = read("(value of:1)", Value.class);
        Assert.assertNotNull(read);
        Assert.assertEquals(1, read.value, 0);
    }

    @Test
    public void readsEmptyPlus() throws InterruptedException, ExecutionException {
        final Plus read = read("(+)", Plus.class);
        Assert.assertEquals(ImmutableList.of(), read.terms);
    }

    @Test
    public void readsEmptyTimes() throws InterruptedException, ExecutionException {
        final Times read = read("(*)", Times.class);
        Assert.assertEquals(ImmutableList.of(), read.terms);
    }

    @Test
    public void readsDiv() throws InterruptedException, ExecutionException {
        final Div read = read("(/)", Div.class);
        Assert.assertNotNull(read);
    }

    @Test
    public void readsDivWithArguments() throws InterruptedException, ExecutionException {
        final Div read = read("(/ (value of: 1) (value of:2))", Div.class);
        Assert.assertNotNull(read);
        Assert.assertNotNull(read.first);
        Assert.assertNotNull(read.second);
        Assert.assertEquals(1, ((Value) read.first).value, 0);
        Assert.assertEquals(2, ((Value) read.second).value, 0);
    }

    @Test
    public void readsNodeWithoutBinding() throws InterruptedException, ExecutionException {
        final GetNode node = read("(get (+ (value of: 1) (value of: 2)))", GetNode.class);
        Assert.assertEquals("(+ (value of: 1) (value of: 2))", node.node.toString());
    }

    @Test
    public void readsListOfAtoms() throws InterruptedException, ExecutionException {
        final ListOfStrings node = read("(strings values: hello)", ListOfStrings.class);
        Assert.assertEquals(ImmutableList.of("hello"), node.getStrings());

        final ListOfStrings node2 = read("(strings values: [hello world])", ListOfStrings.class);
        Assert.assertEquals(ImmutableList.of("hello", "world"), node2.getStrings());
    }

    @Test
    public void readsListOfAtomsInfix() throws InterruptedException, ExecutionException {
        final ListOfStrings node = read("{strings(values: hello)}", ListOfStrings.class);
        Assert.assertEquals(ImmutableList.of("hello"), node.getStrings());

        final ListOfStrings node2 = read("{strings(values: [hello, world])}", ListOfStrings.class);
        Assert.assertEquals(ImmutableList.of("hello", "world"), node2.getStrings());
    }

    @Test
    public void readsIdentitiesAndResolvesThem() throws InterruptedException, ExecutionException {
        final Times read = read("(* (value name:a of:1) #a #b #a (value name:b of:1))", Times.class);

        for (int i = 0; i < 5; i++) {
            Assert.assertNotNull(read.terms.get(i));
        }

        Assert.assertSame(read.terms.get(0), read.terms.get(1));
        Assert.assertSame(read.terms.get(1), read.terms.get(3));
        Assert.assertSame(read.terms.get(2), read.terms.get(4));
    }

    @Test
    public void readsListsOfListsOfStringsInRemainder() throws InterruptedException, ExecutionException {
        final ListOfListsOfString read = read("(listoflists [x y z] [a b c] [d e f])", ListOfListsOfString.class);

        Assert.assertEquals(ImmutableList.of("x", "y", "z"), read.getFirst());

        Assert.assertEquals(ImmutableList.of(ImmutableList.of("a", "b", "c"),
                ImmutableList.of("d", "e", "f")),
                read.getContents());
    }

    @Test
    public void readsListsOfListsOfStringsInRemainderInInfix() throws InterruptedException, ExecutionException {
        final ListOfListsOfString read = read("{listoflists([x, y, z], [a, b, c], [d, e, f])}", ListOfListsOfString.class);

        Assert.assertEquals(ImmutableList.of("x", "y", "z"), read.getFirst());

        Assert.assertEquals(ImmutableList.of(ImmutableList.of("a", "b", "c"),
                ImmutableList.of("d", "e", "f")),
                read.getContents());
    }
}
