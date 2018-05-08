package uk.org.cse.nhm.bundle.api;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;

/**
 * Provides information about the NHM language, and answers questions for
 * content assist purposes about what can be typed in where.
 */
public interface ILanguage {

    /**
     * A linkedlist from where the cursor is backwards up to the top of the
     * scenario
     */
    public interface ICursor {

        /**
         * The name of the command around this cursor, so something like
         * "scenario" or "apply"
         */
        public String command();

        /**
         * @return the text left of the insertion point, if we are at an
         * argument
         */
        public String left();

        /**
         * The argument we are at, so a number or a keyword or neither
         */
        public IArgument argument();

        /**
         * The cursor around this cursor
         */
        public Optional<ICursor> previous();
    }

    /**
     * Something which could be typed in where you are
     */
    public interface ISuggestion {

        /**
         * The text to enter (may have parens around it)
         */
        public String text();

        /**
         * The cursor offset after putting in the text (0, -1, -2 etc)
         */
        public int cursorOffset();

        /**
         * how far left to overwrite
         */
        public int leftOffset();

        /**
         * Some documentation about this suggestion
         */
        public String description();

        /**
         * category the suggestion should be in
         */
        public String category();

        /**
         * true if it needs a space before it, if there isn't one
         */
        public boolean spaced();
    }

    public List<? extends ISuggestion> suggest(final ICursor cursor, final Set<? extends IDefinition<?>> definitions);

    public Set<String> elements();

    public Set<String> macros();

    public Optional<String> describe(final ICursor cursor);
}
