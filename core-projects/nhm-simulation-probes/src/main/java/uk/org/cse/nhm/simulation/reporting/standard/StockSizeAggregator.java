package uk.org.cse.nhm.simulation.reporting.standard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ConstructAndDemoLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * StockSizeAggregator2.
 * 
 * @author richardt
 * @version $Id: StockSizeAggregator2.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class StockSizeAggregator implements ISimulationStepListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockSizeAggregator.class);
    
    private final ILogEntryHandler loggingService;

    private final Map<RegionType, AtomicInteger> dwellingWeights = new HashMap<RegionType, AtomicInteger>();
    private boolean logOutOfDate;

    @Inject
    protected StockSizeAggregator(
    		final IDimension<BasicCaseAttributes> attributes,
            final ILogEntryHandler loggingService, final ISimulator simulator, final ICanonicalState state) {

        this.loggingService = loggingService;
        
        for (final RegionType t : RegionType.values()) {
            dwellingWeights.put(t, new AtomicInteger(0));
        }

        simulator.addSimulationStepListener(this);


        state.addStateListener(new IStateListener() {
            @Override
            public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
                BasicCaseAttributes caseAttributes;
                for (final IDwelling d : notification.getCreatedDwellings()) {
                    caseAttributes = state.get(attributes, d);
                    caseAttributes.getRegionType();
                    
                    dwellingWeights.get(caseAttributes.getRegionType()).addAndGet((int) d.getWeight());
                    logOutOfDate = true;
                }

                for (final IDwelling d : notification.getDestroyedDwellings()) {
                    caseAttributes = state.get(attributes, d);
                    caseAttributes.getRegionType();

                    dwellingWeights.get(caseAttributes.getRegionType()).addAndGet((int)-d.getWeight());
                    logOutOfDate = true;
                }

                // treat the special creation event differently
                if (notification.getRootScope().getTag().getSourceType() == StateChangeSourceType.CREATION) {
                    try {
                        storeUpdatedCounts(notification.getDate());
                    } catch (final NHMException e) {
                        throw new RuntimeException(e);
                    }
                    logOutOfDate = false;
                }
            }
        });
        
        loggingService.acceptLogEntry(new ReportHeaderLogEntry(Type.HouseCount));
    }

    /**
     * @param dateOfStep
     * @param nextDate
     * @param isFinalStep
     * @throws NHMException
     * @see uk.org.cse.nhm.simulator.main.ISimulationStepListener#simulationStepped(org.joda.time.DateTime,
     *      org.joda.time.DateTime, boolean)
     */
    @Override
    public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep)
        throws NHMException {
        if (logOutOfDate) {
            storeUpdatedCounts(dateOfStep);
        }
        logOutOfDate = false;
    }

    protected void storeUpdatedCounts(final DateTime modificationDate)
        throws NHMException {
    	
    	final ImmutableMap.Builder<RegionType, Integer> builder = ImmutableMap.builder();
    	
        LOGGER.debug("Updating technology distribution log");

        for (final RegionType t : RegionType.values()) {
            builder.put(t, dwellingWeights.get(t).get());
        }
        
        loggingService.acceptLogEntry(new ConstructAndDemoLogEntry(
                modificationDate, builder.build()));
    }
}
