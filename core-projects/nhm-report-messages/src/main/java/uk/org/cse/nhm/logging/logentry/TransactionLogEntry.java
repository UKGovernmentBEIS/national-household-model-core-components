package uk.org.cse.nhm.logging.logentry;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@AutoProperty
public class TransactionLogEntry extends AbstractLogEntry {

    private final DateTime date;
    private final String payer;
    private final double amount;
    private final String payee;
    private final String path;
    private final String reportName;
    private final String tags;
    private final float weight;

    @JsonCreator
    public TransactionLogEntry(
            @JsonProperty("date") final DateTime date,
            @JsonProperty("payer") final String payer,
            @JsonProperty("weight") final float weight,
            @JsonProperty("amount") final double amount,
            @JsonProperty("path") final String path,
            @JsonProperty("payee") final String payee,
            @JsonProperty("reportName") final String reportName,
            @JsonProperty("tags") final String tags) {
        this.date = date;
        this.payer = payer;
        this.weight = weight;
        this.amount = amount;
        this.path = path;
        this.payee = payee;
        this.reportName = reportName;
        this.tags = tags;
    }

    public DateTime getDate() {
        return date;
    }

    public String getPayer() {
        return payer;
    }

    public double getAmount() {
        return amount;
    }

    public String getPath() {
        return path;
    }

    public String getPayee() {
        return payee;
    }

    public String getTags() {
        return tags;
    }

    public String getReportName() {
        return reportName;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    public float getWeight() {
        return weight;
    }
}
