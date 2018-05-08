package com.larkery.jasb.io;

import java.io.StringReader;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.io.testmodel.Arithmetic;
import com.larkery.jasb.sexp.errors.ErrorCollector;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.parse.Parser;

public class TestErrorExpressions extends JasbIOTest {

    private List<IError> runWithErrors(
            final Class<?> out,
            final String testName,
            final String source) {
        final ErrorCollector errors = new ErrorCollector();
        try {
            context.getReader().read(
                    out,
                    Parser.source(
                            "test:" + testName,
                            new StringReader(source),
                            errors),
                    errors
            );
        } catch (final JasbErrorException jee) {
            return ImmutableList.<IError>builder().addAll(errors.getErrors()).addAll(jee.getErrors()).build();
        }
        return ImmutableList.copyOf(errors.getErrors());
    }

    @Test
    public void extraArgumentsProduceErrors() {
        final List<IError> errors
                = runWithErrors(Arithmetic.class, "extraArgumentErrors",
                        "(value this that the other)");
        Assert.assertFalse("There should be errors", errors.isEmpty());
    }

    @Test
    public void duplicateIdentitiesProduceErrors() {
        final List<IError> errors
                = runWithErrors(Arithmetic.class, "duplicateIdentitiesProduceErrors",
                        "(+ (* name: times) (* name: times))");

        Assert.assertEquals("There should be one error", 1, errors.size());
    }

    @Test
    public void wrongBracketsProduceErrors() {
        final List<IError> errors
                = runWithErrors(Arithmetic.class, "wrongBracketsProduceErrors",
                        "(");

        Assert.assertEquals("There should be one error", 1, errors.size());
    }

    @Test
    public void wrongBracketsProduceErrors2() {
        final List<IError> errors
                = runWithErrors(Arithmetic.class, "wrongBracketsProduceErrors2",
                        "())");

        Assert.assertEquals("There should be one error, because the bad bracket has broken everything", 1, errors.size());
    }
}
