package uk.org.cse.nhm.energycalculator.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.impl.GraphvizEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;
import uk.org.cse.nhm.energycalculator.impl.appliances.Appliances09;
import uk.org.cse.nhm.energycalculator.impl.demands.HotWaterDemand09;
import uk.org.cse.nhm.energycalculator.impl.gains.EvaporativeGainsSource;
import uk.org.cse.nhm.energycalculator.impl.gains.GainsTransducer;
import uk.org.cse.nhm.energycalculator.impl.gains.MetabolicGainsSource;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;

import static uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep.*;

/**
 * An implementation of an EnergyCalculator. The main method is the
 * {@link #evaluate(IEnergyCalculatorHouseCase, IEnergyCalculatorParameters)}
 * method, which has an extensive javadoc where the operation of the algorithm
 * is described.
 *
 * @author hinton
 *
 */
public class EnergyCalculatorCalculator implements IEnergyCalculator {

    private static final Logger log = LoggerFactory.getLogger(EnergyCalculatorCalculator.class);

    private static double div(final double num, final double den, final String die) {
        if (den == 0) {
            if (num == 0) {
                return 0;
            } else {
                throw new RuntimeException("Illegal division for " + die);
            }
        } else {
            return num / den;
        }
    }

    private static Comparator<IEnergyTransducer> phasingComparator = new Comparator<IEnergyTransducer>() {
        @Override
        public int compare(final IEnergyTransducer arg0, final IEnergyTransducer arg1) {
            final int p1 = arg0.getPhase().ordinal();
            final int p2 = arg1.getPhase().ordinal();
            final int c1 = c(p1, p2);
            if (c1 == 0) {
                return c(arg0.getPriority(), arg1.getPriority());
            } else {
                return c1;
            }
        }

        private final int c(final int a, final int b) {
            if (a == b) {
                return 0;
            }
            if (a > b) {
                return 1;
            }
            return -1;
        }
    };

    private final IConstants constants;

    /**
     * Should you wish to inject some logic into the {@link IEnergyState} to
     * keep track of calculations, for example to produce something like
     * {@link GraphvizEnergyState}'s output, you can set an instance of this
     * interface into the calculator to ensure the right kind of state is used.
     *
     * @author hinton
     *
     */
    public interface IEnergyStateFactory {

        public IEnergyState createEnergyState();
    }

    /**
     * The default state factory produces a {@link ClassEnergyState}, but you
     * could put in another one with
     * {@link #setStateFactory(IEnergyStateFactory)}
     */
    private IEnergyStateFactory stateFactory = new IEnergyStateFactory() {
        @Override
        public IEnergyState createEnergyState() {
            return new ClassEnergyState();
        }
    };

    /**
     * Defined in BREDEM 7.0 equations 1 and 2
     */
    private final double VENTILATION_HEAT_LOSS_COEFFICIENT;
    private static final double MINUTES_PER_HOUR = 60.0;

    private final double TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER;

    /*
    BEISDOC
    NAME: Thermal Briding Coefficient
    DESCRIPTION: The thermal bridging coefficient used to estimate the thermal bridge contribution to heat loss parameter under SAP 2012.
    TYPE: value
    UNIT: W/℃/m^2
    SAP: (36)
    SAP_COMPLIANT: SAP mode only
    BREDEM_COMPLIANT: N/A - user defined
    ID: thermal-bridging-coefficient
    CODSIEB
     */
    private final double SAP_THERMAL_BRIDGING_COEFFICIENT = 0.15;

    /**
     * Contains a list of transducers which are present in every house. These
     * are stateless, so it is safe to share them between different calculation
     * threads.
     */
    private final List<IEnergyTransducer> defaultTransducers = new ArrayList<IEnergyTransducer>();

    private final double SHELTERED_SIDES_EXPOSURE_FACTOR;

    private final double WIND_FACTOR_DIVISOR;

    private final double REFERENCE_HEAT_LOSS_PARAMETER;
    private final double REFERENCE_HEAT_LOSS_PARAMETER2;

    private final double MINIMUM_COOLING_TIME;

    private final double COOLING_TIME_CONSTANT_MULTIPLIER;

    private final double UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR;

    public EnergyCalculatorCalculator() {
        this(DefaultConstants.INSTANCE);
    }

    @Inject
    public EnergyCalculatorCalculator(final IConstants constants) {
        log.debug("Constructing EnergyCalculator with {}", constants);

        this.constants = constants;
        VENTILATION_HEAT_LOSS_COEFFICIENT = constants.get(EnergyCalculatorConstants.VENTILATION_HEAT_LOSS_COEFFICIENT);
        TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER = constants
                .get(EnergyCalculatorConstants.TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER);
        SHELTERED_SIDES_EXPOSURE_FACTOR = constants.get(EnergyCalculatorConstants.SHELTERED_SIDES_EXPOSURE_FACTOR);
        WIND_FACTOR_DIVISOR = constants.get(EnergyCalculatorConstants.WIND_FACTOR_DIVISOR);
        REFERENCE_HEAT_LOSS_PARAMETER = constants.get(EnergyCalculatorConstants.REFERENCE_HEAT_LOSS_PARAMETER);
        REFERENCE_HEAT_LOSS_PARAMETER2 = Math.pow(REFERENCE_HEAT_LOSS_PARAMETER, 2);
        MINIMUM_COOLING_TIME = constants.get(EnergyCalculatorConstants.MINIMUM_COOLING_TIME);
        COOLING_TIME_CONSTANT_MULTIPLIER = constants.get(EnergyCalculatorConstants.COOLING_TIME_TIME_CONSTANT_MULTIPLIER);
        UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR = constants
                .get(EnergyCalculatorConstants.UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR);

        defaultTransducers.add(new Appliances09(constants));
        defaultTransducers.add(new MetabolicGainsSource(constants));
        defaultTransducers.add(new EvaporativeGainsSource(constants));
        defaultTransducers.add(new GainsTransducer(constants));
    }

