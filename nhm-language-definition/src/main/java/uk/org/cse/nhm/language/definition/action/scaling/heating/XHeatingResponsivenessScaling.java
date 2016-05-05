package uk.org.cse.nhm.language.definition.action.scaling.heating;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.scaling.XScalingMeasure;
import uk.org.cse.nhm.language.definition.function.num.XSpaceHeatingResponsiveness;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("scale.responsiveness")
@Doc({
	"An action which modifies a heating system by modifying its responsiveness.",
	"New responsiveness = old responsiveness * (1 + scaling).",
	"The resulting new responsiveness must be between 0 and 1 inclusive. It the value lies outside of these bounds, it will be clamped to fit them.",
	"Responsiveness is a number defined by SAP which indicates how quickly a heating system can adapt to a change in temperature.",
})
@SeeAlso(XSpaceHeatingResponsiveness.class)
public class XHeatingResponsivenessScaling extends XScalingMeasure {
	public static final double Min = 0.0;
	public static final double Max = 1.0;
	
	private List<XSpaceHeatingSystem> of = new ArrayList<>();

	@Prop(P.of)
	@Doc("Determines which heating systems the scaling will be applied to. Any systems which are not present in the dwelling will be ignored..")
	@Size(min = 1, message = "scale.responsiveness must have an 'of' attribute.")
	@BindNamedArgument
	public List<XSpaceHeatingSystem> getOf() {
		return of;
	}

	public void setOf(final List<XSpaceHeatingSystem> of) {
		this.of = of;
	}
}