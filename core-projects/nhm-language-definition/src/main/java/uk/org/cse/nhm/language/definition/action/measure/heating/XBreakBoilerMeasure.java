package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;

@Bind("measure.break-boiler")
@Doc({
    "Removes a household's boiler.",
    "Primary space heating will be provided by assumed portable electric heaters as specified in SAP Appendix A.",
    "Water heating will be provided by the secondary water heater if present, or by assumed electric heating if not."
})
@Unsuitability(value = {"no boiler is present"})
public class XBreakBoilerMeasure extends XMeasure {
}