    /**
     * Compute the specific heat losses for a house case, given the summed
     * values collected from visiting a house; this method combines the fabric
     * losses together with thermal bridging losses and ventilation losses.
     *
     * @param houseCase
     * @param parameters
     * @param totalFabricHeatLoss
     * @param totalThermalMass
     * @param totalExternalArea
     * @param infiltration
     * @param ventilationSystems
     * @return
     *
     * @assumption In the CHM, the thermal bridging parameter is switched on
     * houses that are built before 2003, but in BREDEM8 a thermal bridging
     * value is computed with reference to the layout of the floorplan. Here we
     * are using the CHM's method.
     */
    public SpecificHeatLosses calculateSpecificHeatLosses(final IEnergyCalculatorHouseCase houseCase,
            final IInternalParameters parameters, final double fabricLosses, final double totalThermalMass,
            final double totalExternalArea, final IStructuralInfiltrationAccumulator infiltration,
            final List<IVentilationSystem> ventilationSystems) {

        log.debug("Total thermal mass: {}", totalThermalMass);

        final double structuralAirChangeRate = infiltration.getAirChangeRate();
        final double structureAirChangeExcludingDeliberate = structuralAirChangeRate - infiltration.getDeliberateAirChanges(houseCase.getHouseVolume());

        /*
        BEISDOC
        NAME: Shelter Factor
        DESCRIPTION: Multiply the number of sheltered sides by the sheltered sides exposure factor. Subtract the result from 1.
        TYPE: formula
        UNIT: dimensionless
        SAP: 20
        SAP_COMPLIANT: Yes
        BREDEM: Table 22
        BREDEM_COMPLIANT: Yes
        DEPS: sheltered-sides-exposure-factor,num-sheltered-sides
        GET:
        SET:
        ID: shelter-factor
        CODSIEB
         */
        final double shelterFactor = 1 - (houseCase.getNumberOfShelteredSides() * SHELTERED_SIDES_EXPOSURE_FACTOR);
        StepRecorder.recordStep(SidesSheltered, houseCase.getNumberOfShelteredSides());
        StepRecorder.recordStep(ShelterFactor, shelterFactor);

        final double structuralAirChangeRateIncludingShelter = structuralAirChangeRate * shelterFactor;
        StepRecorder.recordStep(InfiltrationRate_IncludingShelter, structuralAirChangeRateIncludingShelter);

        final double windSpeed = parameters.getClimate().getSiteWindSpeed();
        StepRecorder.recordStep(AverageWindSpeed, windSpeed);

        /*
        BEISDOC
        NAME: Wind factor
        DESCRIPTION: The average wind speed divided by 4.
        TYPE: formula
        UNIT: m/s
        SAP: (22a)
        SAP_COMPLIANT: Yes
        BREDEM: 3E
        BREDEM_COMPLIANT: Yes
        DEPS: wind-speed,wind-factor-divisor
        ID: wind-factor
        CODSIEB
         */
        final double windFactor = (parameters.getClimate().getSiteWindSpeed() / WIND_FACTOR_DIVISOR);
        StepRecorder.recordStep(WindFactor, windFactor);

        final double structuralAirChangeRateIncludingShelterAndWind = structuralAirChangeRateIncludingShelter * windFactor;
        StepRecorder.recordStep(InfiltrationRate_IncludingShelterAndWind, structuralAirChangeRateIncludingShelterAndWind);

        final double siteExposureFactor = getSiteExposureFactor(houseCase, parameters);
        StepRecorder.recordStep(SiteExposureFactor, siteExposureFactor);

        /*
        BEISDOC
        NAME: Adjused infiltration rate
        DESCRIPTION: The infiltration rate allowing for shelter and wind speed
        TYPE: formula
        UNIT: ach/h
        SAP: (21,22b)
        SAP_COMPLIANT: Yes
        BREDEM: 3E
        BREDEM_COMPLIANT: Yes
        DEPS: wind-factor,shelter-factor,total-infiltration,site-exposure-factor
        ID: adjusted-infiltration
        CODSIEB
         */
        final double climateAdjustedAirChangeRate = structuralAirChangeRateIncludingShelterAndWind * siteExposureFactor;
        StepRecorder.recordStep(InfiltrationRate_IncludingShelterAndWindAndSiteExposure, climateAdjustedAirChangeRate);

        final double climateAdjustedAirChangeRateAirChangeExcludingDeliberate = structureAirChangeExcludingDeliberate
                * windFactor * siteExposureFactor * shelterFactor;

        double houseAirChangeRate = climateAdjustedAirChangeRate;

        // If no mechanical ventilation rate then assume human will ventilate
        if (ventilationSystems.isEmpty()) {
            ventilationSystems.add(new HumanVentilationSystem(constants));
        }

        for (final IVentilationSystem system : ventilationSystems) {
            houseAirChangeRate = system.getAirChangeRate(houseAirChangeRate);
        }

        log.debug("air change rates: {} {} {}", structuralAirChangeRate, climateAdjustedAirChangeRate,
                houseAirChangeRate);

        StepRecorder.recordStep(AirChanges_Effective, houseAirChangeRate);

        // CHM handles thermal bridging using a simple model, which we just add
        // in here; the simple model states
        // that the linear thermal bridge effect is 0.15 * total external area
        // if the house is built before 2003,
        // or 1/4 that if it is built after 2003.
        final double thermalBridgeEffect = getThermalBridgeEffect(houseCase, parameters, totalExternalArea);

        /*
        BEISDOC
        NAME: Ventilation heat loss
        DESCRIPTION: The heat loss in the house due to ventilation and infiltration
        TYPE: formula
        UNIT: W/℃
        SAP: (38)
        SAP_COMPLIANT: Yes
        BREDEM: 3G
        BREDEM_COMPLIANT: Yes
        DEPS: ventilation-heat-loss-coefficient,natural-infiltration,dwelling-volume
        ID: ventilation-heat-loss
        CODSIEB
         */
        final double ventilationLosses = VENTILATION_HEAT_LOSS_COEFFICIENT * houseAirChangeRate
                * houseCase.getHouseVolume();

        final double H3 = getInterzoneSpecificHeatLoss(parameters.getCalculatorType(), houseCase);
        return new SpecificHeatLosses(fabricLosses,
                H3,
                totalThermalMass,
                houseCase.getFloorArea(),
                ventilationLosses,
                thermalBridgeEffect,
                climateAdjustedAirChangeRate,
                climateAdjustedAirChangeRateAirChangeExcludingDeliberate);
    }

    protected double getThermalBridgeEffect(final IEnergyCalculatorHouseCase houseCase, final IInternalParameters parameters,
            final double totalExternalArea) {
        /*
        BEISDOC
        NAME: Thermal Bridging Heat Loss
        DESCRIPTION: Heat loss due to thermal bridging per degree of temperature difference.
        TYPE: formula
        UNIT: W / ℃
        SAP: (36), Appendix K (K2)
        SAP_COMPLIANT: Yes
        BREDEM: 3A.b
        BREDEM_COMPLIANT: Yes
        DEPS: sap-thermal-bridging-coefficient,thermal-bridging-coefficient,external-area
        ID: thermal-bridging-heat-loss
        NOTES: The thermal bridging coefficient is always 0.15 in SAP 2012 mode.
        CODSIEB
         */
        final double thermalBridgingCoefficient;
        switch (parameters.getCalculatorType().uvalues) {
            case SAP2012:
                thermalBridgingCoefficient = SAP_THERMAL_BRIDGING_COEFFICIENT;
                break;
            case SCENARIO:
                thermalBridgingCoefficient = houseCase.getThermalBridgingCoefficient();
                break;
            default:
                throw new UnsupportedOperationException("Unknown energy calculator type while calculating thermal bridging " + parameters.getCalculatorType());
        }

        return thermalBridgingCoefficient * totalExternalArea;
    }

    protected final double getInterzoneSpecificHeatLoss(final EnergyCalculatorType calculatorType,
            final IEnergyCalculatorHouseCase houseCase) {
        switch (calculatorType.uvalues) {
            case SAP2012:
                return 0;
            case SCENARIO:
                return houseCase.getInterzoneSpecificHeatLoss();
            default:
                throw new UnsupportedOperationException("getInterzoneSpecificHeatLoss does not know about EnergyCalculatorType " + calculatorType);
        }
    }

