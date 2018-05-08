package uk.org.cse.nhm.language.visit;

public interface IVisitor<V extends IVisitable<V>> {

    public boolean enter(final V v);

    public void visit(final V v);

    public void leave(final V v);
}
