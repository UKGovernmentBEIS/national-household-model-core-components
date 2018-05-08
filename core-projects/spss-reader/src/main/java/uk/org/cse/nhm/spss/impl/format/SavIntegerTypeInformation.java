package uk.org.cse.nhm.spss.impl.format;

import java.io.IOException;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.spss.impl.SavDataStream;

@AutoProperty
@SuppressWarnings("unused") // The fields are used by the @AutoProperty attribute.
public class SavIntegerTypeInformation {

    private int characterRep;
    private int endianness;
    private int compressionScheme;
    private int floatRep;
    private int machineCode;
    private int releaseSpecial;
    private int releaseMinor;
    private int releaseMajor;

    public SavIntegerTypeInformation(final SavDataStream stream) throws IOException {
        final int dataElementLength = stream.readInt();
        assert dataElementLength == 4;

        final int numberOfElements = stream.readInt();
        assert numberOfElements == 8;

        releaseMajor = stream.readInt();
        releaseMinor = stream.readInt();
        releaseSpecial = stream.readInt();
        machineCode = stream.readInt();
        floatRep = stream.readInt();
        compressionScheme = stream.readInt();
        endianness = stream.readInt();
        characterRep = stream.readInt();
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
