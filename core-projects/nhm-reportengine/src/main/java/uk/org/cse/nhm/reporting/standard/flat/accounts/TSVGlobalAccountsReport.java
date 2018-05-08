package uk.org.cse.nhm.reporting.standard.flat.accounts;

import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.GlobalAccountsLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.flat.ReportFormats;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter;
import uk.org.cse.nhm.reporting.standard.flat.TableType;

public class TSVGlobalAccountsReport extends SimpleTabularReporter<GlobalAccountsLogEntry> {

    boolean started = false;

    public TSVGlobalAccountsReport(IOutputStreamFactory factory) {
        super(factory, GlobalAccountsLogEntry.class, TableType.AGGREGATE, "globalAccounts");
    }

    @Override
    protected void doHandle(final GlobalAccountsLogEntry entry) {
        if (!started) {
            started = true;
            start(ImmutableList.of(
                    Field.of("Date", "The date at which this account had this balance.", "Date"),
                    Field.of("Account", "The name of the account.", "Double"),
                    Field.of("Balance", "The sum of the amounts of all the transactions which this account has been involved in during this simulation.", "Double")
            ));
        }

        for (Entry<String, Double> account : entry.getAccountBalances().entrySet()) {
            write(buildRow(entry.getDate(), account.getKey(), account.getValue()));
        }
    }

    private String[] buildRow(DateTime date, String key, Double value) {
        return new String[]{
            ReportFormats.getDateAsDay(date),
            key,
            String.format("%.2f", value)
        };
    }

    @Override
    public String getDescription() {
        return "Lists the balances of the 'global accounts' which are not associated with a particular dwelling. Each row represent the balance of an account at a given date.";
    }

}
