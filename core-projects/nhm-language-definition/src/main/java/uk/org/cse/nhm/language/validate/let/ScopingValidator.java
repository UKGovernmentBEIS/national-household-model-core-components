package uk.org.cse.nhm.language.validate.let;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import uk.org.cse.nhm.language.definition.XCaseOtherwise;
import uk.org.cse.nhm.language.definition.XCaseWhen;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.XTarget;
import uk.org.cse.nhm.language.definition.function.num.XGet;
import uk.org.cse.nhm.language.definition.function.num.XSnapshotDelta;
import uk.org.cse.nhm.language.definition.function.num.XUnderFunction;
import uk.org.cse.nhm.language.definition.reporting.XDwellingsReport;
import uk.org.cse.nhm.language.definition.sequence.IScopingElement;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XSequenceFunction;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotAction;
import uk.org.cse.nhm.language.definition.sequence.XVarSetAction;
import uk.org.cse.nhm.language.definition.two.actions.XSetHookAction;
import uk.org.cse.nhm.language.validate.NoCyclesValidatorWithDelegates.IDelegate;

/*
 * This is maybe due a rewrite.
 * 
 * If we do rewrite it, we should make some tests, because it is a difficult and complicated problem.
 */
public class ScopingValidator implements IDelegate {
	static class ScopedValue {
		private final boolean snapshot;
		private final XElement definition;
		
		public ScopedValue(final boolean snapshot,final XElement definition) {
			super();
			this.snapshot = snapshot;
			this.definition = definition;
		}
		
		public boolean isSnapshot() {
			return snapshot;
		}
		public XElement getDefinition() {
			return definition;
		}
	}
	
	static class ScopingBlock {
		private final Map<String, ScopedValue> values = new HashMap<String, ScopedValue>();
		private final XElement element;
		
		private ScopingBlock(final XElement element) {
			this.element = element;
		}
		
		public boolean isFor(final XElement element) {
			return this.element == element;
		}

		public boolean contains(final String key) {
			return values.containsKey(key);
		}

		public ScopedValue get(final String key) {
			return values.get(key);
		}

		public static Optional<ScopingBlock> of(final XElement element) {
			if (element instanceof IScopingElement) {
				return Optional.of(new ScopingBlock(element));
			} else {
				return Optional.absent();
			}
		}
	}
	
	private final Deque<ScopingBlock> scopes = new LinkedList<>();
	private final Deque<XElement> elements = new LinkedList<>();
	private final HashSet<String> yieldedInTarget = new HashSet<>();
	private final HashSet<String> globals = new HashSet<>();
	private final Multimap<String, ScopingError> unboundValuesInThisTarget = HashMultimap.create();
	private final Multimap<String, ScopingError> globallyUnboundValues = HashMultimap.create();
	private final ImmutableList.Builder<ScopingError> errors = ImmutableList.builder();
	
