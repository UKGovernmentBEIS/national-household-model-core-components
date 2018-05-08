package uk.org.cse.nhm.hom.structure;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;

public interface IGlazedElement {

    GlazingType getGlazingType();

    void setGlazingType(GlazingType type);

    double getLightTransmissionFactor();

    void setLightTransmissionFactor(double lightTransmissionFactor);

    double getGainsTransmissionFactor();

    void setGainsTransmissionFactor(double gainsTransmissionFactor);

    FrameType getFrameType();

    void setFrameType(FrameType frameType);

    double getFrameFactor();

    void setFrameFactor(double frameFactor);
}
