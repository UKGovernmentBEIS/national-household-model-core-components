package uk.org.cse.nhm.language.validate;

import uk.org.cse.commons.Glob;

import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.action.IXFlaggedAction;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.bool.house.XFlagsAre;

import uk.org.cse.nhm.language.validate.NoCyclesValidatorWithDelegates.IDelegate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;

public class FlagsValidator implements IDelegate {
    private final Multimap<String, Location> setLocations = HashMultimap.create();
    private final Multimap<Glob, Location> testLocations = HashMultimap.create();
    private final Multimap<Glob, Location> removeLocations = HashMultimap.create();

    private void test(final XElement element, final List<Glob> testFlags) {
        if (element.getLocation() == null) return;
        for (final Glob g : testFlags) {
            testLocations.put(g, element.getLocation());
        }
    }

    private void set(final XElement element, final List<Glob> setFlags) {
        if (element.getLocation() == null) return;
        for (final Glob g : setFlags) {
            if (g.isNegated()) {
                removeLocations.put(g, element.getLocation());
            } else if (!g.isWild()) {
                setLocations.put(g.getLiteralValue().get(), element.getLocation());
            }
        }
    }
    
    @Override
	public void visit(final XElement v) {
		if (v instanceof IXFlaggedAction) {
            final IXFlaggedAction xfa = (IXFlaggedAction) v;
            test(v, xfa.getTestFlags());
            set(v, xfa.getUpdateFlags());
        } else if (v instanceof XFlagsAre) {
            final XFlagsAre xfa = (XFlagsAre) v;
            test(v, xfa.getMatch());
        }
	}

	public Iterable<? extends IError> getProblems() {
		// check every test glob against every setting string
        // any flag set but not read or vice versa should produce a warning.
        
        final ImmutableList.Builder<IError> problems = ImmutableList.builder();
        final Set<Glob> neverSetButTested = new HashSet<>(testLocations.keySet());
        final Set<Glob> neverSetButRemoved = new HashSet<>(removeLocations.keySet());
        
        for (final String setFlag : setLocations.keySet()) {
            boolean flagWasSetButNotTested = true;
            boolean flagWasSetButNotRemoved = true;
            Iterator<Glob> iterator = neverSetButTested.iterator();

            while (iterator.hasNext()) {
                final Glob g = iterator.next();
                if (g.matches(setFlag, false)) {
                    flagWasSetButNotTested = false;
                    iterator.remove();
                }
            }
            
            iterator = neverSetButRemoved.iterator();
            
            while (iterator.hasNext()) {
                final Glob g = iterator.next();
                if (g.matches(setFlag, false)) {
                    iterator.remove();
                    flagWasSetButNotRemoved = true;
                }
            }

            if (flagWasSetButNotTested) {
                final String msg =
                    String.format("The flag '%s' is set here, but is never %s",
                                  setFlag,
                                  flagWasSetButNotRemoved ?
                                  "tested or removed" :
                                  "tested (although it is removed)"); 
                
                for (final Location loc : setLocations.get(setFlag)) {
                    problems.add(BasicError.warningAt(loc, msg));
                }
            }
        }

        for (final Glob g : neverSetButTested) {
            final String msg =
                String.format("The flag test '%s' used here does not match any of the flags set in this scenario",
                              g);
            for (final Location loc : testLocations.get(g)) {
                problems.add(BasicError.warningAt(loc, msg));
            }
        }

        for (final Glob g : neverSetButRemoved) {
            final String msg =
                String.format("The flag removal command '%s' used here does not remove any of the flags set in this scenario",
                              g);
            for (final Location loc : removeLocations.get(g)) {
                problems.add(BasicError.warningAt(loc, msg));
            }
        }

        return problems.build();
	}
	
	@Override
	public boolean doEnter(final XElement v) {
		return true;
	}
	
	@Override
	public void doLeave(final XElement v) {}
}
