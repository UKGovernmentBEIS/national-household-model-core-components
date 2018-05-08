package uk.org.cse.nhm.reporting.standard.explain.model;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Edge {

    private Node source;
    private Node target;
    private double value;
    private String cause;
    private DateTime startDate;
    private DateTime endDate;
    private List<Node> nodeSearchSource;
    private DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    public Edge(Node source, Node target, double value, String cause) {
        this(source, target, value, cause, null, null);
    }

    public Edge(Node source, Node target, double value, String cause, DateTime date) {
        this(source, target, value, cause, date, date);
    }

    public Edge(Node source, Node target, double value, String cause, DateTime startDate, DateTime endDate) {
        if (source == null || target == null) {
            throw new IllegalArgumentException(String.format("Source and target should not be null. Source %s target %s.", source, target));
        }
        this.source = source;
        this.target = target;
        this.value = value;
        this.cause = cause;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void connect() {
        this.source.addChildEdge(this);
        this.target.addParentEdge(this);
    }

    public Node getSource() {
        return source;
    }

    @JsonProperty("source")
    public int getSourceIndex() {
        if (nodeSearchSource == null) {
            throw new RuntimeException("Need to set node search source on all elements before trying to find their node indexes.");
        }
        int result = nodeSearchSource.indexOf(source);

        if (result == -1) {
            throw new RuntimeException(String.format("Source node was not in nodes list for edge going from %s to %s.", source, target));
        }

        return result;
    }

    public Node getTarget() {
        return target;
    }

    @JsonProperty("target")
    public int getTargetIndex() {
        if (nodeSearchSource == null) {
            throw new RuntimeException("Need to set node search source on all elements before trying to find their node indexes.");
        }
        int result = nodeSearchSource.indexOf(target);

        if (result == -1) {
            throw new RuntimeException(String.format("Target node was not in nodes list for edge going from %s to %s.", source, target));
        }

        return result;
    }

    @JsonProperty("value")
    public double getValue() {
        return value;
    }

    @JsonProperty("cause")
    public String getCause() {
        return cause;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        if (startDate == null) {
            return "";
        } else {
            return format.print(startDate);
        }
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        if (endDate == null) {
            return "";
        } else {
            return format.print(endDate);
        }
    }

    public void setNodeSearchSource(List<Node> nodeSearchSource) {
        this.nodeSearchSource = nodeSearchSource;
    }
}
