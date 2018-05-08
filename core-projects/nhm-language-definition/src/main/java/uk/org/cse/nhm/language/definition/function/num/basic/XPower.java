package uk.org.cse.nhm.language.definition.function.num.basic;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("pow")
@Doc({
    "Computes its first argument raised to the power of its second, all raised to the power of its third, and so on."
})
public class XPower extends XBasicNumberFunction {
}
