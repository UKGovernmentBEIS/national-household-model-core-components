package uk.org.cse.boilermatcher.sedbuk;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.pojomatic.annotations.AutoProperty;

/**
 * @since 1.0
 */
@AutoProperty
public class ModelDescription {
    /**
     * @since 1.0
     */
    public enum Type {
		WORD,
		QUALIFIER
	}
	
	public final Type type;
	private Set<String> words = new HashSet<String>();
	
	/**
     * @since 1.0
     */
    public ModelDescription(Type type, String string) {
		this.type = type;
		addWord(string);
	}
	
    /**
     * @since 1.0
     */
    public void addWord(final String s) {
		this.words.add(s.trim().replaceAll(",", ""));
	}
	
    /**
     * @since 1.0
     */
    public Collection<String> getWords() {
		return words;
	}
	
	@Override
	public String toString() {
		return type+"("+words+")";
	}
}