    protected final double getSiteExposureFactor(final IEnergyCalculatorHouseCase houseCase, final IInternalParameters parameters) {
        /*
        BEISDOC
        NAME: Site Exposure Factor
        DESCRIPTION: A multiplier to the air change rate based on the local geographical area of the dwelling.
        TYPE: value
        UNIT: Dimensionless
        SAP_COMPLIANT: SAP mode only
        BREDEM: Table 21
        BREDEM_COMPLIANT: Yes
        DEPS: site-exposure-factor-lookup
        NOTES: Does not apply when the calculator is running in SAP mode.
        ID: site-exposure-factor
        CODSIEB
         */
        switch (parameters.getCalculatorType().uvalues) {
            case SAP2012:
                return 1;
            case SCENARIO:
                return constants.get(EnergyCalculatorConstants.SITE_EXPOSURE_FACTOR, houseCase.getSiteExposure().ordinal());
            default:
                throw new UnsupportedOperationException("Site exposure calculation does not know about energy calculator type " + parameters.getCalculatorType());
        }
    }

    /**
     * Compute the gains utilisation factor as defined in BREDEM 2009 ch. 7.
     *
     * @param Tin
     * @param Text
     * @param H
     * @param totalGains
     * @param utilisationFactorExponent
     * @return
     *
     * @assumption In BREDEM, the gains utilisation factor is defined using the
     * gain to loss ratio. The BREDEM 2009 definition contains a singular point
     * at Tin = TextAs the specific heat loss tends to zero. In this case, the
     * gains utilisation factor should also tend to zero, by the application of
     * L'Hopital's rule for limits. However, BREDEM does not specify this, so it
     * has been assumed here so we mark it up here as an assumption.
     */
    protected static double calculateGainsUtilisationFactor(final double Tin, final double Text, final double H,
            final double totalGains, final double utilisationFactorExponent) {

        /*
        BEISDOC
        NAME: Heat loss rate at temperature
        DESCRIPTION: The amount of heat lost by a dwelling at given internal and external temperatures
        TYPE: formula
        UNIT: W
        SAP: Table 9a (computation of L)
        SAP_COMPLIANT: Yes
        BREDEM: 7H, 7Q
        BREDEM_COMPLIANT: Yes
        DEPS: external-temperature,zone-1-demand-temperature,zone-2-adjusted-demand-temperature,specific-heat-loss
        ID: heat-loss-at-temperature
        NOTES: This calculation step repeated twice - once for each Zone.
        CODSIEB
         */
        final double heatLossRate = H * (Tin - Text);

        // from l'hopital's rule.
        if (heatLossRate == 0) {
            return 0;
        }

        /*
        BEISDOC
        NAME: Gain to Loss Ratio
        DESCRIPTION: The ratio of total internal gains to heat loss
        TYPE: formula
        UNIT: Dimensionless
        SAP: Table 9a (computation of gamma)
        SAP_COMPLIANT: Yes
        BREDEM: 7I, 7R
        BREDEM_COMPLIANT: Yes
        DEPS: heat-loss-at-temperature,total-gains
        ID: gain-to-loss-ratio
        NOTES: This calculation step repeated twice - once for each Zone.
        CODSIEB
         */
        final double gainToLossRatio = totalGains / heatLossRate;

        final double gainsUtilisationFactor;

        /*
        BEISDOC
        NAME: Gains Utilisation Factor
        DESCRIPTION: The proportion of total internal gains which was useful for heating the dwelling.
        TYPE: formula
        UNIT: Dimensionless
        SAP: Table 9a (computation of Nu)
        SAP_COMPLIANT: Yes
        BREDEM: 7J,7S
        BREDEM_COMPLIANT: Yes
        DEPS: utilisation-factor-exponent,gain-to-loss-ratio
        ID: gains-utilisation-factor
        NOTES: This calculation step repeated twice - once for each Zone.
        CODSIEB
         */
        if (gainToLossRatio < 0) {
            gainsUtilisationFactor = 1;
        } else if (gainToLossRatio == 1f) {
            gainsUtilisationFactor = div(utilisationFactorExponent, (utilisationFactorExponent + 1),
                    "gains utilisation factor (glr = 1)");
        } else {
            gainsUtilisationFactor = div((1 - Math.pow(gainToLossRatio, utilisationFactorExponent)),
                    (1 - Math.pow(gainToLossRatio, utilisationFactorExponent + 1)), "gains utilisation factor");
        }

        log.debug("GU: gamma = {}/{} = {}, a = {}, factor = {}", totalGains, heatLossRate, gainToLossRatio,
                utilisationFactorExponent, gainsUtilisationFactor);

        return gainsUtilisationFactor;
    }

