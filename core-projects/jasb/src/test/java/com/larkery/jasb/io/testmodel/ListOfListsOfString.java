package com.larkery.jasb.io.testmodel;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

@Bind("listoflists")
public class ListOfListsOfString {
	private List<String> first = new ArrayList<String>();
	private List<List<String>> contents = new ArrayList<List<String>>();

	public ListOfListsOfString() {
		
	}

	@BindPositionalArgument(0)
	public List<String> getFirst() {
		return first;
	}

	public void setFirst(List<String> first) {
		this.first = first;
	}
	

	@BindRemainingArguments
	public List<List<String>> getContents() {
		return contents;
	}

	public void setContents(final List<List<String>> newContents) {
		this.contents = newContents;
	}
}
