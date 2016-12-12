package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;


public class MeasureIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void addingAdjustmentReducesEnergyUse() throws Exception {
		final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("adjusting/adjustusage.s"), true, Collections.<Class<?>>emptySet());
		
		int weakFails = 0;
		
		for (final IDwelling d : output.state.getDwellings()) {
			final Optional<Double> delta = output.state.get(output.flags, d).getRegister("delta");
			
			if (Math.abs(delta.get() + 1) > 0.1) weakFails++;
			
			if (delta.get() < -1) {
				Assert.fail("this should never happen");
			} else if (delta.get() > 0) {
				Assert.fail("this should never happen");
			}
			
			final Optional<Double> delta2 = output.state.get(output.flags, d).getRegister("delta2");
			if (delta2.get() > 0) weakFails++;
		}
		
		Assert.assertTrue("fails " + weakFails, weakFails < 12000);
	}
	
	@Test
	public void installingMeasuresCreatesInstallationLogs() throws Exception {
		super.runSimulation(dataService, loadScenario("measures/installlog.nhm"), true, 
				Collections.<Class<?>>emptySet(), new IStateListener() {
					@Override
					public void stateChanged(ICanonicalState state, IStateChangeNotification notification) {
						if (notification.getCauses().size() > 0 && Iterables.get(notification.getCauses(), 0).toString().equals("on.dates")) {
							for (final IDwelling d : notification.getAllChangedDwellings()) {
								final IComponentsScope scope = notification.getRootScope().getComponentsScope(d).get();
								final List<ITechnologyInstallationDetails> installs = scope.getAllNotes(ITechnologyInstallationDetails.class);
								boolean gotCWI = false;
								boolean gotHP = false;
								for (final ITechnologyInstallationDetails tid : installs) {
									gotCWI = gotCWI || tid.getInstalledTechnology().equals(TechnologyType.wallInsulation(WallInsulationType.FilledCavity));
									gotHP = gotHP || tid.getInstalledTechnology().equals(TechnologyType.airSourceHeatPump());
								}
								Assert.assertTrue(gotCWI);
								Assert.assertTrue(gotHP);
							}
						}
					}
				}
			);
	}
}
