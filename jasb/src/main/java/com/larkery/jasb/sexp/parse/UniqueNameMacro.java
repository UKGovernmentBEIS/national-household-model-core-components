package com.larkery.jasb.sexp.parse;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public class UniqueNameMacro extends SimpleMacro {
	private final String name;
	
	public UniqueNameMacro(final String name) {
		super();
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	protected Set<String> getRequiredArgumentNames() {
		return Collections.emptySet();
	}

	@Override
	protected Set<String> getAllowedArgumentNames() {
		return Collections.emptySet();
	}

	@Override
	protected int getMaximumArgumentCount() {
		return 1;
	}

	@Override
	protected int getMinimumArgumentCount() {
		return 0;
	}

	@Override
	protected ISExpression doTransform(final Invocation validated, final IMacroExpander expander, final IErrorHandler errors) {
		final String uuid = UUID.randomUUID().toString();
		if (!validated.remainder.isEmpty()) {
			Node n;
			try {
				n = Node.copy(expander.expand(validated.remainder.get(0)));
				
				if (n instanceof Atom) {
					return Atom.create(
							String.format("*%s-%s*", ((Atom) n).getValue(), uuid),
							validated.node.getLocation()
							);
				}
			} catch (final UnfinishedExpressionException e) {
			}
		}
		
		return Atom.create(
				String.format("*unique-name-%s*", uuid)
				,validated.node.getLocation());
	}
	
	@Override
	public MacroModel getModel() {
		return MacroModel.builder()
				.desc("A macro which will expand to a globally unique name when used.")
				.desc("This is useful for creating temporary variables within a template, in a way which will not conflict with any other templates")
				.pos()
					.allow("A string which will be used as part of the name - this will be expanded before use.")
				.and()
				.build();
	}
}
