package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.google.common.util.concurrent.AtomicDouble;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * EnergyUseTransducer.
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
     * @return
     * @see uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#getServiceType()
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
     * @see uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#generate(uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase, uk.org.cse.nhm.energycalculator.api.IInternalParameters, uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses, uk.org.cse.nhm.energycalculator.api.IEnergyState)
     */
    @Override
    public void generate(IEnergyCalculatorHouseCase house, IInternalParameters parameters, ISpecificHeatLosses losses,
            IEnergyState state) {
        
        double k = linearTerm;
        
        EnumMap<EnergyType, Double> proportionOfHeating = proportionOfEnergyDemand(serviceType, state);
        EnumMap<EnergyType, Double> constTerms = calcProportionOfConstantTerm(constantTerm, proportionOfHeating);
        
        state.setCurrentServiceType(serviceType, serviceType.toString());
        constTerms.forEach(new BiConsumer<EnergyType, Double>() {
            @Override
            public void accept(EnergyType t, Double c) {
                final double beforeDemand = state.getTotalDemand(t, serviceType);
                state.increaseDemand(t, ((k * beforeDemand) + c) - beforeDemand);
            }            
        });
    }
    
    /**
     * TODO.
     * 
     * @param linearTerm
     * @param proportionOfHeating
     * @return
     */
    protected EnumMap<EnergyType, Double> calcProportionOfConstantTerm(double linearTerm,
            final EnumMap<EnergyType, Double> proportionOfHeating) {
        
        final Set<EnergyType> energyTypes = proportionOfHeating.keySet();
        energyTypes.forEach(t -> {
            proportionOfHeating.computeIfPresent(t, new BiFunction<EnergyType, Double, Double>() {
                @Override
                public Double apply(EnergyType t, Double u) {
                    if(u > 0){
                        return linearTerm * u;
                    } else {
                        return null;
                    }
                }
            });
        });
        
        return proportionOfHeating;
    }

    protected EnumMap<EnergyType, Double> proportionOfEnergyDemand(ServiceType serviceType, IEnergyState state){
        EnumMap<EnergyType, Double> proportionOfHeating = new EnumMap<>(EnergyType.class);
        final List<EnergyType> energyTypes = Arrays.asList(EnergyType.values());
        
        //Get the current energy demand for each Energy Type for the service type as a proportion
        AtomicDouble beforeDemand = new AtomicDouble();
        energyTypes.forEach(et -> {
            //TODO: Move get of total-demand by service to to state class?
            double demand = state.getTotalDemand(et, serviceType);
            proportionOfHeating.put(et, demand);
            
            //TODO: Could use 
            beforeDemand.addAndGet(state.getTotalDemand(et, serviceType));
        });
        
        energyTypes.forEach(et -> {
            proportionOfHeating.computeIfPresent(et, new BiFunction<EnergyType, Double, Double>() {
                @Override
                public Double apply(EnergyType t, Double u) {
                    if(u != 0){
                        return u / beforeDemand.get();
                    } else {
                        return null;
                    }
                }
            });            
        });
        
        return proportionOfHeating;
    };

    /**
     * @return
     * @see uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#getPriority()
     */
    @Override
    public int getPriority() {
        return 10;
    }

    /**
     * @return
     * @see uk.org.cse.nhm.energycalculator.api.IEnergyTransducer#getPhase()
     */
    @Override
    public TransducerPhaseType getPhase() {
        return TransducerPhaseType.AfterEverything;
    }

}
