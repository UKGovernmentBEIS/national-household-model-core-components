package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.XScenario;

@Doc({
    "Evaluates to the weight of the current house. This is the number of real houses that the current simulated house represents.",
    "If this is used inside the 'weight-by:' property of the scenario, then this will be the dwelling case weight from the survey, because the stock has not been divided into multiple simulated houses by that point."
})
@Bind("house.weight")
@SeeAlso({XScenario.class, XSimQuantum.class})
public class XHouseWeight extends XHouseNumber {

}
