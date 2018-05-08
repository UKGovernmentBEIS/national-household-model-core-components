package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * EnergyUseTransducer..
 *
 * @author trickyBytes
 */
public class EnergyUseTransducer implements IEnergyTransducer {

    private final ServiceType serviceType;
    private final double linearTerm;
    private final double constantTerm;

    /**
     * @param appliances
     * @param linearTerm
     * @param constantTerm
     */
    public EnergyUseTransducer(ServiceType serviceType, double linearTerm, double constantTerm) {
        super();
        this.serviceType = serviceType;
        this.linearTerm = linearTerm;
        this.constantTerm = constantTerm;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#getServiceType()
     */
    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * @param house
     * @param parameters
     * @param losses
     * @param state
     * @see
     * uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#generate(uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase,
     * uk.org.cse.nhm.energycalculator.api.IInternalParameters,
     * uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses,
     * uk.org.cse.nhm.energycalculator.api.IEnergyState)
     */
    @Override
    public void generate(IEnergyCalculatorHouseCase house, IInternalParameters parameters, ISpecificHeatLosses losses,
            IEnergyState state) {

        double k = linearTerm;
        // EnumMap<EnergyType, Double> constTerms = calConstantTerm(serviceType, state);
        Map<EnergyType, Double> constantTermSplit = proportionalEnergyDemand(serviceType, state);

        final double[] totalDemands = new double[2];
        state.setCurrentServiceType(serviceType, serviceType.toString());
        constantTermSplit.forEach(new BiConsumer<EnergyType, Double>() {
            @Override
            public void accept(EnergyType t, Double f) {
                final double Ea = state.getTotalDemand(t, serviceType);
                final double c = constantTerm * f;
                // final double delta = Ea - ((k * Ea) + c);
                final double delta = ((k * Ea) + c) - Ea;

                totalDemands[0] += Ea;
                totalDemands[1] += (Ea + delta);

                if (delta != 0) {
                    state.increaseDemand(t, delta);
                }
            }
        });

        if (totalDemands[0] != 0) {
            if (serviceType == ServiceType.APPLIANCES) {
                final double gains = state.getTotalSupply(EnergyType.GainsAPPLIANCE_GAINS);
                final double gainsRatio = totalDemands[1] / totalDemands[0];
                state.increaseSupply(EnergyType.GainsAPPLIANCE_GAINS, (gainsRatio - 1) * gains);
            } else if (serviceType == ServiceType.COOKING) {
                final double gains = state.getTotalSupply(EnergyType.GainsCOOKING_GAINS);
                final double gainsRatio = totalDemands[1] / totalDemands[0];
                state.increaseSupply(EnergyType.GainsCOOKING_GAINS, (gainsRatio - 1) * gains);
            }
        }
    }

    protected static Map<EnergyType, Double> proportionalEnergyDemand(ServiceType st, IEnergyState state) {
        EnumMap<EnergyType, Double> proportionOfHeating = new EnumMap<>(EnergyType.class);

        final double total = state.getTotalDemand(st);

        for (EnergyType et : EnergyType.values()) {
            if (total == 0) {
                proportionOfHeating.put(et, 0d);
            } else {
                proportionOfHeating.put(et, state.getTotalDemand(et, st) / total);
            }
        }

        return proportionOfHeating;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#getPriority()
     */
    @Override
    public int getPriority() {
        return 10;
    }

    /**
     * @return @see
     * uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#getPhase()
     */
    @Override
    public TransducerPhaseType getPhase() {
        return TransducerPhaseType.BeforeGains;
    }
}
