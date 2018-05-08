package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import uk.org.cse.nhm.ehcs10.physical.ChimneyEntry;
import uk.org.cse.nhm.ehcs10.physical.InteriorEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.ChimneyEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.InteriorEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.hom.types.VentilationSystem;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.IVentilationDTO;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.VentilationDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * SpssVentilationReader Builds a VentilationDTO for each unique AACode in the
 * data set.
 *
 * @author glenns
 * @since 1.0
 */
public class SpssVentilationReader extends AbsSpssReader<IVentilationDTO> {

    private static final Logger logger = LoggerFactory.getLogger(SpssVentilationReader.class);

    /**
     * @since 1.0
     */
    public SpssVentilationReader(final String executionId, final IHouseCaseSourcesRepositoryFactory houseSourcesFactory) {
        super(executionId, houseSourcesFactory);
    }

    @Override
    protected Set<Class<?>> getSurveyEntryClasses() {
        return ImmutableSet.<Class<?>>of(HouseCaseDTO.class, ISpaceHeatingDTO.class, InteriorEntryImpl.class, ChimneyEntryImpl.class, ServicesEntryImpl.class, ElevationDTO.class);
    }

    @Override
    public List<IVentilationDTO> read(final IHouseCaseSources<Object> provider) {

        final String aaCode = provider.getAacode();

        if (logger.isDebugEnabled()) {
            logger.debug("building ventilation dto for: " + aaCode);
        }

        final List<IVentilationDTO> results = new ArrayList<IVentilationDTO>();
        final VentilationDTO dto = new VentilationDTO();
        dto.setAacode(aaCode);
        final Collection<? extends ChimneyEntry> houseChimneys = provider.getAll(ChimneyEntry.class);
        final ServicesEntryImpl services = provider.requireOne(ServicesEntryImpl.class);
        final HouseCaseDTO houseCase = provider.requireOne(HouseCaseDTO.class);
        final ISpaceHeatingDTO spaceHeating = provider.requireOne(ISpaceHeatingDTO.class);

        int totalChimneys = getTotalChimneys(houseChimneys);

        if (totalChimneys > 0 && chimneyIsActuallyAFlue(spaceHeating, houseCase, services)) {
            totalChimneys -= 1;
        }

        dto.setChimneysMainHeating(totalChimneys);
        dto.setChimneysSecondaryHeating(0);
        dto.setChimneysOther(0);

        final InteriorEntry interior = provider.requireOne(InteriorEntry.class);
        dto.setIntermittentFans(calculateIntermittentFans(houseCase, interior));

        dto.setPassiveVents(getNumberOfPassiveVents());

        dto.setWindowsAndDoorsDraughtStrippedProportion((calculateDraughtStrippingProportion(provider.getAll(IElevationDTO.class))));

        dto.setVentilationSystem(determineVentilationSystem());

        results.add(dto);
        return results;
    }

    private final Set<SecondaryHeatingSystemType> gasFireWithFlue = Sets.newHashSet(new SecondaryHeatingSystemType[]{
        SecondaryHeatingSystemType.GAS_FIRE,
        SecondaryHeatingSystemType.GAS_FIRE_OPEN_FLUE,
        SecondaryHeatingSystemType.GAS_COAL_EFFECT_FIRE});

