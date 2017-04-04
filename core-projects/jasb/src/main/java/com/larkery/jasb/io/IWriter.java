package com.larkery.jasb.io;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Location;

public interface IWriter {

	public abstract ISExpression write(Object object);
	
	public abstract ISExpression write(Object object,
			Function<Object, Optional<Location>> locator);

}