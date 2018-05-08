package uk.org.cse.stockimport.imputation.floors;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

public class FloorPropertyTables implements IFloorPropertyTables {

    private double[] englandInsulationBySapAgeBand;
    private SAPAgeBandValue.Band lastAgeBandForSuspendedTimber;

    private double Rsi;
    private double Rse; // square meter kelvin per watt
    private double soilThermalConductivity;// watts / meter kelvin
    private double deckThermalResistance; // square meter kelvin per
    // watt
    private double openingsPerMeterOfExposedPerimeter; // meters
    private double heightAboveGroundLevel; // meters
    private double uValueOfWallsToUnderfloorSpace; // wats per meter
    // square kelvin
    private double averageWindSpeedAt10m;// meters per second
    private double windShieldingFactor; // dimensionless
    private double floorInsulationConductivity; // watts per meter
    // kelvin

    final List<TreeMap<Integer, Double>> exposedFloorUValueBySapAgeBand = new ArrayList<>();

    public FloorPropertyTables() {
        exposedFloorUValueBySapAgeBand.add(new TreeMap<Integer, Double>());
        exposedFloorUValueBySapAgeBand.add(new TreeMap<Integer, Double>());
    }

    @Override
    public double[] getEnglandInsulationBySapAgeBand() {
        return englandInsulationBySapAgeBand;
    }

    @Override
    public SAPAgeBandValue.Band getLastAgeBandForSuspendedTimber() {
        return lastAgeBandForSuspendedTimber;
    }

    public void setEnglandInsulationBySapAgeBand(
            final double[] englandInsulationBySapAgeBand) {
        this.englandInsulationBySapAgeBand = englandInsulationBySapAgeBand;
    }

    public void setLastAgeBandForSuspendedTimber(
            final SAPAgeBandValue.Band lastAgeBandForSuspendedTimber) {
        this.lastAgeBandForSuspendedTimber = lastAgeBandForSuspendedTimber;
    }

    @Override
    public double getRsi() {
        return Rsi;
    }

    @Override
    public double getRse() {
        return Rse;
    }

    @Override
    public double getSoilThermalConductivity() {
        return soilThermalConductivity;
    }

    @Override
    public double getDeckThermalResistance() {
        return deckThermalResistance;
    }

    @Override
    public double getOpeningsPerMeterOfExposedPerimeter() {
        return openingsPerMeterOfExposedPerimeter;
    }

    @Override
    public double getHeightAboveGroundLevel() {
        return heightAboveGroundLevel;
    }

    @Override
    public double getUValueOfWallsToUnderfloorSpace() {
        return uValueOfWallsToUnderfloorSpace;
    }

    @Override
    public double getAverageWindSpeedAt10m() {
        return averageWindSpeedAt10m;
    }

    @Override
    public double getWindShieldingFactor() {
        return windShieldingFactor;
    }

    @Override
    public double getFloorInsulationConductivity() {
        return floorInsulationConductivity;
    }

    public void setRsi(final double rsi) {
        Rsi = rsi;
    }

    public void setRse(final double rse) {
        Rse = rse;
    }

    public void setSoilThermalConductivity(final double soilThermalConductivity) {
        this.soilThermalConductivity = soilThermalConductivity;
    }

    public void setDeckThermalResistance(final double deckThermalResistance) {
        this.deckThermalResistance = deckThermalResistance;
    }

    public void setOpeningsPerMeterOfExposedPerimeter(
            final double openingsPerMeterOfExposedPerimeter) {
        this.openingsPerMeterOfExposedPerimeter = openingsPerMeterOfExposedPerimeter;
    }

    public void setHeightAboveGroundLevel(final double heightAboveGroundLevel) {
        this.heightAboveGroundLevel = heightAboveGroundLevel;
    }

    public void setAverageWindSpeedAt10m(final double averageWindSpeedAt10m) {
        this.averageWindSpeedAt10m = averageWindSpeedAt10m;
    }

    public void setWindShieldingFactor(final double windShieldingFactor) {
        this.windShieldingFactor = windShieldingFactor;
    }

    public void setFloorInsulationConductivity(
            final double floorInsulationConductivity) {
        this.floorInsulationConductivity = floorInsulationConductivity;
    }

    public void setuValueOfWallsToUnderfloorSpace(
            final double uValueOfWallsToUnderfloorSpace) {
        this.uValueOfWallsToUnderfloorSpace = uValueOfWallsToUnderfloorSpace;
    }

    public void addUValueForExposedFloor(final boolean isInsulated, final Band ageBandValue, final double uValue) {
        final int ordinal = isInsulated == true ? 1 : 0;

        final TreeMap<Integer, Double> ageBandValues = exposedFloorUValueBySapAgeBand.get(ordinal);
        ageBandValues.put(ageBandValue.ordinal(), uValue);
    }

    @Override
    public double[][] getExposedFloorUValueBySapAgeBand() {
        final double[][] values = new double[exposedFloorUValueBySapAgeBand.size()][11];

        int row = 0;
        for (final TreeMap<Integer, Double> map : exposedFloorUValueBySapAgeBand) {
            for (final Integer ageBand : map.keySet()) {
                values[row][ageBand] = map.get(ageBand);
            }
            row++;
        }

        return values;
    }
}
