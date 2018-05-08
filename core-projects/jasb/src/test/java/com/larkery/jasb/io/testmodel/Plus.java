package com.larkery.jasb.io.testmodel;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

/**
 * (+ a b c d)
 *
 * @author hinton
 *
 */
@Bind("+")
public class Plus extends Arithmetic {

    public List<Arithmetic> terms = new ArrayList<>();

    @BindRemainingArguments
    public List<Arithmetic> getTerms() {
        return terms;
    }

    public void setTerms(final List<Arithmetic> terms) {
        this.terms = terms;
    }

}
