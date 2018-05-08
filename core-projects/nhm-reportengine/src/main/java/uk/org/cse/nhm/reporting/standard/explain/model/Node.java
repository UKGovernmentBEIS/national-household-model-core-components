package uk.org.cse.nhm.reporting.standard.explain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Node {

    private String name;
    private List<Edge> parents = new ArrayList<Edge>();
    protected List<Edge> children = new ArrayList<Edge>();

    public Node(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("totalSize")
    public double getTotalSize() {
        double accum = 0;
        for (Edge parent : parents) {
            accum += parent.getValue();
        }
        return accum;
    }

    List<Edge> getChildEdges() {
        return children;
    }

    List<Edge> getParentEdges() {
        return parents;
    }

    public void addParentEdge(Edge parent) {
        parents.add(parent);
    }

    public void addChildEdge(Edge child) {
        if (calcRemainingSizeToAllocateToChildren() - child.getValue() < 0) {
            throw new IllegalArgumentException(String.format("Adding edge of size %s to %s would make source node %s negative.", child.getValue(), child.getTarget().getName(),
                    getName()));
        }
        children.add(child);
    }

    public double calcRemainingSizeToAllocateToChildren() {
        double accum = getTotalSize();
        for (Edge child : children) {
            accum -= child.getValue();
        }
        return accum;
    }

    public double getRemainingSizeToAllocateToChildrenSpeculative(List<Edge> newPotentialChildren) {
        double accum = calcRemainingSizeToAllocateToChildren();
        for (Edge e : newPotentialChildren) {
            if (e.getSource() == this) {
                accum -= e.getValue();
            }
        }
        return accum;
    }

    public boolean isUnconnected() {
        return children.isEmpty() && parents.isEmpty();
    }
}
