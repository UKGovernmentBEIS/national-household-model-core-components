package uk.org.cse.nhm.language.validate;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.visit.IPropertyVisitor;
import uk.org.cse.nhm.language.visit.NonRecursiveVisitor;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

public class NoCyclesValidatorWithDelegates extends NonRecursiveVisitor<XElement> implements IPropertyVisitor<XElement> {
	private final IDelegate[] delegates;
	private final IPropertyVisitor<XElement>[] propertyVisitors;
	private final int[] deactivationDepths;
	private int currentDepth;
	private final ImmutableList.Builder<IError> problems = ImmutableList.builder();
	
	public interface IDelegate {
		public boolean doEnter(final XElement v);
		public void doLeave(final XElement v);
		public void visit(final XElement v);
	}
	
	@SuppressWarnings("unchecked")
	public NoCyclesValidatorWithDelegates(final List<IDelegate> delegates) {
		this.delegates = delegates.toArray(new IDelegate[delegates.size()]);
		this.deactivationDepths= new int[delegates.size()];
		Arrays.fill(deactivationDepths, -1);
		this.propertyVisitors = new IPropertyVisitor[delegates.size()];
		
		for (int i= 0; i<this.delegates.length; i++) {
			if (this.delegates[i] instanceof IPropertyVisitor<?>) {
				this.propertyVisitors[i] = (IPropertyVisitor<XElement>) this.delegates[i];
			}
		}
	}
	
	@Override
	public void enterProperty(final XElement element, final Method name) {
		for (int i= 0; i<delegates.length; i++) {
			if (deactivationDepths[i] >= 0 && deactivationDepths[i] < currentDepth) continue;
			if (propertyVisitors[i] == null) continue;
			propertyVisitors[i].enterProperty(element, name);
		}
	}
	
	@Override
	public void leaveProperty(final XElement element, final Method name) {
		for (int i= 0; i<delegates.length; i++) {
			if (deactivationDepths[i] >= 0 && deactivationDepths[i] < currentDepth) continue;
			if (propertyVisitors[i] == null) continue;
			propertyVisitors[i].leaveProperty(element, name);
		}
	}
	
	@Override
	protected boolean doEnter(final XElement v) {
		boolean result = false;
		
		for (int i= 0; i<delegates.length; i++) {
			if (deactivationDepths[i] >= 0 && deactivationDepths[i] < currentDepth) continue;
			final IDelegate delegate = delegates[i];
			final boolean delegateEnters = delegate.doEnter(v);
			if (delegateEnters) {
				result = true;
			} else {
				deactivationDepths[i] = currentDepth;
			}
		}
		
		currentDepth++;
		
		return result;
	}

	@Override
	protected void doLeave(final XElement v) {
		currentDepth--;
		for (int i= 0; i<delegates.length; i++) {
			if (deactivationDepths[i] >= 0 && deactivationDepths[i] < currentDepth) continue;
			if (deactivationDepths[i] == currentDepth) deactivationDepths[i] = -1;
			final IDelegate delegate = delegates[i];
			delegate.doLeave(v);
		}
	}
	
	@Override
	public void visit(final XElement v) {
		for (int i= 0; i<delegates.length; i++) {
			if (deactivationDepths[i] >= 0 && deactivationDepths[i] < currentDepth) continue;
			final IDelegate delegate = delegates[i];
			delegate.visit(v);
		}
	}
	
	@Override
	protected void wouldRecur(final XElement v) {
		problems.add(BasicError.at(v.getLocation(), String.format("Cycle detected: %s contains itself.", v.getIdentifier().getName())));
	}

	public Iterable<? extends IError> getProblems() {
		return problems.build();
	}
}
