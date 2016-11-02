package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;

import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.*;
import static uk.org.cse.nhm.energycalculator.api.types.RegionType.Country.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;

public class SAPUValues {
	/**
	 * SAP 2012 Wall U-Value lookup
	 *
	 * RdSAP Section 5.1, 5.3
	 */
	public static class Walls {
		private static final double CALC = Double.POSITIVE_INFINITY;
		private static final double NONE = 0;
		
		private static final Optional<Boolean> Any = Optional.<Boolean>absent();
		private static final Optional<Boolean> Filled = Optional.of(true);
		private static final Optional<Boolean> Unfilled = Optional.of(false);
		
		private static final Set<Country> WalesAndEngland = ImmutableSet.of(Wales, England);
		private static final Set<Country> AnyCountry = ImmutableSet.of(Wales, England, Scotland, NorthernIreland);
		
		private static final Set<WallConstructionType> StoneAndSolid = ImmutableSet.of(GraniteOrWhinstone, Sandstone, SolidBrick);
		
		private static final List<ExternalWallRow> externalRows = new ArrayList<>();
		
		{
			/**
			 * SAP 2012 Tables S6, S7, S8
			 * Rows of the same time must be added in order from least insulation to most insulation.
			 */
			extRow(GraniteOrWhinstone, WalesAndEngland, 0, Any, 	new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30, 0.28});
			extRow(GraniteOrWhinstone, Scotland, 0, Any, 			new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(GraniteOrWhinstone, NorthernIreland, 0, Any, 	new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});
			
