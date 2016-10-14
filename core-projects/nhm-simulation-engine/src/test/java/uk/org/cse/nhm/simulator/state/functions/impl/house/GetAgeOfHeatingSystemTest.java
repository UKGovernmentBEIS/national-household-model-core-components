package uk.org.cse.nhm.simulator.state.functions.impl.house;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetAgeOfHeatingSystemTest extends Mockito {
	@Test
	public void IfHeatingSystemAgeIsZeroThenAgeOfZeroShouldBeReturned() throws Exception {
		IComponentsScope scope = mock(IComponentsScope.class);
		ITechnologyModel techModel = mock(ITechnologyModel.class);
		ICentralHeatingSystem heatingsystem = mock(ICentralHeatingSystem.class);
		IHeatSource heatsource = mock(IHeatSource.class);
        ILogEntryHandler leh = mock(ILogEntryHandler.class);
        @SuppressWarnings("unchecked")
		IDimension<BasicCaseAttributes> basic = mock(IDimension.class);

        GetAgeOfHeatingSystem function = new GetAgeOfHeatingSystem(null, null, leh, basic);

		when(techModel.getPrimarySpaceHeater()).thenReturn(heatingsystem);
		when(heatingsystem.getHeatSource()).thenReturn(heatsource);
		when(heatsource.getInstallationYear()).thenReturn(0);
        when(scope.get(basic)).thenReturn(new BasicCaseAttributes(
                                              "H1", 0, 0,
                                              RegionType.London,
                                              MorphologyType.Urban,
                                              TenureType.OwnerOccupied,
                                              1990,
                                              SiteExposureType.Average
                                              ));
		
		double age = function.getHeatSourceAge(techModel,
                                               new DateTime(2014, 1, 1, 1, 1),
                                               scope);
		Assert.assertEquals(0d, age, 0.0);
	}
}
