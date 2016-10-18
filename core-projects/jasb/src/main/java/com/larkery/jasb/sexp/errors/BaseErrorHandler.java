package com.larkery.jasb.sexp.errors;


public abstract class BaseErrorHandler implements IErrorHandler {
	@Override
	public void error(final ILocated location, final String format, final Object... interpolate) {
		handle(BasicError.at(location.getLocation(), String.format(format, interpolate)));
	}

	@Override
	public void warn(final ILocated location, final String string, final Object... interpolate) {
		handle(BasicError.warningAt(location.getLocation(), String.format(string, interpolate)));
	}
}
