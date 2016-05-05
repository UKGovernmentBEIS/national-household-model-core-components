package uk.org.cse.nhm.language.definition.two.selectors;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("sample")
@Doc("construct a set by sampling from another set")
public class XSampledSet extends XSetOfHouses {
	public static final class P {
		public static final String size = "size";
		public static final String source = "source";
	}
	private XNumber size;
	private XSetOfHouses source = new XAllTheHouses();
	
	@BindPositionalArgument(0)
	@NotNull(message = "sample must have a size as its first argument")
	@Doc("the size of the sample; if this is less than 1, it is taken to be a proportion; otherwise, it is rounded and used as a count")
	public XNumber getSize() {
		return size;
	}
	public void setSize(final XNumber size) {
		this.size = size;
	}
	
	@Doc("the source group from which to sample")
	@BindPositionalArgument(1)
	public XSetOfHouses getSource() {
		return source;
	}
	public void setSource(final XSetOfHouses source) {
		this.source = source;
	}
}
