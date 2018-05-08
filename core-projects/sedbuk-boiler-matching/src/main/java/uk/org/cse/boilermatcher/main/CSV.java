package uk.org.cse.boilermatcher.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * I have yanked this from elsewhere (another part of the NHM) because it seems
 * small enough.
 *
 * Turns out that opencsv has a fault which makes it not work on some CSV files;
 * rather than getting yet another CSV library and fixing things and then
 * discovering where it has a fault, I (tom) would rather just write an API
 * compatible thing for this purpose
 *
 * This utility does not support multiline fields; otherwise it will work fine
 * on anything.
 *
 * It implements excel-style CSV, so:
 *
 * a) cells are separated by commas b) commas are escaped by quoting c) quotes
 * are escaped by double-quoting
 *
 * Slightly beyond excel's behaviour, it will concatenate strings within cells,
 * so you can write "something something" "something", asdf, which will parse to
 * ["something something something", "asdf"]
 *
 * Parsed cells are trimmed if you ask
 *
 */
public class CSV {

    enum ParseState {
        NORMAL,
        QUOTED,
        ESCAPED
    }

    public static char guessSeparator(final String row) {
        final String[] comma = parse(row, false, ',');
        final String[] tab = parse(row, false, '\t');
        if (comma.length >= tab.length) {
            return ',';
        } else {
            return '\t';
        }
    }

    public static String[] parse(final String row, final boolean trim, final char separator) {
        ParseState state = ParseState.NORMAL;

        final ImmutableList.Builder<String> b = ImmutableList.builder();
        final StringBuffer field = new StringBuffer();

        for (int i = 0; i < row.length(); i++) {
            final char c = row.charAt(i);
            switch (state) {
                case ESCAPED:
                    if (c == '"') {
                        field.append(c);
                        state = ParseState.QUOTED;
                        break;
                    } else {
                        state = ParseState.NORMAL;
                        // FALLTHROUGH TO NORMAL
                    }
                case NORMAL:
                    if (c == '"') {
                        state = ParseState.QUOTED;
                    } else if (c == separator) {
                        b.add(trim ? field.toString().trim() : field.toString());
                        field.setLength(0);
                    } else {
                        field.append(c);
                    }
                    break;
                case QUOTED:
                    if (c == '"') {
                        state = ParseState.ESCAPED;
                    } else {
                        field.append(c);
                    }
                    break;

            }
        }

        if (state == ParseState.QUOTED) {
            throw new IllegalArgumentException(String.format("unterminated quote in csv; input = '%s', parsed = '%s', remainder = '%s', state = %s",
                    row,
                    b.build(),
                    field,
                    state));
        }

        b.add(trim ? field.toString().trim() : field.toString());

        final ImmutableList<String> build = b.build();
        return build.toArray(new String[build.size()]);
    }

    public static String format(final String[] row) {
        final StringBuffer out = new StringBuffer();
        boolean notFirst = false;
        for (final String s : row) {
            if (notFirst) {
                out.append(",");
            }
            notFirst = true;

            out.append(escape(s));
        }
        return out.toString();
    }

    private static String escape(final String s) {
        if (s.contains("\"") || s.contains(",")) {
            return '"' + s.replace("\"", "\"\"") + '"';
        } else {
            return s;
        }
    }

    public static Reader reader(final Path path, final boolean trim) throws IOException {
        final BOMInputStream stream = new BOMInputStream(Files.newInputStream(path));
        final String charsetName = stream.getBOMCharsetName();
        final BufferedReader r = new BufferedReader(new InputStreamReader(stream,
                charsetName == null
                        ? "UTF-8" : charsetName));
        return trim ? trimmedReader(r) : reader(r);
    }

    public static Reader reader(final BufferedReader br) {
        return new Reader(br, false);
    }

    public static Reader trimmedReader(final BufferedReader br) {
        return new Reader(br, true);
    }

    public static Writer writer(final BufferedWriter bw) {
        return new Writer(bw);
    }

    public static class Reader implements AutoCloseable {

        private final BufferedReader reader;
        private int line = 1;
        private final boolean trim;
        private char separator = ' ';
        private String[] header = null;

        Reader(final BufferedReader reader, final boolean trim) {
            super();
            this.reader = reader;
            this.trim = trim;
        }

        public String[] read() throws IOException {
            final String line = reader.readLine();
            if (line == null) {
                return null;
            }

            if (separator == ' ') {
                separator = guessSeparator(line);
            }

            try {
                final String[] result = parse(line, trim, separator);
                if (header == null) {
                    header = result;
                }
                return result;
            } catch (final IllegalArgumentException iae) {
                throw new IOException("line " + this.line + ": " + iae.getMessage());
            } finally {
                this.line++;
            }
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }

        public Iterable<Map<String, String>> maps() throws IOException {
            if (header == null) {
                read();
            }
            final String[] first = read();

            return new Iterable<Map<String, String>>() {
                @Override
                public Iterator<Map<String, String>> iterator() {
                    return new Iterator<Map<String, String>>() {
                        String[] next = first;

                        @Override
                        public boolean hasNext() {
                            return header != null && next != null;
                        }

                        @Override
                        public Map<String, String> next() {
                            String[] out = next;
                            try {
                                next = read();
                            } catch (IOException e) {
                                throw new RuntimeException(e.getMessage(), e);
                            }
                            return zip(out);
                        }

                        @Override
                        public void remove() {
                        }

                        private Map<String, String> zip(final String[] row) {
                            final ImmutableMap.Builder<String, String> b = ImmutableMap.builder();

                            for (int i = 0; i < header.length && i < row.length; i++) {
                                b.put(header[i].toLowerCase(), row[i]);
                            }

                            return b.build();
                        }

                    };
                }
            };
        }
    }

    public static class Writer implements AutoCloseable {

        private final BufferedWriter writer;

        Writer(final BufferedWriter writer) {
            super();
            this.writer = writer;
        }

        public void write(final String... row) throws IOException {
            writer.write(format(row));
            writer.write("\n");
        }

        @Override
        public void close() throws IOException {
            writer.close();
        }
    }
}
