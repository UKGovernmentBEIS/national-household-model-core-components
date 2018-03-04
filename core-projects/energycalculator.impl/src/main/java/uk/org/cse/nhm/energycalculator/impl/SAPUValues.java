package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;

import static uk.org.cse.nhm.energycalculator.api.types.WallConstructionType.*;
import static uk.org.cse.nhm.energycalculator.api.types.RegionType.Country.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.EnumMap;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

public class SAPUValues {
	private static final double _check(final double d) {
		assert !(Double.isNaN(d) || Double.isInfinite(d)) : "SAP table returned NaN or infinite";
		return d;
	}

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

        private static final Map<WallConstructionType, List<ExternalWallRow>> externalRows = new EnumMap<>(WallConstructionType.class);

		static {
			/**
			 * SAP 2012 Tables S6, S7, S8
			 * Rows of the same time must be added in order from most insulation to least insulation.
			 */
			extRow(StoneAndSolid, WalesAndEngland, 200, Any, 		new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(StoneAndSolid, WalesAndEngland, 150, Any, 		new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(StoneAndSolid, WalesAndEngland, 100, Any, 		new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(StoneAndSolid, WalesAndEngland, 50, Any, 		new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});

			extRow(StoneAndSolid, Scotland, 200, Any, 				new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(StoneAndSolid, Scotland, 150, Any, 				new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(StoneAndSolid, Scotland, 100, Any, 				new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(StoneAndSolid, Scotland, 50, Any, 				new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});

			extRow(StoneAndSolid, NorthernIreland, 200, Any, 		new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			extRow(StoneAndSolid, NorthernIreland, 150, Any, 		new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, NONE, 0.14, 0.14});
			extRow(StoneAndSolid, NorthernIreland, 100, Any, 		new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, NONE, 0.17, 0.16});
			extRow(StoneAndSolid, NorthernIreland, 50, Any, 		new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, NONE, 0.21, 0.21});

			extRow(GraniteOrWhinstone, WalesAndEngland, 0, Any, 	new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30, 0.28});
			extRow(GraniteOrWhinstone, Scotland, 0, Any, 			new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(GraniteOrWhinstone, NorthernIreland, 0, Any, 	new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});

			extRow(Sandstone, WalesAndEngland, 0, Any,		 		new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30, 0.28});
			extRow(Sandstone, Scotland, 0, Any, 					new double[]{CALC, CALC, CALC, CALC, 1.50, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(Sandstone, NorthernIreland, 0, Any,		 		new double[]{CALC, CALC, CALC, CALC, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});

			extRow(SolidBrick, WalesAndEngland, 0, Any,	 			new double[]{2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.60, 0.45, 0.35, 0.30, 0.28});
			extRow(SolidBrick, Scotland, 0, Any, 					new double[]{2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});
			extRow(SolidBrick, NorthernIreland, 0, Any,	 			new double[]{2.10, 2.10, 2.10, 2.10, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});

			extRow(Cob, WalesAndEngland, 200, Any, 					new double[]{0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(Cob, WalesAndEngland, 150, Any, 					new double[]{0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(Cob, WalesAndEngland, 100, Any, 					new double[]{0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(Cob, WalesAndEngland, 50, Any, 					new double[]{0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, 0.21, 0.19, 0.17});
			extRow(Cob, WalesAndEngland, 0, Any, 					new double[]{0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, 0.30, 0.25, 0.22});

			extRow(Cob, Scotland, 200, Any, 						new double[]{0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(Cob, Scotland, 150, Any, 						new double[]{0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(Cob, Scotland, 100, Any, 						new double[]{0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(Cob, Scotland, 50, Any, 							new double[]{0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, 0.21, 0.19, 0.17});
			extRow(Cob, Scotland, 0, Any, 							new double[]{0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, 0.30, 0.25, 0.22});

			extRow(Cob, NorthernIreland, 200, Any, 					new double[]{0.16, 0.16, 0.16, 0.16, 0.16, 0.16, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			extRow(Cob, NorthernIreland, 150, Any, 					new double[]{0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, 0.20, NONE, 0.20, 0.14});
			extRow(Cob, NorthernIreland, 100, Any, 					new double[]{0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, 0.26, NONE, 0.21, 0.16});
			extRow(Cob, NorthernIreland, 50, Any, 					new double[]{0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.35, 0.35, 0.30, NONE, 0.21, 0.21});
			extRow(Cob, NorthernIreland, 0, Any, 					new double[]{0.80, 0.80, 0.80, 0.80, 0.80, 0.80, 0.60, 0.60, 0.45, NONE, 0.30, 0.28});

			extRow(Cavity, WalesAndEngland, 200, Unfilled, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(Cavity, WalesAndEngland, 150, Unfilled, 			new double[]{0.25, 0.23, 0.23, 0.23, 0.23, 0.21, 0.18, 0.17, 0.17, 0.15, 0.14, 0.12});
			extRow(Cavity, WalesAndEngland, 100, Unfilled, 			new double[]{0.35, 0.32, 0.32, 0.32, 0.32, 0.30, 0.24, 0.21, 0.21, 0.19, 0.17, 0.14});
			extRow(Cavity, WalesAndEngland, 50, Unfilled, 			new double[]{0.60, 0.53, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30, 0.30, 0.25, 0.19, 0.17});
			extRow(Cavity, WalesAndEngland, 0, Unfilled, 			new double[]{2.10, 1.60, 1.60, 1.60, 1.60, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});


			extRow(Cavity, Scotland, 200, Unfilled, 				new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(Cavity, Scotland, 150, Unfilled, 				new double[]{0.25, 0.23, 0.23, 0.23, 0.23, 0.21, 0.18, 0.17, 0.17, 0.15, 0.14, 0.12});
			extRow(Cavity, Scotland, 100, Unfilled, 				new double[]{0.35, 0.32, 0.32, 0.32, 0.32, 0.30, 0.24, 0.21, 0.21, 0.19, 0.17, 0.14});
			extRow(Cavity, Scotland, 50, Unfilled, 					new double[]{0.60, 0.53, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30, 0.30, 0.25, 0.19, 0.17});
			extRow(Cavity, Scotland, 0, Unfilled, 					new double[]{2.10, 1.60, 1.60, 1.60, 1.60, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});

			extRow(Cavity, NorthernIreland, 200, Unfilled, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			extRow(Cavity, NorthernIreland, 150, Unfilled, 			new double[]{0.25, 0.23, 0.23, 0.23, 0.23, 0.21, 0.18, 0.18, 0.17, NONE, 0.14, 0.14});
			extRow(Cavity, NorthernIreland, 100, Unfilled, 			new double[]{0.35, 0.32, 0.32, 0.32, 0.32, 0.30, 0.24, 0.24, 0.21, NONE, 0.17, 0.16});
			extRow(Cavity, NorthernIreland, 50, Unfilled, 			new double[]{0.60, 0.53, 0.53, 0.53, 0.53, 0.45, 0.35, 0.35, 0.30, NONE, 0.21, 0.21});
			extRow(Cavity, NorthernIreland, 0, Unfilled, 			new double[]{2.10, 1.60, 1.60, 1.60, 1.50, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});

			extRow(Cavity, WalesAndEngland, 200, Filled, 			new double[]{0.14, 0.14, 0.14, 0.14, 0.14, 0.13, 0.13, 0.13, 0.13, 0.13, 0.12, 0.10});
			extRow(Cavity, WalesAndEngland, 150, Filled, 			new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, 0.15, 0.15, 0.12});
			extRow(Cavity, WalesAndEngland, 100, Filled, 			new double[]{0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, 0.19, 0.19, 0.14});
			extRow(Cavity, WalesAndEngland, 50, Filled, 			new double[]{0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, 0.25, 0.25, 0.17});
			extRow(Cavity, WalesAndEngland, 0, Filled, 				new double[]{0.50, 0.50, 0.50, 0.50, 0.50, 0.40, 0.35, 0.45, 0.45, 0.30, 0.25, 0.22});

			extRow(Cavity, Scotland, 200, Filled, 					new double[]{0.14, 0.14, 0.14, 0.14, 0.14, 0.13, 0.13, 0.13, 0.13, 0.13, 0.12, 0.10});
			extRow(Cavity, Scotland, 150, Filled, 					new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, 0.15, 0.15, 0.12});
			extRow(Cavity, Scotland, 100, Filled, 					new double[]{0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, 0.19, 0.19, 0.14});
			extRow(Cavity, Scotland, 50, Filled, 					new double[]{0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, 0.25, 0.25, 0.17});
			extRow(Cavity, Scotland, 0, Filled, 					new double[]{0.50, 0.50, 0.50, 0.50, 0.50, 0.40, 0.35, 0.45, 0.45, 0.30, 0.25, 0.22});

			extRow(Cavity, NorthernIreland, 200, Filled, 			new double[]{0.14, 0.14, 0.14, 0.14, 0.14, 0.13, 0.13, 0.13, 0.13, NONE, 0.12, 0.12});
			extRow(Cavity, NorthernIreland, 150, Filled, 			new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.16, 0.15, 0.15, 0.15, NONE, 0.15, 0.14});
			extRow(Cavity, NorthernIreland, 100, Filled, 			new double[]{0.22, 0.22, 0.22, 0.22, 0.22, 0.20, 0.19, 0.19, 0.19, NONE, 0.19, 0.16});
			extRow(Cavity, NorthernIreland, 50, Filled, 			new double[]{0.31, 0.31, 0.31, 0.31, 0.31, 0.27, 0.25, 0.25, 0.25, NONE, 0.25, 0.21});
			extRow(Cavity, NorthernIreland, 0, Filled, 				new double[]{0.50, 0.50, 0.50, 0.50, 0.50, 0.40, 0.35, 0.45, 0.45, NONE, 0.30, 0.28});

			// Note that TimberFrame can only have internal insulation
			extRow(TimberFrame, WalesAndEngland, 1, Any, 			new double[]{0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});
			extRow(TimberFrame, WalesAndEngland, 0, Any, 			new double[]{2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});

			extRow(TimberFrame, Scotland, 1, Any, 					new double[]{0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});
			extRow(TimberFrame, Scotland, 0, Any, 					new double[]{2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, 0.30, 0.25, 0.22});

			extRow(TimberFrame, NorthernIreland, 1, Any, 			new double[]{0.60, 0.55, 0.55, 0.40, 0.40, 0.40, 0.40, 0.40, 0.40, NONE, 0.30, 0.28});
			extRow(TimberFrame, NorthernIreland, 0, Any, 			new double[]{2.50, 1.90, 1.90, 1.00, 0.80, 0.45, 0.40, 0.40, 0.40, NONE, 0.30, 0.28});

			extRow(SystemBuild, WalesAndEngland, 200, Any, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(SystemBuild, WalesAndEngland, 150, Any, 			new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(SystemBuild, WalesAndEngland, 100, Any, 			new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(SystemBuild, WalesAndEngland, 50, Any, 			new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});
			extRow(SystemBuild, WalesAndEngland, 0, Any, 			new double[]{2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});

			extRow(SystemBuild, Scotland, 200, Any,					new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, 0.13, 0.12, 0.10});
			extRow(SystemBuild, Scotland, 150, Any, 				new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, 0.15, 0.14, 0.12});
			extRow(SystemBuild, Scotland, 100, Any, 				new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, 0.19, 0.17, 0.14});
			extRow(SystemBuild, Scotland, 50, Any, 					new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, 0.21, 0.19, 0.17});
			extRow(SystemBuild, Scotland, 0, Any, 					new double[]{2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.45, 0.45, 0.30, 0.25, 0.22});

			extRow(SystemBuild, NorthernIreland, 200, Any, 			new double[]{0.18, 0.18, 0.18, 0.18, 0.18, 0.17, 0.15, 0.15, 0.14, NONE, 0.12, 0.12});
			extRow(SystemBuild, NorthernIreland, 150, Any, 			new double[]{0.25, 0.25, 0.25, 0.25, 0.25, 0.21, 0.18, 0.18, 0.17, NONE, 0.14, 0.14});
			extRow(SystemBuild, NorthernIreland, 100, Any, 			new double[]{0.35, 0.35, 0.35, 0.35, 0.35, 0.32, 0.24, 0.24, 0.21, NONE, 0.17, 0.16});
			extRow(SystemBuild, NorthernIreland, 50, Any, 			new double[]{0.60, 0.60, 0.60, 0.60, 0.55, 0.45, 0.35, 0.30, 0.30, NONE, 0.21, 0.21});
			extRow(SystemBuild, NorthernIreland, 0, Any, 			new double[]{2.00, 2.00, 2.00, 2.00, 1.70, 1.00, 0.60, 0.45, 0.45, NONE, 0.30, 0.28});

            // This row handles all metal frame walls - origin unclear. Possibly from the Cambridge Housing Model?
			extRow(MetalFrame, AnyCountry, 0, Any, 					new double[]{2.20, 2.20, 2.20, 0.86, 0.86, 0.53, 0.53, 0.53, 0.45, 0.35, 0.30, 0.30});
		}

		static class ExternalWallRow {
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
                result = prime * result + ((countries == null) ? 0 : countries.hashCode());
				result = prime * result + ((filledCavity == null) ? 0 : filledCavity.hashCode());
				long temp;
				temp = Double.doubleToLongBits(minimumInsulation);
				result = prime * result + (int) (temp ^ (temp >>> 32));
				result = prime * result + Arrays.hashCode(uValues);
				return result;
			}

			@Override
			public boolean equals(final Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
                final ExternalWallRow other = (ExternalWallRow) obj;
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

            private final Set<Country> countries;
            private final double minimumInsulation;
            private final Optional<Boolean> filledCavity;
			private final double[] uValues;

            ExternalWallRow(final Set<Country> countries, final double minimumInsulation, final Optional<Boolean> filledCavity, final double[] uValues) {
				if (uValues.length != SAPAgeBandValue.Band.values().length) {
                    throw new IllegalArgumentException("Wrong number of u-values in wall lookup row " + " " + countries + " " + minimumInsulation + " " + filledCavity + " " + uValues);
				}

                this.countries = countries;
				this.minimumInsulation = minimumInsulation;
				this.filledCavity = filledCavity;
				this.uValues = uValues;
			}

            public boolean test(final Country country, final double internalOrExternalInsulation, final boolean cavityInsulationPresent) {
                return 	countries.contains(country) &&
						internalOrExternalInsulation >= this.minimumInsulation &&
						cavityInsulationMatch(cavityInsulationPresent);
			}

			private boolean cavityInsulationMatch(final boolean cavityInsulationPresent) {
				if (this.filledCavity.isPresent()) {
                    return this.filledCavity.get().equals(cavityInsulationPresent);
				} else {
					return true;
				}
			}

			public double get(final SAPAgeBandValue.Band ageBand) {
				return this.uValues[ageBand.ordinal()];
			}
		}

		// Four different helper methods to make adding a test row easy.
		private static void extRow(final WallConstructionType constructionType, final Country country, final double minimumInsulation, final Optional<Boolean> filledCavity, final double[] uValues) {
			extRow(ImmutableSet.of(constructionType), ImmutableSet.of(country), minimumInsulation, filledCavity, uValues);
		}

		private static void extRow(final WallConstructionType constructionType, final Set<Country> countries, final double minimumInsulation, final Optional<Boolean> filledCavity, final double[] uValues) {
			extRow(ImmutableSet.of(constructionType), countries, minimumInsulation, filledCavity, uValues);
		}

		private static void extRow(final Set<WallConstructionType> constructionTypes, final Country country, final double minimumInsulation, final Optional<Boolean> filledCavity, final double[] uValues) {
			extRow(constructionTypes, ImmutableSet.of(country), minimumInsulation, filledCavity, uValues);
		}

        private static void extRow(final Set<WallConstructionType> constructionTypes, final Set<Country> countries, final double minimumInsulation, final Optional<Boolean> filledCavity, final double[] uValues) {
            final ExternalWallRow row = new ExternalWallRow(countries, minimumInsulation, filledCavity, uValues);
            for (final WallConstructionType type : constructionTypes) {
                if (!externalRows.containsKey(type)) externalRows.put(type, new ArrayList<>());
                externalRows.get(type).add(row);
            }
        }

		private static final SAPAgeBandValue.Band highestStoneThicknessAdjustment = SAPAgeBandValue.Band.E;

		/**
		 * A minimum u-value for old solid walls based on their thickness.
		 *
		 * Based on SAP 2012 S5.1.1 (footnotes a and b of Tables S6, S7, S8)
		 * @param country
		 */
		private static double applyStoneThicknessAdjustment(final double uValue, final WallConstructionType constructionType, final double insulationThickness, final double thickness, final SAPAgeBandValue.Band ageBand) {
			if (insulationThickness > 0 || ageBand.after(highestStoneThicknessAdjustment)) {
				return _check(uValue);
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
				return _check(uValue);
			}

			final double maxUValue = baseUValue - (0.002 * thickness);

			return _check(Math.min(maxUValue, uValue));
		}

		/**
		 * SAP 2012 Table S8B, Table 3.6
		 *
		 * This is using a u-value to simulate air movement inside a cavity.
		 */
		private static double getPartyWallUValue(final WallConstructionType constructionType, final boolean hasCavityInsulation) {
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
		    	return hasCavityInsulation ? 0.0 : 0.5;

		    default:
		    	throw new IllegalArgumentException("Unknown part wall construction type " + constructionType);
			}
		}

		private static double lookupExternalUValue(
                final WallConstructionType constructionType,
                final Country country,
				final double externalOrInternalInsulationThickness,
				final boolean hasCavityInsulation,
				final Band ageBand
				) {
            for (final ExternalWallRow row : externalRows.get(constructionType)) {
                if (row.test(country, externalOrInternalInsulationThickness, hasCavityInsulation)) {
					return row.get(ageBand);
				}
			}

			throw new UnsupportedOperationException("The external wall u-value lookup does not support this combination, and needs to be extended " + country + " " + constructionType + " " + externalOrInternalInsulationThickness + " " + hasCavityInsulation + " " + ageBand);
		}

		public static double get(final Country country, final WallConstructionType constructionType, final double externalOrInternalInsulationThickness, final boolean hasCavityInsulation, final SAPAgeBandValue.Band band, final double thickness) {
			switch (constructionType.getWallType()) {
				case External:
					return _check(applyStoneThicknessAdjustment(
                            lookupExternalUValue(constructionType, country, externalOrInternalInsulationThickness, hasCavityInsulation, band),
							constructionType,
							externalOrInternalInsulationThickness,
							thickness,
							band
						));
				case Internal:
					return 0;
				case Party:
					return getPartyWallUValue(constructionType, hasCavityInsulation);
				default:
					throw new IllegalArgumentException("Unknown wall type when looking up u-value " + constructionType.getWallType());
			}
		}
	}

	/**
	 * @assumption All doors in the stock lead to the outside.
	 *
	 * (We have no information about hallways or stairwells adjacent to the outside of the dwelling.)
	 */
	public static class Doors {
		/**
		 * @param country
		 * @return the u-value for an outside door according to SAP 2012 Table S15A
		 */
		public static double getOutside(final Band ageBand, final Country country) {
			switch (ageBand) {
			case A:
			case B:
			case C:
			case D:
			case E:
			case F:
			case G:
			case H:
			case I:
			case J:
				return 3.0;
			case K:
				return 2.0;
			case L:
				switch(country) {
				case England:
				case Wales:
				case NorthernIreland:
					return 1.8;
				case Scotland:
					return 1.6;
				default:
					throw new IllegalArgumentException("Unknown country when computing door u-value " + country);
				}
			default:
				throw new IllegalArgumentException("Unknown age band when computing door u-value " + ageBand);
			}
		}

	}

	/**
	 * Window u-value lookup based on Table 6e.
	 *
	 * (RdSAP also includes a Table S14, but we ignore this as we have extra information about frame type and insulation in the model.)
	 *
	 * We ignore the footnotes in the table.
	 */
	public static class Windows {
		private static final IWindowUValues windowUValues = new WindowUValues();

		public static double get(final FrameType frameType, final GlazingType glazingType, final WindowInsulationType insulationType) {
			return _check(windowUValues.getUValue(frameType, glazingType, insulationType));
		}
	}

	/**
	 * Floor u-value lookup based on S5.5 for ground floors, and Table S12 for other floors with heat loss areas.
	 *
	 * We ignore footnote 1.
	 */
	public static class Floors {
        private static final double[] insulationLevels = new double[]{0,    50,   100,  150};

        private static final double[] KtoLFloorU       = new double[]{0.22, 0.22, 0.22, 0.22};
        private static final double[] JFloorU          = new double[]{0.25, 0.25, 0.25, 0.22};
        private static final double[] HtoIFloorU       = new double[]{0.51, 0.50, 0.30, 0.22};
        private static final double[] AtoGFloorU       = new double[]{1.20, 0.50, 0.30, 0.22};

		private static double scotlandFootnote2 = 0.18;

		private static final GroundFloorUValues groundFloor = new GroundFloorUValues();

		public static double get(
			final boolean isParty,
			final boolean isBasementOrGroundFloor,
			final double floorArea,
			final double exposedPerimeter,
			final double wallThickness,
			final FloorConstructionType groundFloorConstructionType,
			final double insulationThickness,
			final Band ageBand,
			final Country country
				) {
			if (isParty) {
				return 0d;

			} else if (isBasementOrGroundFloor) {
				return _check(groundFloor.getU(wallThickness, floorArea, exposedPerimeter, groundFloorConstructionType, insulationThickness));

			} else {
				if (country == Country.Scotland && ageBand == Band.L) {
					return scotlandFootnote2;
				}

				final double[] insulationLookup;

				switch (ageBand) {
				case A:
				case B:
				case C:
				case D:
				case E:
				case F:
				case G:
					insulationLookup = AtoGFloorU;
					break;
				case H:
				case I:
					insulationLookup = HtoIFloorU;
					break;
				case J:
					insulationLookup = JFloorU;
					break;
				case K:
				case L:
					// Rows K and L are differentiated only by Scotland footnote 2, which is handled above.
					insulationLookup = KtoLFloorU;
					break;
				default:
					throw new IllegalArgumentException("Unknown age band when calculation floor u-value " + ageBand);
				}

				for (int i = 0; i < insulationLevels.length; i++) {
					if (insulationThickness <= insulationLevels[i]) {
						return insulationLookup[i];
					}
				}

				throw new RuntimeException("SAP 2012 floor u-value lookup failed.");
			}
		}
	}

	/**
	 * Roof u-value lookup based on Tables S9 (for PitchedSlateOrTiles or Thatched) and S10 (for Flat).
	 */
    public static class Roofs {
        private static final double[] S9Levels   = new double[]{0,    12,   25,   50,   75,   100,  150,  200,  250,  270,  300,  350,  400};
        private static final double[] S9Slate    = new double[]{2.30, 1.50, 1.00, 0.68, 0.50, 0.40, 0.30, 0.21, 0.17, 0.16, 0.14, 0.12, 0.11};
        private static final double[] S9Thatched = new double[]{0.35, 0.32, 0.30, 0.25, 0.22, 0.20, 0.17, 0.14, 0.12, 0.12, 0.11, 0.10, 0.09};

        // by age band:
        private static final double[] S10Flat    = new double[]{2.30, 2.30, 2.30, 2.30, 1.50, 0.68, 0.40, 0.35, 0.35, 0.25, 0.25, 0.18};
        // Table S10 footnote (a)
        private static final double UNINSULATED_FLAT_ROOF = 2.30;

		private static final Band S10ScottishFootnote2Band = Band.K;
		private static final double S10ScottishFootnote2 = 0.2;

		public static double get(final RoofType type, final RoofConstructionType constructionType, final double insulationThickness, final Country country, final Band ageBand) {
			if (type == RoofType.Party) {
				return 0.0;
			}

			final double[] s9Lookup;

			switch (constructionType) {
			case PitchedSlateOrTiles:
				s9Lookup = S9Slate;
				break;

			case Thatched:
				s9Lookup = S9Thatched;
				break;

            case Flat:
                if (insulationThickness == 0) {
                    return UNINSULATED_FLAT_ROOF;
                } else if (country == Country.Scotland && ageBand == S10ScottishFootnote2Band) {
					return S10ScottishFootnote2;
				} else {
					return S10Flat[ageBand.ordinal()];
				}

			default:
				throw new IllegalArgumentException("Unknown roof construction type when looking up roof u-value " + constructionType);
			}

			for (int i = 0; i < S9Levels.length; i++) {
				if (insulationThickness <= S9Levels[i]) {
					return s9Lookup[i];
				}
			}

			throw new RuntimeException("Roof u-value lookup failed with insulation thickness " + insulationThickness + " and roof type " + constructionType);
		}
	}
}
