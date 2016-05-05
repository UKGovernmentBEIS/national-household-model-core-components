package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.measure.renewable.XSolarPhotovoltaicMeasure;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;


@Bind("house.has-solar-photovoltaic")
@Doc("A test that matches houses which have solar PV.")
@SeeAlso(XSolarPhotovoltaicMeasure.class)
public class XHasSolarPV extends XHouseBoolean {
}
