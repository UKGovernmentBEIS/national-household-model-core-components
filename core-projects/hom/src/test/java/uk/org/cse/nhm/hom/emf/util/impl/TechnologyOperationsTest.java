package uk.org.cse.nhm.hom.emf.util.impl;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.hom.emf.technologies.*;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.StorageHeaterImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;

public class TechnologyOperationsTest {

    private static final double ERROR_DELTA = 0.01;
    private ITechnologyOperations operations;

    private static class IndividualSource extends HeatSourceImpl implements IIndividualHeatSource {

        @Override
        public double getResponsiveness(
                final IConstants parameters,
                final EList<HeatingSystemControlType> controls,
                final EmitterType emitterType) {
            return super.getSAPTable4dResponsiveness(parameters, controls, emitterType);
        }

        @Override
        public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters,
                final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
            return Zone2ControlParameter.Two;
        }

        @Override
        public boolean isCommunityHeating() {
            return false;
        }
    }

    @Before
    public void setup() {
        this.operations = new TechnologyOperations();
    }

    @Test
    public void testHasCommunitySpaceHeating() {
        final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;
        final ITechnologyModel technologies = new TechnologyModelImpl() {
            {
                final ICommunityHeatSource communityHeat = factory.createCommunityHeatSource();
                setCommunityHeatSource(communityHeat);
                setPrimarySpaceHeater(new CentralHeatingSystemImpl() {
                    {
                        setHeatSource(communityHeat);
                    }
                });
            }
        };

        Assert.assertTrue("Should detect when community space heating is the main heating system", operations.hasCommunitySpaceHeating(technologies));
    }

    @Test
    public void testHasCommunitySpaceHeatingReturnsFalseWhenNotPresent() {
        final ITechnologyModel technologies = new TechnologyModelImpl() {
        };

        Assert.assertFalse("Should detect when no community space heating is present", operations.hasCommunitySpaceHeating(technologies));
    }

    @Test
    public void testHasCommunityWaterHeating() {
        final ITechnologiesFactory factory = ITechnologiesFactory.eINSTANCE;
        final ITechnologyModel technologies = new TechnologyModelImpl() {
            {
                final ICommunityHeatSource communityHeat = factory.createCommunityHeatSource();
                setCommunityHeatSource(communityHeat);

                setCentralWaterSystem(new CentralWaterSystemImpl() {
                    {
                        setPrimaryWaterHeater(new MainWaterHeaterImpl() {
                            {
                                setHeatSource(communityHeat);
                            }
                        });
                    }
                });
            }
        };

        Assert.assertTrue("Should detect when community water heating is the main hot water system", operations.hasCommunityWaterHeating(technologies));
    }

    @Test
    public void testHasCommunityWaterHeatingReturnsFalseWhenNotPresent() {
        final ITechnologyModel technologies = new TechnologyModelImpl() {
        };

        Assert.assertFalse("Should detect when no community water heating is present", operations.hasCommunityWaterHeating(technologies));
    }

    @Test
    public void testReplaceMainHeatSourceForCentralHeatingAndCentralWaterAddsAHeatSource() {
        final ITechnologyModel technologies = new TechnologyModelImpl() {
        };

        final IHeatSource heatSource = new BoilerImpl() {
        };

        operations.installHeatSource(technologies, heatSource, true, true, EmitterType.RADIATORS, Collections.<HeatingSystemControlType>emptySet(), 0.0f, 0.0f);

        Assert.assertEquals(heatSource, technologies.getIndividualHeatSource());
    }

    @Test
    public void testReplaceMainHeatSourceForCentralHeatingAndCentralWaterReplacesExistingHeatSource() {
        final ITechnologyModel technologies = new TechnologyModelImpl() {
            {
                setIndividualHeatSource(new BoilerImpl() {
                });
            }
        };

        final IHeatSource heatSource = new BoilerImpl() {
        };

        operations.installHeatSource(technologies, heatSource, true, true, EmitterType.RADIATORS, Collections.<HeatingSystemControlType>emptySet(), 0.0f, 0.0f);

        Assert.assertEquals(heatSource, technologies.getIndividualHeatSource());
    }

    @Test
    public void testReplaceCentralHeatingHeatSourceShouldAddNewCentralHeatingIfNoSpaceHeatingPresent() {
        final ITechnologyModel technologies = new TechnologyModelImpl() {
        };
        final IHeatSource newHeatSource = new IndividualSource() {
        };

        operations.installHeatSource(technologies, newHeatSource, EmitterType.RADIATORS, Collections.<HeatingSystemControlType>emptySet());

        Assert.assertTrue(technologies.getPrimarySpaceHeater() instanceof ICentralHeatingSystem);
        Assert.assertSame(newHeatSource, ((ICentralHeatingSystem) technologies.getPrimarySpaceHeater()).getHeatSource());
        Assert.assertNull(technologies.getSecondarySpaceHeater());
    }

    @Test
    public void testReplaceCentralHeatingHeatSourceShouldReplaceOldNonCentralSpaceHeating() {
        final ITechnologyModel technologies = new TechnologyModelImpl() {
            {
                setPrimarySpaceHeater(new StorageHeaterImpl() {
                });
            }
        };

        operations.installHeatSource(technologies, new IndividualSource(), EmitterType.RADIATORS, Collections.<HeatingSystemControlType>emptySet());

        Assert.assertTrue(technologies.getPrimarySpaceHeater() instanceof ICentralHeatingSystem);
        Assert.assertNull(technologies.getSecondarySpaceHeater());
    }

    @Test
    public void testReplaceCentralHeatingHeatSourceShouldReuseOldCentralHeating() {

        final ICentralHeatingSystem existingCentralHeating = new CentralHeatingSystemImpl() {
            {
                setHeatSource(new IndividualSource());
            }
        };

        final IHeatSource newHeatSource = new IndividualSource();

        final ITechnologyModel technologies = new TechnologyModelImpl() {
            {
                setPrimarySpaceHeater(existingCentralHeating);
            }
        };

        operations.installHeatSource(technologies, newHeatSource, EmitterType.RADIATORS, Collections.<HeatingSystemControlType>emptySet());

        Assert.assertSame(existingCentralHeating, technologies.getPrimarySpaceHeater());
        Assert.assertSame(newHeatSource, ((ICentralHeatingSystem) technologies.getPrimarySpaceHeater()).getHeatSource());
        Assert.assertNull(technologies.getSecondarySpaceHeater());
    }

    @Test
    public void testReplaceCentralHeatingHeatSourceShouldKeepOldSecondarySystems() {

        final IRoomHeater existingSecondaryHeating = new RoomHeaterImpl() {
        };

        final ITechnologyModel technologies = new TechnologyModelImpl() {
            {
                setPrimarySpaceHeater(new StorageHeaterImpl() {
                });
                setSecondarySpaceHeater(existingSecondaryHeating);
            }
        };

        final IHeatSource newHeatSource = new IndividualSource();

        operations.installHeatSource(technologies, newHeatSource, EmitterType.RADIATORS, Collections.<HeatingSystemControlType>emptySet());

        Assert.assertNotNull(technologies.getPrimarySpaceHeater());
        Assert.assertSame(existingSecondaryHeating, technologies.getSecondarySpaceHeater());
        Assert.assertSame(newHeatSource, ((ICentralHeatingSystem) technologies.getPrimarySpaceHeater()).getHeatSource());
    }

    @Test
    public void testInstallHotWaterCylinderShouldInstallStandardModernTank() {
        final ICentralWaterSystem water = new CentralWaterSystemImpl() {
        };
        operations.installWaterTank(water, false, 0, 0);

        Assert.assertTrue(water.isPrimaryPipeworkInsulated());

        final IWaterTank tank = water.getStore();
        Assert.assertTrue(tank instanceof IWaterTank);
        final IWaterTank standardTank = tank;

        Assert.assertTrue(standardTank.isThermostatFitted());
        Assert.assertTrue(standardTank.isFactoryInsulation());
        Assert.assertEquals(0f, standardTank.getVolume(), ERROR_DELTA);
        Assert.assertEquals(0f, standardTank.getInsulation(), ERROR_DELTA);
    }

    @Test
    public void testInstallHotWaterCylinderShouldReplaceExistingTank() {
        final IWaterTank tank = new WaterTankImpl() {
        };

        final ICentralWaterSystem water = new CentralWaterSystemImpl() {
            {
                setStore(tank);
            }
        };
        operations.installWaterTank(water, false, 0f, 0f);
        Assert.assertNotSame(tank, water.getStore());
    }

    @Test
    public void testInstallHotWaterCylinderShouldObeyRetainExisting() {
        final IWaterTank tank = new WaterTankImpl() {
        };

        final ICentralWaterSystem water = new CentralWaterSystemImpl() {
            {
                setStore(tank);
            }
        };

        operations.installWaterTank(water, true, 0f, 0f);
        Assert.assertSame(tank, water.getStore());
    }

    @Test
    public void testInstallHotWaterCylinderShouldIgnoreRetainExistingIfNoExistingCylinder() {
        final ICentralWaterSystem water = new CentralWaterSystemImpl() {
        };
        operations.installWaterTank(water, true, 0f, 0f);
        Assert.assertNotNull(water.getStore());
    }
}
