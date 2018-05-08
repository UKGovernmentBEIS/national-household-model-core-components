package uk.org.cse.nhm.hom.emf.util;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;

/**
 * Describes operations on an {@link ITechnologyModel}, for most standard
 * measures to use.
 *
 * @author hinton
 *
 */
public interface ITechnologyOperations {

    /**
     * This will find anything which implements IHasInstallationYear in the
     * technology model whose installation year is unset, and set it to the
     * given year.
     *
     * As a result, if you install some new things in a house you can call this
     * and they will end up with the right installation year.
     *
     * @param technologies
     * @return
     */
    public boolean setInstallationYears(final ITechnologyModel technologies, final int year);

    public boolean hasCommunitySpaceHeating(final ITechnologyModel technologies);

    public boolean hasCommunityWaterHeating(final ITechnologyModel technologies);

    /**
     * Install a new heat source into the technology model, and connect it up to
     * the space & water heating systems as suggested. Will also remove any
     * defunct heat source or heating system in the process.
     *
     * @param technologies
     * @param newHeatSource
     * @param forSpace
     * @param forWater
     * @param cylinderVolume
     * @param cylinderInsulationThickness
     */
    public void installHeatSource(final ITechnologyModel technologies, final IHeatSource newHeatSource, final boolean forSpace, final boolean forWater, EmitterType emitterType, Set<HeatingSystemControlType> heatingControls, double cylinderInsulationThickness, double cylinderVolume);

    /**
     * Install a heat source just for space heating
     *
     * @param technologies
     * @param heatSource
     * @param emitterType
     * @param controls
     */
    public void installHeatSource(final ITechnologyModel technologies, final IHeatSource heatSource, final EmitterType emitterType, final Set<HeatingSystemControlType> controls);

    /**
     * Install a heat source just for water heating.
     *
     * @param technologies
     * @param heatSource
     * @param cylinderInsulation
     * @param cylinderVolume
     */
    public void installHeatSource(final ITechnologyModel technologies, final IHeatSource heatSource, double cylinderInsulation, double cylinderVolume);

    /**
     * Install a new water tank into the
     *
     * @param model
     * @param retainExisting
     * @return
     */
    public IWaterTank installWaterTank(final ICentralWaterSystem model,
            final boolean retainExisting,
            final double insulationThickness,
            final double cylinderVolume
    );

    void replacePrimarySpaceHeater(ITechnologyModel technologies, IPrimarySpaceHeater newHeater);

    void removePrimarySpaceHeater(ITechnologyModel technologies);

    void removeHeatSource(IHeatSource heatSource);

    ICentralHeatingSystem getOrInstallCentralHeating(ITechnologyModel technologies);

    boolean changeHeatingEfficiency(ITechnologyModel technologies, final double winterTarget, final double summerTarget, final boolean up, final boolean down);

    Optional<IBoiler> getBoiler(ITechnologyModel technologies);

    double getSAPSecondaryHeatingProportion(ITechnologyModel modifiable);

    /**
     * @return The fuel type of the primary heating system if one is present and
     * working, otherwise the fuel type of the secondary heating system..
     */
    FuelType getMainHeatingFuel(ITechnologyModel technologies);
}
