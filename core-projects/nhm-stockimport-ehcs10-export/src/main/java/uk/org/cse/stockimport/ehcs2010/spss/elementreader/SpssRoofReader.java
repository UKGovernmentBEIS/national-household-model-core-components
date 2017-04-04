package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.Physical_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum110;
import uk.org.cse.nhm.ehcs10.derived.types.Enum131;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1757;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.CoveringType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofStructureType;
import uk.org.cse.stockimport.domain.geometry.IRoofDTO;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.impl.RoofDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.util.DefaultLookup;
import uk.org.cse.stockimport.util.ILookup;

/**
 * SpssRoofReader.
 * 
 * @author richardt
 * @version $Id: SpssRoofReader.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class SpssRoofReader extends AbsSpssReader<IRoofDTO> {

	private static final Logger logger = LoggerFactory.getLogger(SpssRoofReader.class);

	/**
	 * A lookup from loft insulation thickness codings in the EHS, to thickness
	 * values in mm.
	 */
	private static final Map<Enum1757, Double> loftInsulationThicknessLookup =
        ImmutableMap.<Enum1757, Double> builder()
            .put(Enum1757._25Mm, 25d)
            .put(Enum1757._50Mm, 50d)
			.put(Enum1757._75Mm, 75d)
            .put(Enum1757._100Mm, 100d)
            .put(Enum1757._125Mm, 125d)
            .put(Enum1757._150Mm, 150d)
            .put(Enum1757._200Mm, 200d)
            .put(Enum1757._250Mm, 250d)
			.put(Enum1757._300Mm, 300d)
            .put(Enum1757.GreaterThan300mm, 350d)
        .build();

	/**
	 * A lookup from {@link Enum110} (EHS covering type) to {@link CoveringType}
	 */
	private static final ILookup<Enum110, CoveringType> coveringTypeLookup = DefaultLookup.of(
			ImmutableMap.<Enum110, CoveringType> builder().put(Enum110.ManMadeSlate, CoveringType.Slates).put(Enum110.Asphalt, CoveringType.Asphalt)
					.put(Enum110.Felt, CoveringType.Felt).put(Enum110.Thatch, CoveringType.Thatch).put(Enum110.ConcreteTile, CoveringType.Tile)
					.put(Enum110.NaturalSlate_Stone_Shingle, CoveringType.Slates).put(Enum110.ClayTile, CoveringType.Tile).put(Enum110.Glass_Metal_Laminate, CoveringType.Metal)
					.build(), CoveringType.Tile);

	/**
	 * A lookup from {@link Enum131}, EHS roof structure type to
	 * {@link RoofStructureType}
	 */
	private static final ILookup<Enum131, RoofStructureType> roofStructureTypeLookup = DefaultLookup.of(
			ImmutableMap.<Enum131, RoofStructureType> builder().put(Enum131.Flat, RoofStructureType.Flat).put(Enum131.Pitched, RoofStructureType.Pitched)
					.put(Enum131.Chalet, RoofStructureType.Chalet).put(Enum131.Mansard, RoofStructureType.Mandard).build(), RoofStructureType.Flat);

	/**
	 * Get loft insulation thickness from survey code, using
	 * {@link #loftInsulationThicknessLookup}
	 * 
	 * @assumption Loft insulation "Greater than 300mm" is mapped to 350mm; this
	 *             will be fine for u-value lookups but may have a consequence
	 *             for the application of loft insulation in the simulation (as
	 *             that tops-up from the current value to a desired value)
	 * @param survey
	 * @return
	 */
	private Optional<Double> getLoftInsulationThickness(final Enum1757 survey) {
		if (loftInsulationThicknessLookup.containsKey(survey)) {
			return Optional.of(loftInsulationThicknessLookup.get(survey));
		} else {
			return Optional.absent();
		}
	}

	@Override
	protected Set<Class<?>> getSurveyEntryClasses() {
		return ImmutableSet.of(Physical_09Plus10EntryImpl.class, ServicesEntryImpl.class, IStoreyDTO.class);
	}

	/**
	 * Default Constructor.
	 * 
	 * @since 1.0
	 */
	public SpssRoofReader(final String executionId, final IHouseCaseSourcesRepositoryFactory providerFactory) {
		super(executionId, providerFactory);
	}

	@Override
	public List<IRoofDTO> read(final IHouseCaseSources<Object> provider) {
		if (logger.isDebugEnabled()) {
			logger.debug("building case: " + provider.getAacode());
		}

		final List<IRoofDTO> roofs = new ArrayList<IRoofDTO>();

		final Physical_09Plus10Entry physicalProps = provider.requireOne(Physical_09Plus10Entry.class);
		final ServicesEntry services = provider.requireOne(ServicesEntry.class);

		final RoofDTO roof = new RoofDTO();
		roof.setAacode(provider.getAacode());

		addInsulationAndConstructionDetails(roof, physicalProps, services);

		roofs.add(roof);

		return roofs;
	}

	/**
	 * <p>
	 * According to the CHM conversion document, we can use
	 * <code>derived/physical/typercov</code>,
	 * <code>derived/physical/typerstr</code> and <code>services/flithick</code>
	 * <p>
	 * 
	 * @assumption When roof covering type is unknown, default to tile.
	 * @assumption If there is no date on roof structure type or it is mixed,
	 *             default to a flat roof.
	 * 
	 * @issue 1
	 * @since 1.0.1
	 * @param roof
	 * @param services
	 * @param physicalProps
	 */
	private void addInsulationAndConstructionDetails(final RoofDTO roof, final Physical_09Plus10Entry physicalProps, final ServicesEntry services) {
		final Optional<Double> thickness = getLoftInsulationThickness(services.getLoft_ApproxThickness());

		roof.setInsulationThickness(thickness);

		final Enum110 predominantTypeOfRoofCovering = physicalProps.getPredominantTypeOfRoofCovering();
		final Enum131 predominantTypeOfRoofStucture = physicalProps.getPredominantTypeOfRoofStucture();

		roof.setRoofType(getRoofConstructionType(predominantTypeOfRoofCovering, predominantTypeOfRoofStucture));
		roof.setStructureType(roofStructureTypeLookup.get(predominantTypeOfRoofStucture));
		roof.setCoveringType(coveringTypeLookup.get(predominantTypeOfRoofCovering));
	}

	/**
	 * Determine roof construction type from the two variables specified in the
	 * CHM document. This lookup was reverse-engineered from the values in the
	 * survey and the values in the CHM spreadsheet, as the CHM document does
	 * not specify it. The logic of this method follows from looking at all
	 * distinct combinations present in the survey and spreadsheet. See the test
	 * case for detail of these.
	 * 
	 * @param predominantTypeOfRoofCovering
	 * @param predominantTypeOfRoofStucture
	 * @return
	 */
	protected RoofConstructionType getRoofConstructionType(final Enum110 covering, final Enum131 structure) {
		final RoofConstructionType result;
		if (covering == Enum110.Thatch) {
			// whenever covering is thatch, the CHM uses thatched
			result = RoofConstructionType.Thatched;
		} else {
			// in all other cases, the type is determined by the structure
			// variable
			// and this is always mapped to pitched slate or tiles except for
			// the case of flat.
			switch (structure) {
			case Chalet:
			case Mansard:
			case Pitched:
			case MixedTypes:
			default:
				result = RoofConstructionType.PitchedSlateOrTiles;
				break;
			case Flat:
				result = RoofConstructionType.Flat;
				break;
			}
		}
		return result;
	}

	/**
	 * @return
	 * @see uk.org.cse.stockimport.ehcs2010.spss.elementreader.AbsSpssReader#readClass()
	 */
	@Override
	protected Class<?> readClass() {
		return IRoofDTO.class;
	}
}
