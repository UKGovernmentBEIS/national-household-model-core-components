package com.larkery.jasb.io.testmodel;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

/**
 * (/ a b)
 * 
 */
@Bind("/")
public class Div extends Arithmetic {
	public Arithmetic first;
	public Arithmetic second;
	
	@BindPositionalArgument(0)
	public Arithmetic getFirst() {
		return first;
	}
	public void setFirst(final Arithmetic first) {
		this.first = first;
	}
	
	@BindPositionalArgument(1)
	public Arithmetic getSecond() {
		return second;
	}
	public void setSecond(final Arithmetic second) {
		this.second = second;
	}
}
