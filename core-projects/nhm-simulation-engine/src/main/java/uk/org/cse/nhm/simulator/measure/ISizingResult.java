package uk.org.cse.nhm.simulator.measure;

public interface ISizingResult {

    public boolean isSuitable();

    public double getSize();

    public Units getUnits();
}
