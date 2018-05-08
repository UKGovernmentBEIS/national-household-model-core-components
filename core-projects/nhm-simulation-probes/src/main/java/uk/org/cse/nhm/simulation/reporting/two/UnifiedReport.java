package uk.org.cse.nhm.simulation.reporting.two;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.logging.logentry.ProbeLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.action.IUnifiedReport;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.utility.DeduplicatingMap;
import uk.org.cse.nhm.utility.DeduplicatingMap.Deduplicator;
import uk.org.cse.nhm.utility.DeduplicatingMap.StandardDeduplicator;

public class UnifiedReport extends AbstractNamed implements IStateListener, ISimulationStepListener, Initializable, IUnifiedReport {
	/**
	 * A single disaggregated record - when a house is sent to the report, it makes one of these
	 * and puts it in the {@link UnifiedReport#buffer}
	 */
	class Record implements IRecord {
		/**
		 * A unique rownumber for the given report, which only ever goes up
		 */
		public final long sequenceBefore;
        public long sequenceAfter = -1;
		/**
		 * What dwelling was in that row
		 */
		public final IDwelling dwelling;

		/**
		 * Whether the state for the dwelling was selected to be the true state
		 */
		public boolean selected = false;

		/**
		 * Values of the user-defined columns
		 */
		public final Object[] columnValuesBefore;

        /**
         * Column values after
         */
        public Object[] columnValuesAfter;

		/**
		 * the key field is entered by the user when they send
		 * a house to the report
		 */
		public final String key;

		public Record(
				final long sequence,
				final String key,
				final IDwelling dwelling,
				final Object[] columnValues) {
			super();
			this.sequenceBefore = sequence;
			this.key = key;
			this.dwelling = dwelling;
			this.columnValuesBefore = columnValues;
		}

		@Override
		public void after(final IComponentsScope scope, final ILets lets) {
            UnifiedReport.this.after(this, scope, lets);
		}

        public boolean isFrom(final UnifiedReport r) {
            return UnifiedReport.this == r;
        }
	}

	private final List<Column> columns;
	private final List<Cut> cuts;
	private final ILogEntryHandler out;
    private final boolean recordChange;
    private final ISimulator simulator;
	private final ICanonicalState state;
	private static final Object NAME_PLACEHOLDER = new Object();

	/**
	 * Maps from cuts to their names
	 */
	private final Deduplicator<Object> cutNames = new StandardDeduplicator<>();

	private final Deduplicator<Object> columnNames = new StandardDeduplicator<>();

	@AssistedInject
	public UnifiedReport(
			final ICanonicalState state,
			final ISimulator simulator,
			final ILogEntryHandler out,
			@Assisted final boolean recordChange,
			@Assisted final List<IReportPart> contents) {
		this.state = state;
		this.simulator = simulator;
		this.out = out;
        this.recordChange = recordChange;
        columns = new ArrayList<Column>();
		cuts = new ArrayList<Cut>();

		// add default cut
		final Cut cut;
		if (recordChange) {
			cut = new Cut(ImmutableList.of(Cut.SENT_FROM_COLUMN, Cut.OUTCOME_COLUMN));
		} else {
			cut = new Cut(ImmutableList.of(Cut.SENT_FROM_COLUMN));
		}

		cut.setIdentifier(Name.of("summary"));
		cuts.add(cut);

		for (final IReportPart part : contents) {
			columns.addAll(part.columns());
			cuts.addAll(part.cuts());
		}

		if (recordChange) {
            columnNames.add(NAME_PLACEHOLDER, Cut.OUTCOME_COLUMN);
            columnNames.add(NAME_PLACEHOLDER, Cut.SELECTED_COLUMN);
        }
		columnNames.add(NAME_PLACEHOLDER, Cut.SENT_FROM_COLUMN);
		columnNames.add(NAME_PLACEHOLDER, "count");

		if (recordChange) {
            columnNames.add(NAME_PLACEHOLDER, "suitable-count");
        }

		int index = 0;
		for (final Column c : columns) {
			c.setIndex(index++);
			c.takeNames(columnNames);
		}
		for (final Cut c : cuts) {
			c.takeColumnIndices(columns);
			cutNames.add(c, c.getIdentifier().getName());
		}
	}

	private static final Object NA = new Object() { public String toString() { return "n/a"; } };

	/**
	 * Because the report wants to have information about things which were selected, we keep a buffer
	 * of report rows in memory in the simulator before we send them down the pipe to get written.
	 *
	 * This buffer could just as well be at the other end of the pipe, but one of the things that
	 * has to happen is the aggregation, which depends on knowing what aggregators are configured.
	 *
	 * Sending that would be a pain.
	 */
	private final List<Record> buffer = new ArrayList<>();

	/**
	 * Used to track how many {@link Record}s we have constructed so far
	 */
	long sequence = 0;

