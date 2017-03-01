package uk.ac.ucl.hideem;
import uk.ac.ucl.hideem.IExposure.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.ucl.hideem.Person.Sex;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;


public class HealthModuleTest {
	//Sensitivity of exposures to retrofits and built forms
/*	@Test
	public void sensitivityPermeabilityOnExposures() {
        final IHealthModule hm = new HealthModule();
        
        
        final double defaultVal = 20;
        final double minVal = 0;
        final double maxVal = 50;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Permeability-Exposure response:\n");
        sb.append("After Perm");
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\tAfter_%s\tDelta_%s", e,e));
        }
        sb.append("\n");

        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(1),
                        18d, 18d, 
                        defaultVal, testVal,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
            
            sb.append(testVal);
            
            for (final IExposure.Type e : IExposure.Type.values()) {
            	sb.append(String.format("\t%f\t%f",
                        effect.finalExposure(e, IExposure.OccupancyType.H45_45_10),
                        effect.deltaExposure(e, IExposure.OccupancyType.H45_45_10)));
            }
            sb.append("\n");
            
            
        }
        System.out.println(sb);
    }	
	
	@Test
	public void sensitivityFabricHeatLossOnExposures() {
        final IHealthModule hm = new HealthModule();
        
        final double defaultVal = 10;
        final double minVal = 0;
        final double maxVal = 20;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Fabric Heat Loss-Exposure response:\n");
        sb.append("After Perm");
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\tAfter_%s\tDelta_%s", e,e));
        }
        sb.append("\n");

        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(1),
                        18d, 18d, 
                        20d, 20d,
                        defaultVal, testVal,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
            
            sb.append(testVal);
            
            for (final IExposure.Type e : IExposure.Type.values()) {
            	sb.append(String.format("\t%f\t%f",
                        effect.finalExposure(e, IExposure.OccupancyType.H45_45_10),
                        effect.deltaExposure(e, IExposure.OccupancyType.H45_45_10)));
            	
            	if (e != IExposure.Type.SIT2DayMax) {
            	Assert.assertEquals("Impact should be 0 for " + e, 0d, effect.deltaExposure(e, IExposure.OccupancyType.H45_45_10), 0d);
        		}        	
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }	
	
	@Test
	public void sensitivityBuiltFormAndFloorAreaAndLevelOnExposures() {
        final IHealthModule hm = new HealthModule();

        final double minVal = 40;
        final double maxVal = 200;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Built Form-Exposure response:\n");
        sb.append("Built Form\tFloor Area\tLevel");
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\tAfter_%s", e));
        }
        sb.append("\n");
        
        for (final BuiltForm.Type b : BuiltForm.Type.values()){
        	for (int f = 1; f<4; f++) {
        		for (int i = 0; i<3; i++) {
                    double testVal = minVal + i * (maxVal-minVal) / 3;
                    
                    final CumulativeHealthOutcome effect = hm.effectOf(
                            CumulativeHealthOutcome.factory(1),
                                18d, 18d, 
                                20d, 20d,
                                10d, 10d,
                                b, testVal, BuiltForm.Region.London, f, true, true, true, true, true, true,
                                ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
                    
                    sb.append(String.format("%s\t%f\t%d",
                    		b,testVal,f));
                    
                    for (final IExposure.Type e : IExposure.Type.values()) {
                    	sb.append(String.format("\t%f",
                                effect.finalExposure(e, IExposure.OccupancyType.H45_45_10)));	
                    }
                    sb.append("\n");
       
                }
        	}
        	        	
        }
        
        System.out.println(sb);
    }
	
	@Test
	public void sensitivityRegionOnExposures() {
		
		//Onlt Radon and SIT2DayMax should be different
        final IHealthModule hm = new HealthModule();

        final StringBuffer sb = new StringBuffer();
        sb.append("Region-Exposure response:\n");
        sb.append("Region");
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\tAfter_%s", e));
        }
        sb.append("\n");
        
        for (final BuiltForm.Region r : BuiltForm.Region.values()){
        	
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(1),
                        18d, 18d, 
                        20d, 20d,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, r, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
            
            sb.append(String.format("%s",
            		r));
            
            for (final IExposure.Type e : IExposure.Type.values()) {
            	sb.append(String.format("\t%f",
                        effect.finalExposure(e, IExposure.OccupancyType.H45_45_10)));	
            }
            sb.append("\n");
    	
        }
        
        System.out.println(sb);
    }	
	
	
	@Test
	public void sensitivityVentillationTypeOnExposures() {
        final IHealthModule hm = new HealthModule();
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Ventillation Type-Exposure relationships:\n");
        sb.append("Before Vent\tAfter Vent");
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\tAfter_%s\tDelta_%s", e,e));
        }
        sb.append("\n");

        for (final IExposure.VentilationType iVent : IExposure.VentilationType.values()) {
        	for (final IExposure.VentilationType fVent : IExposure.VentilationType.values()) {
        		
        		sb.append(String.format("%s\t%s", iVent,fVent));
        		
        		boolean iExtract = false;
        		boolean iTrickle = false;
        		boolean fExtract = false;
        		boolean fTrickle = false;
        		
        		if (iVent == IExposure.VentilationType.E){
        			iExtract = true;
        		}else if (iVent == IExposure.VentilationType.T){
        			iTrickle = true;
        		}else if (iVent == IExposure.VentilationType.TE){
        			iExtract = true;
        			iTrickle = true;
        		}
        		
        		if (fVent == IExposure.VentilationType.E){
        			fExtract = true;
        		}else if (fVent == IExposure.VentilationType.T){
        			fTrickle = true;
        		}else if (fVent == IExposure.VentilationType.TE){
        			fExtract = true;
        			fTrickle = true;
        		}
        		
        		final CumulativeHealthOutcome effect = hm.effectOf(
                        CumulativeHealthOutcome.factory(1),
                            18d, 18d, 
                            20d, 20d,
                            10d, 10d,
                            BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, iExtract, iTrickle, fExtract, fTrickle, true, true,
                            ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
                
                for (final IExposure.Type e : IExposure.Type.values()) {
                	sb.append(String.format("\t%f\t%f",
                            effect.finalExposure(e, IExposure.OccupancyType.H45_45_10),
                            effect.deltaExposure(e, IExposure.OccupancyType.H45_45_10)));
                }
                sb.append("\n");
        		
        	}
        }

        System.out.println(sb);
    }
	
	@Test
	public void sensitivityGlazingTypeOnExposures() {
		//Only effects SIT2DayMax
        final IHealthModule hm = new HealthModule();
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Add Glazing:\n");
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\tAfter_%s\tDelta_%s", e,e));
        }
        sb.append("\n");


		final CumulativeHealthOutcome effect = hm.effectOf(
                CumulativeHealthOutcome.factory(1),
                    18d, 18d, 
                    20d, 20d,
                    10d, 10d,
                    BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, false, true,
                    ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
        
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("\t%f\t%f",
                    effect.finalExposure(e, IExposure.OccupancyType.H45_45_10),
                    effect.deltaExposure(e, IExposure.OccupancyType.H45_45_10)));
        }
        sb.append("\n");

        System.out.println(sb);
    }*/	
	
/*	//Test Sensitivity of RRs to exposures
	@Test
	public void sensitivityRROnExposures() {
		
		//These are needed to make an instance of a HealthOutcome
		final IHealthModule hm = new HealthModule();
		final HealthOutcome effect = hm.effectOf(
                CumulativeHealthOutcome.factory(1),
                    18d, 18d, 
                    20d, 20d,
                    10d, 10d,
                    BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                    ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
                
        final double defaultVal = 20;
        final double minVal = 0;
        final double maxVal = 50;
        
        final StringBuffer sb = new StringBuffer();
        
        for (final IExposure.Type e : IExposure.Type.values()) {
        	sb.append(String.format("%s-RR response:\n",e));
            sb.append("Delta Exposure");
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%s QALYs", d));
            }
            sb.append("\n");

            for (int i = 0; i<20; i++) {
                double testVal = minVal + i * (maxVal-minVal) / 20;
                
                effect.setExposures(e, IExposure.OccupancyType.H45_45_10, defaultVal, testVal);
                          
                sb.append(effect.deltaExposure(e, IExposure.OccupancyType.H45_45_10));

                for (final Disease.Type d : Disease.Type.values()) {
                	final double riskChangeTime = d.relativeRisk(effect, IExposure.OccupancyType.H45_45_10);
                	
                	
                	sb.append(String.format("\t%f", riskChangeTime));
                }
                sb.append("\n");
                
                
            }        	
        	
        }
        
        System.out.println(sb);
        
    }*/
	
/*	//Test Sensitivity of QALYs to permeability
	//Would have liked to have done directly for each exposure but the calculateQalys method is private and the coefficients are to difficult to read in
	@Test
	public void sensitivityPermeabilityOnQalys() {
        final IHealthModule hm = new HealthModule();

        final double defaultVal = 20;
        final double minVal = 0;
        final double maxVal = 50;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Permeability-QALY response:\n");
        sb.append("Perm");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format("\t%s", d));
        }
        
        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        18d, 18d, 
                        defaultVal, testVal,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));

            sb.append(String.format("%f\t",testVal));
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%f", effect.qaly(d)));
            }
            
            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }
	
	@Test
	public void sensitivitySITOnQalys() {
        final IHealthModule hm = new HealthModule();

        final double defaultVal = 18;
        final double minVal = 5;
        final double maxVal = 30;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("SIT-QALY response:\n");
        sb.append("SIT");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format("\t%s", d));
        }
                
        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        defaultVal, testVal, 
                        20d, 20d,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));

            sb.append(String.format("%f\t",testVal));
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%f", effect.qaly(d)));
            }

            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }
	
	//Morb QALY tests
	@Test
	public void sensitivityPermeabilityOnMorbQalys() {
        final IHealthModule hm = new HealthModule();

        final double defaultVal = 20;
        final double minVal = 0;
        final double maxVal = 50;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Permeability-Morb QALY response:\n");
        sb.append("Perm");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format("\t%s", d));
        }
        
        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        18d, 18d, 
                        defaultVal, testVal,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));

            sb.append(String.format("%f\t",testVal));
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%f", effect.morbQaly(d)));
            }
            
            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }
	
	@Test
	public void sensitivitySITOnMorbQalys() {
        final IHealthModule hm = new HealthModule();

        final double defaultVal = 18;
        final double minVal = 5;
        final double maxVal = 30;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("SIT-Morb QALY response:\n");
        sb.append("SIT");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format("\t%s", d));
        }
                
        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        defaultVal, testVal, 
                        20d, 20d,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));

            sb.append(String.format("%f\t",testVal));
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%f", effect.morbQaly(d)));
            }

            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }
	
	//Costs
	@Test
	public void sensitivityPermeabilityOnCosts() {
        final IHealthModule hm = new HealthModule();

        final double defaultVal = 20;
        final double minVal = 0;
        final double maxVal = 50;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Permeability-Cost response:\n");
        sb.append("Perm");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format("\t%s", d));
        }
        
        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        18d, 18d, 
                        defaultVal, testVal,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));

            sb.append(String.format("%f\t",testVal));
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%f", effect.cost(d)));
            }
            
            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }
	
	@Test
	public void sensitivitySITOnCosts() {
        final IHealthModule hm = new HealthModule();

        final double defaultVal = 18;
        final double minVal = 5;
        final double maxVal = 30;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("SIT-Cost response:\n");
        sb.append("SIT");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format("\t%s", d));
        }
                
        for (int i = 0; i<20; i++) {
            double testVal = minVal + i * (maxVal-minVal) / 20;
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        defaultVal, testVal, 
                        20d, 20d,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person(40, Sex.MALE, true, 2)));

            sb.append(String.format("%f\t",testVal));
            for (final Disease.Type d : Disease.Type.values()) {
            	sb.append(String.format("\t%f", effect.cost(d)));
            }

            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }*/
	
