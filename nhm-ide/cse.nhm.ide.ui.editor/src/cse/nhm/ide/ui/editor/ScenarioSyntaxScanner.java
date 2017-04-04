package cse.nhm.ide.ui.editor;

import java.util.Set;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

import cse.nhm.ide.ui.PluginPreferences;

public class ScenarioSyntaxScanner implements ITokenScanner {
	private IDocument doc;
	private int begin;
	private int end;

	private int tokenOffset;
	private int tokenLength;
	boolean head = false;
	private IToken builtinToken;
	private IToken WordToken;
	private IToken keyToken;
	private IToken parenToken;
	private Set<String> keys;
	private IToken defSomethingToken;
	private IToken placeholderToken;
	private Token macroToken;
	
	public ScenarioSyntaxScanner(final Set<String> keys) {
		this.keys = keys;
		builtinToken = themeToken(PluginPreferences.Theme.BUILTIN);
		defSomethingToken = themeToken(PluginPreferences.Theme.CUSTOM);
		keyToken = themeToken(PluginPreferences.Theme.KEY);
		parenToken = themeToken(PluginPreferences.Theme.PAREN);
		placeholderToken = themeToken(PluginPreferences.Theme.PLACEHOLDER);
		macroToken = themeToken(PluginPreferences.Theme.MACRO);
		
		WordToken = Token.UNDEFINED;
	}
	
	private static Token themeToken(final String key) {
		final Color color = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getColorRegistry().get(key);
		return (new Token(new TextAttribute(color)));
	}
	
	enum ScanState {
		START,
		BLANK,
		WORD
	}
	
	@Override
	public void setRange(final IDocument document, int offset, int length) {
		this.doc = document;
		this.begin = offset;
		this.end = offset + length;
		this.tokenOffset = this.begin;
		this.tokenLength = 0;
	}
	
	
	@Override
	public IToken nextToken() {
		try {
			char c = '\0';
			ScanState state = ScanState.START;
			tokenOffset += tokenLength;
			tokenLength = 0;
			loop: while (tokenOffset + tokenLength < end) {
				c = doc.getChar(tokenOffset + tokenLength);
				tokenLength++;

				switch (c) {
				case '(':
				case ')':
				case '[':
				case ']':
				case '{':
				case '}':
					if (tokenLength > 1) tokenLength--;
					break loop;
				case ':':
					if (state != ScanState.START) {
						break loop;
					}
				default:
					if (Character.isWhitespace(c)) {
						if (state == ScanState.WORD) {
							tokenLength--;
							break loop;
						} else {
							state = ScanState.BLANK;
						}
					} else {
						if (state == ScanState.BLANK) {
							tokenLength--;
							break loop;
						} else {
							state = ScanState.WORD;
						}
					}
				}
			}
			
			if (tokenLength > 0) {
				return token(state, c);
			}
		} catch (BadLocationException e) {}
		return Token.EOF;
	}

	private IToken token(ScanState state, char c) {
		try {
			switch (state) {
			case START:
				switch (c) {
				case '(':
				case '[':
				case '{':
				case ')':
				case ']':
				case '}':
					return ptoken(c);
				default:
					return Token.UNDEFINED;
				}
			case WORD:
				if (c == ':') return keyToken;
				try {
					final char c0 = doc.getChar(tokenOffset);
					
					if (c0 == '@') return placeholderToken;
					if (c0 == '#') return defSomethingToken;
					if (c0 == '~') return macroToken;
					
					final String word = doc.get(tokenOffset, tokenLength);
					if (keys.contains(word)) {
						return builtinToken;
					} else if (head) {
						return macroToken;
					}
				} catch (final BadLocationException ble) {}
				return WordToken;
			case BLANK:
			default:
				return Token.UNDEFINED;
			}
		} finally {
			head = c == '(';
		}
	}

	private IToken ptoken(char c) {
		return parenToken;
	}

	@Override
	public int getTokenOffset() {
		return tokenOffset;
	}

	@Override
	public int getTokenLength() {
		return tokenLength;
	}
}
