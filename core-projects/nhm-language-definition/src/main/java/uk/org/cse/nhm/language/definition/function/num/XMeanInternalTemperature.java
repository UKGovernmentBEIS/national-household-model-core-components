package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("house.mean-internal-temperature")
@Doc(
		{"The mean internal temperature computed by the energy calculator for this house, averaged over the whole year.",
		 "In each month, this is the average value of the time/temperature curve produced by the heating schedule, with 'on' periods being at the",
		 "living area temperature and 'off' periods at the mean external temperature, modified to incorporate the cooling-down period after",
		 "heating is switched off, and some minor adjustments due to heating controls, type of heating system, and internal gains."
		}
)
public class XMeanInternalTemperature extends XHouseNumber {
	
}
