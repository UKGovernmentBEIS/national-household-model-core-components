package com.larkery.jasb.sexp.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.NodeBuilder;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.Editor;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.MacroExpander;
import com.larkery.jasb.sexp.template.Template.Argument;
import com.larkery.jasb.sexp.template.Template.NamedArgument;
import com.larkery.jasb.sexp.template.Template.NumberedArgument;
import com.larkery.jasb.sexp.template.Template.RestArgument;

public class Templates {
	public static ISExpression expand(final ISExpression input, final IErrorHandler errors) {
		final NodeBuilder builder = NodeBuilder.create();
			
		final List<IMacro> templates = extract(input, builder, errors);
		
		try {
			return MacroExpander.expand(templates, builder.getOrEmpty(), errors);
		} catch (final UnfinishedExpressionException uee) {
			final Node best = uee.getBestEffort();
			errors.handle(BasicError.at(uee.getUnclosed(), 
					"Unfinished expression encountered whilst extracting templates"));
			return best;
		}
	}
	
	public static List<IMacro> extract(final ISExpression input, final ISExpressionVisitor output, final IErrorHandler errors) {
		final ImmutableList.Builder<IMacro> templates = ImmutableList.builder();
		
		final Set<String> names = new HashSet<>();
		
		input.accept(new Editor(output) {
			@Override
			protected ISExpression edit(final Seq cut) {
				final Optional<? extends IMacro> template = parse(cut, errors);
				
				if (template.isPresent()) {
					final IMacro template_ = template.get();
					if (names.contains(template_.getName())) {
						errors.error(cut, "redefinition of template %s", template_.getName());
					} else {
						names.add(template_.getName());
						templates.add(template_);
					}
				}
				
				return SExpressions.empty();
			}
			
			@Override
			protected Action act(final String name) {
				if (name.equals("template")) {
					return Action.SingleEdit;
				} else {
					return Action.Pass;
				}
			}
		});
		
		return templates.build();
	}
	
	public static Optional<? extends IMacro> parse(final Seq definition, final IErrorHandler errors) {
		Preconditions.checkNotNull(definition, "A sequence node was expected, but null was provided");
		Preconditions.checkNotNull(errors, "An error handler was expected, but null was provided");
		
		final List<Node> exceptComments = definition.exceptComments();
		
		if (exceptComments.isEmpty()) {
			errors.handle(BasicError.at(definition, "internal error - a template cannot be constructed from an empty expression"));
			return Optional.absent();
		}
		
		final Node first = exceptComments.get(0);
		if (!(first instanceof Atom && ((Atom) first).getValue().equals("template"))) {
			errors.handle(BasicError.at(first, "the first element in a template definition should be the word template"));
			return Optional.absent();
		}
		
		if (exceptComments.size() < 2 || (!(exceptComments.get(1) instanceof Atom))) {
			errors.handle(BasicError.at(exceptComments.get(1), "the second word in a template definition should be the name of the template"));
			return Optional.absent();
		}
		
		if (exceptComments.size() < 3 || (!(exceptComments.get(2) instanceof Seq)) || ((Seq) exceptComments.get(2)).getDelimeter() != Delim.Bracket) {
			errors.handle(BasicError.at(exceptComments.get(2), "the third term in a template definition should be an argument list in square brackets"));
			return Optional.absent();
		}
	
		final Atom templateName = (Atom) exceptComments.get(1);
		final Seq templateArguments = (Seq) exceptComments.get(2);
		
		return parse(definition, templateName.getValue(), templateArguments.exceptComments(), exceptComments.subList(3, exceptComments.size()), errors);
	}
	
