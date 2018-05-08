package uk.org.cse.nhm.language.definition.function.health;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Category(CategoryType.HEALTH)
@Doc({"Computes a standardised internal temperature, per the methodology given in",
    "'Health effects of home efficiency interventions in England: a modelling study.', Hamilton et al.",
    " 27th April 2015.",
    "This is an estimate of the internal temperature likely to be found in a house based on the heat loss",
    "and heating efficiency (which determine an 'e-value'). The estimate is based on an empirically determined relationship."
})
@Bind("house.sit")
public class XSITFunction extends XHouseNumber {

}