    /**
     * This calculates the background temperature (responsive / unresponsive) as
     * specified in BREDEM 2009. The CHM does something different.
     *
     * @param parameters
     * @param heatLosses
     * @param zoneTwoheatedProportion
     * @param totalGains
     * @param utilisationFactorExponent
     * @return
     * @assumption BREDEM 2009 has a bug in the equation for computing the
     * unheated zone two temperature, which we have corrected here. The BREDEM
     * document is dimensionally inconsistent, containing the sum of the heat
     * loss parameter with the interzone heat loss in the denominator. This
     * should be the sum of the specific heat loss and the interzone heat loss.
     */
    protected static double[][] calculateBackgroundTemperatures(final IInternalParameters parameters,
            final SpecificHeatLosses heatLosses, final double zoneTwoheatedProportion, final double totalGains,
            final double utilisationFactorExponent) {
        final double Td1 = parameters.getZoneOneDemandTemperature();
        final double Td2 = parameters.getZoneTwoDemandTemperature();
        final double Text = parameters.getClimate().getExternalTemperature();

        final double specificHeatLoss = heatLosses.getSpecificHeatLoss();
        final double H = specificHeatLoss;
        final double H3 = heatLosses.interzoneHeatLoss;

        /*
        BEISDOC
        NAME: Unheated Zone 2 Temperature
        DESCRIPTION: The average temperature in Zone 2 while the heating is off.
        TYPE: formula
        UNIT: ℃
        SAP_COMPLIANT: SAP mode only
        BREDEM: 7D
        BREDEM_COMPLIANT: Yes
        DEPS: zone-1-demand-temperature,interzone-specific-heat-loss,external-temperature,specific-heat-loss,total-gains,interzone-specific-heat-loss
        NOTES: This will only be used in BREDEM 2012 mode, because the zone 2 heated proportion is always 100% in SAP 2012.
        ID: unheated-zone-2-temperature
        CODSIEB
         */
        final double unheatedZoneTwoTemperature = Math.min(
                Td1,
                ((Td1 * H3) + (Text * H) + totalGains) / (H + H3)
        );

        log.debug("Unheated zone 2 temperature (with gains): {}", unheatedZoneTwoTemperature);

        /*
        BEISDOC
        NAME: Adjusted Zone 2 Demand Temperature
        DESCRIPTION: The Zone 2 Demand Temperature interpolated with the unheated Zone 2 temperature, based on the proportion of Zone 2 which is heated.
        TYPE: formula
        UNIT: ℃
        SAP_COMPLIANT: SAP mode only
        BREDEM: 7E
        BREDEM_COMPLIANT: Yes
        DEPS: unheated-zone-2-temperature,zone-2-demand-temperature,zone-2-heated-proportion
        NOTES: This does not have an effect in SAP 2012 mode (the zone-2 heated proportion is considered to be 1).
        ID: zone-2-adjusted-demand-temperature
        CODSIEB
         */
        // interpolate zone two demand temperature
        final double Td2_2 = Td2 * zoneTwoheatedProportion + (1 - zoneTwoheatedProportion) * unheatedZoneTwoTemperature;

        /*
        BEISDOC
        NAME: Zone 1 Utilisation Factor
        DESCRIPTION: The gains utilisation factor calculated with the temperatures from Zone 1.
        TYPE: formula
        UNIT: Dimensionless
        SAP: Table 9a
        SAP_COMPLIANT: Yes
        BREDEM: 7J
        BREDEM_COMPLIANT: Yes
        DEPS: gains-utilisation-factor
        ID: zone-1-utilisation-factor
        CODSIEB
         */
        final double utilisationFactorInZone1 = calculateGainsUtilisationFactor(Td1, Text, specificHeatLoss, totalGains,
                utilisationFactorExponent);

        StepRecorder.recordStep(GainsUtilisation_LivingArea, utilisationFactorInZone1);

        /*
        BEISDOC
        NAME: Zone 2 Utilisation Factor
        DESCRIPTION: The gains utilisation factor calculated with the temperatures from Zone 1.
        TYPE: formula
        UNIT: Dimensionless
        SAP: Table 9a
        SAP_COMPLIANT: Yes
        BREDEM: 7J
        BREDEM_COMPLIANT: Yes
        DEPS: gains-utilisation-factor
        ID: zone-2-utilisation-factor
        CODSIEB
         */
        final double utilisationFactorInZone2 = calculateGainsUtilisationFactor(Td2_2, Text, specificHeatLoss,
                totalGains, utilisationFactorExponent);

        StepRecorder.recordStep(GainsUtilisation_RestOfDwelling, utilisationFactorInZone2);

        final double deltaT = parameters.getConstants().get(EnergyCalculatorConstants.UNRESPONSIVE_HEATING_SYSTEM_DELTA_T);

        /*
        BEISDOC
        NAME: Unresponsive temperatures
        DESCRIPTION: The temperatures of each Zone while heating is off, if the heating system were completely unresponsiveness.
        TYPE: formula
        UNIT: ℃
        SAP: Table 9b
        SAP_COMPLIANT: Yes
        BREDEM: 7L, 7T
        BREDEM_COMPLIANT: Yes
        DEPS: unresponsive-temperature-reduction,zone-1-demand-temperature,zone-2-adjusted-demand-temperature
        ID: unresponsive-temperatures
        CODSIEB
         */
        final double[] unresponsiveTemperatures = new double[]{Math.max(Text, Td1 - deltaT),
            Math.max(Text, Td2_2 - deltaT)};

        /*
        BEISDOC
        NAME: Responsive temperatures
        DESCRIPTION: The temperatures of each Zone while heating is off, if the heating system were completely responsive.
        TYPE: formula
        UNIT: ℃
        SAP: Table 9b
        SAP_COMPLIANT: Yes
        BREDEM: 7L, 7T
        BREDEM_COMPLIANT: Yes
        DEPS: external-temperature,zone-1-utilisation-factor,zone-2-utilisation-factor,total-gains,specific-heat-loss
        ID: responsive-temperatures
        CODSIEB
         */
        final double[] responsiveTemperatures = new double[]{
            Text + div(utilisationFactorInZone1 * totalGains, specificHeatLoss, "responsive temperature 0"),
            Text + div(utilisationFactorInZone2 * totalGains, specificHeatLoss, "responsive temperature 1")};

        log.debug("Utilisation factors: {}, {}", utilisationFactorInZone1, utilisationFactorInZone2);

        // OK, so this gives us responsive/unresponsive for zone1/zone2
        return new double[][]{responsiveTemperatures, unresponsiveTemperatures};
    }

    public IEnergyStateFactory getStateFactory() {
        return stateFactory;
    }

    public void setStateFactory(final IEnergyStateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }

