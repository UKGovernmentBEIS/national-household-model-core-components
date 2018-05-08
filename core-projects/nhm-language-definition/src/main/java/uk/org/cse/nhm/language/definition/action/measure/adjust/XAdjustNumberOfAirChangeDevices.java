package uk.org.cse.nhm.language.definition.action.measure.adjust;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;

/**
 * XAdjustNumberOfPassiveVentsAction.
 *
 * @author trickyBytes
 */
@Doc("Adjust the number of passive vents within a property")
@Bind("measure.adjust-airchange-devices")
@Unsuitability(alwaysSuitable = true)
public class XAdjustNumberOfAirChangeDevices extends XMeasure {

    public static final class P {

        public static final String adjustment = "adjustment";
        public static final String airChangeDevice = "airChangeDevice";
    }

    private XAirChangeDevice airChangeDevice;

    @Doc("The type of device to adjust.")
    public enum XAirChangeDevice {
        @Doc("Passive Vent")
        Vents,
        @Doc("Mechanical Fans")
        Fans
    }

    private int adjustment;

    /**
     * Return the adjustment.
     *
     * @return the adjustment
     */
    @BindNamedArgument("adjustment")
    @Doc("The adjustment in number of vents to make.")
    @NotNull(message = "measure.adjust-airchange-devices must define an adjustment")
    @Prop(P.adjustment)
    public int getAdjustment() {
        return adjustment;
    }

    /**
     * Set the adjustment.
     *
     * @param adjustment the adjustment
     */
    public void setAdjustment(int adjustment) {
        this.adjustment = adjustment;
    }

    /**
     * Return the airChangeDevice.
     *
     * @return the airChangeDevice
     */
    @BindNamedArgument("device-type")
    @Doc("The type of air change device to make adjustments to number of")
    @NotNull(message = "adjust-airchange-devices must define the type of device to adjust")
    public XAirChangeDevice getAirChangeDevice() {
        return airChangeDevice;
    }

    /**
     * Set the airChangeDevice.
     *
     * @param airChangeDevice the airChangeDevice
     */
    public void setAirChangeDevice(XAirChangeDevice airChangeDevice) {
        this.airChangeDevice = airChangeDevice;
    }
}
