package uk.org.cse.nhm.language.definition.action.hypothetical;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("sap.occupancy")
@Doc({
    "Used within under, modifies the house to have SAP standard occupancy.",
    "Has no effect in when the energy calculator is in SAP 2012 mode, as occupancy will already have been set to SAP occupancy in that case.",})
public class XSapOccupancyAction extends XCounterfactualAction {
}
