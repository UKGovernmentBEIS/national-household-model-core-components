package uk.org.cse.nhm.language.definition.function.num.basic;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.BindRemainingArguments;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Category(CategoryType.ARITHMETIC)
public abstract class XBasicNumberFunction extends XNumber {

    public static final class P {

        public static final String children = "children";
    }

    private List<XNumber> children = new ArrayList<XNumber>();

    @Prop(P.children)

    @BindRemainingArguments
    @Doc("The other numbers to operate on.")
    public List<XNumber> getChildren() {
        return children;
    }

    public void setChildren(final List<XNumber> children) {
        this.children = children;
    }
}
