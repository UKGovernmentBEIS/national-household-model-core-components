package com.larkery.jasb.sexp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;


public class Invocation {
	public final Seq node;
	public final String name;
	public final Map<String, Node> arguments;
	public final List<Node> remainder;
	
	public Invocation(final Seq node, final String name, final Map<String, Node> arguments,
			final List<Node> remainder) {
		super();
		this.node = node;
		this.name = name;
		this.arguments = arguments;
		this.remainder = remainder;
	}
	
	public static final Invocation of(final Node node, final IErrorHandler errors) {
		return of(node, errors, false);
	}
	
	public static final Invocation of(final Node node, final IErrorHandler errors, final boolean withComments) {
		if (node instanceof Seq) {
			final Seq seq = (Seq) node;
			if (seq.size() == 0) {
				errors.handle(BasicError.at(node, "An empty pair of parentheses was not expected here. A parenthesis will usually be followed by the name of a command."));
			} else if (seq.getDelimeter() != Delim.Paren) {
				errors.handle(BasicError.at(node, "You have used an opening bracket - '[' - where a parenthesis '(' was expected. Possibly you are trying to supply multiple values in a place where a single value is required."));
			} else {
				final Node head = seq.getHead();
				
				if (head instanceof Atom) {
					final Atom name = (Atom) head;
					final List<Node> tail = seq.getTail();
					final HashSet<String> seenArguments = new HashSet<String>();
					final ImmutableMap.Builder<String, Node> arguments = ImmutableMap.builder();
					final ImmutableList.Builder<Node> rest = ImmutableList.builder();
					
					String key = null;
					for (final Node argument : tail) {
						if (argument instanceof Comment) {
							if (withComments) {
								if (key == null) {
									rest.add(argument);
								}
							}
							continue;
						}
						
						final String thisKey;
						if (argument instanceof Atom) {
							final Atom a = (Atom) argument;
							if (a.getValue().endsWith(":")) {
								thisKey = a.getValue().substring(0, a.getValue().length()-1);
							} else {
								thisKey = null;
							}
						} else {
							thisKey = null;
						}
						
						if (key == null && thisKey != null) {
							key = thisKey;
						} else if (key != null) {
							if (seenArguments.contains(key)) {
								errors.handle(BasicError.at(argument, "repeated keyword " + key +" in " +name.getValue()));
								return null;
							} else {
								arguments.put(key, argument);
                                seenArguments.add(key);
							}
							key = null;
						} else {
							rest.add(argument);
						}
					}
					if (key != null) {
						errors.handle(BasicError.at(node, "unused keyword " + key +" at end of " +name.getValue()));
					}
					return new Invocation(seq, name.getValue(), arguments.build(), rest.build());
				} else {
					if (head == null) {
						errors.handle(BasicError.at(node, "An empty list was not expected here"));
					} else {
						errors.handle(BasicError.at(head, "An opening parenthesis - '(' - should always be followed by the name of a command, and never by another parenthesis."));
					}
				}
				
			}
		} else {
			errors.handle(BasicError.at(node, "a list was expected here, not a singular word"));
		}
		return null;
	}

	public static boolean isInvocation(final Seq node) {
		return of(node, IErrorHandler.NOP) != null;
	}
}
