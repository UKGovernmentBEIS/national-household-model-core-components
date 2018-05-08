package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

abstract class ExternalParameters implements IEnergyCalculatorParameters {

    private final ElectricityTariffType tarrifType;
    private Object internal1 = null,
            internal2 = null,
            internal3 = null;

    ExternalParameters(final ElectricityTariffType tariffType) {
        tarrifType = tariffType;
    }

    @Override
    public final ElectricityTariffType getTarrifType() {
        return tarrifType;
    }

    @Override
    public final EnergyType getInternalEnergyType(final Object object) {
        if (internal1 == null) {
            internal1 = object;
            return EnergyType.FuelINTERNAL1;
        } else if (internal1 == object) {
            return EnergyType.FuelINTERNAL1;
        } else if (internal2 == null) {
            internal2 = object;
            return EnergyType.FuelINTERNAL2;
        } else if (internal2 == object) {
            return EnergyType.FuelINTERNAL2;
        } else if (internal3 == null) {
            internal3 = object;
            return EnergyType.FuelINTERNAL3;
        } else if (internal3 == object) {
            return EnergyType.FuelINTERNAL3;
        } else {
            throw new IllegalArgumentException("Cannot do an energy calculation with more than three kinds of internal energy");
        }
    }
}
