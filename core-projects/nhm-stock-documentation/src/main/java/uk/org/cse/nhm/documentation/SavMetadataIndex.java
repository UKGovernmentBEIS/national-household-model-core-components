package uk.org.cse.nhm.documentation;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.collect.ImmutableSortedSet;

import uk.org.cse.nhm.spss.SavInputStream;
import uk.org.cse.nhm.spss.SavMetadata;
import uk.org.cse.nhm.spss.SavVariable;
import uk.org.cse.nhm.spss.SavVariableType;
import uk.org.cse.nhm.spss.impl.SavInputStreamImpl;

/**
 * Utility for loading an index of sav metadata from some sav files.
 *
 * @author hinton
 *
 */
public class SavMetadataIndex {

    final Map<String, SavVariable> variableIndex = new HashMap<>();
    final Map<String, String> fileIndex = new HashMap<>();

    public SavMetadataIndex(final String zipPath) throws IOException {
        try (final ZipFile zip = new ZipFile(zipPath)) {
            final Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".sav")) {
                    final SavInputStream stream = new SavInputStreamImpl(zip.getInputStream(entry), false);
                    final SavMetadata meta = stream.getMetadata();
                    for (final SavVariable v : meta.getVariables()) {
                        if (v.getType() == SavVariableType.STRING_CONTINUATION) {
                            continue;
                        }
                        variableIndex.put(v.getName().toLowerCase(), v);
                        fileIndex.put(v.getName().toLowerCase(), entry.getName());
                    }
                }
            }
        }
    }

    public String getFileContaining(final String variableName) {
        return fileIndex.get(variableName.toLowerCase());
    }

    public SavVariable getVariable(final String variableName) {
        if (variableIndex.containsKey(variableName.toLowerCase())) {
            return variableIndex.get(variableName.toLowerCase());
        } else {
            throw new RuntimeException("Missing metadata for "
                    + variableName + ", only have "
                    + ImmutableSortedSet.copyOf(variableIndex.keySet()));
        }
    }
}
