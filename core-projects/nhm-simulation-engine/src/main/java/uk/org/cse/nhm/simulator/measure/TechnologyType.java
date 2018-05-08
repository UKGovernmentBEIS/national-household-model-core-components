package uk.org.cse.nhm.simulator.measure;

import java.util.HashMap;
import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;

public class TechnologyType {

    private static final Map<String, TechnologyType> existing = new HashMap<>();
    private final String name;

    private TechnologyType(final String s) {
        this.name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TechnologyType other = (TechnologyType) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    private static final TechnologyType named(final String pattern, final Object... interpolate) {
        final String s = String.format(pattern, interpolate);
        if (existing.containsKey(s)) {
            return existing.get(s);
        }
        final TechnologyType result = new TechnologyType(s);
        existing.put(s, result);
        return result;
    }

    public static TechnologyType standardBoiler(final FuelType ft) {
        return named("Standard %s boiler", ft);
    }

    public static TechnologyType storageHeater() {
        return named("Storage heater");
    }

    public static TechnologyType wetCentralHeating() {
        return named("Wet Central Heating");
    }

    public static TechnologyType combiBoiler(final FuelType ft) {
        return named("%s Combi Boiler", ft);
    }

    public static TechnologyType districtHeating() {
        return named("District Heating");
    }

    public static TechnologyType airSourceHeatPump() {
        return named("ASHP");
    }

    public static TechnologyType groundSourceHeatPump() {
        return named("GSHP");
    }

    public static TechnologyType pointOfUseHW() {
        return named("Point-of-use DHW");
    }

    public static TechnologyType draughtProofing() {
        return named("Draught-proofing");
    }

    public static TechnologyType floorInsulation() {
        return named("Floor insulation");
    }

    public static TechnologyType glazing(final GlazingType glazingType) {
        return named("%s glazing", glazingType);
    }

    public static TechnologyType loftInsulation() {
        return named("Loft insulation");
    }

    public static TechnologyType wallInsulation(
            final WallInsulationType insulationType) {
        return named("%s wall insulation", insulationType);
    }

    public static TechnologyType solarHotWater() {
        return named("Solar DHW");
    }

    public static TechnologyType roomHeater(final FuelType fuel) {
        return named("%s room heater", fuel);
    }

    public static TechnologyType heatingControls(
            final HeatingSystemControlType controlType) {
        return named("%s", controlType);
    }

    public static TechnologyType lowEnergyLighting() {
        return named("Low energy lighting");
    }

    public static TechnologyType solaPhotovoltaic() {
        return named("Solar Photovoltaic");
    }

    public static TechnologyType hotWaterCylinderInsulation() {
        return named("Hot Water Cylinder Insulation");
    }

    public static TechnologyType hotWaterCylinderThermostat() {
        return named("Hot Water Thermostat");
    }

    public static TechnologyType warmAirHeatingSystem() {
        return named("Warm Air Heating System");
    }
}
