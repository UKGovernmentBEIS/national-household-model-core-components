package uk.org.cse.nhm.reporting.standard.explain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class NodesAndLinks {

    private List<Node> nodes;
    private List<Edge> links;

    public NodesAndLinks(List<Node> nodes, List<Edge> links) {
        this.nodes = nodes;
        this.links = links;
    }

    @JsonProperty("nodes")
    public List<Node> getNodes() {
        return nodes;
    }

    @JsonProperty("links")
    public List<Edge> getLinks() {
        return links;
    }
}
