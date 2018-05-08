package uk.org.cse.nhm.simulator;

import com.google.inject.name.Named;
import com.google.inject.name.Names;

public final class SimulatorConfigurationConstants {

    public static final String START_DATE = "simulator.start-date";
    public static final String END_DATE = "simulator.end-date";
    public static final String GRANULARITY = "simulator.granularity";
    public static final String WEIGHTING = "simulator.weighting";
    public static final String STOCK_ID = "simulator.stockId";
    public static final String DEMAND_TEMPERATURE = "simulator.demandTemperature";
    public static final String RANDOM_SEED = "simulator.randomSeed";
    public static final String PROFILING_DEPTH = "simulator.profilingDepth";
    public static final String SURVEY_WEIGHT_FUNCTION = "simulator.surveyWeightFunction";
    public static final String ENERGY_CALCULATOR_TYPE = "simulator.energyCalculatorType";

    public static final Named StartDate = Names.named(START_DATE);
    public static final Named EndDate = Names.named(END_DATE);
    public static final Named Granularity = Names.named(GRANULARITY);
    public static final Named StockID = Names.named(STOCK_ID);
    public static final Named DemandTemperature = Names.named(DEMAND_TEMPERATURE);
    public static final Named RandomSeed = Names.named(RANDOM_SEED);
    public static final Named Weighting = Names.named(WEIGHTING);
    public static final Named ProfilingDepth = Names.named(PROFILING_DEPTH);
    public static final Named SurveyWeightFunction = Names.named(SURVEY_WEIGHT_FUNCTION);
    public static final Named EnergyCalculatorType = Names.named(ENERGY_CALCULATOR_TYPE);
}
