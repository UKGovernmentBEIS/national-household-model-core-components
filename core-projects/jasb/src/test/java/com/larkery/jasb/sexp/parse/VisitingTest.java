package com.larkery.jasb.sexp.parse;

import java.io.StringReader;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;

import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;
import com.larkery.jasb.sexp.NodeBuilder;
import com.larkery.jasb.sexp.errors.ErrorCollector;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

public class VisitingTest {
	final ErrorCollector record = new ErrorCollector();
	
	@Before
	public void resetErrors() {
		 record.clear();
	}
	
	static class E implements ISExpressionVisitor {
		final String value;
		enum T {
			Open, Atom, Comment, Close
		}
		final T type;
		
		private E(final String value, final T type) {
			super();
			this.value = value;
			this.type = type;
		}

		@Override
		public void locate(final Location loc) {}

		@Override
		public void open(final Delim delimeter) {
			Assert.assertEquals(this.type, T.Open);
		}

		@Override
		public void atom(final String string) {
			Assert.assertEquals(this.value, string);
			Assert.assertEquals(this.type, T.Atom);
		}

		@Override
		public void comment(final String text) {
			Assert.assertEquals(this.value, text);
			Assert.assertEquals(this.type, T.Comment);
		}

		@Override
		public void close(final Delim delimeter) {
			Assert.assertEquals(this.type, T.Close);
		}
	}
	
	public static E e(final String value) {
		switch (value) {
		case "(":return new E(value, E.T.Open);
		case ")":return new E(value, E.T.Close);
		default:
			if (value.startsWith(";")) return new E(value.substring(1), E.T.Comment);
			else return new E(value, E.T.Atom);
		}
	}
	
	protected ISExpression source(final String name, final String src) {
		return Parser.source(
				createTestURI(name),
				new StringReader(src),
				record);
	}
	
	protected void check(final String name, final String src, final Set<Class<? extends IError>> errorTypes) {
		try {
			source(name, src).accept(NodeBuilder.create());
		} finally {
			outer:
			for (final Class<? extends IError> e : errorTypes) {
				for (final IError err : record.getErrors()) {
					if (e.isInstance(err)) {
						continue outer;
					}
				}
				
				Assert.fail("No error of type " + e.getSimpleName() + " seen");
			}
		}
	}
	
	protected void check(final String name, final String src, final E... values) {
		try {
		source(name, src)
				.accept(new ISExpressionVisitor() {
					int offset = 0;
					@Override
					public void open(final Delim delimeter) {
						values[offset++].open(delimeter);
					}
					
					@Override
					public void locate(final Location loc) {
					}
					
					@Override
					public void comment(final String text) {
						values[offset++].comment(text);
					}
					
					@Override
					public void close(final Delim delimeter) {
						values[offset++].close(delimeter);
					}
					
					@Override
					public void atom(final String string) {
						values[offset++].atom(string);
					}
				});
		} catch (final Throwable e) {
			throw e;
		}
	}

	private String createTestURI(final String name)  {
		return "test:" + name;
	}
}
