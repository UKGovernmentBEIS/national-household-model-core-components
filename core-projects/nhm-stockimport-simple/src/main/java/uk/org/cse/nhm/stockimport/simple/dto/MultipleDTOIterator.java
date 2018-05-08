package uk.org.cse.nhm.stockimport.simple.dto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.stockimport.simple.ImportErrorHandler;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.schema.IDTOReader;
import uk.org.cse.stockimport.domain.schema.IKeyValue;
import uk.org.cse.stockimport.domain.schema.InvalidHeaderException;
import uk.org.cse.stockimport.domain.schema.InvalidRowException;

public class MultipleDTOIterator implements Iterator<List<IBasicDTO>>, AutoCloseable {

    private final List<IDTOReader<? extends IBasicDTO>> readers;
    private final List<GroupedKeyValueIterator> iterators;
    private final ImportErrorHandler errors;
    private List<String> lastKeys = new LinkedList<>();
    int lines = 1;

    public final boolean validationFailed;

    public MultipleDTOIterator(final Path baseDirectory, final List<IDTOReader<? extends IBasicDTO>> allReaders, final ImportErrorHandler errors) {
        this.errors = errors;
        final LinkedList<IDTOReader<? extends IBasicDTO>> readers = new LinkedList<>();
        final LinkedList<GroupedKeyValueIterator> iterators = new LinkedList<>();

        boolean badHeaders = false;

        for (final IDTOReader<? extends IBasicDTO> r : allReaders) {
            final Path file = baseDirectory.resolve(r.getFilename() + ".csv");
            if (Files.exists(file)) {
                try {
                    final GroupedKeyValueIterator it = new GroupedKeyValueIterator(file, "aacode");

                    r.checkHeaders(ImmutableList.copyOf(it.getHeader()));

                    iterators.add(it);
                    readers.add(r);
                    lastKeys.add("");
                } catch (final IOException e) {
                    errors.handle(file, 0, "unknown", "IO Error: " + e.getMessage());
                } catch (final InvalidHeaderException e) {
                    errors.handle(file, 1, "unknown", "Invalid headers: " + e.getMessage());
                    badHeaders = true;
                }
            } else {
                errors.handle(file, 0, "unknown", "Input DTO file does not exist");
            }
        }

        this.readers = readers;
        this.iterators = iterators;

        if (badHeaders) {
            this.readers.clear();
            this.iterators.clear();
            this.lastKeys.clear();
            errors.handle(baseDirectory, 0, "n/a", "Failing early due to errors in headers");
        }

        this.validationFailed = badHeaders;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        for (final GroupedKeyValueIterator iter : iterators) {
            try {
                iter.close();
            } catch (final Exception ex) {
            }
        }
    }

    @Override
    public boolean hasNext() {
        final Iterator<GroupedKeyValueIterator> iterator = iterators.iterator();
        final Iterator<IDTOReader<? extends IBasicDTO>> iterator2 = readers.iterator();
        final Iterator<String> lki = lastKeys.iterator();

        boolean hasNext = false;
        while (iterator.hasNext()) {
            final GroupedKeyValueIterator next = iterator.next();
            final IDTOReader<? extends IBasicDTO> next2 = iterator2.next();
            final String next3 = lki.next();
            if (!next.hasNext()) {
                System.out.println(next2.getFilename() + " exhausted at " + next3 + " (" + lines + ")");
                iterator.remove();
                iterator2.remove();
                lki.remove();
            } else {
                hasNext = true;
            }
        }

        return hasNext;
    }

    @Override
    public List<IBasicDTO> next() {
        // for each iterator, aacode is the key we will get if we read
        lines++;
        final List<String> keys = new LinkedList<>();

        for (final GroupedKeyValueIterator i : iterators) {
            keys.add(i.getKey());
        }

        final String firstCode = getMinimumAndCheckAscending(keys);

        final ImmutableList.Builder<IBasicDTO> output = ImmutableList.builder();
        int index = 0;
        for (final GroupedKeyValueIterator i : iterators) {
            if (firstCode.equals(i.getKey())) {
                final IDTOReader<? extends IBasicDTO> reader = readers.get(index);
                final List<IKeyValue> inputs = i.next();
                for (final IKeyValue kv : inputs) {
                    try {
                        final IBasicDTO read = reader.read(kv);

                        final List<String> validation = read.validate();

                        if (validation.isEmpty()) {
                            output.add(read);
                        } else {
                            for (final String s : validation) {
                                errors.handle(i.file, i.line, read.getAacode(), s);
                            }
                        }
                    } catch (final InvalidRowException e) {
                        // we should bail and goto next here?
                        errors.handle(kv.file(), kv.line(), kv.get("aacode"), e.getMessage());
                    }
                }
            }

            index++;
        }

        return output.build();
    }

    private String getMinimumAndCheckAscending(final List<String> keys) {
        final String min = Collections.min(Collections2.filter(keys, Predicates.notNull()));

        if (lastKeys != null) {
            for (int i = 0; i < lastKeys.size(); i++) {
                if (lastKeys.get(i).compareTo(keys.get(i)) > 0) {
                    throw new IllegalArgumentException(iterators.get(i).file + " is not well-sorted; "
                            + lastKeys.get(i) + " vs " + keys.get(i));
                }
            }
        }

        lastKeys = keys;
        return min;
    }

}
