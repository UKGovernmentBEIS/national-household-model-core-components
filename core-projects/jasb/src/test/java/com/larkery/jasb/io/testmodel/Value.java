package com.larkery.jasb.io.testmodel;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

/**
 * (value of: 1)
 *
 * @author hinton
 *
 */
@Bind("value")
public class Value extends Arithmetic {

    public double value;

    @BindNamedArgument("of")
    public double getValue() {
        return value;
    }

    public void setValue(final double value) {
        this.value = value;
    }
}
