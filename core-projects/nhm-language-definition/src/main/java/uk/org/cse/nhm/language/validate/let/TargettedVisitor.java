package uk.org.cse.nhm.language.validate.let;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.visit.IVisitor;

abstract class TargettedVisitor<T> implements IVisitor<XElement> {

	private final Class<T> type;

	protected TargettedVisitor(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public final boolean enter(XElement v) {
		return true;
	}

	@Override
	public final void visit(XElement v) {
		if (type.isInstance(v)) {
			doVisit(type.cast(v));
		}
	}
	
	protected abstract void doVisit (T v);

	@Override
	public final void leave(XElement v) {
		// NO-OP
	}
}
