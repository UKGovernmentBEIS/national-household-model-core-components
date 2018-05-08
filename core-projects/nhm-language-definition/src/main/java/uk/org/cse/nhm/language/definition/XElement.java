package uk.org.cse.nhm.language.definition;

import com.larkery.jasb.bind.AfterReading;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.commons.names.Path;
import uk.org.cse.nhm.language.visit.impl.AdaptableVisitable;

/**
 * The base class for all elements in the language.
 *
 * It is transient, since it has nothing in it.
 *
 * @author hinton
 *
 */
public abstract class XElement extends AdaptableVisitable<XElement> implements IIdentified {

    private Path path = null;

    private String name;
    private Node node;
    private Name myName = null;
    private int sequence = 1;

    @Doc({"The command's name.",
        "You might think of this as a 'caption' or 'title'.",
        "",
        "If this command produces output, the name will be used to refer to it in the output.",
        "",
        "For example, if this value is being captured in a probe, the name will title the column produced.",
        "",
        "Some elements can be referred to by name (for example definitions) by writing the name prefixed with the hash symbol."
    })
    @BindNamedArgument
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    private String inventName() {
        String inventedName = "unknown";
        if (node == null) {
            if (getClass().isAnnotationPresent(Bind.class)) {
                inventedName = "implicit " + getClass().getAnnotation(Bind.class).value();
            }
        } else {
            try {
                boolean complex = false;
                Atom firstAtom = null;
                if (node instanceof Seq) {
                    for (final Node n : (Seq) node) {
                        if (n instanceof Seq) {
                            complex = true;
                        } else if (firstAtom == null && n instanceof Atom) {
                            firstAtom = (Atom) n;
                        }
                    }
                }

                if (complex && firstAtom != null) {
                    inventedName = firstAtom.getValue();
                } else {
                    inventedName = String.valueOf(node);
                }
            } catch (final RuntimeException e) {
            }
        }
        return inventedName;
    }

    @Override
    public final Name getIdentifier() {
        if (myName == null) {
            final String name = getName() == null ? inventName() : getName();
            myName = Name.of(name,
                    Path.get(name, path),
                    sequence,
                    getLocation());
        }
        return myName;
    }

    public final Location getLocation() {
        return node == null ? null : node.getLocation();
    }

    @AfterReading
    public final void setSourceNode(final Node node) {
        this.node = node;
    }

    public final Node getSourceNode() {
        return this.node;
    }

    @Override
    public String toString() {
        return getIdentifier().toString();
    }

    public final void setPath(final Path path) {
        this.path = path;
    }

    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }
}
