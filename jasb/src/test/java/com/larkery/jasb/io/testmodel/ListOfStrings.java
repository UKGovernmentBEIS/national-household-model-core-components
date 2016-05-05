package com.larkery.jasb.io.testmodel;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("strings")
public class ListOfStrings {
	private List<String> strings = new ArrayList<>();

	@BindNamedArgument("values")
	public List<String> getStrings() {
		return strings;
	}

	public void setStrings(final List<String> strings) {
		this.strings = strings;
	}
}
