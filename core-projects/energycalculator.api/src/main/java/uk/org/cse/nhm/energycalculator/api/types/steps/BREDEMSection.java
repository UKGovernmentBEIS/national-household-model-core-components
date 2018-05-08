package uk.org.cse.nhm.energycalculator.api.types.steps;

public enum BREDEMSection {
    LightsAppliancesAndCooking("1"),
    WaterVolumeAndEnergyContent("2.1"),
    WaterHeatingLosses("2.2"),
    ElectricShowerEnergy("2.3"),
    SolarEnergy("2.4.1"),
    WaterHeatingSolarOutput("2.4.2"),
    WaterHeatingEnergyRequirement("2.5"),
    HeatLoss("3"),
    ThermalMassParameter("4"),
    Gains_Solar("5"),
    Gains_InternalAndTotal("6"),
    MeanInternalTemperature("7"),
    SpaceHeating("8"),
    SpaceCooling("9"),
    PhotovoltaicsAndWindTurbines("10"),
    UsingOutputs("11"),
    ExternalTemperatureAndSolarRadiation("Appendix A"),
    EfficiencyAndRepsonsiveness("Appendix B"),
    ThermalBridgingValues("Appendix C"),
    ThermalMassOfBuildingElements("Appendix D");

    private final String id;

    BREDEMSection(String id) {
        this.id = id;
    }

    public BREDEMLocation var(String variable) {
        return new BREDEMLocation(this, null, variable);
    }

    public BREDEMLocation step(char step, String variable) {
        return new BREDEMLocation(this, step, variable);
    }
}
