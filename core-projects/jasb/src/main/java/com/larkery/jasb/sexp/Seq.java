package com.larkery.jasb.sexp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoDetectPolicy;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

@AutoProperty(autoDetect = AutoDetectPolicy.NONE)
public class Seq extends Node implements Iterable<Node> {

    private final List<Node> nodes;
    private final Location end;
    private final Delim marker;
    private transient Node withoutComments;

    private Seq(
            final Delim marker,
            final Location location,
            final Location end,
            final List<Node> nodes) {
        super(location);
        this.marker = marker;
        this.end = end;
        this.nodes = ImmutableList.copyOf(nodes);
    }

    @Override
    protected Node removeComments() {
        if (withoutComments == null) {
            final ImmutableList.Builder<Node> removedComments = ImmutableList.builder();
            boolean allSame = true;
            for (final Node n : nodes) {
                final Node n_ = n.removeComments();
                if (n_ != null) {
                    removedComments.add(n_);
                }
                allSame = allSame && (n == n_);
            }
            if (allSame) {
                withoutComments = this;
            } else {
                withoutComments = new Seq(this.getDelimeter(), this.getLocation(), this.getEndLocation(),
                        removedComments.build());
            }
        }
        return withoutComments;
    }

    public Delim getDelimeter() {
        return marker;
    }

    public Node get(final int arg0) {
        return nodes.get(arg0);
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    public List<Node> getTail() {
        return nodes.subList(1, nodes.size());
    }

    public Node getHead() {
        if (nodes.isEmpty()) {
            throw new NoSuchElementException("tried to get head element of empty s-expression");
        }
        return nodes.get(0);
    }

    public Optional<Atom> firstAtom() {
        for (final Node n : nodes) {
            if (n instanceof Atom) {
                return Optional.of((Atom) n);
            }
        }
        return Optional.absent();
    }

    @Override
    public void accept(final ISExpressionVisitor visitor) {
        super.accept(visitor);
        visitor.open(marker);
        for (final Node node : this) {
            node.accept(visitor);
        }
        visitor.locate(end);
        visitor.close(marker);
    }

    public Location getEndLocation() {
        return end;
    }

    public int size() {
        return nodes.size();
    }

    public Optional<Node> exceptComments(int i) {
        for (final Node n : this) {
            if (n instanceof Comment) {
                continue;
            }

            if (i == 0) {
                return Optional.of(n);
            }

            i--;
        }

        return Optional.absent();
    }

    public List<Node> exceptComments() {
        final ImmutableList.Builder<Node> b = ImmutableList.builder();
        for (final Node n : this) {
            if (n instanceof Comment) {
                continue;
            }
            b.add(n);
        }
        return b.build();
    }

    public List<Node> getNodesAfter(final Node node) {
        return nodes.subList(nodes.indexOf(node) + 1, nodes.size());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(marker.open);
        boolean space = false;
        for (final Node n : this) {
            if (space) {
                sb.append(" ");
            } else {
                space = true;
            }
            sb.append(n);
        }
        sb.append(marker.close);
        return sb.toString();
    }

    public static class Builder {

        private final Location start;
        private final ImmutableList.Builder<Node> builder = ImmutableList.builder();
        private final Delim marker;

        private Builder(final Location start, final Delim marker) {
            super();
            this.start = start;
            this.marker = marker;
        }

        public Builder add(final Node node) {
            builder.add(node);
            return this;
        }

        public Builder add(final String atom) {
            return add(Atom.create(atom, start));
        }

        public Builder add(final String atom, final Node value) {
            add(atom + ":");
            return add(value);
        }

        public Builder add(final String atom, final String value) {
            add(atom + ":");
            return add(value);
        }

        public Seq build(final Location end) {
            return new Seq(marker, start, end, this.builder.build());
        }

        @Override
        public String toString() {
            return "" + builder.build();
        }

        public Builder addAll(final Collection<Node> nodes) {
            builder.addAll(nodes);
            return this;
        }
    }

    public static Builder builder(final Location start, final Delim marker) {
        return new Builder(start, marker);
    }

    @Property(policy = PojomaticPolicy.HASHCODE_EQUALS)
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public void accept(final INodeVisitor visitor) {
        if (visitor.seq(this)) {
            for (final Node n : nodes) {
                n.accept(visitor);
            }
        }
    }

    @Override
    public boolean equals(final Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
