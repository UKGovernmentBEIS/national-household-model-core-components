package uk.org.cse.stockimport.spss;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import uk.org.cse.stockimport.IHomElementBuilder;
import uk.org.cse.stockimport.spss.elementreader.ISpssReader;

public class ReaderBuilderPair<T> {

    private ISpssReader<T> reader;
    private IHomElementBuilder<T> builder;
    private Collection<T> currentDTOs;
    boolean alreadyWroteHeaders = false;

    public static <T> ReaderBuilderPair<T> getReaderBuilderPair(ISpssReader<T> reader, IHomElementBuilder<T> builder) {
        return new ReaderBuilderPair<T>(reader, builder);
    }

    public ReaderBuilderPair(ISpssReader<T> reader, IHomElementBuilder<T> builder) {
        this.reader = reader;
        this.builder = builder;
    }

    public ISpssReader<T> getReader() {
        return reader;
    }

    public IHomElementBuilder<T> getBuilder() {
        return builder;
    }

    public boolean hasNext() {
        return reader.hasNext();
    }

    public void moveNext() {
        currentDTOs = reader.next();
    }

    public Collection<T> currentDTOs() {
        return currentDTOs;
    }

    public void writeChunk(OutputStream outputStream) throws IOException {
        builder.writeChunk(outputStream, currentDTOs, !alreadyWroteHeaders);
        outputStream.flush();
    }
}
