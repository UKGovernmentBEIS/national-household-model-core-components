package uk.org.cse.commons.scopes;

public interface IScopeVisitor<T> {
	public void enterScope(final T tag);
	public void visit(final Object note);
	public void exitScope();
}
