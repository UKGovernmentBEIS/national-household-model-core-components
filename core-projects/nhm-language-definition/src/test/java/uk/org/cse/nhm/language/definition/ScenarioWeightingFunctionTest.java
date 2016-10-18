package uk.org.cse.nhm.language.definition;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.language.definition.XScenario.Weighting;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

public class ScenarioWeightingFunctionTest {
	private static void validate(final Weighting weighting, final int quantum, final double caseweight, final double... values) {
		final List<Double> output = weighting.getFunction(quantum).apply(caseweight);
		final Multiset<Double> hits = HashMultiset.create();
		for (final Double d : output) {
			hits.add(d);
		}
		System.out.println(String.format("%s %d %f %s", weighting, quantum, caseweight, hits));
		for (int i = 0; i<values.length; i+=2) {
			final double value = values[i];
			final double count = values[i+1];
			
			Assert.assertEquals(String.format("There should be %f %fs", count, value), count, hits.count(value), 0.1);
		}
	}
	
	@Test
	public void roundedWeightingRoundsUp() {
		validate(Weighting.Round, 
				400, 200, 
				400, 1);
		
		validate(Weighting.Round, 
				400, 650, 
				400, 2);
	}
	
	@Test
	public void roundedWeightingRoundsDown() {
		validate(Weighting.Round, 
				400, 249, 
				400, 1);
		
		validate(Weighting.Round, 
				400, 149);
	}
	
	@Test
	public void uniformWeightingAlwaysProducesOneEntry() {
		validate(Weighting.Uniform, 
				10000, 30,
				30, 1);
		
		validate(Weighting.Uniform, 
				30, 30,
				30, 1);
	}
	
	@Test
	public void uniformWeightingProducesWithQuantumWhenExactlyDivisible() {
		validate(Weighting.Uniform, 
				100, 350,
				87.5, 4);
	}
	
	@Test
	public void remainderWeightingAlwaysProducesOneEntry() {
		validate(Weighting.Remainder, 
				10000, 30,
				30, 1);
		
		validate(Weighting.Remainder, 
				30, 30,
				30, 1);
	}
	
	@Test
	public void remainderWeightingProducesSeveralQuantaAndTheRemainder() {
		validate(Weighting.Remainder, 
				400, 900,
				400, 2,
				100, 1);
	}
}
