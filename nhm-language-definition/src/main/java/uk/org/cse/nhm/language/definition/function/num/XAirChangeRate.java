package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;

import com.larkery.jasb.bind.Bind;

@Bind("house.air-change-rate")
@Doc("Produces the mean over months of the year of the air change rate, excluding the effect of occupant behaviour on air change rate. This is equivalent to (22) in the SAP worksheet.")
public class XAirChangeRate extends XHouseNumber implements IEnergyFunction {
    
}

