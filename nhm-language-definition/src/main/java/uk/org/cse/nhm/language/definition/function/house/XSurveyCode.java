package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@Bind("house.survey-code")
@Doc({"Returns the survey code (AACODE) that was associated with the DTO entries which defined this house.",
	"Note that because of the quantum many houses may have the same survey code during a run, and can be ",
	"in different states, so the survey code is not a good identifier."})
@RequireParent(IHouseContext.class)
@Category(CategoryType.HOUSEPROPERTIES)
public class XSurveyCode extends XFunction {

}
