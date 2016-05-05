package uk.org.cse.nhm.language.definition.function.bool.house;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import uk.org.cse.commons.Glob;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

@Doc("A more convenient version of house.test-flag which can test several flags at once.")
@Category(CategoryType.SETSANDFLAGS)
@Bind("house.flags-match")
public class XFlagsAre extends XHouseBoolean {
	public static final class P {
		public static final String match = "match";
	}
	
	private List<Glob> match = new ArrayList<>();
	
	@Doc("A sequence of tags to match; no tags prefixed with ! may be present and all other tags must be present.")
	
	@BindRemainingArguments
	public List<Glob> getMatch() {
		return match;
	}

	public void setMatch(final List<Glob> match) {
		this.match = match;
	}
}
