package com.larkery.jasb.sexp.parse;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public abstract class SimpleMacro implements IMacro {
	protected abstract Set<String> getRequiredArgumentNames();
	protected abstract Set<String> getAllowedArgumentNames();

	protected abstract int getMaximumArgumentCount();

	protected abstract int getMinimumArgumentCount();
	
	protected ISExpression expandResult(final IMacroExpander expander, final ISExpression transformed) {
		return expander.expand(transformed);
	}
	
	protected Set<String> getInvalidArguments(final Set<String> arguments) {
		return Sets.difference(arguments, getAllowedArgumentNames());
	}
	
	protected Set<String> getMissingArguments(final Set<String> arguments) {
		return Sets.difference(getRequiredArgumentNames(), arguments);
	}
	
	@Override
	public ISExpression transform(
			final Seq unexpanded,
			final IMacroExpander expander, 
			final IErrorHandler errors) {
		if (unexpanded.isEmpty()) {
			throw new RuntimeException("This should never happen - if pasting part of a macro, it should at least have a macro name");
		} else {

			final Invocation inv = Invocation.of(unexpanded, errors);

			if (inv != null) {
				if (validateMacroParameters(inv, errors)) {
					return expandResult(expander, doTransform(inv, expander, errors));
				}
			}
		}
		
		return SExpressions.empty();
	}
	
	protected abstract ISExpression doTransform(final Invocation validated, final IMacroExpander expander, final IErrorHandler errors);
	
	protected boolean validateMacroParameters(final Invocation inv, final IErrorHandler errors) {
		boolean valid = true;

		for (final String s : getMissingArguments(inv.arguments.keySet())) {
			errors.handle(BasicError.at(inv.node, inv.name + " requires named argument " + s));
			valid = false;
		}

		for (final String s : getInvalidArguments(inv.arguments.keySet())) {
			valid = false;
			errors.handle(BasicError.at(inv.arguments.get(s), inv.name + " does not expect argument " + s));
		}

		if (inv.remainder.size() < getMinimumArgumentCount()) {
			errors.handle(BasicError.at(inv.node, 
										inv.name + " expects at least " + 
										getMinimumArgumentCount() + unnamedArgs(getMinimumArgumentCount())));
			valid = false;
		}

		if (inv.remainder.size() > getMaximumArgumentCount()) {
			errors.handle(BasicError.at(inv.remainder.get(getMaximumArgumentCount()), inv.name + " expects at most " + getMaximumArgumentCount() + unnamedArgs(getMaximumArgumentCount()) + ", but there are " + inv.remainder.size()));
			valid = false;
		}

		return valid;
	}
	
	private String unnamedArgs(final int count) {
		return count == 1 ? " unnamed argument" : " unnamed arguments";
	}
	
	@Override
	public MacroModel getModel() {
		final MacroModel.Builder b = MacroModel.builder();
		
		b.desc("Undocumented");
		
		for (final String s : getAllowedArgumentNames()) {
			b.keys().allow(s, "Undocumented");
		}
		
		for (final String s : getRequiredArgumentNames()) {
			b.keys().require(s, "Undocumented");
		}
		
		for (int i = 0; i<getMinimumArgumentCount(); i++) {
			b.pos().require("Undocumented");
		}
		
		if (getMaximumArgumentCount() == Integer.MAX_VALUE) {
			b.pos().remainder("Undocumented");	
		} else {
			for (int i = getMinimumArgumentCount(); i< getMaximumArgumentCount(); i++) {
				b.pos().allow("Undocumented");
			}
		}
		
		return b.build();
	}
	
	@Override
	public Optional<Node> getDefiningNode() {
		return Optional.absent();
	}
}
