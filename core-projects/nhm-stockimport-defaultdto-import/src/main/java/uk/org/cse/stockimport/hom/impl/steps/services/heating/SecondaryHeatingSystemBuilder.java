package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;

/**
 * Builds a secondary heating system, which is always a room heater, by looking
 * up attributes in a couple of maps and on the
 * {@link SecondaryHeatingSystemType} enum.
 *
 * @author hinton
 * @since 1.0
 */
public class SecondaryHeatingSystemBuilder implements ISecondaryHeatingSystemBuilder {

    private final Map<SecondaryHeatingSystemType, Double> efficiency
            = ImmutableMap.<SecondaryHeatingSystemType, Double>builder()
                    .put(SecondaryHeatingSystemType.ELECTRIC_ROOM_HEATERS, 1d)
                    .put(SecondaryHeatingSystemType.GAS_COAL_EFFECT_FIRE, 0.4)
                    .put(SecondaryHeatingSystemType.GAS_FIRE, 0.58)
                    //				.put(SecondaryHeatingSystemType.GAS_FIRE_FLUELESS, 0.58)
                    .put(SecondaryHeatingSystemType.GAS_FIRE_OPEN_FLUE, 0.58)
                    .put(SecondaryHeatingSystemType.LPG_HEATER, 0.58)
                    .put(SecondaryHeatingSystemType.OPEN_FIRE, 0.45)
                    .build();

    @Override
    public IRoomHeater createSecondaryHeatingSystem(final SecondaryHeatingSystemType type) {
        switch (type) {
            case ELECTRIC_ROOM_HEATERS:
            case GAS_COAL_EFFECT_FIRE:
            case GAS_FIRE:
            case GAS_FIRE_OPEN_FLUE:
            case LPG_HEATER:
            case OPEN_FIRE:
                return createRoomHeater(type, efficiency.get(type));
            default:
            case NOT_KNOWN:
            case NO_SECONDARY_SYSTEM:
                return null;
        }
    }

    private static IRoomHeater createRoomHeater(final SecondaryHeatingSystemType type, final double efficiency) {
        final IRoomHeater roomHeater = ITechnologiesFactory.eINSTANCE.createRoomHeater();

        roomHeater.setEfficiency(Efficiency.fromDouble(efficiency));
        roomHeater.setFuel(type.getFuelType());
        roomHeater.setFlueType(type.getFlueType());
        roomHeater.setThermostatFitted(false);

        return roomHeater;
    }
}