	@Override
	public void visit(final XElement v) {
		final Optional<ScopingBlock> block = Optional.fromNullable(scopes.peek()); 
		
		// elements that can create or modify variables
		if (v instanceof XVarSetAction) {
			final XVarSetAction a = (XVarSetAction) v; 
            for (final XNumberDeclaration decl : a.getVariable()) {
                switch (decl.getOn()) {
				case House:
					if (elements.peek() instanceof XSequenceFunction) {
						errors.add(new ScopingError.WrongPlaceForBinding(a, decl));
					}
					break;
				case Event:
					// a yield
					yieldedInTarget.add(decl.getName());
					break;
				case Simulation:
					globals.add(decl.getName());
					break;
				default:
					break;
				}
            }
		} else if (v instanceof XSetHookAction) {
			final XSetHookAction set = (XSetHookAction) v;
            if (set.getVariable() != null) {
                globals.add(set.getVariable().getName());
            }
		} else if (v instanceof XSnapshotAction) {
			final XSnapshotAction a = (XSnapshotAction) v;
			if (a.getSnapshotName() != null) {
				if (block.isPresent() && block.get().isFor(elements.peek())) {
					block.get().values.put(a.getSnapshotName().getName(), new ScopedValue(true, a));
				} else {
					errors.add(new ScopingError.WrongPlaceForBinding(a));
				}
			}
		}
		
		// elements that can use variables
		if (v instanceof XGet) {
			final XNumberDeclaration d = ((XGet) v).getVar();
			
			if (d != null) {
				switch (d.getOn()) {
//				/case Local:
//					require(v, d.getName(), false);
//					break;
				case Event:
					require(v, d.getName(), false);
					break;
				case Simulation:
					require(v, d.getName(), false);
					break;
				default:
					break;
				}
			}
		} else if (v instanceof XUnderFunction) {
			if (((XUnderFunction) v).getSnapshot() != null) {
				require(v, ((XUnderFunction) v).getSnapshot().getName(), true);
			}
		} else if (v instanceof XSnapshotDelta) {
			if (((XSnapshotDelta) v).getBefore() != null && ((XSnapshotDelta)v).getAfter() != null) {
				require(v, ((XSnapshotDelta) v).getBefore().getName(), true);
				require(v, ((XSnapshotDelta) v).getAfter().getName(), true);
			}
		}
		
		// finally, if this is a scoping block, set it up
		{
			final Optional<ScopingBlock> newBlock = ScopingBlock.of(v);
			if (newBlock.isPresent()) {
				scopes.push(newBlock.get());
			}
			elements.push(v);
		}
	}
	
	private void require(final XElement where, final String var, final boolean snap) {
		if (var == null) return;
		final Optional<ScopedValue> find = lookup(var);
		if (find.isPresent()) {
			final ScopedValue sv = find.get();
			if (sv.isSnapshot() != snap) {
				errors.add(new ScopingError.WrongTypeOfBinding(where, var, snap));
			}
		} else {
			if (!snap) {
				unboundValuesInThisTarget.put(var, new ScopingError.NoSuchBinding(where, var, snap));
			} else {
				errors.add(new ScopingError.NoSuchBinding(where, var, snap));
			}
		}
	}
	
	private Optional<ScopedValue> lookup(final String key) {
		final Iterator<ScopingBlock> iterator = scopes.descendingIterator();
		while (iterator.hasNext()) {
			final ScopingBlock block = iterator.next();
			if (block.contains(key)) {
				return Optional.of(block.get(key));
			}
		}
		return Optional.absent();
	}
	
	public List<ScopingError> getErrors() {
		final Multimap<String, ScopingError> copyOfUnboundEtc = HashMultimap.create(unboundValuesInThisTarget);
		
		for (final String yielded : yieldedInTarget) {
			copyOfUnboundEtc.removeAll(yielded);
		}
		
		copyOfUnboundEtc.putAll(globallyUnboundValues);
		for (final String global : globals) {
			copyOfUnboundEtc.removeAll(global);
		}
		
		return ImmutableList.<ScopingError>builder().addAll(errors.build()).addAll(copyOfUnboundEtc.values()).build();
	}

	@Override
	public void doLeave(final XElement v) {
		if (!elements.isEmpty()) {
			final XElement e = elements.pop();
			if (!scopes.isEmpty() && scopes.peek().isFor(e)) {
				scopes.pop();
			}
		}
		
		if (v instanceof XTarget || v instanceof XCaseWhen || v instanceof XCaseOtherwise || v instanceof XDwellingsReport) {
			for (final String y : yieldedInTarget) {
				unboundValuesInThisTarget.removeAll(y);
			}
			yieldedInTarget.clear();
			globallyUnboundValues.putAll(unboundValuesInThisTarget);
			unboundValuesInThisTarget.clear();
		}
	}
	
	@Override
	public boolean doEnter(final XElement v) {
		return true;
	}
}
