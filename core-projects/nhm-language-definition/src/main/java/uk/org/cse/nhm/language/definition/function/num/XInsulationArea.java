package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("size.m2")
@Doc({"When used in a capex or opex function, this will be the area of installed insulation, in m2.",
      "When used in a probe, or in a calculation within a do statement, this will be the total area of all surfaces insulated so far in that probe or package."})
public class XInsulationArea extends XNumber {

}