    /**
     * Run the energy calculation. There are inline comments in the source which
     * may be more helpful, but this documentation gives a summary. The
     * calculation takes the following steps
     * <ol>
     * <li>Passes a {@link Visitor} to the houseCase, to collect its contents;
     * see the {@link IEnergyCalculatorVisitor} interface for more on this; this
     * will total up relevant details of the house</li>
     * <li>Invokes
     * {@link #calculateSpecificHeatLosses(IEnergyCalculatorHouseCase, IEnergyCalculatorParameters, double, double, double, IStructuralInfiltrationAccumulator, List)}
     * , to combine the fabric and infiltration losses and thermal bridging
     * together and produce an {@link ISpecificHeatLosses} for the rest of the
     * calculation.</li>
     * <li>Sorts the energy transducers into order, using
     * {@link #sortTransducers(List)}; this ensures that no transducer will be
     * run before any that depend on it</li>
     * <li>Runs all of the energy transducers
     * {@link IEnergyTransducer#generate(IEnergyCalculatorHouseCase, IInternalParameters, ISpecificHeatLosses, IEnergyState)}
     * methods up to the Gain/load ratio adjuster, by calling
     * {@link #runToNonHeatTransducers(IEnergyCalculatorHouseCase, List, IEnergyTransducer, ISpecificHeatLosses, IInternalParameters, IEnergyState)}
     * . These will include the standard transducers present in every house:
     * <ol>
     * <li>{@link HotWaterDemand09}</li>
     * <li>{@link Appliances09}</li>
     * <li>{@link MetabolicGainsSource}</li>
     * <li>{@link EvaporativeGainsSource}</li>
     * <li>{@link GainsTransducer}</li>
     * </ol>
     *
     * As these are passed into the visitor's constructor via the
     * {@link #defaultTransducers} list.</li>
     * <li>Computes the background temperature for each zone, by computing the
     * background temperatures for fully responsive and unresponsive systems
     * with
     * {@link #calculateBackgroundTemperatures(IInternalParameters, ISpecificHeatLosses, double, double, double)}
     * , and then using
     * {@link #getBackgroundTemperatureFromHeatingSystems(List, ISpecificHeatLosses, IInternalParameters, IEnergyState, double[], double[], double[])}
     * to determine where between those two extremes the house's background
     * temperature</li>
     * <li>Uses the {@link IHeatingSchedule} in the parameters to compute the
     * <em>mean</em> temperature for each zone</li>
     * <li>Invokes
     * {@link #getAreaWeightedMeanTemperature(IEnergyCalculatorHouseCase, double[])}
     * to convert the two temperatures into the single mean background
     * temperature
     * </li>
     * <li>Invokes
     * {@link #configureGainLossAdjuster(ISpecificHeatLosses, IInternalParameters, double[], double, double, double, GainLoadRatioAdjuster)}
     * to tell the gain/load ratio calculation about the temperatures (see
     * {@link GainLoadRatioAdjuster} for why this is required)</li>
     * <li>Finally, runs the remaining heat-specific transducers using
     * {@link #runHeatTransducers(IEnergyCalculatorHouseCase, List, ISpecificHeatLosses, IInternalParameters, IEnergyState, int)}
     * </li>
     * </ol>
     *
     * @param houseCase
     * @param parameters
     * @return
     */
    @Override
    public IEnergyCalculationResultWithSteps evaluate(
            final IEnergyCalculatorHouseCase houseCase,
            final IEnergyCalculatorParameters eparameters,
            final ISeasonalParameters[] climate,
            final Set<EnergyCalculationStep> requestedSteps) {
        try (final StepRecorder.Steps steps = StepRecorder.record(requestedSteps)) {
            final Visitor v = Visitor.create(constants, eparameters, houseCase.getBuildYear(), houseCase.getCountry(), defaultTransducers);

            houseCase.accept(constants, eparameters, v);
            v.infiltration.calculateAirChangeRate(houseCase, eparameters);
            log.debug("visitor: {}", v);

            StepRecorder.recordStep(TotalFloorArea, houseCase.getFloorArea());
            StepRecorder.recordStep(DwellingVolume, houseCase.getHouseVolume());
            StepRecorder.recordStep(Storeys, houseCase.getNumberOfStoreys());

            StepRecorder.recordStep(HeatLossCoefficient_DoorsSolid, v.areasByType[1][AreaType.DoorSolid.ordinal()]);
            StepRecorder.recordStep(HeatLossCoefficient_DoorsSemiGlazed, v.areasByType[1][AreaType.DoorGlazed.ordinal()]);

            final double windowsMetalFrame = v.areasByType[1][AreaType.GlazingMetal.ordinal()];
            final double windowsWoodFrame = v.areasByType[1][AreaType.GlazingWood.ordinal()];
            final double windowsUPVCFrame = v.areasByType[1][AreaType.GlazingUPVC.ordinal()];

            StepRecorder.recordStep(HeatLossCoefficient_Window_Metal, windowsMetalFrame);
            StepRecorder.recordStep(HeatLossCoefficient_Window_Wood, windowsWoodFrame);
            StepRecorder.recordStep(HeatLossCoefficient_Window_UPVC, windowsUPVCFrame);
            StepRecorder.recordStep(HeatLossCoefficient_Window, windowsMetalFrame + windowsWoodFrame + windowsUPVCFrame);

            StepRecorder.recordStep(HeatLossCoefficient_BasementFloor, v.areasByType[1][AreaType.BasementFloor.ordinal()]);
            StepRecorder.recordStep(HeatLossCoefficient_GroundFloor, v.areasByType[1][AreaType.GroundFloor.ordinal()]);
            StepRecorder.recordStep(HeatLossCoefficient_ExposedFloor, v.areasByType[1][AreaType.ExposedUpperFloor.ordinal()]);

            StepRecorder.recordStep(HeatLossCoefficient_ExternalWall, v.areasByType[1][AreaType.ExternalWall.ordinal()]);

            StepRecorder.recordStep(HeatLossCoefficient_Roof, v.areasByType[1][AreaType.ExternalCeiling.ordinal()]);

            StepRecorder.recordStep(AreaExternal, v.totalExternalArea);
            StepRecorder.recordStep(AreaPartyWall, v.areasByType[0][AreaType.PartyWall.ordinal()]);
            StepRecorder.recordStep(AreaPartyFloor, v.areasByType[0][AreaType.PartyFloor.ordinal()]);
            StepRecorder.recordStep(AreaPartyCeiling, v.areasByType[0][AreaType.PartyCeiling.ordinal()]);
            StepRecorder.recordStep(AreaInternalWall, v.areasByType[0][AreaType.InternalWall.ordinal()]);

            StepRecorder.recordStep(FabricHeatLoss, v.totalFabricHeatLoss);
            StepRecorder.recordStep(ThermalMassParameter, v.getBestThermalMassParameter());

            StepRecorder.recordStep(Occupancy, eparameters.getNumberOfOccupants());

            StepRecorder.recordStep(DemandTemperature_LivingArea, eparameters.getZoneOneDemandTemperature());

            StepRecorder.recordStep(LivingAreaFraction, houseCase.getLivingAreaProportionOfFloorArea());

            sortTransducers(v.transducers);
            log.debug("New sort: {}", v.transducers);

            final IEnergyCalculationResult[] results = new IEnergyCalculationResult[climate.length];
            int i = 0;
            for (final ISeasonalParameters c : climate) {
                final IInternalParameters iparameters = new InternalParameters(eparameters, constants, c);
                results[i++] = evaluate(houseCase, iparameters, v);
            }

            return new IEnergyCalculationResultWithSteps() {
                @Override
                public IEnergyCalculationResult[] getResults() {
                    return results;
                }

                @Override
                public IEnergyCalculationSteps getSteps() {
                    return steps;
                }
            };
        }
    }

