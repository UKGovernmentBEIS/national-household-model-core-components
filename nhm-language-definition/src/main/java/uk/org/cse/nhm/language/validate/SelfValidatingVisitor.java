package uk.org.cse.nhm.language.validate;

import java.util.Deque;
import java.util.LinkedList;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.validate.NoCyclesValidatorWithDelegates.IDelegate;

public class SelfValidatingVisitor implements IDelegate {
	final Deque<XElement> stack = new LinkedList<XElement>();
	
	private final ImmutableList.Builder<IError> problems = ImmutableList.builder();
	
	@Override
	public void visit(XElement v) {
		if (v instanceof ISelfValidating) {
			final ISelfValidating sv = (ISelfValidating) v;
			problems.addAll(sv.validate(stack));
		}
	}
	
	@Override
	public boolean doEnter(XElement v) {
		stack.push(v);
		return true;
	}
	
	public void doLeave(XElement v) {
		stack.pop();
	}
	
	public Iterable<? extends IError> getProblems() {
		return problems.build();
	}
}
