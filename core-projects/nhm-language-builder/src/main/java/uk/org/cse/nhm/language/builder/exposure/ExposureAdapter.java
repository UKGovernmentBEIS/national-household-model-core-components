package uk.org.cse.nhm.language.builder.exposure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.management.timer.Timer;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.exposure.XBernoulliSampler;
import uk.org.cse.nhm.language.definition.exposure.XCountSampler;
import uk.org.cse.nhm.language.definition.exposure.XExposeOnEntry;
import uk.org.cse.nhm.language.definition.exposure.XIntervalSchedule;
import uk.org.cse.nhm.language.definition.exposure.XProportionSampler;
import uk.org.cse.nhm.language.definition.exposure.XScheduleInit;
import uk.org.cse.nhm.language.definition.exposure.XTimeSeriesSchedule;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.factories.ISamplerFactory;
import uk.org.cse.nhm.simulator.factories.ITriggerFactory;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;

/**
 * The builder's adapter for core exposure functions.
 *
 * Handles the following terms from the language
 *
 * <ul>
 * <li> {@link XTimeSeriesSchedule} </li>
 * <li> {@link XCountSampler} </li>
 * <li> {@link XIntervalSchedule} </li>
 * <li> {@link XProportionSampler} </li>
 * </ul>
 *
 * @author hinton
 *
 */
public class ExposureAdapter extends ReflectingAdapter {

    final ITriggerFactory triggerFactory;
    final ISamplerFactory samplerFactory;
    final Provider<DateTime> startDateProvider;

    @Inject
    public ExposureAdapter(
            final Set<IConverter> converters,
            final Set<IAdapterInterceptor> interceptors,
            final ITriggerFactory triggerFactory,
            final ISamplerFactory samplerFactory,
            final @Named(SimulatorConfigurationConstants.START_DATE) Provider<DateTime> startDateProvider
    ) {
        super(converters, interceptors);
        this.triggerFactory = triggerFactory;
        this.samplerFactory = samplerFactory;
        this.startDateProvider = startDateProvider;

    }

    @Adapt(XCountSampler.class)
    public IDwellingGroupSampler buildCountSampler(
            @Prop(XCountSampler.P.COUNT) final int count) {
        return samplerFactory.createCountingSampler(count);
    }

    @Adapt(XProportionSampler.class)
    public IDwellingGroupSampler buildProportionSampler(
            @Prop(XProportionSampler.P.PROPORTION) final double proportion) {
        return samplerFactory.createProportionSampler(proportion);
    }

    @Adapt(XIntervalSchedule.class)
    public IExposureFactory buildIntervalSchedule(
            Name id,
            @Prop(XIntervalSchedule.P.START_DATE) final DateTime startDate,
            @Prop(XIntervalSchedule.P.END_DATE) final DateTime endDate,
            @Prop(XIntervalSchedule.P.INTERVAL) final Period interval,
            @Prop(XIntervalSchedule.P.SAMPLER) final IDwellingGroupSampler sampler
    ) {

        if (interval.toDurationFrom(new DateTime()).getMillis() < Timer.ONE_DAY) {
            throw new IllegalArgumentException("Period " + interval + " is too frequent to use in a schedule");
        }
        return new IExposureFactory() {
            @Override
            public Object create(Name id, IDwellingGroup group, IStateAction action) {
                if (startDate == null && endDate == null) {
                    return triggerFactory.createFixedFrequencyTrigger(id, interval, group, sampler, action);
                } else if (startDate == null) {
                    return triggerFactory.createFixedFrequencyTrigger(id, interval, endDate, group, sampler, action);
                } else if (endDate == null) {
                    return triggerFactory.createFixedFrequencyTrigger(id, startDate, interval, group, sampler, action);
                } else {
                    return triggerFactory.createFixedFrequencyTrigger(id, startDate, interval, endDate, group, sampler, action);
                }
            }

            @Override
            public String toString() {
                return "Interval Exposure Factory";
            }
        };
    }

    @Adapt(XTimeSeriesSchedule.class)
    public IExposureFactory buildTimeseriesSchedule(
            @Prop(XTimeSeriesSchedule.P.ENTRIES) final List<TimeSeriesEntry> entries
    ) {
        final List<DateTime> times = new ArrayList<DateTime>();
        final List<IDwellingGroupSampler> samplers = new ArrayList<IDwellingGroupSampler>();

        for (final TimeSeriesEntry te : entries) {
            samplers.add(te.sampler);
            times.add(te.on);
        }

        return new IExposureFactory() {
            @Override
            public Object create(Name id, IDwellingGroup group, IStateAction action) {
                return triggerFactory.createTimeSequenceTrigger(id, times, group, samplers, action);
            }

            @Override
            public String toString() {
                return "Time Series Exposure Factory";
            }
        };
    }

    public static class TimeSeriesEntry {

        public final DateTime on;
        public final IDwellingGroupSampler sampler;

        public TimeSeriesEntry(DateTime on, IDwellingGroupSampler sampler) {
            this.on = on;
            this.sampler = sampler;
        }
    }

    @Adapt(XTimeSeriesSchedule.Entry.class)
    public TimeSeriesEntry buildEntry(
            @Prop(XTimeSeriesSchedule.P.ENTRY_ON) final DateTime on,
            @Prop(XTimeSeriesSchedule.P.ENTRY_SAMPLER) final IDwellingGroupSampler sampler
    ) {
        return new TimeSeriesEntry(on, sampler);
    }

    @Adapt(XExposeOnEntry.class)
    public IExposureFactory buildExposeOnEntry(
            @Prop(XExposeOnEntry.P.delay) final Period delay
    ) {
        return new IExposureFactory() {
            @Override
            public Object create(Name id, IDwellingGroup group, IStateAction action) {
                return triggerFactory.createEntryTrigger(id, delay, group, action);
            }

            @Override
            public String toString() {
                return "Delayed entry exposure factory " + delay;
            }
        };
    }

    @Adapt(XScheduleInit.class)
    public IExposureFactory buildInitSchedule(
            @Prop(XScheduleInit.P.sampler) final IDwellingGroupSampler sampler
    ) {
        return buildTimeseriesSchedule(
                ImmutableList.of(new TimeSeriesEntry(startDateProvider.get(), sampler)));
    }

    @Adapt(XBernoulliSampler.class)
    public IDwellingGroupSampler buildBernoulliSampler(
            @Prop("parameter") final IComponentsFunction<Number> parameter) {
        return samplerFactory.createBernoulliSampler(parameter);
    }
}
