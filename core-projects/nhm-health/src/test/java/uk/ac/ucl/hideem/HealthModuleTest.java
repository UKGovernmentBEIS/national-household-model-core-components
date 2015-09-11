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
				18, 18, 10, 10,
				BuiltForm.Type.Bungalow, 100, 1, true, true, 
				ImmutableList.of(new Person(40, Sex.MALE))
				, 10);
		
		System.out.println(effect);
		for (final Disease.Type d : Disease.Type.values()) {
			for (int i = 0; i<10; i++) {
				Assert.assertEquals("Impact should be 0 for " + d + " in year " + i, 0d, effect.cost(d, i), 0d);				
			}
		}
	}
}
