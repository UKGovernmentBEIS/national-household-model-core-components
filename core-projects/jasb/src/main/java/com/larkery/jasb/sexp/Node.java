package com.larkery.jasb.sexp;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.errors.ILocated;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public abstract class Node implements ISExpression, ILocated {

    private final Location location;

    protected Node(final Location location) {
        super();
        this.location = location;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void accept(final ISExpressionVisitor visitor) {
        visitor.locate(location);
    }

    public static Node copyStructure(final ISExpression source) throws UnfinishedExpressionException {
        if (source instanceof Node) {
            return ((Node) source).removeComments();
        } else {
            final NodeBuilder visitor = NodeBuilder.withoutComments();
            source.accept(visitor);
            return visitor.get();
        }
    }

    protected abstract Node removeComments();

    public static Node copy(final ISExpression source) throws UnfinishedExpressionException {
        if (source instanceof Node) {
            return (Node) source;
        }
        final NodeBuilder visitor = NodeBuilder.create();
        source.accept(visitor);
        return visitor.get();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Node> copyAll(final ISExpression source) throws UnfinishedExpressionException {
        if (source instanceof Node) {
            return ImmutableList.of((Node) source);
        } else if (source instanceof SExpressions.InOrder) {
            final List<ISExpression> parts = ((SExpressions.InOrder) source).getList();
            boolean allNodes = true;
            for (final ISExpression e : parts) {
                if (!(e instanceof Node)) {
                    allNodes = false;
                    break;
                }
            }
            if (allNodes) {
                return (((List) parts));
            }
        }

        final NodeBuilder visitor = NodeBuilder.create();
        source.accept(visitor);
        return visitor.getAll();
    }

    public abstract void accept(final INodeVisitor visitor);
}
