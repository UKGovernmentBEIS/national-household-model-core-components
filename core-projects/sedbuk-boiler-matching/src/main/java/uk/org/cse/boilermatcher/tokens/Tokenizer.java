package uk.org.cse.boilermatcher.tokens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.BasicParseRunner;

import uk.org.cse.boilermatcher.sedbuk.ModelDescription;

/**
 * Turns a string into a bunch of {@link ModelDescription}s via the
 * {@link #parse(String)} method.
 *
 * @author hinton
 * @since 1.0
 */
public class Tokenizer {

    private BasicParseRunner<ModelDescription> parseRunner;

    /**
     * @since 1.0
     */
    public Tokenizer() {
        final ModelGrammar g = Parboiled.createParser(ModelGrammar.class);
        this.parseRunner = new BasicParseRunner<ModelDescription>(g.Parse());
    }

    /**
     * @since 1.0
     */
    public List<ModelDescription> parse(final String string) {
        if (string == null) {
            return Collections.emptyList();
        }

        final String clean = string.toLowerCase().trim();

        final ArrayList<ModelDescription> result = new ArrayList<ModelDescription>();
        for (final ModelDescription md : parseRunner.run(clean).valueStack) {
            result.add(md);
        }

        Collections.reverse(result);

        return result;
    }
}