	long lastAggregateSequence = 0;

	@Override
	public IRecord before(final String key, final IComponentsScope scope, final ILets lets) {
		// compute all the values for the current house, and write a note that we reported on it.
        final Record record =  new Record(sequence++,
                                          key,
                                          scope.getDwelling(),
                                          evaluate(key, scope, lets));
		scope.addNote(record);
		buffer.add(record);
		return record;
	}

	public void after(final Record record,
                      final IComponentsScope scope,
                      final ILets lets) {
        if (recordChange) {
            // record should be in buffer already.
            record.columnValuesAfter = evaluate(record.key, scope, lets);
            record.sequenceAfter = sequence++;
        }
	}

	private Object[] evaluate(final String key, final IComponentsScope scope, final ILets lets) {
		final Object[] values = new Object[columns.size()];
		for (int i = 0; i<values.length; i++) {
			values[i] = columns.get(i).compute(scope, lets);
			if (values[i] == null) {
				values[i] = NA;
			}
			// if (values[i] == null) {
			// 	throw new IllegalStateException(String.format("A function (%s) returned null (this should never happen) when calculating report column " + key,
			// 			columns.get(i).getIdentifier()
			// 			));
			// }
		}
        return values;
	}

	@Override
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
        // just in case there is something to log, flush the buffer out again

        flush(dateOfStep, buffer);

		if (sequence > lastAggregateSequence) {
			// it might be good to chunk things up for the aggregate report somehow - maybe by event?
			// problem is we can't have it turn disaggregated, so it fairly has to be by event.
			for (final Cut cut : cuts) {
				final String name = String.format("%s-%s", getIdentifier().getName(), cutNames.get(cut));

				for (final List<Object> group : cut.getGroups()) {
					final ImmutableMap<String, String> rowKey = cut.createRowKey(group);
					final ImmutableMap.Builder<String, Double> aggregations = ImmutableMap.builder();

					aggregations.put("count", cut.getCountBefore(group));
					if (recordChange) {
                        final double suitableCount = cut.getCountAfter(group);
                        aggregations.put("suitable-count", suitableCount);
                    }

					for (final Column column : columns) {
                        // N/A is not supported here.
						aggregations.putAll(column.getAccumulatedValues(cut, group));
					}

					out.acceptLogEntry(new AggregateLogEntry(name, ImmutableSet.<String>of(),
							rowKey, dateOfStep, aggregations.build()));
				}

				cut.reset();
			}

			lastAggregateSequence = sequence;
		}
	}

	@Override
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
		if (!buffer.isEmpty()) {
			// find out which things happened
			final List<Record> allNotes = notification.getRootScope().getAllNotes(Record.class);

			for (final Record note : allNotes) {
				if (note.isFrom(this)) {
					note.selected = true;
				}
			}

			// dump the disaggregated stuff into the pipe and wipe the memory of it
            // actually it would be nicer to make the note connect to the
			flush(state.getTrueDate(), buffer);
		}
	}

	private void flush(final DateTime date, final List<Record> buffer) {
        // run the accumulators now we know which things happened and which did not
        final String name = getIdentifier().getName();
        for (final Record rec : buffer) {
            for (final Cut cut : cuts) {
                final List<Object> group = cut.count(rec);
                for (final Column col : columns) {
                    col.accumulate(cut, group, rec);
                }
            }
            // transmit message to outside world
            out.acceptLogEntry(new ProbeLogEntry(
                                   name,
                                   rec.dwelling.getWeight(),
                                   date,
                                   (int) rec.sequenceBefore,
                                   rec.dwelling.getID(),
                                   fields(rec)));
        }
		buffer.clear();
	}

	private ImmutableMap<String, Object> fields(final Record r) {
		// deduplicate keys
		final DeduplicatingMap.Builder<Object> builder = DeduplicatingMap.stringBuilder();

		builder.put(Cut.SENT_FROM_COLUMN, r.key);

		if (recordChange) {
            builder.put(Cut.SELECTED_COLUMN, r.selected);
            builder.put(Cut.OUTCOME_COLUMN, r.columnValuesAfter != null);
        }

        for (int i = 0; i<columns.size(); i++) {
            final String name = columns.get(i).getIdentifier().getName();

            if (recordChange) {
                builder.put(name + " (Before)", r.columnValuesBefore[i]);
                if (r.columnValuesAfter != null) {
                    builder.put(name + " (After)", r.columnValuesAfter[i]);
                } else {
                    builder.put(name + " (After)", NA);
                }
            } else {
                builder.put(name, r.columnValuesBefore[i]);
            }
        }

		return builder.build();
	}

	@Override
	public void initialize() throws NHMException {
		state.addStateListener(this);
		simulator.addSimulationStepListener(this);
	}
}
