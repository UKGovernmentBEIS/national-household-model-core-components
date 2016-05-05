package com.larkery.jasb.sexp.module;

import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.JasbErrorException;
import com.larkery.jasb.sexp.parse.Parser;

public class TestModule {
	private ISExpression p(final String s) {
		return Parser.source(
				"test://test",
				new StringReader(s),
				IErrorHandler.RAISE);
	}
	
	@Test
	public void moduleRenamesTemplates() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x []))"), IErrorHandler.RAISE));
		Assert.assertEquals(
				Node.copy(p("(template hello/x [])")),
				result);
	}
	
	@Test
	public void moduleCrossReferencesTemplates() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x [] (y)) (template y []))"), IErrorHandler.RAISE));
		Assert.assertEquals(
				Node.copy(p("(template hello/x [] (hello/y)) (template hello/y [])")),
				result);
	}
	
	@Test
	public void moduleHandlesLocalNames() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x [] (~local stuff)))"), IErrorHandler.RAISE));
		Assert.assertEquals(
				Node.copy(p("(template hello/x [] (~join hello/stuff))")),
				result);
	}
	
	@Test
	public void moduleHandlesLocalReferences() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x [] (~local #stuff)))"), IErrorHandler.RAISE));
		Assert.assertEquals(
				Node.copy(p("(template hello/x [] (~join #hello/stuff))")),
				result);
	}
	
	@Test(expected=JasbErrorException.class)
	public void moduleOnlyContainsTemplates() throws Exception {
		Node.copyAll(Module.transform(p("(~module hello (error) (template x [] (~local #stuff)))"), IErrorHandler.RAISE));
	}

	@Test
	public void moduleHandlesShortLocalNames() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x [] /stuff))"), IErrorHandler.RAISE));
		Assert.assertEquals(Node.copy(p("(template hello/x [] hello/stuff)")),
							result);
	}

	@Test
	public void moduleHandlesShortLocalRefs() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x [] #/stuff))"), IErrorHandler.RAISE));
		Assert.assertEquals(Node.copy(p("(template hello/x [] #hello/stuff)")),
							result);
	}

	@Test
	public void moduleHandlesShortLocalFlags() throws Exception {
		final Node result = Node.copy(Module.transform(p("(~module hello (template x [] !/stuff))"), IErrorHandler.RAISE));
		Assert.assertEquals(Node.copy(p("(template hello/x [] !hello/stuff)")),
							result);
	}
}
