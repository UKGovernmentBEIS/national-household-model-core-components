package uk.org.cse.nhm.language.definition.action.measure.insulation;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.enums.XFrameType;
import uk.org.cse.nhm.language.definition.enums.XGlazingType;
import uk.org.cse.nhm.language.definition.enums.XWindowGlazingAirGap;
import uk.org.cse.nhm.language.definition.enums.XWindowInsulationType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


/**
 * @since 4.2.0
 */
@Bind("measure.install-glazing")
@Doc( { "Sets the values of glazed surfaces in the dwelling.",
		"You can specify the type of glazing and provide additional parameters such as frame type and new u-values.",
		"This will replace any existing glazing with the new type.",
		"This measure only affects windows - it has no effect on glazed doors.",
		"When the energy calculator is in SAP 2012 mode, the frame factors, u-value and transmittance will have no effect: these will be overridden by the relevant SAP table lookups."
})
@Unsuitability(alwaysSuitable = true)
public class XGlazingMeasure extends XMeasure {
	public static final class P {
		public static final String capex = "capex";
		public static final String uValue = "uValue";
		public static final String lightTransmittance = "lightTransmittance";
		public static final String gainsTransmittance = "gainsTransmittance";
		public static final String frameFactor = "frameFactor";
		public static final String frameType = "frameType";
		public static final String glazingType = "glazingType";
		public static final String insulationType = "insulationType";
		public static final String airGap = "airGap";
	}
	
	private XNumber capex;
	private double uValue;
	private double lightTransmittance;
	private double gainsTransmittance;
	private double		frameFactor;
	private XFrameType	frameType;
	private XGlazingType glazingType;
	private XWindowInsulationType insulationType;
	private XWindowGlazingAirGap glazingAirGap = XWindowGlazingAirGap.gapOf6mm;
	
    /**
     * Return the uValue.
     *
     * @return the uValue
     */
    @BindNamedArgument("u-value")
    @Doc("The new u-value for the window(s) affected by this measure.")
    @Prop(P.uValue)
    public double getuValue() {
        return uValue;
    }

    /**
     * Set the uValue.
     *
     * @param uValue the uValue 
     */
    public void setuValue(final double uValue) {
        this.uValue = uValue;
    }
    
	@BindNamedArgument("capex")
	@Prop(P.capex)
	@Doc("The capital cost function for the installed glazing.")
	public XNumber getCapex() {
		return capex;
	}
	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}

	@BindNamedArgument("light-transmittance")
	@Prop(P.lightTransmittance)
	@Doc("The light transmission factor for the installed window glazing.")
	public double getLightTransmittance() {
		return lightTransmittance;
	}

	public void setLightTransmittance(final double lightTransmittance) {
		this.lightTransmittance = lightTransmittance;
	}

	@BindNamedArgument("gains-transmittance")
	@Prop(P.gainsTransmittance)
	@Doc("The gains transmission factor for the installed glazing.")
	public double getGainsTransmittance() {
		return gainsTransmittance;
	}

	public void setGainsTransmittance(final double gainsTransmittance) {
		this.gainsTransmittance = gainsTransmittance;
	}

	@BindNamedArgument("frame-type")
	@Prop(P.frameType)
	@Doc("The frame type for the installed glazing.")
	@NotNull(message = "measure.install-glazing must specify a frame-type")
	public XFrameType getFrameType() {
		return frameType;
	}

	public void setFrameType(final XFrameType frameType) {
		this.frameType = frameType;
	}

	@BindNamedArgument("glazing-type")
	@Prop(P.glazingType)
	@Doc("The type of glazing to be installed (e.g. Single, Double, Triple, Secondary, etc.)")
	@NotNull(message = "measure.install-glazing must specify a glazing-type")
	public XGlazingType getGlazingType() {
		return glazingType;
	}

	public void setInsulationType(final XGlazingType insulationType) {
		this.setGlazingType(insulationType);
	}

	public void setGlazingType(final XGlazingType glazingType) {
		this.glazingType = glazingType;
	}

	@BindNamedArgument("frame-factor")
	@Prop(P.frameFactor)
	@Doc("The frame factor of the windows after this new glazing has been installed.")
	public double getFrameFactor() {
		return frameFactor;
	}
	
	public void setFrameFactor(final double frameFactor) {
		this.frameFactor = frameFactor;
	}

	@BindNamedArgument("insulation-type")
	@Prop(P.insulationType)
	@Doc("The type of insulation provided by this new window glazing.")
	@NotNull(message = "measure.install-glazing must specify an insulation-type")
	public XWindowInsulationType getInsulationType() {
		return insulationType;
	}

	public void setInsulationType(final XWindowInsulationType insulationType) {
		this.insulationType = insulationType;
	}

	@BindNamedArgument("insulation-type")
	@Prop(P.airGap)
	@Doc({ "The size of the air gap between glazings of this window (applies to double and triple glazing only).",
			"Default value is gapOf6mm" })
	public XWindowGlazingAirGap getGlazingAirGap() {
		return glazingAirGap;
	}

	public void setGlazingAirGap(XWindowGlazingAirGap glazingAirGap) {
		this.glazingAirGap = glazingAirGap;
	}
}
