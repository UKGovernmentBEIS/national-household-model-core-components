package uk.org.cse.nhm.energycalculator.impl;

import static java.lang.Math.PI;
import static java.lang.Math.log;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;

public class GroundFloorUValues {
	private double Rsi = 0.17; // square meter kelvin per watt
	private double Rse = 0.04; // square meter kelvin per watt
	private double soilThermalConductivity = 1.5;// watts / meter kelvin
	private double deckThermalResistance = 0.2; // square meter kelvin per
												// watt
	private double openingsPerMeterOfExposedPerimeter = 0.003; // meters
	private double heightAboveGroundLevel = 0.3; // meters
	private double uValueOfWallsToUnderfloorSpace = 1.5; // wats per meter
															// square kelvin
	private double averageWindSpeedAt10m = 5;// meters per second
	private double windShieldingFactor = 0.05; // dimensionless
	private double floorInsulationConductivity = 0.035; // watts per meter

	public double getU(
			final double wallThickness,
			final double floorArea,
			final double exposedPerimeter,
			final FloorConstructionType constructionType,
			final double insulationThickness
			) {
		if (exposedPerimeter == 0) {
			// The algorithm specified in SAP 2012 has a division by 0 in this case.
			// We have worked through it, and believe it tends to 0.

			// Obviously, in real world terms, this is nonsense.

			// If you have dwellings with no external perimeter, you should fix your stock.
			return 0;
		}

		// convert wall thickness into M
		final double wallThicknessM = wallThickness / 1000;

		final double B = 2 * floorArea / exposedPerimeter;

		switch (constructionType) {
		case Solid:
			final double surfaceThermalResistance = 0.001
					* insulationThickness / floorInsulationConductivity;

			final double dt = wallThicknessM + (soilThermalConductivity
					* (Rsi + surfaceThermalResistance + Rse));

			if (dt < B) {
				return 2 * soilThermalConductivity * log((PI * B / dt) + 1)
						/ ((PI * B) + dt);
			} else {
				return soilThermalConductivity / ((0.457 * B) + dt);
			}
		case SuspendedTimberSealed:
		case SuspendedTimberUnsealed:
			final double dg = wallThicknessM + soilThermalConductivity
					* (Rsi + Rse);
			final double Ug = 2 * soilThermalConductivity
					* log((PI * B / dg) + 1) / ((PI * B) + dg);
			final double Ux = (2 * heightAboveGroundLevel
					* uValueOfWallsToUnderfloorSpace / B)
					+ (1450 * openingsPerMeterOfExposedPerimeter
							* averageWindSpeedAt10m * windShieldingFactor / B);

			return 1 / ((2 * Rsi) + deckThermalResistance + 0.2 + (1 / (Ug + Ux)));
		default:
			return 0;
		}
}

	public GroundFloorUValues setRsi(final double rsi) {
		this.Rsi = rsi;
		return this;
	}

	public GroundFloorUValues setRse(final double rse) {
		this.Rse = rse;
		return this;
	}

	public GroundFloorUValues setSoilThermalConductivity(final double soilThermalConductivity) {
		this.soilThermalConductivity = soilThermalConductivity;
		return this;
	}

	public GroundFloorUValues setDeckThermalResistance(final double deckThermalResistance) {
		this.deckThermalResistance = deckThermalResistance;
		return this;
	}

	public GroundFloorUValues setOpeningsPerMeterOfExposedPerimeter(final double openingsPerMeterOfExposedPerimeter) {
		this.openingsPerMeterOfExposedPerimeter = openingsPerMeterOfExposedPerimeter;
		return this;
	}

	public GroundFloorUValues setHeightAboveGroundLevel(final double heightAboveGroundLevel) {
		this.heightAboveGroundLevel = heightAboveGroundLevel;
		return this;
	}

	public GroundFloorUValues setuValueOfWallsToUnderfloorSpace(final double uValueOfWallsToUnderfloorSpace) {
		this.uValueOfWallsToUnderfloorSpace = uValueOfWallsToUnderfloorSpace;
		return this;
	}

	public GroundFloorUValues setAverageWindSpeedAt10m(final double averageWindSpeedAt10m) {
		this.averageWindSpeedAt10m = averageWindSpeedAt10m;
		return this;
	}

	public GroundFloorUValues setWindShieldingFactor(final double windShieldingFactor) {
		this.windShieldingFactor = windShieldingFactor;
		return this;
	}

	public GroundFloorUValues setFloorInsulationConductivity(final double floorInsulationConductivity) {
		this.floorInsulationConductivity = floorInsulationConductivity;
		return this;
	}
}