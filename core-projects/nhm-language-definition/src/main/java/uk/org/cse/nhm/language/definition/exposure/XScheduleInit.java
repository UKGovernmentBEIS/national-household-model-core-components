package uk.org.cse.nhm.language.definition.exposure;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("schedule.init")
@Doc({"This scheduler exposes houses in the group to the action once at the start of the simulation."})
public class XScheduleInit extends XSchedule {
	public static final class P {
		public static final String sampler = "sampler";
	}
	private XSampler sampler = createEveryoneSampler();

	private XSampler createEveryoneSampler() {
		final XProportionSampler out = new XProportionSampler();
		
		out.setProportion(1d);
		
		return out;
	}

	@NotNull(message = "schedule.init must have a sampler")
	@Prop(P.sampler)
	@Doc("A sampler to use, so that schedule.init need not apply to all houses in the source group.")
	@BindPositionalArgument(0)
	public XSampler getSampler() {
		return sampler;
	}

	public void setSampler(XSampler sampler) {
		this.sampler = sampler;
	}
}
