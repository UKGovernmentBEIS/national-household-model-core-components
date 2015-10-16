package uk.ac.ucl.hideem;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.ucl.hideem.Person.Sex;

import com.google.common.collect.ImmutableList;

public class HealthModuleTest {
	@Test
	public void healthImpactOfDoingNothingIsZero() {
		final IHealthModule hm = new HealthModule();
		
		final HealthOutcome effect = hm.effectOf(
                18d, 18d, 10d, 10d,
                10d, 20d,
                BuiltForm.Type.Bungalow, 100d, BuiltForm.Region.London, 1, true, true, true,
                true,
                ImmutableList.of(new Person(40, Sex.MALE, true, 2))
				, 10);
		
		System.out.println(effect);
		for (final Disease.Type d : Disease.Type.values()) {
			for (int i = 0; i<10; i++) {
                Assert.assertEquals("Impact should be 0 for " + d + " in year " + i, 0d, effect.cost(d, i, 0), 0d);
			}
		}
	}
}
