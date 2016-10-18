package uk.org.cse.nhm.simulator.state.dimensions;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class FuelServiceTableTest {
    @Test
    public void emptyTableIsAllZeroes() {
        final FuelServiceTable table = FuelServiceTable.builder().build();
        for (final FuelType ft : FuelType.values()) {
            for (final ServiceType st : ServiceType.values()) {
                Assert.assertEquals(0d, table.get(ft, st), 0);
            }
        }
    }

    @Test
    public void twoEmptyTablesAreSame() {
        final FuelServiceTable table1 = FuelServiceTable.builder().build();
        final FuelServiceTable table2 = FuelServiceTable.builder().build();
        Assert.assertSame(table1, table2);
    }

    @Test
    public void addingValuesWorksAsExpected() {
        final FuelServiceTable.Builder b = FuelServiceTable.builder();

        b.add(FuelType.MAINS_GAS, ServiceType.PRIMARY_SPACE_HEATING, 5d);
        b.add(FuelType.MAINS_GAS, ServiceType.PRIMARY_SPACE_HEATING, 5d);

        final FuelServiceTable t = b.build();
        
        Assert.assertEquals("mains gas for space heating is ten",
                            10d, t.get(FuelType.MAINS_GAS, ServiceType.PRIMARY_SPACE_HEATING), 0);
        Assert.assertEquals("mains gas overall is ten",
                            10d, t.get(FuelType.MAINS_GAS), 0);
        Assert.assertEquals("space heating overall is ten",
                            10d, t.get(ServiceType.PRIMARY_SPACE_HEATING), 0);

        Assert.assertEquals("no electric use for space",
                            0d, t.get(FuelType.ELECTRICITY, ServiceType.PRIMARY_SPACE_HEATING), 0);
        Assert.assertEquals("no electric use at all",
                            0d, t.get(FuelType.ELECTRICITY), 0);
        Assert.assertEquals("no lighting use at all",
                            0d, t.get(ServiceType.LIGHTING), 0);

        Assert.assertSame("identical table is same",
                          t, b.build());
    }
}
