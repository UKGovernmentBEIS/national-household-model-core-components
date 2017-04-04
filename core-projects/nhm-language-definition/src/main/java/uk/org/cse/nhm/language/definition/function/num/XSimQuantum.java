package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.XScenario;

import com.larkery.jasb.bind.Bind;

@Bind("sim.quantum")
@Doc("The quantum being used in this simulation. This may not be the same as the weight of the current house")
@SeeAlso({XHouseWeight.class, XScenario.class})
public class XSimQuantum extends XNumber {
}
