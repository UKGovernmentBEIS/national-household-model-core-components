package uk.org.cse.nhm.language.definition.function.house;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAdjustNumberOfAirChangeDevices;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Bind("house.number-air-change-devices")
@Doc("The number of air change devices within the dwelling.")
public class XNumberOfAirChangeDevices extends XHouseNumber {

    public static final class P {

        public static final String airChangeDevice = "airChangeDevice";
    }

    private XAdjustNumberOfAirChangeDevices.XAirChangeDevice airChangeDevice;

    /**
     * Return the airChangeDevice.
     *
     * @return the airChangeDevice
     */
    @BindNamedArgument("device-type")
    @Doc("The type of air change device to return number of")
    @NotNull(message = "house.number-air-change-devices must define the type of device to return number of")
    @Prop(P.airChangeDevice)
    public XAdjustNumberOfAirChangeDevices.XAirChangeDevice getAirChangeDevice() {
        return airChangeDevice;
    }

    /**
     * Set the airChangeDevice.
     *
     * @param airChangeDevice the airChangeDevice
     */
    public void setAirChangeDevice(XAdjustNumberOfAirChangeDevices.XAirChangeDevice airChangeDevice) {
        this.airChangeDevice = airChangeDevice;
    }

    public static XNumberOfAirChangeDevices create(final XAdjustNumberOfAirChangeDevices.XAirChangeDevice device) {
        final XNumberOfAirChangeDevices out = new XNumberOfAirChangeDevices();
        out.setAirChangeDevice(device);
        return out;
    }
}
