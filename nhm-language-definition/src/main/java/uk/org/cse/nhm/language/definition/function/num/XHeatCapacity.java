package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("size.kw")
@Doc({"When used in a capex or opex function, this will be the capacity in kW of the heating technology being installed.",
      "When used in a probe, or in a calculation within a do statement, this will be the total kW of all heating technologies installed so far in that probe or package."})
public class XHeatCapacity extends XHouseNumber {

}
