package uk.org.cse.nhm.clitools;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class LogHelper extends AppenderSkeleton {

    Thread executionThread;
    private Level level;

    public LogHelper() {

    }

    public void install() {
        install(Thread.currentThread());
    }

    public void install(final Thread executionThread) {
        this.executionThread = executionThread;
        Logger.getRootLogger().addAppender(this);
        level = Logger.getRootLogger().getLevel();
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    public void remove() {
        if (executionThread != null) {
            Logger.getRootLogger().removeAppender(this);
            if (level != null) {
                Logger.getRootLogger().setLevel(level);
            }
            executionThread = null;
        }
    }

    protected void log(final Level level2, final String msg) {

    }

    @Override
    protected void append(final LoggingEvent event) {
        if (Thread.currentThread() == executionThread) {
            // do the append on the thing
            final String msg = event.getRenderedMessage();
            log(event.getLevel(), msg);
        }
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    public void close() {
        remove();
    }
}
