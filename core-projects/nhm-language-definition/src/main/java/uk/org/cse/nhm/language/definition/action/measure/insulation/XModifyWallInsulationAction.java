package uk.org.cse.nhm.language.definition.action.measure.insulation;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;

@Bind("action.set-wall-insulation")
@Doc({"An action which directly changes the properties of all wall insulation of a house to a specific value."})
@Unsuitability(alwaysSuitable = true)
public class XModifyWallInsulationAction extends XModifyInsulationAction {
}
