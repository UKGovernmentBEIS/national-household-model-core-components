package uk.org.cse.nhm.macros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.IMacroExpander;
import com.larkery.jasb.sexp.parse.MacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;
import com.larkery.jasb.sexp.parse.SimpleMacro;
import com.larkery.jasb.sexp.template.Templates;

/**
 * A macro which generates all possible combinations from some lists of values
 * 
 * for example
 * 
 * (generate-combinations
 * 		[a b c]
 * 		[d e f])
 * 
 * expands to
 * 
 * a d a e a f b d b e b f
 * 
 * To specify a wrapper for the expanded bits, you can use the template: keyword
 * 
 * (generate-combinations
 * 		template:(do all:true  @rest)
 * 
 * 		[a b c]
 * 		[d e f])
 * 
 * expands to
 * 
 * (do all:true  a d)
 * (do all:true  a e)
 * (do all:true  a f)
 * (do all:true  b d)
 * (do all:true  b e)
 * (do all:true  b f)
 * (do all:true  c d)
 * (do all:true  c e)
 * (do all:true  c f)
 * 
 * and so on.
 * 
 * Other macros in the result will be expanded.
 * 
 * 
 */
public class CombinationsMacro extends SimpleMacro {
	private static final String TEMPLATE = "template";
	private final String name;
	
	public CombinationsMacro(final String name) {
		this.name = name;
	}
	
	public CombinationsMacro() {
		this("generate-combinations");
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
		return ImmutableSet.of(TEMPLATE);
	}

	@Override
	protected int getMaximumArgumentCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMinimumArgumentCount() {
		return 0;
	}
	
	@Override
	protected ISExpression doTransform(final Invocation validated, final IMacroExpander expander, final IErrorHandler errors) {
		final List<Set<Node>> sources = new ArrayList<>();
		try {
			for (final Node n : validated.remainder) {
				if (n instanceof Comment) continue;
				if (n instanceof Seq) {
					if (((Seq)n).getDelimeter() == Delim.Bracket) {
						final ImmutableSet.Builder<Node> b = ImmutableSet.builder();
						for (final Node n2 : ((Seq)n).getNodes()) {
							if (n2 instanceof Comment) continue;
							b.addAll(Node.copyAll(expander.expand(n2)));
						}
						final Set<Node> s = b.build();
						if (s.isEmpty()) continue;
						sources.add(s);
					} else {
						sources.add(ImmutableSet.copyOf(Node.copyAll(expander.expand(n))));
					}
				}
			}
		} catch (final UnfinishedExpressionException ee) {
			throw new JasbErrorException(ee.getError());
        }

        if (Thread.interrupted()) {
            throw new JasbErrorException(BasicError.at(validated.node, "Validation terminated"));
        }

		final Set<List<Node>> values = Sets.cartesianProduct(sources);
		
        final Node unexpandedTemplateNode = validated.arguments.get(TEMPLATE);

        if (unexpandedTemplateNode instanceof Atom) {
            final ImmutableList<ISExpression> invocations = generateTemplateInvocations(validated, values, unexpandedTemplateNode);
			return SExpressions.inOrder(invocations);
		} else {
			final Optional<? extends IMacro> templates = defineLocalTemplate(validated, errors, unexpandedTemplateNode);
			
			if (!templates.isPresent()) {
				return SExpressions.empty();
			} else {
				final IMacro macro = templates.get();
				
				final ImmutableList.Builder<ISExpression> invocationsOfLocalTemplate = 
						ImmutableList.builder();
				
				for (final List<Node> n : values) {
					invocationsOfLocalTemplate.add(
							MacroExpander.expand(ImmutableList.of(macro), 
								Seq.builder(validated.node.getLocation(), Delim.Paren)
									.add("implicit-template")
									.addAll(n)
									.build(validated.node.getEndLocation())
							, errors));
				}
				
				return SExpressions.inOrder(invocationsOfLocalTemplate.build());
			}
		}
	}


	private Optional<? extends IMacro> defineLocalTemplate(final Invocation validated, final IErrorHandler errors, final Node templateNode) {
		final Node templateNode_;
		if (templateNode == null) {
			templateNode_ = Atom.create("@rest", validated.node.getLocation());
		} else {
			templateNode_ = templateNode;
		}
		
		final Optional<? extends IMacro> templates = Templates.parse(
				Seq.builder(validated.node.getLocation(), Delim.Paren)
				.add("template")
				.add("implicit-template")
				.add(
					Seq.builder(validated.node.getLocation(), Delim.Bracket)
						.add("@rest")
						.build(validated.node.getLocation())
					)
				.add(templateNode_)
				.build(validated.node.getLocation())
				, 
				errors);
		return templates;
	}

	private ImmutableList<ISExpression> generateTemplateInvocations(final Invocation validated, final Set<List<Node>> values, final Node templateNode) {
		// presume template node is a template to invoke
		final ImmutableList.Builder<ISExpression> b = ImmutableList.builder();
		
		for (final List<Node> n : values) {
			final Seq invocationOfTemplate = 
					Seq.builder(validated.node.getLocation(), Delim.Paren)
					.add(templateNode)
					.addAll(n).build(validated.node.getEndLocation());
			
			b.add(invocationOfTemplate);
		}
		
		final ImmutableList<ISExpression> invocations = b.build();
		return invocations;
	}
	
	@Override
	public MacroModel getModel() {
		return MacroModel.builder()
				.desc("Generates all possible combinations of values chosen from its unnamed arguments.")
				.keys().allow("template", "Either the name of a template, which will be used to wrap each combination, or the body of a temporary template (this will not validate within a template definition, however").and()
				.pos().remainder("A sequence of bracketed lists; a combination will be produced for each way of taking one value from each of these lists.").and()
				.build();
	}
}
