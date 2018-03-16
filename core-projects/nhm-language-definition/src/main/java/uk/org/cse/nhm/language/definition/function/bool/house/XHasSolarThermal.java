package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.measure.renewable.XSolarHotWaterMeasure;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Bind("house.has-solar-thermal")
@Doc("A test that matches dwelling which use solar thermal as part of a hot-water system")
@SeeAlso(XSolarHotWaterMeasure.class)
public class XHasSolarThermal extends XHouseBoolean {

}
