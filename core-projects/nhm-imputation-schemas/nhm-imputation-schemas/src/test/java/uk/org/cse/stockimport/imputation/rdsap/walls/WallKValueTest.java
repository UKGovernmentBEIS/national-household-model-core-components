package uk.org.cse.stockimport.imputation.rdsap.walls;

import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Cavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Cob;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.GraniteOrWhinstone;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.MetalFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.Sandstone;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.SolidBrick;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.SystemBuild;
import static uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType.TimberFrame;
import static uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType.External;
import static uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType.FilledCavity;
import static uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType.Internal;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.stockimport.imputation.walls.WallPropertyImputer;

import com.google.common.collect.ImmutableSet;

public class WallKValueTest {

    private WallPropertyImputer i;

    @Before
    public void create() {
        this.i = new WallPropertyImputer();
    }

    @Test
    public void testKValues() {
        Assert.assertEquals(202d, i.getKValue(GraniteOrWhinstone, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(156d, i.getKValue(Sandstone, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(135d, i.getKValue(SolidBrick, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(148d, i.getKValue(Cob, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(139d, i.getKValue(Cavity, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(9d, i.getKValue(TimberFrame, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(211d, i.getKValue(SystemBuild, ImmutableSet.<WallInsulationType>of()));
        Assert.assertEquals(14d, i.getKValue(MetalFrame, ImmutableSet.<WallInsulationType>of()));

        Assert.assertEquals(135d, i.getKValue(SolidBrick, ImmutableSet.of(External)));

        Assert.assertEquals(148d, i.getKValue(Cob, ImmutableSet.of(External)));
        Assert.assertEquals(66d, i.getKValue(Cob, ImmutableSet.of(Internal)));
        Assert.assertEquals(66d, i.getKValue(Cob, ImmutableSet.of(Internal, External)));

        Assert.assertEquals(139d, i.getKValue(Cavity, ImmutableSet.of(FilledCavity)));

        Assert.assertEquals(211d, i.getKValue(SystemBuild, ImmutableSet.of(External)));
        Assert.assertEquals(10d, i.getKValue(SystemBuild, ImmutableSet.of(Internal)));
    }
}
