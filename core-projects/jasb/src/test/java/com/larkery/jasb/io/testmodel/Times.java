package com.larkery.jasb.io.testmodel;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

@Bind("*")
public class Times extends Arithmetic {

    public List<Arithmetic> terms = new ArrayList<>();

    @BindRemainingArguments
    public List<Arithmetic> getTerms() {
        return terms;
    }

    public void setTerms(final List<Arithmetic> terms) {
        this.terms = terms;
    }
}
