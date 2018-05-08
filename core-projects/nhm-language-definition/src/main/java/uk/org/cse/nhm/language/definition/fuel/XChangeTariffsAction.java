package uk.org.cse.nhm.language.definition.fuel;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;

@Bind("action.set-tariffs")
@Unsuitability({
    "The measure would leave the house without a tariff for any fuel (for example, if a dual-fuel tariff was removed and replaced with a single-fuel tariff)."
})
@Doc({
    "Each tariff listed will be added to the house.",
    "When a tariff is added, any existing tariffs for the same fuel types as the new tariff will be replaced.",
    "Other existing tariffs will remain in effect."
})
@Category(CategoryType.TARIFFS)
public class XChangeTariffsAction extends XFlaggedDwellingAction {

    public static class P {

        public static final String tariffs = "tariffs";
    }

    private List<XTariffBase> tariffs = new ArrayList<>();

    @Doc("The tariffs to add to the house.")
    @BindRemainingArguments
    @Prop(P.tariffs)

    public List<XTariffBase> getTariffs() {
        return tariffs;
    }

    public void setTariffs(final List<XTariffBase> tariffs) {
        this.tariffs = tariffs;
    }
}
