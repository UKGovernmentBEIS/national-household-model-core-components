package uk.org.cse.nhm.language.definition.function.house;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XLightType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("house.lighting-proportion")
@Doc({"Produces the proportion of lighting in the house which is one of the desired types."})
public class XHouseLightingProportion extends XNumber {
	private List<XLightType> types = new ArrayList<>();

	@Size(min=1, message="At least one type of lighting needs to be specified")
	@Doc("The types of lighting to look for.")
	@BindRemainingArguments
	public List<XLightType> getTypes() {
		return types;
	}

	public void setTypes(List<XLightType> types) {
		this.types = types;
	}
}
