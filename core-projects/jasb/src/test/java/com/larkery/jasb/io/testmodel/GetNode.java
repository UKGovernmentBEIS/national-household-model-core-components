package com.larkery.jasb.io.testmodel;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.sexp.Node;

@Bind("get")
public class GetNode extends Arithmetic {
	public Node node;

	@BindPositionalArgument(0)
	public Node getNode() {
		return node;
	}

	public void setNode(final Node node) {
		this.node = node;
	}
}
