package uk.org.cse.nhm.language.builder.function;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Predicate;

import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;

public class MapWallTypesTest {
	@Test
	public void nothingWhichMatchesAnySolidMatchesAnyCavity() {
		final Predicate<WallConstructionType> cav = MapWallTypes.getPredicateMatching(XWallConstructionTypeRule.AnyCavity);
		final Predicate<WallConstructionType> sol = MapWallTypes.getPredicateMatching(XWallConstructionTypeRule.AnySolid);
		for (final WallConstructionType wct : WallConstructionType.getExternalWallTypes()) {
			Assert.assertFalse("For external walls, cav should be opposite of sol on " + wct, cav.apply(wct) == sol.apply(wct));
		}
	}
}
