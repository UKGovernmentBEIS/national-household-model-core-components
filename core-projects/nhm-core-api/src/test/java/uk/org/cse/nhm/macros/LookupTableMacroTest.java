package uk.org.cse.nhm.macros;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.parse.IMacro;

public class LookupTableMacroTest extends BaseMacroTest {	
	
	private final LookupTableMacro TO_TEST = new LookupTableMacro("~lookup-table") {
		@Override
		protected String generateIdentifier() {
			return "some-guid";
		};
	};
	
	@Test
	public void normalLookupWorks() throws Exception {
		verify("normal-lookup.s", ImmutableList.<IMacro>of(TO_TEST));
		verify("normal-lookup-2.s", ImmutableList.<IMacro>of(TO_TEST));
	}
	
	@Test
	public void interpolateLookupWorks() throws Exception {
		verify("interpolating-lookup.s", ImmutableList.<IMacro>of(TO_TEST));
	}

    @Test
    public void lookupWithoutRowKeysWorks() throws Exception {
        verify("lookup-without-row-keys.s", ImmutableList.<IMacro>of(TO_TEST));
    }
}
