package cse.nhm.ide.ui.reader;

public class Expr extends Form {
    public final char delimiter;
    public final Form[] children;
    
    Expr(final long offset, final long eoffset, final char delim, final Form[] child) {
    	super(offset, eoffset+1);
        this.delimiter = delim;
        this.children = child;
        for (final Form f : this.children) {
        	f.up = this;
        }
        for (int i = 0; i<this.children.length; i++) {
        	this.children[i].up = this;
        	this.children[i].prev = (i == 0 ? null : this.children[i-1]);
        	this.children[i].next = (i+1 == this.children.length ? null : this.children[i+1]);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();

        sb.append(this.delimiter);
        boolean sp = false;
        for (final Form f : this.children) {
            if (sp) sb.append(" ");
            else sp = true;
            sb.append(String.valueOf(f));
        }
        sb.append(Form.opposite(this.delimiter));
        
        return sb.toString();
    }
    
    @Override
	public Form findContainer(final int offset) {
    	if (contains(offset)) {
    		for (final Form f : this.children) {
    			final Form c = f.findContainer(offset);
    			if (c != null) return c;
    		}
    		return this;
    	} else {
    		return null;
    	}
    }
    
    @Override
    public boolean isEmpty() {
    	return this.children.length == 0;
    }

	public Expr findExpr(final int offset) {
		final Form f = findContainer(offset);
		
		if (f instanceof Expr) {
			return (Expr) f;
		} else if (f instanceof Atom) {
			return f.up;
		} else {
			return null;
		}
	}
}


