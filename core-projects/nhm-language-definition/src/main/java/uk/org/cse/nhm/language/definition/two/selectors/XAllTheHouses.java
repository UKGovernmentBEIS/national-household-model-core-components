package uk.org.cse.nhm.language.definition.two.selectors;

import java.util.Deque;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.two.hooks.XConstructHook;
import uk.org.cse.nhm.language.validate.ISelfValidating;

@Bind("all-houses")
@Doc("A set containing every house")
public class XAllTheHouses extends XSetOfHouses implements ISelfValidating {

    @Override
    public List<IError> validate(final Deque<XElement> context) {
        for (final XElement e : context) {
            if (e instanceof XConstructHook) {
                final String message;
                if (getSourceNode() == null) {
                    message = "Using all-houses as a default source group from within the on.construction command. You should probably add affected-houses as a source group.";
                } else {
                    message = "Using all-houses inside the on.construction command. You probably mean to use affected-houses instead.";
                }

                return ImmutableList.<IError>of(BasicError.warningAt(getLocation(), message));
            }
        }
        return ImmutableList.of();
    }
}