    /**
     * <p>
     * CHM specify that a chimney is actually a flue if the secondary heating
     * system is a gas fire which has been introduced since 1975. 2010 - 1975 =
     * age 35
     * </p>
     *
     * @assumption 1 chimney may be replaced by a flue if the secondary heating
     * system is a gas fire installed after 1975 according to the CAR conversion
     * document. We have extended this to also apply to a secondary gas fire of
     * unknown age in a house which was built after 1975.
     *
     * @param spaceHeating
     * @return
     * @since 1.0
     */
    public boolean chimneyIsActuallyAFlue(final ISpaceHeatingDTO spaceHeating, final IHouseCaseDTO house, final ServicesEntryImpl services) {
        if (gasFireWithFlue.contains(spaceHeating.getSecondaryHeatingSystemType())) {
            try {
                return services.getOtherHeating_Age() <= 35;

            } catch (final NullPointerException ex) {
                try {
                    return house.getBuildYear() >= 1975;
                } catch (final NullPointerException inner) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Determines the total number of chimneys in the survey for the house. Does
     * this by summing up front and back chimney numbers across all types of
     * chimney.
     * </p>
     *
     * @assumption A house can never have more than 1 chimney, as specified by
     * the CAR conversion document.
     *
     * @param houseChimneys
     * @return
     * @since 1.0
     */
    public int getTotalChimneys(final Collection<? extends ChimneyEntry> houseChimneys) {
        int result = 0;
        for (final ChimneyEntry entry : houseChimneys) {
            if (entry == null) {
                logger.error("Null chimney");
            } else {
                final Optional<Integer> front = Optional.fromNullable(entry.getFRONT_Number());
                final Optional<Integer> back = Optional.fromNullable(entry.getBACK_Number());
                result += front.or(0) + back.or(0);
            }
        }
        return Math.min(result, 1);
    }

    /**
     * @assumption CAR specify that properties have 0 passive vents.
     *
     * @return The number of passive vents at the property.
     * @since 1.0
     */
    public int getNumberOfPassiveVents() {
        return 0;
    }

    /**
     * @assumption CAR specify that properties always use Natural Ventilation.
     *
     * @return the VentilationSystem of the property.
     * @since 1.0
     */
    public VentilationSystem determineVentilationSystem() {
        return VentilationSystem.Natural;
    }

    /**
     * @assumption Using the CAR methodology, draught stripping equal to the
     * proportion of double glazing.
     *
     * @return
     * @since 1.0
     */
    public double calculateDraughtStrippingProportion(final List<IElevationDTO> elevations) {
        double pdg = 0;

        for (final IElevationDTO e : elevations) {
            pdg += e.getPercentageWindowDblGlazed();
        }

        return (pdg / elevations.size()) / 100d;
    }

    /**
     * <p>
     * Calculates the number of fans based on the construction date (converted
     * to an SAP band) and the number of habitable rooms. Where there are
     * multiple SAP bands that can apply, we use the one which overlaps our date
     * range most.
     * </p>
     *
     * <p>
     * Taken from SAP 2005 RDSAP table S5:
     * </p>
     *
     *
     * <table>
     * <tr>
     * Age bands A to E
     * <td></td>
     * <td>0 fans</td>
     * </tr>
     * <tr>
     * Age bands F to G
     * <td></td>
     * <td>1 fan</td>
     * </tr>
     * <tr>
     * Age bands H to K with habitable rooms <= 2 <td></td> <td>1 fan</td>
     * </tr>
     * <tr>
     * Age bands H to K with 3 <= habitable rooms <= 5 <td></td> <td>2 fans</td>
     * </tr>
     * <tr>
     * Age bands H to K with 6 <= habitable rooms <= 8 <td></td> <td>3 fans</td>
     * </tr>
     * <tr>
     * Age bands H to K with 9 <= habitable rooms <td></td> <td>4 fans</td>
     * </tr>
     * </table>
     *
     * @assumption The number of intermittent fans is calculated based on the
     * house age band and the number of habitable rooms as specified by SAP 2005
     * RDSAP table S5.
     *
     * @param houseCase
     * @param interior
     * @return
     * @since 1.0
     */
    public int calculateIntermittentFans(final HouseCaseDTO houseCase, final InteriorEntry interior) {
        final SAPAgeBandValue ageBand = SAPAgeBandValue.fromYear(houseCase.getBuildYear(), houseCase.getRegionType());

        switch (ageBand.getName()) {
            case A:
            case B:
            case C:
            case D:
            case E:
                return 0;
            case F:
            case G:
                return 1;
            case H:
            case I:
            case J:
            case K:
            case L:
                final int rooms = interior.getNumberHabitableRooms();
                return rooms <= 2 ? 1 : rooms > 2 && rooms <= 5 ? 2 : rooms > 5 && rooms <= 8 ? 3 : 4; // rooms
            // >

            default:
                throw new RuntimeException("Lookup against a non-existent SAP age band. If new SAP age bands have been created, add a new case to cover them.");
        }
    }

    @Override
    protected Class<?> readClass() {
        return VentilationDTO.class;
    }

}
