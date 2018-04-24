package uk.org.cse.nhm.language.definition.reporting.modes;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;


@Bind("mode.sum")
@Doc({"Report mode which adds up all the values observed during the simulation, then outputs the final results at the end.", 
	"Use this for reports which would normally produce a count or quantity of things (such as house.meter-reading, which is an amount of energy used since the last reading).",
	"WARNING: it is quite easy to use this report incorrectly. Make sure that the units of all the columns you capture are quantities which you want to add up every time they change."})
@SeeAlso(XIntegralMode.class)
public class XSumMode extends XReportMode {
}
