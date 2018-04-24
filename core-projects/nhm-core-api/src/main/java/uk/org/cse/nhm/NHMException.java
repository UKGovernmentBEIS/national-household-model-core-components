package uk.org.cse.nhm;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Location;

import uk.org.cse.commons.names.Name;

public class NHMException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final List<Name> names;

    public NHMException(final List<Name> names, final String message) {
        super(message);
        this.names = ImmutableList.copyOf(names);
    }
    
    public String getModelStackTrace() {
        final StringBuffer sb = new StringBuffer();
        for (final Name name : names) {
            sb.append(String.format("in %s\n", name.getPath()));
            Location loc = name.getLocation().orNull();
            String how = "defined at";
            while (loc != null) {
                sb.append(String.format("\t%s %s:%s\n", how, loc.name, loc.line));
                if (loc.via.isPresent()) {
                    final Location.Via via = loc.via.get();
                    loc = via.location;
                    switch (via.type) {
                    default:
                    case Normal:
                        how = "defined at";
                        break;
                    case Include:
                        how = "from include";
                        break;
                    case Template:
                        how = "in template";
                        break;
                    }
                } else {
                    loc = null;
                }
            }
        }
        return sb.toString();
    }
}

