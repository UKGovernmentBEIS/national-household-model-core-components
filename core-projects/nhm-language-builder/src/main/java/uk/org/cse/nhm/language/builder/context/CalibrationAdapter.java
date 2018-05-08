package uk.org.cse.nhm.language.builder.context;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.PutScope;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.context.calibration.XCalibrationContext;
import uk.org.cse.nhm.language.definition.context.calibration.XCalibrationRule;
import uk.org.cse.nhm.language.definition.context.calibration.XPolynomialCalibration;
import uk.org.cse.nhm.language.definition.context.calibration.XReplacementCalibration;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.ICalibrationRule;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.IEnergyCalibrations;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.PolynomialRule;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.ReplacementRule;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class CalibrationAdapter extends ReflectingAdapter {

    public static final String INSIDE_CALIBRATION = "INSIDE_CALIBRATION";
    private final Provider<IEnergyCalibrations> calibrationsProvider;

    @Inject
    protected CalibrationAdapter(
            final Set<IConverter> delegates,
            final Set<IAdapterInterceptor> interceptors,
            final Provider<IEnergyCalibrations> calibrationsProvider) {
        super(delegates, interceptors);
        this.calibrationsProvider = calibrationsProvider;
    }

    @Adapt(XCalibrationContext.class)
    public Initializable buildCalibrationContext(
            @Prop(XCalibrationContext.P.always_true)
            @PutScope(INSIDE_CALIBRATION) final boolean alwaysTrue,
            @Prop(XCalibrationContext.P.rules) final List<RuleAndFuels> rules
    ) {
        final IEnergyCalibrations cal = calibrationsProvider.get();

        for (final RuleAndFuels raf : rules) {
            cal.addRuleForFuels(raf.rule, raf.fuels);
        }

        return Initializable.NOP;
    }

    static class RuleAndFuels {

        public final ICalibrationRule rule;
        public final Set<FuelType> fuels;

        RuleAndFuels(final ICalibrationRule rule, final Set<FuelType> fuels) {
            super();
            this.rule = rule;
            this.fuels = fuels;
        }
    }

    private static Set<FuelType> convert(final List<XFuelType> fuels) {
        final ImmutableSet.Builder<FuelType> builder = ImmutableSet.builder();

        for (final XFuelType xft : fuels) {
            builder.add(MapEnum.fuel(xft));
        }

        return builder.build();
    }

    @Adapt(XReplacementCalibration.class)
    public RuleAndFuels buildReplacementRule(
            @Prop(XCalibrationRule.P.fuels) final List<XFuelType> fuels,
            @Prop(XReplacementCalibration.P.value) final IComponentsFunction<Number> function) {
        return new RuleAndFuels(new ReplacementRule(function), convert(fuels));
    }

    @Adapt(XPolynomialCalibration.class)
    public RuleAndFuels buildPolynomialRule(
            @Prop(XCalibrationRule.P.fuels) final List<XFuelType> fuels,
            @Prop(XPolynomialCalibration.P.terms) final List<IComponentsFunction<Number>> terms) {
        return new RuleAndFuels(new PolynomialRule(terms), convert(fuels));
    }
}
