package uk.org.cse.nhm.language.validate.let;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;
import uk.org.cse.nhm.language.definition.sequence.XSnapshotAction;

public abstract class ScopingError {
	private final XElement element;
	
	public static class NoSuchBinding extends ScopingError {
		private final String message;
		public NoSuchBinding(final XElement element, final String name, final boolean snapshot) {
			super(element);
			this.message = String.format("no %s value is bound under %s", snapshot ? "snapshot" : "numeric", name);
		}
		@Override
		public String getMessage() {
			return message;
		}
		@Override
		public String toString() {
			return message;
		}
	}
	
	public static class WrongTypeOfBinding extends ScopingError {
		private final String message;

		public WrongTypeOfBinding(final XElement element, final String name, final boolean snapshot) {
			super(element);
			this.message = String.format("the value bound under %s is not a %s", name, snapshot ? "snapshot" : "number");
		}
		
		@Override
		public String getMessage() {
			return message;
		}
		@Override
		public String toString() {
			return message;
		}
	}
	
	public static class WrongPlaceForBinding extends ScopingError {
		private final String message;

		public WrongPlaceForBinding(final XElement where, final XNumberDeclaration element) {
			super(where);
			message = String.format("%s is declared to be on %s; variables on %s cannot be modified by this element (most likely because it is not an action, but a function)",
					element.getName(), element.getOn(), element.getOn()
					);
		}
		
		public WrongPlaceForBinding(final XSnapshotAction a) {
			super(a);
			message = String.format("you can only take snapshots directly inside a (do) action, or the do-first: arguments to a choice or a selector");
		}

		@Override
		public String getMessage() {
			return message;
		}
		@Override
		public String toString() {
			return message;
		}
	}
	
	public ScopingError(final XElement element) {
		super();
		this.element = element;
	}

	public XElement getElement() {
		return element;
	}
	
	public abstract String getMessage() ;
}
