package uk.org.cse.nhm.language.visit.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.visit.IVisitor;

public class AdaptableVisitableVisitorTest {

    public static class AdaptableVisitableImpl extends AdaptableVisitable<AdaptableVisitableImpl> {

        private AdaptableVisitableImpl child;
        private List<AdaptableVisitableImpl> children;

        public AdaptableVisitableImpl(final AdaptableVisitableImpl child,
                final List<AdaptableVisitableImpl> children) {
            super();
            this.child = child;
            this.children = children;
        }

        public AdaptableVisitableImpl getChild() {
            return child;
        }

        public void setChild(final AdaptableVisitableImpl child) {
            this.child = child;
        }

        public List<AdaptableVisitableImpl> getChildren() {
            return children;
        }

        public void setChildren(final List<AdaptableVisitableImpl> children) {
            this.children = children;
        }
    }

    public static class Visitor implements IVisitor<AdaptableVisitableImpl> {

        public final HashSet<AdaptableVisitableImpl> visited = new HashSet<AdaptableVisitableImpl>();
        ;
		public final HashSet<AdaptableVisitableImpl> prune = new HashSet<AdaptableVisitableImpl>();

        @Override
        public boolean enter(final AdaptableVisitableImpl v) {
            return !prune.contains(v);
        }

        @Override
        public void visit(final AdaptableVisitableImpl v) {
            visited.add(v);
        }

        @Override
        public void leave(final AdaptableVisitableImpl v) {

        }
    }

    @Test
    public void testVisitableNoChildren() {
        final AdaptableVisitableImpl thing = new AdaptableVisitableImpl(null, null);
        final Visitor v = new Visitor();
        thing.accept(v);
        Assert.assertEquals("Saw only thing", Collections.singleton(thing), v.visited);
    }

    @Test
    public void testVisitableOneChild() {
        final AdaptableVisitableImpl child = new AdaptableVisitableImpl(null, null);
        final AdaptableVisitableImpl thing = new AdaptableVisitableImpl(child, null);
        final Visitor v = new Visitor();
        thing.accept(v);
        Assert.assertEquals("Saw both things", ImmutableSet.of(child, thing), v.visited);
    }

    @Test
    public void testVisitablePruneChild() {
        final AdaptableVisitableImpl child = new AdaptableVisitableImpl(null, null);
        final AdaptableVisitableImpl thing = new AdaptableVisitableImpl(child, null);
        final Visitor v = new Visitor();
        v.prune.add(child);
        thing.accept(v);
        Assert.assertEquals("Pruned child", ImmutableSet.of(thing), v.visited);
    }

    @Test
    public void testVisitableSomeChildren() {
        final AdaptableVisitableImpl child = new AdaptableVisitableImpl(null, null);

        final AdaptableVisitableImpl child2 = new AdaptableVisitableImpl(null, null);
        final AdaptableVisitableImpl thing = new AdaptableVisitableImpl(null, ImmutableList.<AdaptableVisitableImpl>of(child, child2));
        final Visitor v = new Visitor();
        thing.accept(v);
        Assert.assertEquals("Saw all the things", ImmutableSet.of(child, thing, child2), v.visited);
    }
}
