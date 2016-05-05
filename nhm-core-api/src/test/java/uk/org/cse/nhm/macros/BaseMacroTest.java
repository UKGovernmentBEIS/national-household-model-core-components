package uk.org.cse.nhm.macros;

import java.io.InputStreamReader;

import org.junit.Assert;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.ErrorCollector;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.MacroExpander;
import com.larkery.jasb.sexp.parse.Parser;

public abstract class BaseMacroTest {
	public void verifyError(final String inputFile, final ImmutableList<IMacro> macro) throws Exception {
		final Node node = Node.copy(
				Parser.source(
						("test:test"), 
						new InputStreamReader(
								getClass().
								getClassLoader().
								getResourceAsStream(inputFile))
						, IErrorHandler.RAISE));
		
		final ErrorCollector collect = new ErrorCollector();		
		Node out = null;
		try {
			out = Node.copy(MacroExpander.expand(
					macro,
					node, 
					collect));
		} catch (final UnfinishedExpressionException uee) {
			collect.handle(uee.getError());
		}

		Assert.assertFalse("Expected error in " + inputFile + " ("+ out+")", collect.isEmpty());
	}
		
	
	public void verify(final String inputFile, final ImmutableList<IMacro> macro) throws Exception {
		final Seq node = (Seq) Node.copy(
				Parser.source(
						("test:test"), 
						new InputStreamReader(
								getClass().
								getClassLoader().
								getResourceAsStream(inputFile))
						, IErrorHandler.RAISE));
		
		final Node input = node.get(0);
		final Node output = node.get(1);
		
		final Node expanded = 
				Node.copy(
						MacroExpander.expand(
								macro,
								input, 
								IErrorHandler.RAISE)
				);
		
		Assert.assertEquals(output, expanded);
	}
}
