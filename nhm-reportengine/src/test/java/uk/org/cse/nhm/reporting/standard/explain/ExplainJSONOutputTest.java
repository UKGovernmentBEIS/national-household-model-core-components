package uk.org.cse.nhm.reporting.standard.explain;

import java.util.ArrayList;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;

import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.standard.explain.model.NodesAndLinks;

public class ExplainJSONOutputTest {

	private static final DateTime NOW = new DateTime(0);

	private static final TreeSet<ExplainLogEntry> EMPTY_EXPLAIN = new TreeSet<ExplainLogEntry>();
	
	private TreeSet<ExplainLogEntry> explain;
	private ExplainJSONOutput explainJSONOutput;
	private ExplainLogEntry entry;

	private ArrayList<ExplainArrow> arrows;

	@Before
	public void setUp() {
		explain = new TreeSet<ExplainLogEntry>();
		arrows = new ArrayList<ExplainArrow>();
		entry = new ExplainLogEntry(NOW, "test", "test", false, 0, arrows);
		explain.add(entry);
		explainJSONOutput = new ExplainJSONOutput("test", explain);
	}
	
	@Test
	public void testFileName() {
		Assert.assertEquals("JSON files should be output in the data directory with a sensible name.", IReportOutput.DATA + "explain-test.jsonp", new ExplainJSONOutput("test", EMPTY_EXPLAIN).getPath());
	}

	@Test
	public void emptyArrowShouldBeIgnored() {
		arrows.add(new ExplainArrow("from", "to", 0));

		NodesAndLinks result = explainJSONOutput.buildReport();
		Assert.assertEquals(ImmutableList.of(), result.getNodes());
		Assert.assertEquals(ImmutableList.of(), result.getLinks());
	}
	
	@Test
	public void shouldIncludeOutsideNodesIfUsed() {
		arrows.add(new ExplainArrow(ExplainArrow.OUTSIDE, ExplainArrow.OUTSIDE, 1));
		
		NodesAndLinks result = explainJSONOutput.buildReport();
		Assert.assertEquals(2, result.getNodes().size());
		Assert.assertEquals(ExplainArrow.OUTSIDE, result.getNodes().get(0).getName());
		Assert.assertEquals(ExplainArrow.OUTSIDE, result.getNodes().get(1).getName());
	}
	
	@Test
	public void shouldConnectNodesWhichDoNotChange () {
		String to = "unchanged_group";
		
		arrows.add(new ExplainArrow(ExplainArrow.OUTSIDE, to, 1));
		entry = new ExplainLogEntry(NOW, "explain_report_name", "Stock Creator (some stock)", true, 0, arrows);
		explainJSONOutput = new ExplainJSONOutput("test", ImmutableSortedSet.of(entry));
		
		NodesAndLinks result = explainJSONOutput.buildReport();
		
		Assert.assertEquals(2, result.getNodes().size());
		Assert.assertEquals("Source node should be the unchanged group.", to, result.getNodes().get(0).getName());
		Assert.assertEquals("Generated target node should be the unchanged group.", to, result.getNodes().get(1).getName());
	}
}
