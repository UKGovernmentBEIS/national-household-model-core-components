package uk.org.cse.nhm.macros;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.parse.IMacro;

public class TransposeMacroTest extends BaseMacroTest {

    @Test
    public void transposeWorks() throws Exception {
        verify("transpose.s", ImmutableList.<IMacro>of(new TransposeMacro("~transpose")));
    }
}
