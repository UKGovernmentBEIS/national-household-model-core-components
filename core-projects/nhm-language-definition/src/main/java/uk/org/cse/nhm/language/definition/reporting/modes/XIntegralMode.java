package uk.org.cse.nhm.language.definition.reporting.modes;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

import com.larkery.jasb.bind.Bind;


@Bind("mode.integral")
@Doc({"Report mode which calculates the integral with respect to time (measured in years) of all the values observed during the simulation, then records the results.",
	"Use this for reports which would normally produce a rate of things happening over a time period (such as house.energy-use, which is the rate of energy used per year).",
	"WARNING: it is quite easy to use this report incorrectly. Make sure that the units of all the columns you capture are rates with respect to time."})
@SeeAlso(XSumMode.class)
public class XIntegralMode extends XReportMode {
}
