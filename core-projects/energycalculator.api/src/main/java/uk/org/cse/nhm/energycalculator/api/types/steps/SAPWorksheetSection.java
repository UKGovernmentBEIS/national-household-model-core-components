package uk.org.cse.nhm.energycalculator.api.types.steps;

public enum SAPWorksheetSection {
    Dimensions,
    Ventilation,
    HeatLosses_and_Heat_Loss_Parameter,
    Water_Heating,
    Gains_Internal,
    Gains_Solar,
    Mean_Internal_Temperature,
    Space_Heating,
    Space_Cooling,
    Fabric_Energy_Efficiency,
    Energy_Requirements,
    Fuel_Costs,
    SAP_Rating,
    Emissions,
    Primary_Energy,
    Energy_Requirements_Community,
    Fuel_Costs_Community,
    SAP_Rating_Community,
    Emissions_Community,
    Primary_Energy_Community
    ;

    public SAPLocation cell(int cell) {
        return new SAPLocation(this, cell, null);
    }

    public SAPLocation subCell(int cell, Character subCell) {
        return new SAPLocation(this, cell, subCell);
    }

    @Override
    public String toString() {
        return name().replace('_', ' ');
    }
}
