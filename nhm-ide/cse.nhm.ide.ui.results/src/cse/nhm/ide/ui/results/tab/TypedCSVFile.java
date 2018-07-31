package cse.nhm.ide.ui.results.tab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;

import cse.nhm.ide.ui.results.NHMResultViewPlugin;

/**
 * A class which can read a CSV into a typed table, a bit like read.table in R.
 *
 * At the moment, this is a memory-intensive horror, because of the issues around boxing.
 */
public class TypedCSVFile {
	static class ColType implements Comparator<Object> {
		enum T {
			DATE,
			DOUBLE,
			INTEGER,
			NUMBER,
			BOOL,
			STRING,
			MIXED,
			EMPTY;

			public T merge(final T type) {
				if (type == null || type == T.EMPTY) return this;
				if (type == NUMBER && (this == INTEGER || this == DOUBLE)) return type;
				if (this == NUMBER && (type == INTEGER || type == DOUBLE)) return this;
				if (type != this) return T.MIXED;
				return this;
			}
		}

		private double min = Double.MAX_VALUE;
		private double max = Double.MIN_VALUE;
		Set<String> values = new HashSet<>();
		public T type = T.EMPTY;

		public Set<String> values() {
			return values;
		}

		public boolean categorical() {
			return !continuous();
		}

		public boolean continuous() {
			return date() || number();
		}

		public boolean date() {
			return type == T.DATE;
		}

		public boolean number() {
			return type == T.NUMBER || type == T.DOUBLE || type == T.INTEGER;
		}

		public void absorb(final Object o) {
			if (o == null) return;
			type = typeof(o).merge(type);
			if (type == T.STRING) {
				values.add(String.valueOf(o));
			} else {
				values.clear();
			}

			if (o instanceof Number) {
				min = Math.min(min, ((Number) o).doubleValue());
				max = Math.max(max, ((Number) o).doubleValue());
			}
		}

		private T typeof(final Object o) {
			if (o instanceof Date) return T.DATE;
			if (o instanceof Integer) return T.INTEGER;
			if (o instanceof Double) return T.DOUBLE;
			if (o instanceof Integer) return T.NUMBER;
			if (o instanceof Boolean) return T.BOOL;
			return T.STRING;
		}

		@Override
		public int compare(Object a, Object b) {
			if (a == null && b == null) {
				return 0;
			} else if (a == null) {
				return -1;
			} else if (b == null) {
				return 1;
			}
			switch (type) {
			case DOUBLE:
			case INTEGER:
				if (a instanceof Number &&
				    b instanceof Number) {
					return Double.compare(((Number)a).doubleValue(), ((Number)b).doubleValue());
				} else if (a instanceof Number) {
					return 1;
				} else if (b instanceof Number) {
					return -1;
				} else {
					return 0;
				}
			case DATE:
				if (a instanceof Date &&
				    b instanceof Date) {
					return ((Date)a).compareTo((Date)b);
				} else if (a instanceof Date) {
					return 1;
				} else if (b instanceof Date) {
					return -1;
				} else {
					return 0;
				}
			case BOOL:
				if (a instanceof Boolean &&
				    b instanceof Boolean) {
					return ((Boolean)a).compareTo((Boolean)b);
				} else if (a instanceof Boolean) {
					return 1;
				} else if (b instanceof Boolean) {
					return -1;
				} else {
					return 0;
				}
			case STRING:
			case MIXED:
			default:
				return String.valueOf(a)
					.compareTo(String.valueOf(b));
			}
		}
	}

	public final ColType[] types;
	public final String[] header;
	public final List<Object[]> rows;

	static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
	static final Pattern pat = Pattern.compile("\\d{4}/\\d{2}/\\d{2}");

	static TypedCSVFile load(final IURIEditorInput input) {
		final TypedCSVFile[] result = new TypedCSVFile[1];
		final IWorkbench wb = PlatformUI.getWorkbench();
		final IProgressService ps = wb.getProgressService();

		try {
			ps.busyCursorWhile(new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor pm) throws InterruptedException {
					try {
						result[0] = new TypedCSVFile(input, pm);
					} catch (CoreException | IOException e) {
						// silently fail? wargh.
						NHMResultViewPlugin.getDefault().logException(e);
					}
				}
			});
		} catch (final InvocationTargetException e) {
		} catch (final InterruptedException e) {
		}
		return result[0];
	}

	public TypedCSVFile(final IURIEditorInput input, final IProgressMonitor monitor) throws CoreException, IOException, InterruptedException {
		final IFileStore store = EFS.getStore(input.getURI());
		monitor.beginTask("Loading " + store.getName(), 100);
		final int length = (int) store.fetchInfo().getLength();
		String[] header = null;
		ColType[] types = null;
		final ArrayList<Object[]> rows = new ArrayList<>();
		final Interner<Object> strings = Interners.newStrongInterner();
		if (monitor.isCanceled()) throw new InterruptedException();
		try (final CSV.Reader reader = CSV.trimmedReader(
				new BufferedReader(
						new InputStreamReader(store.openInputStream(EFS.NONE, new SubProgressMonitor(monitor, 10)),
								StandardCharsets.UTF_8)))) {
			final SubProgressMonitor sub = new SubProgressMonitor(monitor, 90);
			sub.beginTask("Reading data", length);
			String[] row = null;
			while ((row = reader.read()) != null) {
				if (monitor.isCanceled()) throw new InterruptedException();

				if (header == null) {
					header = row;
					types = new ColType[header.length];
					for (int i = 0; i<types.length; i++) {
						types[i] = new ColType();
					}
				} else {
					final Object[] values = infer(strings, row, header.length);
					rows.add(values);
					for (int i = 0; i<Math.min(values.length, header.length); i++) {
						types[i].absorb(values[i]);
					}
				}
				sub.worked(reader.lineLength());
			}
		}

		this.types = types;
		this.header = header;
		this.rows = rows;

		monitor.done();
	}

	private Object[] infer(final Interner<Object> strings, final String[] row, final int length) {
		final Object[] out = new Object[length];
		for (int i = 0; i<out.length && i < row.length; i++) {
			final Object result = read(strings, row[i], row[i]);
			out[i] = result;
		}
		return out;
	}

	/**
	 * Almost certainly horrendously slow; parseDouble is synchronized for some reason until java 8, so if you open two csv files at once
	 * it will really fall down.
	 *
	 * @param strings
	 * @param string
	 * @param row
	 * @return
	 */
	private Object read(final Interner<Object> strings, final String string, final String row) {
		if ("n/a".equals(string)) return null;
		final Integer i = Ints.tryParse(string);
		if (i != null) {
			return strings.intern(i);
		}
		final Double d = Doubles.tryParse(string);
		if (d != null) {
			return strings.intern(d);
		}
		if ("true".equalsIgnoreCase(string)) return Boolean.TRUE;
		if ("false".equalsIgnoreCase(string)) return Boolean.FALSE;
		if (pat.matcher(string).matches()) {
			try {
				return strings.intern(fmt.parse(string));
			} catch (final ParseException pe) {}
		}
		return strings.intern(string);
	}
}
