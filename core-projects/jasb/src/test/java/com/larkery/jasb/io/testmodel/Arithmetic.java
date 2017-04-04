package com.larkery.jasb.io.testmodel;

import com.larkery.jasb.bind.AfterReading;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.Identity;
import com.larkery.jasb.sexp.Node;

/**
 * 
 * (thing name:hello)
 * 
 * #hello
 * 
 */
public abstract class Arithmetic {
	public String name;
	public Node node;

	@BindNamedArgument
	@Identity
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@AfterReading
	public void setSourceNode(final Node node) {
		this.node = node;
	}
}
