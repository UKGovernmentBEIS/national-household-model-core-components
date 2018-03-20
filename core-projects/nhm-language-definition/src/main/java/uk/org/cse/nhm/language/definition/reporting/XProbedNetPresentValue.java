package uk.org.cse.nhm.language.definition.reporting;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;


@Bind("probe.npv")
@Doc(
		{
			"Collects details from a net present value calculation, if they exist. These details are:",
			XProbedNetPresentValue.FIELD_NPV + ": the actual net present value.",
			XProbedNetPresentValue.FIELD_CAPEX + ": the capital cost contribution to the NPV.",
			XProbedNetPresentValue.DISCOUNTED_FUTURE+ ": the estimated future cost, discounted.",
			XProbedNetPresentValue.FUTURE + ": the estimated future cost, before discounting.",
		}
	)
public class XProbedNetPresentValue extends XProbedValue {
	public static final String FIELD_NPV = "NPV";
	public static final String FIELD_CAPEX = "CAPEX";
	public static final String DISCOUNTED_FUTURE = "DISCOUNTEDFUTURE";
	public static final String FUTURE = "FUTURE";
	
}