	//Person tests
	@Test
	public void personAgeTest() {
        final IHealthModule hm = new HealthModule();

        final double minVal = 0;
        final double maxVal = 150;
        
        final StringBuffer sb = new StringBuffer();
        sb.append("Age-QALY response:\n");
        sb.append("Age\tMale QALYs\tFemale QALYs");
        sb.append("\n");
        
        for (int i = 0; i<20; i++) {
            int testVal = (int) Math.round(minVal + i * (maxVal-minVal) / 20);
            
            final CumulativeHealthOutcome effect = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        18d, 20d, 
                        25d, 20d,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person( testVal, Sex.MALE, false, 2)));

            sb.append(String.format("%03d\t",testVal));
            double sumMaleQalys = 0;
            for (final Disease.Type d : Disease.Type.values()) {
            	if (Double.isNaN(effect.qaly(d)) != true){
            		sumMaleQalys += effect.qaly(d);
            	}
            	
            }
            sb.append(String.format("\t%f", sumMaleQalys));
            
            final CumulativeHealthOutcome effectFemale = hm.effectOf(
                    CumulativeHealthOutcome.factory(10),
                        18d, 20d, 
                        25d, 20d,
                        10d, 10d,
                        BuiltForm.Type.SemiDetached, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                        ImmutableList.of(new Person( testVal, Sex.FEMALE, false, 2)));

