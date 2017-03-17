package uk.org.cse.nhm.language.definition.function.health;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;

@Doc("The permeability of the house; this is equivalent to (* 20 house.air-change-rate house.volume (/ 1 house.envelope-area))")
@Bind("house.permeability")
public class XPermeabilityFunction extends XHouseNumber {

}