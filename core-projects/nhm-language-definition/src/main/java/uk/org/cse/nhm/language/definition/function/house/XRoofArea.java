package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Bind("house.roof-area")
@Doc("The area of the roof of the house, in square meters.")
public class XRoofArea extends XHouseNumber {

    public static final class P {

        public static final String pitchCorrection = "pitchCorrection";
    }

    private boolean pitchCorrection = false;

    @Prop(P.pitchCorrection)
    @BindNamedArgument("pitch-correction")
    @Doc({
        "If this is set to true, the reported roof area for will be increased for dwellings with PitchedSlateOrTiles or Thatched roofs.",
        "This is done by dividing the roof area by cos(35°).",
        "Useful for calculating the potential roof area for photovoltaics: see SAP 2012 section S11.1."
    })
    public boolean getPitchCorrection() {
        return pitchCorrection;
    }

    public void setPitchCorrection(final boolean pitchCorrection) {
        this.pitchCorrection = pitchCorrection;
    }
}
