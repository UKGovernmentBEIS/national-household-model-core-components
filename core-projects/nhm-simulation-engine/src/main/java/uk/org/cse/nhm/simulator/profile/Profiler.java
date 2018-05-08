package uk.org.cse.nhm.simulator.profile;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Profiler implements IProfiler {

    private static final Logger log = LoggerFactory.getLogger(Profiler.class);
    private final Stack<Long> times = new Stack<Long>();
    private final Stack<String> headings = new Stack<String>();

    public Profiler() {

    }

    @Override
    public void start(final String msg, final String category) {
        if (log.isDebugEnabled()) {
            times.push(System.currentTimeMillis());
            headings.push(category + ", " + msg);
        }
    }

    @Override
    public void stop() {
        if (log.isDebugEnabled()) {
            final long now = System.currentTimeMillis();
            final long start = times.pop();
            final String heading = headings.pop();
            final double len = (now - start) / 1000d;
            if (len > 0.5) {
                log.debug("({}) {}, {}", times.size(), heading, len);
            }
        }
    }
}
