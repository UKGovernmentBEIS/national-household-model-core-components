package uk.org.cse.nhm.language.definition;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.exposure.XExposure;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("case")
public class XCaseWhen extends XElement {
	public static final class P {
		public static final String NAME = "name";
		public static final String TEST = "test";
		public static final String EXPOSURE = "exposure";
		public static final String ACTION = "action";
	}
	
	private String name;
	private XBoolean test;
	private XExposure exposure;
	private XAction action;
	
	@Override
	
@BindNamedArgument
	@NotNull
	@Doc("The name for this case, which will appear in reports.")
	public String getName() {
		return name;
	}
	@Override
	public void setName(final String name) {
		this.name = name;
	}
	
	
	@Prop(P.TEST)
	@BindNamedArgument
	@Doc("A logical test which a house must pass to belong to this when")
	public XBoolean getTest() {
		return test;
	}
	public void setTest(final XBoolean test) {
		this.test = test;
	}
	
	
	@BindNamedArgument
	@Prop(P.EXPOSURE)
	@Doc("The exposure which determines when and how houses will be sampled from this when and have action taken against them.")
	public XExposure getExposure() {
		return exposure;
	}
	public void setExposure(final XExposure exposure) {
		this.exposure = exposure;
	}
	
	
	@Prop(P.ACTION)
	@Doc("Defines the action to apply to exposed houses from this when.")
	@BindNamedArgument
	public XAction getAction() {
		return action;
	}
	public void setAction(final XAction action) {
		this.action = action;
	}
}
