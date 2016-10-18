package uk.org.cse.nhm.simulator.obligations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class ObligationHistoryTest {

	private ObligationHistory obligationHistory;

	@Before
	public void setup() {
		obligationHistory = new ObligationHistory();
	}
	
	@Test
	public void getObligationsShouldFindObligationsBelowRequestedType() {
		Match match = new Match();
		InheritanceMatch inheritanceMatch = new InheritanceMatch();
		
		obligationHistory.add(new NoMatch());
		obligationHistory.add(match);
		obligationHistory.add(inheritanceMatch);
		
		Assert.assertEquals("Should include the match and the obligation which inherited from it.", ImmutableList.of(match, inheritanceMatch), obligationHistory.getObligations(Match.class));
		
		Assert.assertEquals("Should still be able to search for obligations from branch.", ImmutableList.of(match, inheritanceMatch), obligationHistory.branch().getObligations(Match.class));
	}
	
	@Test
	public void getObligationShouldReturnAbsentIfFindsNoneOrMultiple() {
		obligationHistory.add(new NoMatch());
		
		Assert.assertEquals("Should be empty if no matching things were added.",  Optional.<Match>absent(), obligationHistory.getObligation(Match.class));
		
		Match match = new Match();
		obligationHistory.add(match);
		
		Assert.assertEquals("Should return match.",  Optional.of(match), obligationHistory.getObligation(Match.class));
		
		obligationHistory.add(match);
		
		Assert.assertEquals("Should be empty if too many matching things were added.",  Optional.<Match>absent(), obligationHistory.getObligation(Match.class));
	}
	
	class NoMatch extends TestObligation {
	}
	
	class Match extends NoMatch {
	}
	
	class InheritanceMatch extends Match {
	}
	

}
