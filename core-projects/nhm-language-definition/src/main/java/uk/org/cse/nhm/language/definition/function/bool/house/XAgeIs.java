package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("house.age-is")
@Doc({
    "A numerical test on the age of a house, relative to the current simulation year."
})
public class XAgeIs extends XIntegerIs {

}
