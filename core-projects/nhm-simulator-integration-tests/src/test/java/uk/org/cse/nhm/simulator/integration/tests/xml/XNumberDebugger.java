package uk.org.cse.nhm.simulator.integration.tests.xml;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Doc("Debugging element")
@Bind("debug/function")

public class XNumberDebugger extends XNumber {

    private XNumber delegate;
    private String name;

    @Doc("debug")
    @BindNamedArgument

    @NotNull(message = "required")
    public XNumber getDelegate() {
        return delegate;
    }

    public void setDelegate(final XNumber delegate) {
        this.delegate = delegate;
    }

    @Override
    @Doc("debug")

    @BindNamedArgument
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

}
