package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.InsolationPlaneUtil;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.impl.SeasonalParameters;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetInsolation extends AbstractNamed implements IComponentsFunction<Number> {
	private final IDimension<IWeather> weather;
    private final IDimension<BasicCaseAttributes> basicAttributes;
    private final double orientation, inclination;

	@AssistedInject
    public GetInsolation(final IDimension<IWeather> weather,
                         final IDimension<BasicCaseAttributes> basicAttributes,
                         @Assisted("orientation") final double orientation,
                         @Assisted("inclination") final double inclination) {
        this.orientation = Math.toRadians(orientation);
        this.inclination = Math.toRadians(inclination);
		this.weather = weather;
        this.basicAttributes = basicAttributes;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final IWeather weather = scope.get(this.weather);
        final double latitude = scope.get(this.basicAttributes).getRegionType().getLatitudeRadians();
		double acc = 0;
		for (final MonthType mt : MonthType.values()) {
			// weather value is in wats per square meter
			// we want kWh/year
			final double flux = weather.getHorizontalSolarFlux(mt);
            final double declination = SeasonalParameters.DECLINATION[mt.ordinal()];
            // wangle flux into flux w/inclination
            final double multiplier = InsolationPlaneUtil.getSolarFluxMultiplier(declination, latitude, inclination, orientation);
			final int days = mt.getStandardDays();
			acc +=
				(multiplier * flux / 1000) // flux in kW / m2
				* (days * 24); // number of hours in the month
			// i.e. kWh/m2
		}
		return acc;
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return ImmutableSet.of();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(weather);
	}
}
