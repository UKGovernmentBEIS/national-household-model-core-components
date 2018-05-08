package uk.org.cse.boilermatcher.tokens;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

import uk.org.cse.boilermatcher.sedbuk.ModelDescription;
import uk.org.cse.boilermatcher.sedbuk.ModelDescription.Type;

/**
 * @since 1.0
 */
@BuildParseTree
public class ModelGrammar extends BaseParser<ModelDescription> {

    /**
     * @since 1.0
     */
    public Rule Parse() {
        return OneOrMore(Element());
    }

    /**
     * @since 1.0
     */
    public Rule Element() {
        return FirstOf(
                Identifiers(),
                Sequence(Word(), Optional(Sep())));
    }

    /**
     * @since 1.0
     */
    public Rule Identifiers() {
        return OneOrMore(Identifier(), Optional(Sep()));
    }

    /**
     * @since 1.0
     */
    public Rule Identifier() {
        return Sequence(
                FirstOf(
                        Sequence(
                                ZeroOrMore(Letter()),
                                Digit(),
                                ZeroOrMore(FirstOf(Digit(), Letter()))
                        ),
                        Sequence(
                                Letter(),
                                Optional(Letter()),
                                Optional(Letter()),
                                FirstOf(Sep(), EOI)
                        )
                ),
                push(new ModelDescription(Type.QUALIFIER, match())));
    }

    /**
     * @since 1.0
     */
    public Rule Word() {
        return Sequence(OneOrMore(Letter()), push(new ModelDescription(Type.WORD, match())));
    }

    /**
     * @since 1.0
     */
    public Rule Sep() {
        return OneOrMore(AnyOf(", "));
    }

    /**
     * @since 1.0
     */
    public Rule Digit() {
        return FirstOf(AnyOf("/-()*.+"), CharRange('0', '9'));
    }

    /**
     * @since 1.0
     */
    public Rule Letter() {
        return CharRange('a', 'z');
    }
}