    private IEnergyCalculationResult evaluate(final IEnergyCalculatorHouseCase houseCase, final IInternalParameters parameters,
            final Visitor v) {
        final SpecificHeatLosses heatLosses = calculateSpecificHeatLosses(
                houseCase,
                parameters,
                v.totalFabricHeatLoss,
                v.getBestThermalMassParameter(),
                v.totalExternalArea,
                v.infiltration,
                v.ventilationSystems);

        // apply demand temperature adjustment, and compute zone 2 temp etc.
        final IInternalParameters adjustedParameters = adjustParameters(parameters, heatLosses, v.heatingSystems);

        final IEnergyState state = stateFactory.createEnergyState();

        // run transducer up to heat to compute background gains etc.
        final int indexOfFirstHeatingSystem = runToNonHeatTransducers(houseCase, v.transducers, v.glrAdjuster,
                heatLosses, adjustedParameters, state);

        final double[] demandTemperature = new double[]{adjustedParameters.getZoneOneDemandTemperature(),
            adjustedParameters.getZoneTwoDemandTemperature()};

        final double timeConstant = getTimeConstant(heatLosses); // tau in 9a
        final double coolingTime = getCoolingTime(timeConstant); // t_c in 9b
        final double utilisationFactorExponent = getUtilisationFactorExponent(timeConstant); // a in table 9a

        /*
        BEISDOC
        NAME: Total gains
        DESCRIPTION: Offset evaporation loss against the internal heat gains.
        TYPE: type
        UNIT: unit
        SAP: (73)
        SAP_COMPLIANT: Yes
        BREDEM: 6J
        BREDEM_COMPLIANT: Yes
        DEPS: useful-gains,evaporation-loss
        NOTES: In SAP and BREDEM, this happens earlier on. We do it here in order to take advantage of the supply vs demand ledger structure of our code.
        ID: total-gains
        CODSIEB
         */
        final double totalGains = state.getExcessSupply(EnergyType.GainsUSEFUL_GAINS);
        final double[][] backgroundTemperatures = calculateBackgroundTemperatures(adjustedParameters, heatLosses,
                houseCase.getZoneTwoHeatedProportion(), totalGains, utilisationFactorExponent);
        final double[] responsiveBackgroundTemperature = backgroundTemperatures[0];
        final double[] unresponsiveBackgroundTemperature = backgroundTemperatures[1];

        if (log.isDebugEnabled()) {
            log.debug("Demand temperature: {}", demandTemperature);
            log.debug("Fully responsive background temperature: {}", responsiveBackgroundTemperature);
            log.debug("Fully unresponsive background temperature: {}", unresponsiveBackgroundTemperature);
        }

        final double[] backgroundTemperature = getBackgroundTemperatureFromHeatingSystems(v.heatingSystems,
                v.proportions, heatLosses, adjustedParameters, state, responsiveBackgroundTemperature,
                unresponsiveBackgroundTemperature);

        if (log.isDebugEnabled()) {
            log.debug("Average background temperature for all systems = {}", backgroundTemperature);
        }

        // find mean temperature from profile
        final double[] meanTemperature = new double[ZoneType.values().length];

        meanTemperature[ZoneType.ZONE1.ordinal()] = adjustedParameters.getClimate()
                .getZone1HeatingSchedule()
                .getMeanTemperature(demandTemperature[ZoneType.ZONE1.ordinal()],
                        backgroundTemperature[ZoneType.ZONE1.ordinal()],
                        coolingTime * MINUTES_PER_HOUR);

        meanTemperature[ZoneType.ZONE2.ordinal()] = adjustedParameters.getClimate()
                .getZone2HeatingSchedule(adjustedParameters.getZone2ControlParameter())
                .getMeanTemperature(demandTemperature[ZoneType.ZONE2.ordinal()],
                        backgroundTemperature[ZoneType.ZONE2.ordinal()],
                        coolingTime * MINUTES_PER_HOUR);

        StepRecorder.recordStep(MeanInternalTemperature_LivingArea, meanTemperature[ZoneType.ZONE1.ordinal()]);
        StepRecorder.recordStep(MeanInternalTemperature_RestOfDwelling, meanTemperature[ZoneType.ZONE2.ordinal()]);

        double areaWeightedMeanTemperature = getAreaWeightedMeanTemperature(houseCase, meanTemperature);
        StepRecorder.recordStep(MeanInternalTemperature_Unadjusted, areaWeightedMeanTemperature);
        state.increaseSupply(EnergyType.HackUNADJUSTED_TEMPERATURE, areaWeightedMeanTemperature);

        /*
        BEISDOC
        NAME: Mean Internal Temperature
        DESCRIPTION: The mean internal temperature for the whole dwelling, after heating system adjustments have been applied.
        TYPE: formula
        UNIT: ℃
        SAP: (93), Table 4e
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: N/A - out of scope
        DEPS: temperature-adjustments,mean-internal-temperature-unadjusted
        ID: mean-internal-temperature-adjusted
        CODSIEB
         */
        areaWeightedMeanTemperature += adjustedParameters.getTemperatureAdjustment();
        StepRecorder.recordStep(MeanInternalTemperature, areaWeightedMeanTemperature);

        if (log.isDebugEnabled()) {
            log.debug("Mean temps = {}, Area-weighted = {}", meanTemperature, areaWeightedMeanTemperature);
        }

        configureGainLossAdjuster(heatLosses, adjustedParameters, demandTemperature, totalGains,
                utilisationFactorExponent, areaWeightedMeanTemperature, v.glrAdjuster);

        runHeatTransducers(houseCase, v.transducers, heatLosses, adjustedParameters, state, indexOfFirstHeatingSystem);

        StepRecorder.recordStep(
                EnergyCalculationStep.WaterHeating_TotalHeat_Monthly_BeforeSolar,
                state.getTotalSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS)
                + state.getTotalSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS)
        );

        return new EnergyCalculationResult(state, heatLosses, v.areasByType[0], v.areasByType[1]);
    }

    /**
     * Run the remaining transducers, once the gain-load ratio adjustment has
     * happened (call to
     * {@link #configureGainLossAdjuster(ISpecificHeatLosses, IInternalParameters, double[], double, double, double, GainLoadRatioAdjuster)}
     * )
     *
     * @param houseCase
     * @param transducers
     * @param heatLosses
     * @param adjustedParameters
     * @param state
     * @param indexOfFirstHeatingSystem the index of the gain load ratio
     * adjuster
     */
    private void runHeatTransducers(final IEnergyCalculatorHouseCase houseCase, final List<IEnergyTransducer> transducers,
            final ISpecificHeatLosses heatLosses, final IInternalParameters adjustedParameters,
            final IEnergyState state, final int indexOfFirstHeatingSystem) {
        /*
        BEISDOC
        NAME: Space Heating Fuel Energy Demand
        DESCRIPTION: Runs all the heating system transducers, triggering them to meet the space heating demand by consuming fuel.
        TYPE: ???
        UNIT: W
        DEPS: boiler-fuel-energy-demand,community-space-heating-fuel-energy-demand,heat-pump-fuel-energy-demand,room-heater-fuel-energy-demand,storage-heater-fuel-energy-demand,warm-air-system-fuel-energy-demand
        GET: house.energy-use
        SAP_COMPLIANT: N/A - out of scope
        BREDEM_COMPLIANT: N/A - out of scope
        NOTES: This doesn't strictly form a part of the SAP or BREDEM calculations. It's included in these documents to indicate the structure of the NHM energy calculator code.
        ID: all-space-heating-fuel-energy-demand
        CODSIEB
         */
        final boolean heatingOn = adjustedParameters.getClimate().isHeatingOn();
        for (int i = indexOfFirstHeatingSystem; i < transducers.size(); i++) {
            final IEnergyTransducer transducer = transducers.get(i);
            if (log.isDebugEnabled()) {
                log.debug("Running transducer {}", transducer);
            }
            state.setCurrentServiceType(transducer.getServiceType(), transducer.toString());
            if (heatingOn || (transducer.getServiceType() != ServiceType.PRIMARY_SPACE_HEATING && transducer.getServiceType() != ServiceType.SECONDARY_SPACE_HEATING)) {
                transducer.generate(houseCase, adjustedParameters, heatLosses, state);
            }
        }
    }

    /**
     * Set the various temperature related values into the GLR adjuster; this
     * involves using
     * {@link #calculateGainsUtilisationFactor(double, double, double, double, double)}
     * to determine the final, true gain utilisation factor after doing the
     * zone-specific versions.
     *
     * @param heatLosses
     * @param adjustedParameters
     * @param demandTemperature
     * @param totalGains
     * @param utilisationFactorExponent
     * @param areaWeightedMeanTemperature
     * @param glrAdjuster
     */
    protected static void configureGainLossAdjuster(final ISpecificHeatLosses heatLosses,
            final IInternalParameters adjustedParameters, final double[] demandTemperature, final double totalGains,
            final double utilisationFactorExponent, final double areaWeightedMeanTemperature,
            final GainLoadRatioAdjuster glrAdjuster) {
        /*
        BEISDOC
        NAME: Revised Gains Utilisation Factor
        DESCRIPTION: The gains utilisation factor, recalculated using the final mean internal temperature for the dwelling.
        TYPE: formula
        UNIT: Dimensionless
        SAP: (94), Table 9a
        SAP_COMPLIANT: Yes
        BREDEM: 8A-C
        BREDEM_COMPLIANT: Yes
        DEPS: mean-internal-temperature-adjusted,external-temperature,specific-heat-loss,total-gains,utilisation-factor-exponent
        ID: gains-utilisation-factor-revised
        CODSIEB
         */
        final double revisedGUF = calculateGainsUtilisationFactor(areaWeightedMeanTemperature,
                adjustedParameters.getClimate().getExternalTemperature(), heatLosses.getSpecificHeatLoss(), totalGains,
                utilisationFactorExponent);

        StepRecorder.recordStep(GainsUtilisation, revisedGUF);

        glrAdjuster.setAreaWeightedMeanTemperature(areaWeightedMeanTemperature);
        glrAdjuster.setDemandTemperature(demandTemperature);
        glrAdjuster.setRevisedGUF(revisedGUF);
        glrAdjuster.setExternalTemperature(adjustedParameters.getClimate().getExternalTemperature());
    }

    /**
     * Compute the area weighted mean temperature for the given zonal means &
     * house case
     *
     * @param houseCase
     * @param meanTemperature
     * @return
     */
    protected static double getAreaWeightedMeanTemperature(final IEnergyCalculatorHouseCase houseCase,
            final double[] meanTemperature) {
        /*
        BEISDOC
        NAME: Mean Internal Temperature Unadjusted
        DESCRIPTION: The mean internal temperature of the whole dwelling, before SAP adjustments are applied.
        TYPE: formula
        UNIT: ℃
        SAP: (92)
        SAP_COMPLIANT: Yes
        BREDEM: 7Y
        BREDEM_COMPLIANT: Yes
        DEPS: mean-zonal-temperatures,living-area-proportion
        ID: mean-internal-temperature-unadjusted
        CODSIEB
         */
        return meanTemperature[0] * houseCase.getLivingAreaProportionOfFloorArea()
                + meanTemperature[1] * (1 - houseCase.getLivingAreaProportionOfFloorArea());
    }

    /**
     *
     * @param v
     * @param heatLosses
     * @param adjustedParameters
     * @param state
     * @param demandTemperature
     * @param idealBackgroundTemperature
     * @param worstCaseBackgroundTemperature
     * @param remainingContribution
     * @return
     * @assumption If there is no heating system in the house, the
     * responsiveness will equal 1 as specified in SAP Table 4a Category 1: No
     * Heating Present
     */
    protected static double[] getBackgroundTemperatureFromHeatingSystems(final List<IHeatingSystem> heatingSystems,
            final Map<IHeatingSystem, Double> proportions, final ISpecificHeatLosses heatLosses,
            final IInternalParameters adjustedParameters, final IEnergyState state,
            final double[] responsiveBackgroundTemperature, final double[] unresponsiveBackgroundTemperature) {

        IHeatingSystem main = null;
        double highestProportion = 0;
        for (final IHeatingSystem system : heatingSystems) {
            if (proportions.get(system) >= highestProportion) {
                main = system;
                highestProportion = proportions.get(system);
            }
        }

        /*
        BEISDOC
        NAME: Responsiveness
        DESCRIPTION: The speed with which the heating system moves the house to a new temperature.
        TYPE: lookup
        UNIT: Unknown
        SAP: Table 4a, 4d
        SAP_COMPLIANT: Yes
        BREDEM: Defers to SAP
        BREDEM_COMPLIANT: N/A - out of scope
        DEPS: room-heater-responsiveness, storage-heater-responsiveness,wet-system-responsiveness,boiler-responsiveness
        ID: responsiveness
        CODSIEB
         */
        final double responsiveness;
        if (main == null) {
            responsiveness = 1;

            if (log.isDebugEnabled()) {
                log.debug("There are no heating systems - using assumed electric heaters in responsiveness calculation");
            }

        } else {
            responsiveness = main.getResponsiveness(adjustedParameters.getConstants(), adjustedParameters.getCalculatorType(), adjustedParameters.getTarrifType());

            if (log.isDebugEnabled()) {
                log.debug("Responsiveness from {} = {}", main, responsiveness);
            }
        }

        /*
        BEISDOC
        NAME: Background temperatures
        DESCRIPTION: The temperature when the heating is off for Zones 1 and 2.
        TYPE: formula
        UNIT: ℃
        SAP: Table 9b
        SAP_COMPLIANT: Yes
        BREDEM: 7L, 7T
        BREDEM_COMPLIANT: Yes
        DEPS: responsiveness, unresponsive-temperatures,responsive-temperatures
        ID: background-temperatures
        CODSIEB
         */
        final double unresponsiveness = 1 - responsiveness;

        return new double[]{
            (responsiveBackgroundTemperature[0] * responsiveness) + (unresponsiveBackgroundTemperature[0] * unresponsiveness),
            (responsiveBackgroundTemperature[1] * responsiveness) + (unresponsiveBackgroundTemperature[1] * unresponsiveness)
        };
    }

    private final double getUtilisationFactorExponent(final double timeConstant) {
        /*
        BEISDOC
        NAME: Utilisation Factor Exponent
        TYPE: formula
        UNIT: Dimensionless
        SAP: Table 9a
        SAP_COMPLIANT: Yes
        BREDEM: 7G
        BREDEM_COMPLIANT: Yes
        DEPS: time-constant,utilisation-factor-time-constant-divisor
        ID: utilisation-factor-exponent
        CODSIEB
         */
        // this is a = 1 + tau / 15
        return 1 + (timeConstant / UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR);
    }

    private final double getCoolingTime(final double timeConstant) {
        /*
        BEISDOC
        NAME: Cooling time
        DESCRIPTION: The rate at which a dwelling cools down.
        TYPE: formula
        UNIT: Hours
        SAP: Table 9b
        SAP_COMPLIANT: Yes
        BREDEM: 7K
        BREDEM_COMPLIANT: Yes
        DEPS: time-constant,mimimum-cooling-time,cooling-time-time-constant-multiplier
        ID: cooling-time
        CODSIEB
         */
        // This is Tc = 4 + 0.25 tau
        return MINIMUM_COOLING_TIME + COOLING_TIME_CONSTANT_MULTIPLIER * timeConstant;
    }

    private final double getTimeConstant(final SpecificHeatLosses heatLosses) {
        /*
        BEISDOC
        NAME: Time constant
        DESCRIPTION: A number which represents how quickly the dwelling's temperature changes.
        TYPE: formula
        UNIT: Hours
        SAP: Table 9a
        SAP_COMPLIANT: Yes
        BREDEM: 7F
        BREDEM_COMPLIANT: Yes
        DEPS: thermal-mass-parameter,heat-loss-parameter,time-constant-heat-loss-parameter-multiplier
        ID: time-constant
        CODSIEB
         */
        // this is tau = TMP / 3.6 * HLP
        return div(heatLosses.getThermalMassParameter(),
                (TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER * heatLosses.getHeatLossParameter()), "time constant");
    }

    /**
     * Run all the transducers until we get to the gain to loss ratio adjuster
     * passed in, and then return the index we stopped at
     *
     * @param houseCase
     * @param v
     * @param heatLosses
     * @param adjustedParameters
     * @param state
     * @return
     */
    protected static int runToNonHeatTransducers(final IEnergyCalculatorHouseCase houseCase,
            final List<IEnergyTransducer> transducers, final IEnergyTransducer stop,
            final ISpecificHeatLosses heatLosses, final IInternalParameters adjustedParameters,
            final IEnergyState state) {
        int indexOfFirstHeatingSystem = 0;

        for (final IEnergyTransducer transducer : transducers) {

            /*
             *  Stop running once we get to the heating transducers:
             *  we only want to run those after we've calculated the mean internal temperature.
             */
            if (transducer == stop) {
                break;
            }

            indexOfFirstHeatingSystem++;

            /*
             * If uncommented, these lines would cause pumps and fans to be disabled during periods when the heating is off.
             */
            //final boolean heatingOn = adjustedParameters.getClimate().isHeatingOn();
            //if (heatingOn || (transducer.getServiceType() != ServiceType.PRIMARY_SPACE_HEATING && transducer.getServiceType() != ServiceType.SECONDARY_SPACE_HEATING)) {
            if (log.isDebugEnabled()) {
                log.debug("Running {}", transducer);
            }
            state.setCurrentServiceType(transducer.getServiceType(), transducer.toString());
            transducer.generate(houseCase, adjustedParameters, heatLosses, state);
            //}
        }

        return indexOfFirstHeatingSystem;
    }

    /**
     * Adjust the demand temperature in the input parameters
     *
     * In summary,
     *
     * <ol>
     * <li>Accumulate zone 1 temperature tweaks from heating system</li>
     * <li>If zone 2 temperature is not set, calculate and override it</li>
     * <li>Accumulate heating period time tweaks</li>
     * <li>Create a new parameters which applies these tweaks</li>
     * </ol>
     *
     * @param parameters
     * @param heatingSystems
     * @return
     * @assumption Secondary heating system control parameter (BREDEM 8 9.1
     * Tctl) is the maximum over all heating systems if there are multiple
     * heating systems (BREDEM does not specify)
     * @assumption When adjusting the demand temperature, if there are multiple
     * adjustments due to multiple systems the adjustments are summed (BREDEM
     * does not specify)
     *
     */
    protected IInternalParameters adjustParameters(final IInternalParameters parameters,
            final ISpecificHeatLosses heatLosses, final List<IHeatingSystem> heatingSystems) {
        double totalTemperatureAdjustment = 0;
        int heatingSystemZoneTwoControlParameterOrdinal = 0;
        double heatingSystemZoneTwoTemperature;

        for (final IHeatingSystem system : heatingSystems) {
            totalTemperatureAdjustment += system.getDemandTemperatureAdjustment(parameters);
            heatingSystemZoneTwoControlParameterOrdinal = Math.max(
                    heatingSystemZoneTwoControlParameterOrdinal,
                    system.getZoneTwoControlParameter(parameters).ordinal()
            );
        }

        final Zone2ControlParameter zone2ControlParameter = Zone2ControlParameter.values()[heatingSystemZoneTwoControlParameterOrdinal];

        if (!parameters.isZoneTwoDemandTemperatureSpecified()) {
            heatingSystemZoneTwoTemperature = calculateZoneTwoDemandTemperature(
                    parameters.getInterzoneTemperatureDifference(), heatLosses.getHeatLossParameter(),
                    zone2ControlParameter, parameters.getZoneOneDemandTemperature());
        } else {
            heatingSystemZoneTwoTemperature = parameters.getZoneTwoDemandTemperature();
        }

        StepRecorder.recordStep(DemandTemperature_RestOfDwelling, heatingSystemZoneTwoTemperature);

        return new ParameterAdjustment(parameters, totalTemperatureAdjustment, heatingSystemZoneTwoTemperature, zone2ControlParameter);
    }

    protected double calculateZoneTwoDemandTemperature(final double interzoneTemperatureDifference,
            final double heatLossParameter, final Zone2ControlParameter zoneTwoControlParameter,
            final double newZone1DemandTemperature) {

        /*
        BEISDOC
        NAME: Uncontrolled Zone 2 Demand Temperature
        DESCRIPTION: The zone 2 demand temperature in case of heating control type 1.
        TYPE: formula
        UNIT: ℃
        SAP: Table 9 (row 1, Temperature H2)
        SAP_COMPLIANT: Yes
        BREDEM: 7A
        BREDEM_COMPLIANT: Yes
        DEPS: reference-heat-loss-parameter,heat-loss-parameter,zone-1-demand-temperature,interzone-temperature-difference
        ID: uncontrolled-zone-2-demand-temperature
        CODSIEB
         */
        final double uncontrolledZoneTwoDemandTemperature = newZone1DemandTemperature - interzoneTemperatureDifference
                * (Math.min(REFERENCE_HEAT_LOSS_PARAMETER, heatLossParameter) / REFERENCE_HEAT_LOSS_PARAMETER);

        /*
        BEISDOC
        NAME: Controlled Zone 2 Demand Temperature
        DESCRIPTION: The zone 2 demand temperature in case of heating control types 2 or 3
        TYPE: formula
        UNIT: ℃
        SAP: Table 9 (rows 2 and 3, Temperature H2)
        SAP_COMPLIANT: Yes
        BREDEM: 7B
        BREDEM_COMPLIANT: Yes
        DEPS: reference-heat-loss-parameter,heat-loss-parameter,zone-1-demand-temperature,interzone-temperature-difference
        ID: controlled-zone-2-demand-temperature
        CODSIEB
         */
        final double controlledZoneTwoDemandTemperature = newZone1DemandTemperature + interzoneTemperatureDifference
                * (Math.pow(Math.min(heatLossParameter - REFERENCE_HEAT_LOSS_PARAMETER, 0), 2)
                / REFERENCE_HEAT_LOSS_PARAMETER2 - 1);

        /*
        BEISDOC
        NAME: Zone 2 Demand Temperature
        DESCRIPTION: The zone 2 demand temperature
        TYPE: formula
        UNIT: ℃
        SAP: Table 9 (Temperature H2 column)
        SAP_COMPLIANT: Yes
        BREDEM: 7C
        BREDEM_COMPLIANT: No, see note
        DEPS: controlled-zone-2-demand-temperature,uncontrolled-zone-2-demand-temperature,zone-2-control-parameter
        NOTES: While this looks like the BREDEM algorithm, it actually behaves according to SAP rather than BREDEM.
        NOTES: If the zone 2 control parameter is 0, we get row 1 of the SAP table. If it is 1, we get rows 2 and 3 (which are the same).
        ID: zone-2-demand-temperature
        CODSIEB
         */
        final double result = controlledZoneTwoDemandTemperature * zoneTwoControlParameter.controlledProportion()
                + uncontrolledZoneTwoDemandTemperature * (1 - zoneTwoControlParameter.controlledProportion());

        return result;
    }

    /**
     * Place the given list of transducers into order satisfying the following
     * rules:
     * <ol>
     * <li>if something is /input led/ that means that /everything/ which
     * outputs a given input needs to happen before it</li>
     * <li>if something is /output led/ that means that everything which inputs
     * it /or/ lazily outputs it needs to happen before it</li>
     * </ol>
     *
     * @param transducers
     */
    protected static void sortTransducers(final List<IEnergyTransducer> transducers) {
        Collections.sort(transducers, phasingComparator);
    }
}
