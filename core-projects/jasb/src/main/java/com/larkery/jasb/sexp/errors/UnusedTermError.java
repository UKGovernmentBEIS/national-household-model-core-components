package com.larkery.jasb.sexp.errors;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Iterables;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;

public class UnusedTermError extends BasicError {

    public UnusedTermError(final Set<Node> nodes) {
        super(Iterables.get(nodes, 0).getLocation(), "Unexpected terms " + nodes, Type.ERROR);
    }

    @JsonCreator
    public UnusedTermError(
            @JsonProperty("location") final Location location,
            @JsonProperty("message") final String message,
            @JsonProperty("type") final Type type) {
        super(location, message, type);
    }
}
