package uk.org.cse.boilermatcher.sedbuk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;


/**
 * @since 1.0
 */
public class Sedbuk implements ISedbuk {
	private static final int DATABASE_REVISION_SPECIFIER = 1;
	private static final String CONTROL_PREFIX = "$";
	private static final String COMMENT_PREFIX = "#";
	private static final int INVALID_CONTROL_CODE = -1;
	private static final int SPECIAL = 998;
	private static final int EOF = 999;
	
	private int version;
	
	private final Map<Integer, ITable> tablesByCode = new HashMap<Integer, ITable>();
	
	/**
     * @since 1.0
     */
    public Sedbuk() throws IOException {
		this(new InputStreamReader(Sedbuk.class.getResourceAsStream("/sedbuk/pcdf2009.dat")));
	}
	
    /**
     * @since 1.0
     */
    public Sedbuk(final Reader reader) throws IOException {
		load(new BufferedReader(reader));
	}
    
    private static ITable createTable(final int id, final int format, final int revision, final int y, final int m, final int d) {
		switch (id) {
		case IBoilerTable.ID:
			if (format == IBoilerTable.CURRENT_FORMAT) {
				return AutoTable.forClass(AbstractBoilerTable.class, IBoilerTable.class);
			}
		}
		return new BaseTable();
	}
	
	private void load(final BufferedReader br) throws IOException {
		String thisLine = null;
		ITable currentTable = null;
		int lineCounter = 0;
		try {
			while ((thisLine = br.readLine()) != null) {
				lineCounter++;
				final String trimmed = thisLine.trim();
				if (trimmed.startsWith(COMMENT_PREFIX)) {
				} else if (trimmed.startsWith(CONTROL_PREFIX)) {
					final int[] codes;
					
					if (trimmed.length() <= CONTROL_PREFIX.length()) {
						codes = new int[] {INVALID_CONTROL_CODE};
					} else {
						// process control line
						final String[] fragments = 
								trimmed.substring(CONTROL_PREFIX.length()).split(",");
						
						codes = new int[fragments.length];
						
						for (int fragmentIndex = 0; fragmentIndex < fragments.length; fragmentIndex++) {
							try {
								codes[fragmentIndex] = Integer.parseInt(fragments[fragmentIndex]);
							} catch (final NumberFormatException exception) {
								codes[0] = INVALID_CONTROL_CODE;
							}
						}
					}
					
					switch (codes[0]) {
					case INVALID_CONTROL_CODE:
						break;
					case DATABASE_REVISION_SPECIFIER:
						// this is the file format specifier
						if (codes.length < 5) {
							throw new SedbukFormatException("Database revision specifier does not have enough fields");
						}
						this.version = codes[1];
						break;
					case SPECIAL:
						throw new SedbukFormatException("Special control code encountered; this should never occur in a released version of SEDBUK");
					case EOF:
						currentTable = null;
						return;
					default:
						if (codes.length != 6) {
							throw new SedbukFormatException("Table with unexpected number of control code fragments");
						} else {
							currentTable = createTable(codes[0], codes[1], codes[2], codes[3], codes[4], codes[5]);
							if (currentTable != null) {
								tablesByCode.put(codes[0], currentTable);
							}
						}
					}
				} else {
					if (currentTable == null) {
						throw new SedbukFormatException("Table statement expected before any data lines");
					} else {
						currentTable.handle(thisLine);
					}
				}
			}
		} catch (final SedbukFormatException formatException) {
			formatException.setLineNumber(lineCounter);
			formatException.setInputLine(thisLine);
			throw formatException;
		}
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.sedbuk.impl.ISedbuk#hasTable(int)
	 */
	@Override
	public boolean hasTable(final int tableCode) {
		return tablesByCode.containsKey(tableCode);
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.sedbuk.impl.ISedbuk#getRevision()
	 */
	@Override
	public int getRevision() {
		return version;
	}
	
	@Override
	public String toString() {
		return "SEDBUK version " + version + ", tables = " + tablesByCode.keySet();
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.sedbuk.impl.ISedbuk#getTable(int)
	 */
	@Override
	public ITable getTable(final int i) {
		return tablesByCode.get(i);
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.stockimport.sedbuk.impl.ISedbuk#getTable(java.lang.Class, int)
	 */
	@Override
	public <T extends ITable> T getTable(final Class<T> tableClass, final int i) {
		return tableClass.cast(getTable(i));
	}
}
