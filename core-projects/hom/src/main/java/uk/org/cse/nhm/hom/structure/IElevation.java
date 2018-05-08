package uk.org.cse.nhm.hom.structure;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.structure.impl.Elevation.IDoorVisitor;

public interface IElevation {

    /**
     * Given a certain basic (unglazed) non-party wall area, visit the windows
     * from this elevation in that wall area and return the amount of area used
     * for windows in doing so
     *
     * @param visitor
     * @param basicArea the area (inclusive of any windows windows) of the wall
     * being considered
     * @return the area of windows visited
     */
    public abstract double visitGlazing(IEnergyCalculatorVisitor visitor, double wallArea, double doorArea);

    /**
     * @return A door visitor, which will visit doors within walls as long as
     * the wall has enough room to stick each door into it.
     */
    public abstract IDoorVisitor getDoorVisitor();

}
