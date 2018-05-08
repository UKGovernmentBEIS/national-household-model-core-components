/**
 *
 */
package uk.org.cse.stockimport.domain.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.hom.types.VentilationSystem;
import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.AbsDTO;

/**
 * @author glenns
 * @since 1.0
 */
@AutoProperty
public class VentilationDTO extends AbsDTO implements IVentilationDTO {

    int chimneysMainHeating;
    int chimneysSecondaryHeating;
    int chimneysOther;

    int intermittentFans;
    int passiveVents;

    double windowsAndDoorsDraughtStrippedProportion;

    VentilationSystem ventilationSystem;

    @Override
    public int getChimneysMainHeating() {
        return chimneysMainHeating;
    }

    @Override
    public void setChimneysMainHeating(final int chimneysMainHeating) {
        this.chimneysMainHeating = chimneysMainHeating;
    }

    @Override
    public int getChimneysSecondaryHeating() {
        return chimneysSecondaryHeating;
    }

    @Override
    public void setChimneysSecondaryHeating(final int chimneysSecondaryHeating) {
        this.chimneysSecondaryHeating = chimneysSecondaryHeating;
    }

    @Override
    public int getChimneysOther() {
        return chimneysOther;
    }

    @Override
    public void setChimneysOther(final int chimneysOther) {
        this.chimneysOther = chimneysOther;
    }

    @Override
    public int getIntermittentFans() {
        return intermittentFans;
    }

    @Override
    public void setIntermittentFans(final int intermittentFans) {
        this.intermittentFans = intermittentFans;
    }

    @Override
    public int getPassiveVents() {
        return passiveVents;
    }

    @Override
    public void setPassiveVents(final int passiveVents) {
        this.passiveVents = passiveVents;
    }

    @Override
    public double getWindowsAndDoorsDraughtStrippedProportion() {
        return windowsAndDoorsDraughtStrippedProportion;
    }

    @Override
    public void setWindowsAndDoorsDraughtStrippedProportion(
            final double windowsAndDoorsDraughtStrippedProportion) {
        this.windowsAndDoorsDraughtStrippedProportion = windowsAndDoorsDraughtStrippedProportion;
    }

    @Override
    public VentilationSystem getVentilationSystem() {
        return ventilationSystem;
    }

    @Override
    public void setVentilationSystem(final VentilationSystem ventilationSystem) {
        this.ventilationSystem = ventilationSystem;
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }

    @Override
    public boolean equals(final Object other) {
        return Pojomatic.equals(this, other);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
