package uk.org.cse.nhm.language.definition.action;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@XmlRootElement(name="house.flag")
@Doc("Sets or clears flags on the house to which it is applied.")
@Bind("house.flag")
@Category(CategoryType.SETSANDFLAGS)
public class XFlagAction extends XFlaggedDwellingAction {
	public static final class P {
		public static final String flags = "flags";
	}
	
	private List<Glob> flags = new ArrayList<>();

	@BindRemainingArguments
	@Prop(P.flags)
	@Size(min = 1, message="house.flag must contain at least one flag to set or clear.")
	@Doc("A sequence of flags to set or clear. Flags prefixed with ! will be cleared, while other flags will be set.")
	public List<Glob> getFlags() {
		return flags;
	}

	public void setFlags(final List<Glob> flags) {
		this.flags = flags;
	}
	
	@Override
	public List<Glob> getUpdateFlags() {
		return getFlags(); // We hijack the AutoFlagInterceptor to sort this out for us. 
	}
}
