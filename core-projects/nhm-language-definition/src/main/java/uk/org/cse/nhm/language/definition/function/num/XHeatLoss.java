package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

import com.larkery.jasb.bind.Bind;

@Doc({
	"Get the specific heat loss computed by the energy calculator for this house.",
	"This is the number of watts required to keep the house warm per degree of",
	"temperature difference between the inside and the outside.",
	"It is composed of the radiative heat loss (the sum of products of u-value and area",
	"for external surfaces) and the ventilation heat loss (the heat lost due to air changes",
	"through flues, chimneys, permeable surfaces and so on).",
	"The SAP notion of the specific heat loss <emphasis>parameter</emphasis> is the ratio between",
	"the specific heat loss and the total floor area of the house."
	})
@Bind("house.heat-loss")
@SeeAlso(XPeakHeatDemand.class)
public class XHeatLoss extends XHouseNumber {
	
}
