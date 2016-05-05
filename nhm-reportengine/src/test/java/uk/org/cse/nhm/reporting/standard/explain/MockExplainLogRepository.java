package uk.org.cse.nhm.reporting.standard.explain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;

public class MockExplainLogRepository {

	private static final DateTime NOW = new DateTime(0);

	public List<ExplainLogEntry> findByScenarioExecutionId(String scenarioExecutionId) {
		List<ExplainLogEntry> result = new ArrayList<ExplainLogEntry>();

		result.addAll(fuelsTest());
		result.addAll(mergeTest());
		result.addAll(wallsTest());
		
		return result;
	}

	private List<ExplainLogEntry> mergeTest() {
		List<ExplainLogEntry> result = new ArrayList<ExplainLogEntry>();

		ExplainLogEntry simultaneousSetup = new ExplainLogEntry(
				NOW, 
				"Merging",
				"Stock Creator (some stock)",
				true,
				0, 
				Collections.singletonList(
					new ExplainArrow(ExplainArrow.OUTSIDE, "source", 10)));

		result.add(simultaneousSetup);

		result.add(makeSmallChange(1));
		result.add(makeSmallChange(2));
		result.add(makeSmallChange(3));
		result.add(makeSmallChange(4));

		return result;
	}

	private ExplainLogEntry makeSmallChange(int count) {
		
		
		ExplainLogEntry entry = new ExplainLogEntry(
				NOW, 
				"Merging", 
				"Simultaneous changes which should be merged " + count,
				false,
				count,
				Collections.singletonList(new ExplainArrow("source", "target", 1)));
		return entry;
	}

	private List<ExplainLogEntry> wallsTest() {
		ExplainLogEntry wallSetup = new ExplainLogEntry(NOW, "walls", "Stock Creator", true, 0, ImmutableList.of(
			new ExplainArrow(ExplainArrow.OUTSIDE, "Cavity Wall Uninsulated", 400),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Solid Wall Uninsulated", 200),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Cavity Wall Filled", 100),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Solid Wall Internal Insulation", 15),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Solid Wall External Insulation", 10)));

		ExplainLogEntry walls = new ExplainLogEntry(NOW, "walls", "insulation incentive scheme", false, 1, ImmutableList.of(
			new ExplainArrow("Cavity Wall Uninsulated", "Cavity Wall Filled", 60),
			new ExplainArrow("Solid Wall Uninsulated", "Solid Wall Internal Insulation", 15),
			new ExplainArrow("Solid Wall Uninsulated", "Solid Wall External Insulation", 10)));

		return ImmutableList.of(wallSetup, walls);
	}

	private List<ExplainLogEntry> fuelsTest() {
		ExplainLogEntry fuelSetup = new ExplainLogEntry(NOW, "fuels", "Stock Creator", true, 0, ImmutableList.of(
			new ExplainArrow(ExplainArrow.OUTSIDE, "Coal", 160),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Gas", 200),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Electricity", 80),
			new ExplainArrow(ExplainArrow.OUTSIDE, "Oil", 50)));

		ExplainLogEntry fuels = new ExplainLogEntry(NOW, "fuels", "minimize net present value", false, 1, ImmutableList.<ExplainArrow>builder()
			.add(new ExplainArrow("Gas", "Coal", 10))
			.add(new ExplainArrow("Gas", "Electricity", 30))
			.add(new ExplainArrow("Electricity", "Gas", 10))
			.add(new ExplainArrow("Electricity", "Coal", 10))
			.add(new ExplainArrow("Coal", "Gas", 20))
			.add(new ExplainArrow("Coal", "Electricity", 50))
			.build());

		ExplainLogEntry fuelsPartTwo = new ExplainLogEntry(NOW, "fuels", "coal elimination strategy", false, 2, Collections.singletonList(
				new ExplainArrow("Coal", "Electricity", 50)));

		return ImmutableList.of(fuelSetup, fuels, fuelsPartTwo);
	}
}
