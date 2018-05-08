package uk.org.cse.nhm.macros;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.parse.IMacro;

public class MatchMacroTest extends BaseMacroTest {

    @Test
    public void simpleMatchWorks() throws Exception {
        verify("match1.s", ImmutableList.<IMacro>of(new MatchMacro()));
        verify("match2.s", ImmutableList.<IMacro>of(new MatchMacro()));
    }

    @Test
    public void defaultMatchWorks() throws Exception {
        verify("defaultmatch.s", ImmutableList.<IMacro>of(new MatchMacro()));
    }

    @Test
    public void errorWorks() throws Exception {
        verifyError("matchwitherror.s", ImmutableList.<IMacro>of(new MatchMacro(), new ErrorMacro()));
        verify("matchwithouterror.s", ImmutableList.<IMacro>of(new MatchMacro(), new ErrorMacro()));
    }
}
