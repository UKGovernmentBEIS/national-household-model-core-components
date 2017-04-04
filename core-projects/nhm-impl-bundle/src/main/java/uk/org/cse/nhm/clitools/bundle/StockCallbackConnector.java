package uk.org.cse.nhm.clitools.bundle;

import java.nio.file.Path;

import org.apache.log4j.Level;

import uk.org.cse.nhm.bundle.api.IStockImportCallback;
import uk.org.cse.nhm.clitools.LogHelper;
import uk.org.cse.nhm.stockimport.simple.ImportErrorHandler;


class StockCallbackConnector extends LogHelper implements ImportErrorHandler {
    private final IStockImportCallback cb;

    public StockCallbackConnector(final IStockImportCallback cb) {
        this.cb = cb;
    }

    @Override
    public void update(final String message) {
        cb.log("INFO", message);
    }

    @Override
    public void handle(final Path file, final int line, final String newParam, final String error) {
        cb.log("ERROR", String.format("%s:%d (%s) - %s", file, line, newParam, error));
    }

    @Override
    protected void log(final Level level2, final String msg) {
        cb.log(String.valueOf(level2), msg);
    }
}
