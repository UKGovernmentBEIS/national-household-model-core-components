package uk.org.cse.nhm.simulator.action.hypothetical;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

public class SapOccupancyActionTest {
	@Test
	public void occupancyCalculationIsCorrect() throws IOException {
		final InputStream resource = getClass().getResourceAsStream("/sapOccupancies.tab");
		
		if (resource == null) throw new IOException("No sap occupancies tab file");
		
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						resource
				)
				)) {
			String line;
			boolean skipFirstLine = true;
			while (null != (line = reader.readLine())) {
				if (skipFirstLine) {
					skipFirstLine = false;
				} else {
					final String[] parts = line.split("\t");
					final double tfa = Double.parseDouble(parts[1]);
					final double out = Double.parseDouble(parts[2]);
					
					final double our = SapOccupancyAction.getSapOccupancy(tfa);
					
					Assert.assertEquals("for " + parts[0],
							out, our, 0.01);
				}
			}
		}
	}
}