            double sumFemaleQalys = 0;
            for (final Disease.Type d : Disease.Type.values()) {
            	if (Double.isNaN(effectFemale.qaly(d)) != true){
            		sumFemaleQalys += effectFemale.qaly(d);	
            	}
            }
            
            sb.append(String.format("\t%f", sumFemaleQalys));
            sb.append("\n");
           	            
        }
        
        System.out.println(sb);
    }
	
	//Tom's tests	
/*    @Test
    public void deltaTemperatureIsSane() {
        final IHealthModule hm = new HealthModule();

        final double tminVal = 14;
        final double tmaxVal = 21;

        System.out.println("Rebate function:");
        System.out.println("base\t+100\t+500\t+1000");
        for (int i = 0; i<20; i++) {
            double ti = tminVal + i * (tmaxVal-tminVal) / 20;
            System.out.println(String.format("%f\t%f\t%f\t%f",
                                             ti,
                                             hm.getRebateDeltaTemperature(ti, 100),
                                             hm.getRebateDeltaTemperature(ti, 500),
                                             hm.getRebateDeltaTemperature(ti, 1000)));
        }

        final double deltaTemperature = hm.getRebateDeltaTemperature(18, 1000);

        System.out.println("Deduced temperature/cost function:");
        for (int i = 0; i<HealthModule.CTC_COST.length; i++) {
            System.out.println(String.format("%f\t%f", HealthModule.CTC_TEMPERATURE[i], HealthModule.CTC_COST[HealthModule.CTC_COST.length - (i + 1)]));
        }

    }

    @Test
    public void testInterpolator() {
        double x = HealthModule.interpolate(0.5, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0.5, 0);
        x = HealthModule.interpolate(0.1, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0.1, 0);
        x = HealthModule.interpolate(0.9, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0.9, 0);

        x = HealthModule.interpolate(0, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0, 0);

        x = HealthModule.interpolate(1, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 1, 0);

        x = HealthModule.interpolate(2, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 1, 0);
    }

	@Test
	public void healthImpactOfDoingNothingIsZero() {
		final IHealthModule hm = new HealthModule();
		
        final CumulativeHealthOutcome effect = hm.effectOf(
            CumulativeHealthOutcome.factory(10),
                18d, 18d, 10d, 10d,
                10d, 20d,
                BuiltForm.Type.Bungalow, 100d, BuiltForm.Region.London, 1, true, true, true, true, true, true,
                ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
		
		System.out.println(effect);
		for (final Disease.Type d : Disease.Type.values()) {
			for (int i = 0; i<10; i++) {
                Assert.assertEquals("Impact should be 0 for " + d + " in year " + i, 0d, effect.cost(d, i), 0d);
			}
		}
	}*/
}
