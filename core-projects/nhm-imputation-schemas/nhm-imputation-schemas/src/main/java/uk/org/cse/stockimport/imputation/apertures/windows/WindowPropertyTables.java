package uk.org.cse.stockimport.imputation.apertures.windows;

public class WindowPropertyTables implements IWindowPropertyTables {

    private IWindowFrameFactor frameFactors = new FrameFactors();
    private IWindowUValues uValues = new UValues(false);
    private ITransmittanceFactors transmittanceFactors = new TransmittanceFactors(false);

    @Override
    public IWindowUValues getUValues() {
        return uValues;
    }

    @Override
    public IWindowFrameFactor getFrameFactors() {
        return frameFactors;
    }

    @Override
    public ITransmittanceFactors getTransmittanceFactors() {
        return transmittanceFactors;
    }
}
