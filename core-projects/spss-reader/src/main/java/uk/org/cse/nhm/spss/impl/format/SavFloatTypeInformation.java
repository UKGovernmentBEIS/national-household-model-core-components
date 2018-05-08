package uk.org.cse.nhm.spss.impl.format;

import java.io.IOException;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.spss.impl.SavDataStream;

@AutoProperty
@SuppressWarnings("unused") // The fields are used by the @AutoProperty attribute.
public class SavFloatTypeInformation {

    private double systemMissing;
    private double maximum;
    private double minimum;

    public SavFloatTypeInformation(SavDataStream stream) throws IOException {
        final int dataElementLength = stream.readInt();
        assert dataElementLength == 8;
        final int dataElementCount = stream.readInt();
        assert dataElementCount == 3;

        systemMissing = stream.readDouble();
        maximum = stream.readDouble();
        minimum = stream.readDouble();
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
