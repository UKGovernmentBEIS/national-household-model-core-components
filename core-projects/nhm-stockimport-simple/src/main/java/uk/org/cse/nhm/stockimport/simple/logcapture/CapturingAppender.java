package uk.org.cse.nhm.stockimport.simple.logcapture;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

public class CapturingAppender extends AppenderSkeleton {

    public interface IHandler {

        public void handle(final String code, final String warning);
    }

    boolean plugged = false;
    private final IHandler handler;
    private String code;

    public CapturingAppender(final IHandler handler) {
        this.handler = handler;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected void append(final LoggingEvent event) {
        if (plugged) {
            handler.handle(code, event.getRenderedMessage());
        }
    }

    public void unplug() {
        if (plugged) {
            Logger.getRootLogger().removeAppender(this);
            plugged = false;
        }
    }

    public void plug(final String code) {
        this.code = code;
        if (!plugged) {
            Logger.getRootLogger().addAppender(this);
            plugged = true;
        }
    }
}