			extRow(Sandstone, WalesAndEngland, 0, Any,		 		new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30, 0.28});
			extRow(Sandstone, Scotland, 0, Any, 					new double[]{CALC, CALC, CALC, CALC, 1.50, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(Sandstone, NorthernIreland, 0, Any,		 		new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});
			
			extRow(SolidBrick, WalesAndEngland, 0, Any,	 			new double[]{2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30, 0.28});
			extRow(SolidBrick, Scotland, 0, Any, 					new double[]{2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(SolidBrick, NorthernIreland, 0, Any,	 			new double[]{2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});
			
			extRow(StoneAndSolid, WalesAndEngland, 50, Any, 		new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});
			extRow(StoneAndSolid, WalesAndEngland, 100, Any, 		new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(StoneAndSolid, WalesAndEngland, 150, Any, 		new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(StoneAndSolid, WalesAndEngland, 200, Any, 		new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			
			extRow(StoneAndSolid, Scotland, 50, Any, 				new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});
			extRow(StoneAndSolid, Scotland, 100, Any, 				new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(StoneAndSolid, Scotland, 150, Any, 				new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(StoneAndSolid, Scotland, 200, Any, 				new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});

			extRow(StoneAndSolid, NorthernIreland, 50, Any, 		new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, NONE, 0.21, 0.21});
			extRow(StoneAndSolid, NorthernIreland, 100, Any, 		new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, NONE, 0.17, 0.16});
			extRow(StoneAndSolid, NorthernIreland, 150, Any, 		new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, NONE, 0.14, 0.14});
			extRow(StoneAndSolid, NorthernIreland, 200, Any, 		new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			
			extRow(Cob, WalesAndEngland, 0, Any, 					new double[]{0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, 0.30, 0.25, 0.22});
			extRow(Cob, WalesAndEngland, 50, Any, 					new double[]{0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, 0.21, 0.19, 0.17});
			extRow(Cob, WalesAndEngland, 100, Any, 					new double[]{0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(Cob, WalesAndEngland, 150, Any, 					new double[]{0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(Cob, WalesAndEngland, 200, Any, 					new double[]{0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			
			extRow(Cob, Scotland, 0, Any, 							new double[]{0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, 0.30, 0.25, 0.22});
			extRow(Cob, Scotland, 50, Any, 							new double[]{0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, 0.21, 0.19, 0.17});
			extRow(Cob, Scotland, 100, Any, 						new double[]{0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(Cob, Scotland, 150, Any, 						new double[]{0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(Cob, Scotland, 200, Any, 						new double[]{0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
	
			extRow(Cob, NorthernIreland, 0, Any, 					new double[]{0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, NONE, 0.30, 0.28});
			extRow(Cob, NorthernIreland, 50, Any, 					new double[]{0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, NONE, 0.21, 0.21});
			extRow(Cob, NorthernIreland, 100, Any, 					new double[]{0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, NONE, 0.21, 0.16});
			extRow(Cob, NorthernIreland, 150, Any, 					new double[]{0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, NONE, 0.20, 0.14});
			extRow(Cob, NorthernIreland, 200, Any, 					new double[]{0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			
			extRow(Cavity, WalesAndEngland, 0, Unfilled, 			new double[]{2.10, 1.60, 1.60, 1.60, 1.60, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(Cavity, WalesAndEngland, 50, Unfilled, 			new double[]{0.60, 0.53, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30, 0.30, 0.25, 0.19, 0.17});
			extRow(Cavity, WalesAndEngland, 100, Unfilled, 			new double[]{0.35, 0.32, 0.32, 0.32, 0.32, 0.30, 0.24, 0.21, 0.21, 0.19, 0.17, 0.14});
			extRow(Cavity, WalesAndEngland, 150, Unfilled, 			new double[]{0.25, 0.23, 0.23, 0.23, 0.23, 0.21, 0.18, 0.17, 0.17, 0.15, 0.14, 0.12});
			extRow(Cavity, WalesAndEngland, 200, Unfilled, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			
			extRow(Cavity, Scotland, 0, Unfilled, 					new double[]{2.10, 1.60, 1.60, 1.60, 1.60, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(Cavity, Scotland, 50, Unfilled, 					new double[]{0.60, 0.53, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30, 0.30, 0.25, 0.19, 0.17});
			extRow(Cavity, Scotland, 100, Unfilled, 				new double[]{0.35, 0.32, 0.32, 0.32, 0.32, 0.30, 0.24, 0.21, 0.21, 0.19, 0.17, 0.14});
			extRow(Cavity, Scotland, 150, Unfilled, 				new double[]{0.25, 0.23, 0.23, 0.23, 0.23, 0.21, 0.18, 0.17, 0.17, 0.15, 0.14, 0.12});
			extRow(Cavity, Scotland, 200, Unfilled, 				new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});

			extRow(Cavity, NorthernIreland, 0, Unfilled, 			new double[]{2.10, 1.60, 1.60, 1.60, 1.50, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});
			extRow(Cavity, NorthernIreland, 50, Unfilled, 			new double[]{0.60, 0.53, 0.53, 0.53, 0.53, 0.45, 0.35, 0.35, 0.30, NONE, 0.21, 0.21});
			extRow(Cavity, NorthernIreland, 100, Unfilled, 			new double[]{0.35, 0.32, 0.32, 0.32, 0.32, 0.30, 0.24, 0.24, 0.21, NONE, 0.17, 0.16});
			extRow(Cavity, NorthernIreland, 150, Unfilled, 			new double[]{0.25, 0.23, 0.23, 0.23, 0.23, 0.21, 0.18, 0.18, 0.17, NONE, 0.14, 0.14});
			extRow(Cavity, NorthernIreland, 200, Unfilled, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			
			extRow(Cavity, WalesAndEngland, 0, Filled, 				new double[]{0.50, 0.50, 0.50, 0.50, 0.50, 0.40, 0.35, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(Cavity, WalesAndEngland, 50, Filled, 			new double[]{0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, 0.25, 0.25, 0.17});
			extRow(Cavity, WalesAndEngland, 100, Filled, 			new double[]{0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, 0.19, 0.19, 0.14});
			extRow(Cavity, WalesAndEngland, 150, Filled, 			new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, 0.15, 0.15, 0.12});
			extRow(Cavity, WalesAndEngland, 200, Filled, 			new double[]{0.14, 0.14, 0.14, 0.14, 0.14, 0.13, 0.13, 0.13, 0.13, 0.13, 0.12, 0.10}); 				

			extRow(Cavity, Scotland, 0, Filled, 					new double[]{0.50, 0.50, 0.50, 0.50, 0.50, 0.40, 0.35, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(Cavity, Scotland, 50, Filled, 					new double[]{0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, 0.25, 0.25, 0.17});
			extRow(Cavity, Scotland, 100, Filled, 					new double[]{0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, 0.19, 0.19, 0.14});
			extRow(Cavity, Scotland, 150, Filled, 					new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, 0.15, 0.15, 0.12});
			extRow(Cavity, Scotland, 200, Filled, 					new double[]{0.14, 0.14, 0.14, 0.14, 0.14, 0.13, 0.13, 0.13, 0.13, 0.13, 0.12, 0.10});
			
			extRow(Cavity, NorthernIreland, 0, Filled, 				new double[]{0.50, 0.50, 0.50, 0.50, 0.50, 0.40, 0.35, 0.45, 0.45, NONE, 0.30, 0.28});
			extRow(Cavity, NorthernIreland, 50, Filled, 			new double[]{0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, NONE, 0.25, 0.21});
			extRow(Cavity, NorthernIreland, 100, Filled, 			new double[]{0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, NONE, 0.19, 0.16});
			extRow(Cavity, NorthernIreland, 150, Filled, 			new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, NONE, 0.15, 0.14});
			extRow(Cavity, NorthernIreland, 200, Filled, 			new double[]{0.14, 0.14, 0.14, 0.14, 0.14, 0.13, 0.13, 0.13, 0.13, NONE, 0.12, 0.12});
			
			// Note that TimberFrame can only have internal insulation
			extRow(TimberFrame, WalesAndEngland, 0, Any, 			new double[]{2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});
			extRow(TimberFrame, WalesAndEngland, 1, Any, 			new double[]{0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});
			
			extRow(TimberFrame, Scotland, 0, Any, 					new double[]{2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});
			extRow(TimberFrame, Scotland, 1, Any, 					new double[]{0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});

			extRow(TimberFrame, NorthernIreland, 0, Any, 			new double[]{2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, NONE, 0.30, 0.28});
			extRow(TimberFrame, NorthernIreland, 1, Any, 			new double[]{0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, NONE, 0.30, 0.28});
			
			extRow(SystemBuild, WalesAndEngland, 0, Any, 			new double[]{2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(SystemBuild, WalesAndEngland, 50, Any, 			new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});
			extRow(SystemBuild, WalesAndEngland, 100, Any, 			new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(SystemBuild, WalesAndEngland, 150, Any, 			new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(SystemBuild, WalesAndEngland, 200, Any, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			
			extRow(SystemBuild, Scotland, 0, Any, 					new double[]{2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(SystemBuild, Scotland, 50, Any, 					new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});
			extRow(SystemBuild, Scotland, 100, Any, 				new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(SystemBuild, Scotland, 150, Any, 				new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(SystemBuild, Scotland, 200, Any,					new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			
			extRow(SystemBuild, NorthernIreland, 0, Any, 			new double[]{2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});
			extRow(SystemBuild, NorthernIreland, 50, Any, 			new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, NONE, 0.21, 0.21});
			extRow(SystemBuild, NorthernIreland, 100, Any, 			new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, NONE, 0.17, 0.16});
			extRow(SystemBuild, NorthernIreland, 150, Any, 			new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, NONE, 0.14, 0.14});
			extRow(SystemBuild, NorthernIreland, 200, Any, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});

            // This row handles all metal frame walls - origin unclear. Possibly from the Cambridge Housing Model?
			extRow(MetalFrame, AnyCountry, 0, Any, 					new double[]{2.20, 2.20, 2.20, 0.86, 0.86, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30, 0.30});
		}
		
		static class ExternalWallRow {
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((constructionTypes == null) ? 0 : constructionTypes.hashCode());
				result = prime * result + ((countries == null) ? 0 : countries.hashCode());
				result = prime * result + ((filledCavity == null) ? 0 : filledCavity.hashCode());
				long temp;
				temp = Double.doubleToLongBits(minimumInsulation);
				result = prime * result + (int) (temp ^ (temp >>> 32));
				result = prime * result + Arrays.hashCode(uValues);
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				ExternalWallRow other = (ExternalWallRow) obj;
				if (constructionTypes == null) {
					if (other.constructionTypes != null)
						return false;
				} else if (!constructionTypes.equals(other.constructionTypes))
					return false;
				if (countries == null) {
					if (other.countries != null)
						return false;
				} else if (!countries.equals(other.countries))
					return false;
				if (filledCavity == null) {
					if (other.filledCavity != null)
						return false;
				} else if (!filledCavity.equals(other.filledCavity))
					return false;
				if (Double.doubleToLongBits(minimumInsulation) != Double.doubleToLongBits(other.minimumInsulation))
					return false;
				if (!Arrays.equals(uValues, other.uValues))
					return false;
				return true;
			}

			private final Set<WallConstructionType> constructionTypes;
			private final Set<Country> countries;
			private final double minimumInsulation;
			private final Optional<Boolean> filledCavity;
			private final double[] uValues;

			ExternalWallRow(Set<WallConstructionType> constructionTypes, Set<Country> countries, double minimumInsulation, Optional<Boolean> filledCavity, double[] uValues) {
				if (uValues.length != SAPAgeBandValue.Band.values().length) {
					throw new IllegalArgumentException("Wrong number of u-values in wall lookup row " + constructionTypes + " " + countries + " " + minimumInsulation + " " + filledCavity + " " + uValues);
				}
				
				this.constructionTypes = constructionTypes;
				this.countries = countries;
				this.minimumInsulation = minimumInsulation;
				this.filledCavity = filledCavity;
				this.uValues = uValues;
			}
			
			public boolean test(WallConstructionType constructionType, Country country, double internalOrExternalInsulation, boolean cavityInsulationPresent) {
				return constructionTypes.contains(constructionType) && 
						countries.contains(country) && 
						internalOrExternalInsulation > this.minimumInsulation &&
						cavityInsulationMatch(cavityInsulationPresent);
			}
			
			private boolean cavityInsulationMatch(boolean cavityInsulationPresent) {
				if (this.filledCavity.isPresent()) {
					return this.filledCavity.get() == cavityInsulationPresent;
				} else {
					return true;
				}
			}
			
			public double get(SAPAgeBandValue.Band ageBand) {
				return this.uValues[ageBand.ordinal()];
			}
		}
		
		// Four different helper methods to make adding a test row easy.
		private static void extRow(WallConstructionType constructionType, Country country, double minimumInsulation, Optional<Boolean> filledCavity, double[] uValues) {
			extRow(ImmutableSet.of(constructionType), ImmutableSet.of(country), minimumInsulation, filledCavity, uValues);
		}

		private static void extRow(WallConstructionType constructionType, Set<Country> countries, double minimumInsulation, Optional<Boolean> filledCavity, double[] uValues) {
			extRow(ImmutableSet.of(constructionType), countries, minimumInsulation, filledCavity, uValues);
		}
		
		private static void extRow(Set<WallConstructionType> constructionTypes, Country country, double minimumInsulation, Optional<Boolean> filledCavity, double[] uValues) {
			extRow(constructionTypes, ImmutableSet.of(country), minimumInsulation, filledCavity, uValues);
		}
		
		private static void extRow(Set<WallConstructionType> constructionTypes, Set<Country> countries, double minimumInsulation, Optional<Boolean> filledCavity, double[] uValues) {
			externalRows.add(new ExternalWallRow(constructionTypes, countries, minimumInsulation, filledCavity, uValues));
		}
		
		private static final SAPAgeBandValue.Band highestStoneThicknessAdjustment = SAPAgeBandValue.Band.E;
		
		/**
		 * A minimum u-value for old solid walls based on their thickness.
		 * 
		 * Based on SAP 2012 S5.1.1 (footnotes a and b of Tables S6, S7, S8)
		 * @param country 
		 */
		private static double applyStoneThicknessAdjustment(double uValue, WallConstructionType constructionType, SAPAgeBandValue.Band ageBand, Country country) {
			if (ageBand.after(highestStoneThicknessAdjustment)) {
				return uValue;
			}
			
			final double baseUValue; 
			
			switch (constructionType) {
			case GraniteOrWhinstone:
				baseUValue = 3.3;
				break;
			case Sandstone:
				baseUValue = 3.0;
				break;
			default:
				return uValue;
			}
			
			final double maxUValue = baseUValue - (0.002 * Thickness.getExternal(country, constructionType, ageBand));
			
			return Math.min(maxUValue, uValue);
		}
		
		/**
		 * SAP 2012 Table S8B, Table 3.6
		 * 
		 * This is using a u-value to simulate air movement inside a cavity.
		 */
		private static double getPartyWallUValue(WallConstructionType constructionType, boolean hasCavityInsulation) {
			switch (constructionType) {
			// Solid masonry / timber frame / system built
			case Party_Solid:
			case Party_Cob:
			case Party_TimberFrame:
			case Party_SystemBuild:
			case Party_MetalFrame:
				return 0.0;
				
		    // Cavity Masonry unfilled/filled
			case Party_Cavity:
		    	return hasCavityInsulation ? 0.2 : 0.5;
		    	
		    default:
		    	throw new IllegalArgumentException("Unknown part wall construction type " + constructionType);
			}
		}
		
		private static double lookupExternalUValue(
				Country country, 
				WallConstructionType constructionType, 
				double externalOrInternalInsulationThickness, 
				boolean hasCavityInsulation, 
				Band ageBand
				) {
			for (ExternalWallRow row : externalRows) {
				if (row.test(constructionType, country, externalOrInternalInsulationThickness, hasCavityInsulation)) {
					return row.get(ageBand);
				}
			}
			
			throw new UnsupportedOperationException("The external wall u-value lookup does not support this combination, and needs to be extended " + country + " " + constructionType + " " + externalOrInternalInsulationThickness + " " + hasCavityInsulation + " " + ageBand);
		}
		
		public static double get(Country country, WallConstructionType constructionType, double externalOrInternalInsulationThickness, boolean hasCavityInsulation, SAPAgeBandValue.Band band) {
			switch (constructionType.getWallType()) {
				case External:
					return applyStoneThicknessAdjustment(
							lookupExternalUValue(country, constructionType, externalOrInternalInsulationThickness, hasCavityInsulation, band),
							constructionType, 
							band,
							country
						);
				case Internal:
					return 0;
				case Party:
					return getPartyWallUValue(constructionType, hasCavityInsulation);
				default:
					throw new IllegalArgumentException("Unknown wall type when looking up u-value " + constructionType.getWallType());
			}
		}

		/**
		 * SAP 2012 Table S3 
		 */
		public static class Thickness {
			public static double getExternal(Country country, WallConstructionType constructionType, SAPAgeBandValue.Band ageBand) {
				if (constructionType.getWallType() != WallType.External) {
					throw new IllegalArgumentException("Can only get thicknesses for external wall types " + constructionType);
				}
				
				return scotlandAdjustment(
						getRow(constructionType)[ageBand.ordinal()],
						country,
						constructionType,
						ageBand
					);
			}
			
			private static final SAPAgeBandValue.Band bigScotlandStoneAdjustment = Band.B; 

			private static double scotlandAdjustment(double uValue, Country country, WallConstructionType constructionType, Band ageBand) {
				switch(constructionType) {
				case GraniteOrWhinstone:
				case Sandstone:
					// Footnote 1
					if (ageBand.after(bigScotlandStoneAdjustment)) {
						return uValue + 100;
					} else {
						return uValue + 200;
					}
					
				case Cavity:
					// Footnote 2
					return uValue + 50;
				default:
					return uValue;
				}
			}

			private static double[] getRow(WallConstructionType constructionType) {
				// In the SAP document, the table lists J, K and L in the same column.
				// I've duplicated them for programming convenience.
				switch (constructionType) {
				case GraniteOrWhinstone:
				case Sandstone:
					return new double[]{500, 500, 500, 500, 450, 420, 420, 420, 450, 450, 450, 450};
				case SolidBrick:
					return new double[]{220, 220, 220, 220, 240, 250, 270, 270, 300, 300, 300, 300};
				case Cavity:
					return new double[]{250, 250, 250, 250, 250, 260, 270, 270, 300, 300, 300, 300};
				case TimberFrame:
				case MetalFrame:
					/**
					 * @assumption Metal Frame walls have a similar thickness to Timber Frame walls. 
					 */
					return new double[]{150, 150, 150, 250, 270, 270, 270, 270, 300, 300, 300, 300};
				case Cob:
					return new double[]{540, 540, 540, 540, 540, 540, 560, 560, 590, 590, 590, 590};
				case SystemBuild:
					return new double[]{250, 250, 250, 250, 250, 300, 300, 300, 300, 300, 300, 300};
				default:
					throw new UnsupportedOperationException("Unknown wall construction type when getting SAP thickness " + constructionType);
				}
			}
		}
	}
	
	public static class Doors {
		
	}
	
	public static class Windows {
		
	}
	
	public static class Floors {
		
	}
	
	public static class Roofs {
		
	}
}
