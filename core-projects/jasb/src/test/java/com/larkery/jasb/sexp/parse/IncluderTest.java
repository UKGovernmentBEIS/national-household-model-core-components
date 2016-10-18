package com.larkery.jasb.sexp.parse;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public class IncluderTest extends VisitingTest {
	private IDataSource<String> resolver;
	private Map<String, String> values;
	@Before
	public void setup() {
		values = new HashMap<>();
		resolver = new IDataSource<String>() {

			@Override
			public String root() {
				return "";
			}

			@Override
			public String resolve(Seq relation, IErrorHandler errors) {
				return "test://" + relation.getTail().get(0).toString();
			}

			@Override
			public Reader open(String resolved) throws IOException {
				return new StringReader(values.get(resolved));
			}
		};
	}
	
	@Override
	protected ISExpression source(final String name, final String src) {
		values.put("test://"+name, src);
		return Includer.source(
				resolver, 
				"test://"+name,
				record
				);
	}
	
	@Test
	public void includesAreIncluded() {
		values.put("test://my-include", "hello");
		check("includesAreIncluded",
				"(include my-include)",
				e("hello")
				);
	}
	
	@Test
	public void recursiveIncludesAreIllegal() {
		values.put("test://my-include", "(include my-include)");
		check("includesAreIncluded",
				"(include my-include)",
				e("hello")
				);
		Assert.assertFalse(record.getErrors().isEmpty());
	}
	
	@Test
	public void includesHaveCorrectLocations() throws UnfinishedExpressionException {
		values.put("test://my-include", "hello");
		final Node node = Node.copy(source("includesHaveCorrectLocations", "(outside (include my-include))"));

		Assert.assertTrue(node instanceof Seq);
		final Seq seq = (Seq) node;

		Assert.assertEquals("test://includesHaveCorrectLocations", 
							seq.getLocation().name);

		// so that is correct

		final Node inner = seq.get(1);

		Assert.assertTrue(inner instanceof Atom);

		final Atom atom = (Atom) inner;

		// the atom should be the included bit, which is the word hello
		Assert.assertEquals(("test://includesHaveCorrectLocations"),
							atom.getLocation().sourceLocation.name);

		Assert.assertEquals(("test://my-include"),
							atom.getLocation().name);
	}
	
	@Test
	public void removesNoInclude() throws UnfinishedExpressionException {
		final Node n = Node.copy(
			source("removesNoInclude", "(a b c (no-include x y z) 1 2 3)"));
		
		Assert.assertEquals("Should strip out the no-include statement.", "(a b c x y z 1 2 3)", n.toString()); 
	}
	
	@Test
	public void removesBodyOfNoIncludeInsideInclude() throws UnfinishedExpressionException {
		values.put("test://no-include", "m n o (no-include x y z) p q r");
		final Node n = Node.copy(
				source("removesNoInclude", "(a b c (include no-include) 1 2 3)"));
		
		Assert.assertEquals("Should strip out the contents of the no-include statement.", "(a b c m n o p q r 1 2 3)", n.toString()); 
	}

	@Test
	public void includeModuleIncludesOnlyModules() throws Exception {
		values.put("test://module", "stuff things (~module my-module x y z)");
		final Node n = Node.copy(
				source("includeModule", "(include-modules module)"));
		Assert.assertEquals("Should only see the module definition",
							"(~module my-module x y z)",
							n.toString());
	}

	@Test
	public void includeModuleAllowsRecursion() throws Exception {
		values.put("test://module1", "(include-modules module2) (~module module1 x y z)");
		values.put("test://module2", "(include-modules module1) (~module module2 x y z)");

		final Node n = Node.copy(source("includeModule", "(test (include-modules module1))"));
		Assert.assertEquals("Should see both modules, and not die " + n,
							"(test (~module module2 x y z) (~module module1 x y z))",
							n.toString());
	}
}
