package uk.org.cse.nhm.language.definition;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.exposure.XExposure;


@Doc("This is the final condition for a case, which will be selected if none of the when conditions is appropriate.")
@Bind("otherwise")
public class XCaseOtherwise extends XElement {
	public static final class P {
		public static final String NAME = "name";
		public static final String EXPOSURE = "exposure";
		public static final String ACTION = "action";
	}
	
	private String name;
	private XExposure exposure;
	private XAction action;
	
	@Override
	
@BindNamedArgument
	@Prop(P.NAME)
	@Doc("A name for this otherwise, which will appear in the reports.")
	public String getName() {
		return name;
	}
	@Override
	public void setName(final String name) {
		this.name = name;
	}
	
	
	@Prop(P.EXPOSURE)
	@BindNamedArgument
	@Doc("An exposure function, which determines when the action will be applied to the houses which are in this otherwise and to which houses it will be applied.")
	public XExposure getExposure() {
		return exposure;
	}
	public void setExposure(final XExposure exposure) {
		this.exposure = exposure;
	}
	
	
	@Prop(P.ACTION)
	@Doc("The action to take on houses, when the exposure decides to take an action.")
	@BindNamedArgument
	public XAction getAction() {
		return action;
	}
	public void setAction(final XAction action) {
		this.action = action;
	}
}
