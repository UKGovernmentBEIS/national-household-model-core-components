package com.larkery.jasb.sexp;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public class SExpressions {

    private static final ISExpression EMPTY = new ISExpression() {
        @Override
        public void accept(final ISExpressionVisitor visitor) {
        }
    };

    public static ISExpression empty() {
        return EMPTY;
    }

    public static class InOrder implements ISExpression {

        private final List<ISExpression> list;

        private InOrder(final List<ISExpression> list) {
            this.list = list;
        }

        public List<ISExpression> getList() {
            return list;
        }

        @Override
        public void accept(final ISExpressionVisitor visitor) {
            for (final ISExpression se : list) {
                se.accept(visitor);
            }
        }
    }

    public static ISExpression inOrder(final Iterable<? extends ISExpression> expressions) {
        final List<ISExpression> list = ImmutableList.copyOf(expressions);
        if (list.isEmpty()) {
            return empty();
        }
        return new InOrder(list);
    }

    /**
     * Makes an error in the given error stream when the s-expression is
     * actually looked at.
     *
     * @param input
     * @param valueOf
     * @param errors
     * @return
     */
    public static ISExpression error(final Seq input, final String valueOf, final IErrorHandler errors) {
        return new ISExpression() {
            @Override
            public void accept(final ISExpressionVisitor visitor) {
                errors.error(input, valueOf);
            }
        };
    }

    /**
     * Makes a warning in the given error stream when the s-expression is
     * actually looked at.
     *
     * @param input
     * @param valueOf
     * @param errors
     * @return
     */
    public static ISExpression warning(final Seq input, final String valueOf, final IErrorHandler errors) {
        return new ISExpression() {
            @Override
            public void accept(final ISExpressionVisitor visitor) {
                errors.warn(input, valueOf);
            }
        };
    }
}
