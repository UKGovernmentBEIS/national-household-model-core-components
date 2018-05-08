package uk.org.cse.nhm.language.visit;

import java.lang.reflect.Method;

public interface IPropertyVisitor<V extends IVisitable<V>> {

    public void enterProperty(final V element, final Method name);

    public void leaveProperty(final V element, final Method name);
}
