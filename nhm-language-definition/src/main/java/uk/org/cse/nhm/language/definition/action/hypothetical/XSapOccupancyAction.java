package uk.org.cse.nhm.language.definition.action.hypothetical;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("sap.occupancy")
@Doc({"Modifies the house to have SAP standard occupancy.",
	"This is intended for use within under, to estimate SAP energy use."})
public class XSapOccupancyAction extends XCounterfactualAction {

}
