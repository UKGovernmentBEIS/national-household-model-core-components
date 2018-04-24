package uk.org.cse.nhm.language.definition.action.hypothetical;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.context.calibration.XCalibrationContext;

@Doc({
		"After applying this action within an under or a snapshot, the house's calibrated and uncalibrated energy use will be the same for all purposes.",
		"Consequently, things like predicted fuel bills, carbon emissions, net present value, and anything else which depends on energy use will be uncalibrated."
})
@Bind("action.decalibrate-energy")
@SeeAlso(XCalibrationContext.class)
@Category(CategoryType.CALIBRATION)
public class XDecalibrateEnergyAction extends XCounterfactualAction {

}
