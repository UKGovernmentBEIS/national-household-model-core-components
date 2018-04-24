package uk.org.cse.nhm.language.builder.batch;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.larkery.jasb.sexp.SimplePrinter;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.ipc.api.tasks.impl.ScenarioSnapshot;
import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;

public class BatchTest {
	private BatchExpander expander;
	private SAXParserFactory saxFactory;

	@Before
	public void setup() throws JAXBException {
		final ScenarioParserFactory factory = new ScenarioParserFactory();
		expander = new BatchExpander(factory.buildBatchParser());
		saxFactory = SAXParserFactory.newInstance();
		saxFactory.setXIncludeAware(true);
		saxFactory.setNamespaceAware(true);
	}
		
	@Test
	public void testVaryingEfficiency() throws IOException {
		final Batch result = expander.expandAll(getScenario("batch/varyingEfficiency.s"));
		
		assertKeys(result, 1000, "$seed", "$efficiency");
	}
	
	@Test
	public void testSwitchingFuelTypes() throws IOException {
		final Batch result = expander.expandAll(getScenario("batch/switchingFuelTypes.s"));
		
		assertKeys(result, 120, "$fuel", "$efficiency");
	}
	
	@Test
	public void testDifferentDensityRanges() throws IOException {
		final Batch result = expander.expandAll(getScenario("batch/differentDensityRanges.s"));
		
		assertKeys(result, 11, "$t");
	}
	
	private void assertKeys(final Batch result, final int expectedSize, final String...keys) {
		Assert.assertTrue("Batch parsed incorrectly: " + result.problems, result.problems.isEmpty());
		
		final List<IBatchInstance> instances = ImmutableList.copyOf(result);
		Assert.assertEquals("Parsed batch should have created the right number of instances.", expectedSize, instances.size());
		
		final IBatchInstance exampleInstance = instances.get(0);
		
		Assert.assertEquals("Result should have the correct number of keys", keys.length, exampleInstance.getParameters().size());
		for(final String key : keys) {
			Assert.assertTrue("Result inputs should contain the key " + key, exampleInstance.getParameters().containsKey(key));
			
			final String value = exampleInstance.getParameters().get(key).toString();
			
			Assert.assertTrue("Result scenario should contain the value " + value, SimplePrinter.toString(exampleInstance).contains(value));
		}
	}

	private IScenarioSnapshot getScenario(final String file) throws IOException {
		try (final InputStream is = BatchTest.class.getClassLoader().getResourceAsStream(file)) {
			return ScenarioSnapshot.fromString(IOUtils.toString(is));
		}
	}
}
