package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;

@Bind("house.surface-area")
@Doc("Surface area of the house, in square meters, please not in the case of flats this includes the top ceiling area")
public class XSurfaceArea extends XHouseNumber {

}