package uk.org.cse.nhm.stockimport.simple.spss;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.base.CharMatcher;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import au.com.bytecode.opencsv.CSVWriter;
import uk.org.cse.nhm.spss.SavEntry;
import uk.org.cse.nhm.spss.SavInputStream;
import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.SavVariableType;
import uk.org.cse.nhm.spss.impl.SavInputStreamImpl;

public class AdditionalPropertiesTransfer {
    static class LoadedSavFile {
        public final String[] header;
        public final Map<String, String[]> rows;

        public LoadedSavFile(final String[] header, final Map<String, String[]> rows) {
            super();
            this.header = header;
            this.rows = rows;
        }

        public static Optional<LoadedSavFile> load(final Path file) {
            try {
                final SavInputStream input = new SavInputStreamImpl(Files.newInputStream(file), true);
                final HashSet<String> seenCodes = new HashSet<>();

                final SavMetadata meta = input.getMetadata();
                final SavVariable aacodeVariable = meta.getVariableIgnoreCase("aacode");

                final ImmutableMap.Builder<String, String[]> rows = ImmutableMap.builder();

                final ImmutableList.Builder<SavVariable> variables = ImmutableList.builder();

                for (final SavVariable var : meta.getVariables()) {
                    if ((var != aacodeVariable)
                            && ((var.getType() == SavVariableType.NUMBER) || (var.getType() == SavVariableType.STRING))) {
                        variables.add(var);
                    }
                }
                final ImmutableList<SavVariable> variablesToGet = variables.build();

                while (input.hasNext()) {
                    final SavEntry next = input.next();
                    final String code = next.getValue(aacodeVariable, String.class);

                    if (seenCodes.contains(code)) {
                        return Optional.absent();
                    }

                    seenCodes.add(code);

                    final String[] row = new String[variablesToGet.size()];
                    int i = 0;
                    for (final SavVariable v : variablesToGet) {
                        row[i++] = format(next, v);
                    }
                    rows.put(code, row);
                }

                final String[] header = new String[variablesToGet.size()];
                int i = 0;
                for (final SavVariable v : variablesToGet) {
                    header[i++] = v.getName().toLowerCase();
                }

                return Optional.of(new LoadedSavFile(header, rows.build()));
            } catch (final IOException e) {
                e.printStackTrace();
                return Optional.absent();
            }
        }

        private static String format(final SavEntry entry, final SavVariable variable) {
            switch (variable.getType()) {
                case NUMBER:
                    final Double value = entry.getValue(variable, Double.class);
                    if (variable.isMissingValue(value)) {
                        return "";
                    } else if (variable.isRestricted()) {
                        return variable.getValueLabel(value);
                    } else {
                        return value.toString();
                    }
                case STRING:
                    return stripNewlines(entry.getValue(variable, String.class));
                case STRING_CONTINUATION:
                default:
                    throw new RuntimeException(String.format(
                            "Unknown variable type %s, please add a new case in the program.", variable.getName()));
            }
        }

        private static String stripNewlines(final String value) {
            return CharMatcher.ASCII.retainFrom(value).replace('\n', ' ').replace('\r', ' ');
        }

        public void putFields(final String code, final List<String> header2) {
            if ((rows == null) || (code == null) || (header2 == null)) {
                System.out.println();
            }

            if (rows.containsKey(code)) {
                try {
                    // Hack need to find where the null values are being inserted further up the stack
                    String[] rowToAdd = rows.get(code);
                    int rowLength = rowToAdd.length;
                    String[] withoutNulls = new String[rows.get(code).length];
                    for (int ct = 0; ct < rowLength; ct++) {
                        if (rowToAdd[ct] == null) {
                            withoutNulls[ct] = "";
                        } else {
                            withoutNulls[ct] = rowToAdd[ct];
                        }

                    }
                    header2.addAll(ImmutableList.copyOf(withoutNulls));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                for (@SuppressWarnings("unused")
                final String ignore : header) {
                    header2.add("");
                }
            }
        }

    }

    public static void transfer(final Path directory, final Path outputFile) throws IOException {
        final LinkedList<LoadedSavFile> loaded = new LinkedList<>();

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(".sav")) {
                    loaded.addAll(LoadedSavFile.load(file).asSet());
                }
                return FileVisitResult.CONTINUE;
            }
        });

        final List<String> header = new ArrayList<String>();

        final SortedSet<String> allCodes = new TreeSet<>();

        header.add("aacode");

        for (final LoadedSavFile lsf : loaded) {
            header.addAll(ImmutableList.copyOf(lsf.header));
            allCodes.addAll(lsf.rows.keySet());
        }

        try (final CSVWriter writer = new CSVWriter(Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8))) {
            writer.writeNext(header.toArray(new String[header.size()]));
            header.clear();
            for (final String code : allCodes) {
                header.add(code);
                try {
                    for (final LoadedSavFile lsf : loaded) {
                        lsf.putFields(code, header);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writer.writeNext(header.toArray(new String[header.size()]));
                header.clear();
            }
        }
    }
}
