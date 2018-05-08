package uk.org.cse.nhm.language.visit;

public interface IVisitable<T extends IVisitable<T>> {

    public void accept(final IVisitor<T> visitor);
}
