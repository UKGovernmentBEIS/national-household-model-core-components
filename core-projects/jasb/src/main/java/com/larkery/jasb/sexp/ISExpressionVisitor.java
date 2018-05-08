package com.larkery.jasb.sexp;

public interface ISExpressionVisitor {

    /**
     * @param loc set the location for the next event
     */
    public void locate(final Location loc);

    /**
     * We saw a (
     *
     * @param delimeter TODO
     */
    public void open(Delim delimeter);

    /**
     * We saw a word
     *
     * @param string the word
     */
    public void atom(final String string);

    /**
     * We saw a comment
     *
     * @param text the comment
     */
    public void comment(final String text);

    /**
     * We saw a )
     *
     * @param delimeter TODO
     */
    public void close(Delim delimeter);

    /**
     * A visitor which ignores things
     */
    public static final ISExpressionVisitor IGNORE = new ISExpressionVisitor() {
        @Override
        public void open(final Delim delimeter) {

        }

        @Override
        public void locate(final Location loc) {

        }

        @Override
        public void comment(final String text) {

        }

        @Override
        public void close(final Delim delimeter) {

        }

        @Override
        public void atom(final String string) {

        }
    };
}