	private static Optional<? extends IMacro> parse(final Seq definition, final String name, final List<Node> arguments, final List<Node> body, final IErrorHandler errors) {
		final ImmutableList.Builder<NamedArgument> namedArguments = ImmutableList.builder();
		final ArrayList<NumberedArgument> numberedArguments = new ArrayList<>();
		Optional<RestArgument> restArgument = Optional.absent();
		
		final HashSet<String> usedExternalNames = new HashSet<>();
		final HashSet<String> usedInternalNames = new HashSet<>();
		final ListIterator<Node> it = arguments.listIterator();
		
		while (it.hasNext()) {
			final Node n = it.next();
			final Location l = n.getLocation();
			final String[] names;
			final Optional<ISExpression> defaultValue;
			if (n instanceof Atom) {
				defaultValue = Optional.absent();
				it.previous();
				names = readNames(it, errors);
			} else if (n instanceof Seq) {
				final Seq s = (Seq) n;
				if (s.getDelimeter() != Delim.Bracket) {
					errors.handle(BasicError.at(s, "template arguments should be names, or names with default values enclosed in square brackets"));
					return Optional.absent();
				}
				final ListIterator<Node> it2 = s.exceptComments().listIterator();
				if (!it2.hasNext()) {
					errors.handle(BasicError.at(s, "unexpected empty list in template argument list"));
					return Optional.absent();
				} else {
					names = readNames(it2, errors);
					final ImmutableList<Node> remainder = ImmutableList.copyOf(it2);
					defaultValue = Optional.of(SExpressions.inOrder(remainder));
				}
			} else {
				names = null;
				defaultValue = null;
			}
			
			if (names != null) {
				if (names.length == 0) {
					return Optional.absent();
				} else if (names.length < 3) {
					final String externalName, internalName;
					if (names.length == 1) {
						externalName = names[0];
						internalName = externalName.substring(1);
					} else {
						externalName = names[0].substring(0, names[0].length() - 1);
						internalName = names[1];
					}
					
					final Optional<? extends Argument> parsedArgument = 
							Argument.of(
									l, 
									externalName,
									internalName,
									defaultValue,
									errors);
					if (parsedArgument.isPresent()) {
						final Argument parsedArgument2 = parsedArgument.get();
						
						if (usedInternalNames.contains(parsedArgument2.getInternalName())) {
							errors.error(l, "two arguments have been given the internal name %s", parsedArgument2.getInternalName());
							return Optional.absent();
						}
													
						if (!parsedArgument2.getExternalName().equals("") && usedExternalNames.contains(parsedArgument2.getExternalName())) {
							errors.error(l, "two arguments have been given the external name %s", parsedArgument2.getExternalName());
							return Optional.absent();
						}
						
						usedInternalNames.add(parsedArgument2.getInternalName());
						usedExternalNames.add(parsedArgument2.getExternalName());
						
						if (parsedArgument2 instanceof NamedArgument) {
							namedArguments.add((NamedArgument) parsedArgument2);
						} else if (parsedArgument2 instanceof NumberedArgument) {
							numberedArguments.add((NumberedArgument) parsedArgument2);
						} else if (parsedArgument2 instanceof RestArgument) {
							restArgument = Optional.of((RestArgument) parsedArgument2);
						}
					} else {
						return Optional.absent();
					}
				} else {
					throw new RuntimeException("readNames returned a list of more than two parts");
				}
			}
		}
		
		// we have collected all the arguments; now we need to validate the numbered arguments and sort them
		Collections.sort(numberedArguments);
		
		boolean hadDefaultValue = false;
		
		final ListIterator<NumberedArgument> it2 = numberedArguments.listIterator();
		while (it2.hasNext()) {
			final NumberedArgument narg = it2.next();
			
			if (narg.getIndex() != it2.nextIndex()) {
				errors.error(narg.getLocation(), "numbered argument %d is missing, but %d is defined - numbered arguments should be contiguous", it2.nextIndex(), narg.getIndex());
				return Optional.absent();
			}
			
			if (narg.hasDefault()) {
				hadDefaultValue = true;
			} else if (hadDefaultValue) {
				errors.error(narg.getLocation(), "numbered argument %d has no default value, but a preceding numbered argument has a default value, which is not allowed", narg.getIndex());
				return Optional.absent();
			}
		}
		
		// finally validate set of internal names
		final ISExpression body_ = SExpressions.inOrder(body);
		body_.accept(new ISExpressionVisitor() {
			Location loc;
			@Override
			public void open(final Delim delimeter) {
			}
			
			@Override
			public void locate(final Location loc) {
				this.loc = loc;
			}
			
			@Override
			public void comment(final String text) {
			}
			
			@Override
			public void close(final Delim delimeter) {
			}
			
			@Override
			public void atom(final String string) {
				if (string.startsWith("@") && !(usedInternalNames.contains(string.substring(1)))) {
					errors.error(loc, "template argument list does not contain an argument named %s", string);
				}
			}
		});
		
		return Optional.of(new Template(definition, name, body_, namedArguments.build(), numberedArguments, restArgument));
	}

	
	
	private static String[] readNames(final ListIterator<Node> it, final IErrorHandler errors) {
		if (it.hasNext()) {
			Node next = it.next();
			if (next instanceof Atom) {
				final String firstPart = ((Atom) next).getValue();
				if (firstPart.endsWith(":")) {
					if (it.hasNext()) {
						next = it.next();
						if (next instanceof Atom) {
							return new String[] {firstPart,((Atom) next).getValue()}; 
						} else {
							errors.handle(BasicError.at(next, "if a template argument has an internal name, the internal name should be an atom"));
						}
					} else {
						errors.handle(BasicError.at(next, "template arguments with a : must be followed by the internal name for the argument"));
					}
				} else {
					return new String []{firstPart};
				}
			} else {
				errors.handle(BasicError.at(next, "template arguments should be atoms, not expressions; " + next + " is an expression"));
			}
		} else {
			throw new IllegalArgumentException("Attempted to read a template argument name from an empty iterator, which should not happen");
		}
		return new String[0];
	}
}
