package uk.org.cse.nhm.reporting.standard.transactions;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.TransactionLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.flat.MultiplexedReporter;
import uk.org.cse.nhm.reporting.standard.flat.ReportFormats;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter;
import uk.org.cse.nhm.reporting.standard.flat.TableType;

public class TSVDwellingTransactionReporter extends MultiplexedReporter<TransactionLogEntry> {

    public TSVDwellingTransactionReporter(final IOutputStreamFactory factory) {
        super(TransactionLogEntry.class, factory);
    }

    @Override
    protected String getName(final TransactionLogEntry entry) {
        return entry.getReportName();
    }

    @Override
    protected IReporter createDelegate(final String name, final IOutputStreamFactory factory) {
        return new NamedDwellingTransactionReport(factory, name);
    }

    private static class NamedDwellingTransactionReport extends SimpleTabularReporter<TransactionLogEntry> {

        public NamedDwellingTransactionReport(final IOutputStreamFactory factory, final String name) {
            super(factory, TransactionLogEntry.class, TableType.DWELLING, name);

            start(
                    ImmutableList.of(
                            Field.of("payer", "The unique ID of the account being reported on", "ID"),
                            Field.of("weight", "The weight of the dwelling to which the account belongs", "Double"),
                            Field.of("date", "The date on which the transaction occurred", "Date"),
                            Field.of("payee", "The non-dwelling entity to which the money was sent", "String"),
                            Field.of("amount", "The amount of money transferred from the dwelling to the payee", "Double"),
                            Field.of("address", "The location in the scenario of the action which caused the transaction", "String"),
                            Field.of("tags", "The tags associated with the transaction (comma separated)", "String")
                    )
            );
        }

        @Override
        protected void doHandle(final TransactionLogEntry logEntry) {
            write(new String[]{
                logEntry.getPayer(),
                String.format("%f", logEntry.getWeight()),
                ReportFormats.getDateAsDay(logEntry.getDate()),
                logEntry.getPayee(),
                String.format("%.2f", logEntry.getAmount()),
                logEntry.getPath(),
                logEntry.getTags()
            });
        }

        @Override
        public String getDescription() {
            return "Contains a record of the transactions that have happened to the dwellings in a given group";
        }
    }
}
