package com.larkery.jasb.io;

import org.junit.Before;

import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.atom.NumberAtomIO;
import com.larkery.jasb.io.atom.StringAtomIO;
import com.larkery.jasb.io.impl.JASB;
import com.larkery.jasb.io.testmodel.Div;
import com.larkery.jasb.io.testmodel.GetNode;
import com.larkery.jasb.io.testmodel.ListOfListsOfString;
import com.larkery.jasb.io.testmodel.ListOfStrings;
import com.larkery.jasb.io.testmodel.Plus;
import com.larkery.jasb.io.testmodel.Times;
import com.larkery.jasb.io.testmodel.Value;

public class JasbIOTest {

    protected JASB context;

    @Before
    public void setup() {
        context = JASB.of(
                ImmutableSet.<Class<?>>of(
                        GetNode.class,
                        Div.class,
                        Plus.class,
                        ListOfStrings.class,
                        ListOfListsOfString.class,
                        Times.class,
                        Value.class),
                ImmutableSet.of(
                        new StringAtomIO(),
                        new NumberAtomIO()));
    }
}
