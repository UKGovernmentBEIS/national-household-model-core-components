package cse.nhm.ide.ui.editor.structure;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import cse.nhm.ide.ui.reader.Expr;
import cse.nhm.ide.ui.reader.Form;

public class Tabulator {
	public static String tabulate(final String in) {
		try {
			return tabulateTrimmed(trimLines(in));
		} catch (IOException e) {
			return in;
		}
	}

	private static String tabulateTrimmed(final String trimLines) throws IOException {
		final List<Form> forms = Expr.readAll(new StringReader(trimLines), false);
		
		final int[][] offsets = offsetColumns(trimLines);
		final int[] columns = offsets[0];
		final int[] lines = offsets[1];
		
		int beforeColumn = 0;
		final List<Integer> maximumWidths = new ArrayList<>();
		
		for (final Form form : forms) {
			if (form instanceof Expr) {
				final Expr e = (Expr) form;
				
				// it is a row of some kind
				int column = 0;
				for (final Form f : e.children) {
					if (maximumWidths.size() <= column)
						maximumWidths.add(0);

					final int width = columns[(int) f.eoffset] - columns[(int) f.offset];
					maximumWidths.set(column, Math.max(maximumWidths.get(column), width));
					if (column == 0)
						beforeColumn = Math.max(beforeColumn, columns[(int) f.offset]);

					column++;
				}
				
			}
		}
		// OK, so now we know how wide each column needs to be, we need to do something on each line to pack out the columns to that width
		// this should just be a matter of repeating the previous loop, and inserting a bunch of carefully-calculated spaces into the string.
		
		final List<Integer> columnStarts = new ArrayList<Integer>();
		int acc = beforeColumn;
		for (int w : maximumWidths) {
			columnStarts.add(acc);
			acc += w + 1;
		}
		
		final StringBuffer result = new StringBuffer();
		int lineOffset = 0;
		int line = 0;
		int lastFormOffset = 0;
		for (final Form form : forms) {
			if (form instanceof Expr) {
				final Expr e = (Expr) form;
				int column = 0;
				for (final Form f : e.children) {
					// we need to see how much the form needs shifting inward,
					// and then
					// we need to inject that many spaces at the start of each
					// line that it touches.
					int start = columnStarts.get(column);

					column++;

					final int formLine = lines[(int) f.offset];
					if (formLine != line) {
						line = formLine;
						lineOffset = 0;
					}

					final int formColumn = columns[(int) f.offset];
					final int offsetFormColumn = formColumn + lineOffset;

					final int delta = start - offsetFormColumn;
					if (delta < 0) {
						// need to delete some space
						result.append(trimLines.substring(lastFormOffset, (int) f.offset + delta));
					} else {
						// need to inject delta many spaces at offsetFormColumn.
						final String space = Indenter.Tabstops.rep(delta);
						// also need to append the space which was after the
						// previous form
						result.append(trimLines.substring(lastFormOffset, (int) f.offset));
						// now our spaces
						result.append(space);
					}

					// the actual form body
					result.append(trimLines.substring((int) f.offset, (int) f.eoffset));
					lineOffset += delta;
					lastFormOffset = (int) f.eoffset;
				}
			}
			
		}
		
		result.append(trimLines.substring(lastFormOffset, trimLines.length()));
		
		return result.toString();
	}
	
	private static int[][] offsetColumns(final String trimLines) {
		int column = 0;
		int[][] answer = new int[2][trimLines.length()];
		int line = 0;
		for (int i = 0; i<trimLines.length(); i++) {
			answer[0][i] = column;
			answer[1][i] = line;
			column++;
			char charAt = trimLines.charAt(i);
			if (isNewlineThing(charAt)) {
				column = 0;
				line++;
			}
		}
		return answer;
	}

	public static String trimLines(String in) {
		final String[] lines = in.split("[\\r\\n]+");
		final StringBuffer out = new StringBuffer();
		for (final String l : lines) {
			final String t = l.trim();
			if (!t.isEmpty()) {
				if (out.length() > 0) out.append("\n");
				out.append(t);
			}
		}
		return out.toString();
	}
	
	public static void tabulate(final IDocument document, final int offset, final int length) throws BadLocationException {
		final String text = document.get(offset, length);
		
		final String tabulatedText = tabulate(text);
		
		document.replace(offset, length, tabulatedText);
	}

	private static boolean isNewlineThing(char charAt) {
		return charAt == '\r' || charAt == '\n';
	}
}
