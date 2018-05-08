package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

/**
 * This is emitted into the clculation by {@link DirectElectricHeaterImpl}.
 *
 * @author hinton
 *
 */
public class DirectElectricHeatTransducer extends EnergyTransducer {

    private static final Logger log = LoggerFactory.getLogger(DirectElectricHeatTransducer.class);
    private final double proportion;

    public DirectElectricHeatTransducer(final int priority, final double proportion) {
        super(ServiceType.SECONDARY_SPACE_HEATING, priority);
        this.proportion = proportion;
    }

    @Override
    public void generate(final IEnergyCalculatorHouseCase house,
            final IInternalParameters parameters, final ISpecificHeatLosses losses,
            final IEnergyState state) {
        final double generated = state.getBoundedTotalHeatDemand(proportion);

        log.debug("generated {} of space heat", generated);
        state.increaseSupply(EnergyType.DemandsHEAT, generated);
        state.increaseElectricityDemand(
                parameters.getConstants().get(SplitRateConstants.DIRECT_ELECTRIC_FRACTIONS,
                        parameters.getTarrifType()),
                generated);
    }

    @Override
    public TransducerPhaseType getPhase() {
        return TransducerPhaseType.Heat;
    }
}
