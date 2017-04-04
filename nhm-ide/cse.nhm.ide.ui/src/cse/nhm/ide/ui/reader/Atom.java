package cse.nhm.ide.ui.reader;

public class Atom extends Form {
    public final boolean quoted;
    public final String string;
    Atom(final long offset,final boolean quoted, final String s) {
    	super(offset, offset+s.length());
        this.quoted = quoted;
        this.string = s;
    }

    @Override
    public String toString() {
        if (this.quoted) {
            return "\"" + this.string +  "\"";
        } else {
            return this.string;
        }
    }
    
    @Override
    public Form findContainer(final int offset) {
    	if (contains(offset)) {
    		return this;
    	} else {
    		return null;
    	}
    }
    
    @Override
    public boolean isEmpty() {
    	return false;
    }
}
