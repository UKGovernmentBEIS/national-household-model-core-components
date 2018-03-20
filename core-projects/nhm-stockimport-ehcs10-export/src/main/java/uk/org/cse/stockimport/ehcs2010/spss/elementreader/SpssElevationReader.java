package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.getTenthsAttached;
import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.getTenthsOpening;
import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.getTenthsPartyWall;
import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.isHouse;

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
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.derived.types.Enum103;
import uk.org.cse.nhm.ehcs10.derived.types.Enum124;
import uk.org.cse.nhm.ehcs10.derived.types.Enum135;
import uk.org.cse.nhm.ehcs10.derived.types.Enum146;
import uk.org.cse.nhm.ehcs10.physical.DoorsEntry;
import uk.org.cse.nhm.ehcs10.physical.ElevateEntry;
import uk.org.cse.nhm.ehcs10.physical.FlatdetsEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.DoorsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ElevateEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.FlatdetsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1289;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * SpssElevationReader.
 * 
 * @author richardt
 * @version $Id: SpssElevationReader.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class SpssElevationReader extends AbsSpssReader<IElevationDTO> {

	private static final Logger logger = LoggerFactory.getLogger(SpssElevationReader.class);

	/** @since 1.0 */
	public static final double _100_PERCENT = 100.00;
	/** @since 1.0 */
	public static final double _75_PERCENT = 75.00;
	/** @since 1.0 */
	public static final double _25_PERCENT = 25.00;
	/** @since 1.0 */
	public static final double _0_PERCENT = 0.00;

	/**
	 * Used to convert from {@link Enum103} windows frame to NHM friendly
	 * {@link FrameType}
	 */
	protected static final Map<Enum103, FrameType> winFrameTypeConversion = ImmutableMap.<Enum103, FrameType> builder().put(Enum103.Double_Glazed_Metal, FrameType.Metal)
			.put(Enum103.Double_Glazed_UPVC, FrameType.uPVC).put(Enum103.Double_Glazed_Wood, FrameType.Wood).put(Enum103.Single_Glazed_Metal, FrameType.Metal)
			.put(Enum103.Single_Glazed_UPVC, FrameType.uPVC).put(Enum103.Single_Glazed_WoodCasement, FrameType.Wood).put(Enum103.Single_Glazed_WoodSash, FrameType.Wood)
			.put(Enum103.MixedTypes, FrameType.Wood).build();

	/**
	 * Used to convert door frame types from SPSS {@link Enum1289} to
	 * {@link FrameType}
	 **/
	protected static final Map<Enum1289, FrameType> frameTypeConversion = ImmutableMap.<Enum1289, FrameType> builder().put(Enum1289.Metal, FrameType.Metal)
			.put(Enum1289.UPVC, FrameType.uPVC).put(Enum1289.Wood, FrameType.Wood).build();

	/**
	 * Used to convert wall construction types from SPSS {@link Enum146} to
	 * {@link WallConstructionType}
	 */
	protected static Map<Enum146, WallConstructionType> wallTypeConversion = ImmutableMap.<Enum146, WallConstructionType> builder()
			.put(Enum146.GreaterThan9InchSolid, WallConstructionType.Sandstone).put(Enum146._9InchSolid, WallConstructionType.GraniteOrWhinstone)
			.put(Enum146.MasonryCavity, WallConstructionType.Cavity).put(Enum146.MasonrySingleLeaf, WallConstructionType.SolidBrick)
			.put(Enum146.TimberPanels, WallConstructionType.TimberFrame).put(Enum146.ConcretePanels, WallConstructionType.SystemBuild)
			.put(Enum146.InSituConcrete, WallConstructionType.SystemBuild).put(Enum146.MetalSheet, WallConstructionType.SystemBuild)
			// TODO ASSUMPTION this is bogus and requires something less
			// entirely spurious
			.put(Enum146.MixedTypes, WallConstructionType.Cavity).build();

	protected static final Map<Enum10, Boolean> installedInsulation = ImmutableMap.<Enum10, Boolean> builder().put(Enum10.Yes, true).put(Enum10.No, false)
			.put(Enum10.__MISSING, false).build();

	/**
	 * Default Constructor.
	 * 
	 * @since 1.0
	 */
	public SpssElevationReader(final String executionId, final IHouseCaseSourcesRepositoryFactory mongoProviderFactory) {
		super(executionId, mongoProviderFactory);
	}

	@Override
	public List<IElevationDTO> read(final IHouseCaseSources<Object> provider) {
		return buildElevationsForHouse(provider);
	}

	@Override
	protected Set<Class<?>> getSurveyEntryClasses() {
		return ImmutableSet.<Class<?>> of(Physical_09Plus10EntryImpl.class, ElevateEntryImpl.class, FlatdetsEntryImpl.class, DoorsEntryImpl.class);
	}

	protected List<IElevationDTO> buildElevationsForHouse(final IHouseCaseSources<Object> provider) {

		final List<IElevationDTO> elevations = new ArrayList<IElevationDTO>();

		final Physical_09Plus10Entry physicalPropEntry = provider.requireOne(Physical_09Plus10Entry.class);

		final ElevateEntry elevateEntry = provider.requireOne(ElevateEntry.class);

		final FlatdetsEntry flatDetsEntry = provider.requireOne(FlatdetsEntry.class);

		final List<DoorsEntry> caseDoorEntries = provider.getAll(DoorsEntry.class);

		final boolean isHouse = isHouse(physicalPropEntry);

		IElevationDTO elevation;
		for (final ElevationType elevationType : ElevationType.values()) {
			elevation = new ElevationDTO();
			elevation.setAacode(provider.getAacode());
			addElevationData(elevation, elevationType, elevateEntry, flatDetsEntry, isHouse);
			addWallConstructionData(elevation, physicalPropEntry.getPredominantTypeOfWallStucture(), elevateEntry);
			addDoorConstructionData(elevation, caseDoorEntries);
			addWindowConstructionData(elevation, physicalPropEntry);

			elevations.add(elevation);
		}

		return elevations;
	}

	/**
	 * 1. Get's the extent of single and double glazing from
	 * {@link Physical_09Plus10Entry#getExtentOfDoubleGlazing_DBLGLAZ2()} and
	 * {@link Physical_09Plus10Entry#getExtentOfDoubleGlazing()}.<br/>
	 * <br/>
	 * 
	 * 2. Calculates the percentage double glazed using
	 * {@link SpssElevationReader#calculateGlazingPercentage(Enum135, Enum124)}<br/>
	 * <br/>
	 * 
	 * 3. Sets the frame type of single and double glazed using
	 * {@link Physical_09Plus10Entry#getPredominantTypeOfWindow()}, if it's
	 * greater than )% double glazed only sets the frame type of double glazing,
	 * if it's less than 100% glazed sets single glazed type <br/>
	 * <br/>
	 * 
	 * 4. Calls {@link IElevationDTO#buildWindows(double, FrameType, FrameType)}
	 * to construct windows
	 * 
	 * @param dto
	 * @param physicalProps
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void addWindowConstructionData(final IElevationDTO dto, final Physical_09Plus10Entry physicalProps) {

		// Percentage Double Glazed
		final Enum135 doubleGlazingExtent2 = physicalProps.getExtentOfDoubleGlazing_DBLGLAZ2();
		final Enum124 doubleGlazingExtent1 = physicalProps.getExtentOfDoubleGlazing();
		final double percentDoubleGlazed = calculateGlazingPercentage(doubleGlazingExtent2, doubleGlazingExtent1);

		// FrameType
		final FrameType predominantFrameType = winFrameTypeConversion.get(physicalProps.getPredominantTypeOfWindow());
		FrameType doubleGlazedFrameType = null;
		FrameType singleGlazed = null;

		if (percentDoubleGlazed > _0_PERCENT) {
			doubleGlazedFrameType = predominantFrameType;
		}
		if (percentDoubleGlazed < _100_PERCENT) {
			singleGlazed = predominantFrameType;
		}

		dto.setPercentageWindowDblGlazed(percentDoubleGlazed);
		dto.setSingleGlazedWindowFrame(Optional.fromNullable(singleGlazed));
		dto.setDoubleGlazedWindowFrame(Optional.fromNullable(doubleGlazedFrameType));
	}

	/**
	 * <p>
	 * CHM 2010 conversion doc details that it uses these two enums to determine
	 * glazed window types, however it doens't mention how it uses them.
	 * </p>
	 * <p>
	 * Assumption:STOCK_IMPORT:003: If Enum135 is null or missing then use
	 * Enum124, otherwise use Enum135 for glazing percentage.
	 * </p>
	 * <p>
	 * Mappings for percentage extent {@link Enum124}:
	 * <ul>
	 * <li>{@link Enum124#EntireHouse} = 100.00</li>
	 * <li>{@link Enum124#MoreThanHalf} = 75.00</li>
	 * <li>
	 * {@link Enum124#LessThanHalf} = 25.00</li>
	 * <li>{@link Enum124#NoDoubleGlazing} = 25.00</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Mappings for percentage extent {@link Enum135}:
	 * <ul>
	 * <li>{@link Enum135#_80_OrMoreDoubleGlazed} = 100.00</li>
	 * <li>
	 * {@link Enum135#LessThan80_DoubleGlazed} = 0.00</li>
	 * </ul>
	 * </p>
	 * 
	 * @param doubleGlazingExtent2
	 * @param doubleGlazingExtent1
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	protected double calculateGlazingPercentage(final Enum135 doubleGlazingExtent2, final Enum124 doubleGlazingExtent1) {
		double percentDoubleGlazed = 0.0;

		if (doubleGlazingExtent1 != null && !doubleGlazingExtent1.equals(Enum124.__MISSING)) {
			switch (doubleGlazingExtent1) {
			case EntireHouse:
				percentDoubleGlazed = _100_PERCENT;
				break;
			case MoreThanHalf:
				percentDoubleGlazed = _75_PERCENT;
				break;
			case LessThanHalf:
				percentDoubleGlazed = _25_PERCENT;
				break;
			case NoDoubleGlazing:
			default:
				percentDoubleGlazed = _0_PERCENT;
				break;
			}
		} else if (doubleGlazingExtent2 != null && !doubleGlazingExtent2.equals(Enum135.__MISSING)) {
			switch (doubleGlazingExtent2) {
			case LessThan80_DoubleGlazed:
				percentDoubleGlazed = _0_PERCENT;
				break;
			default:
				percentDoubleGlazed = _100_PERCENT;
				break;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("calculateGlazingPercentage(){" + ", extent1=" + doubleGlazingExtent1 + ", extent2=" + doubleGlazingExtent2 + ", percentDblGlzd=" + percentDoubleGlazed
					+ "}");
		}

		return percentDoubleGlazed;
	}

	/**
	 * <p>
	 * Builds doors for elevation, currently only builds doors for front and
	 * back elevations as per CHM conversion. We don't know about door
	 * construction type so this defaults to solid for every door.
	 * </p>
	 * <p>
	 * <b>Assumes that elevation type has been set in DTO</b>
	 * </p>
	 * 
	 * 1. Gets the number of doors for the elevation using
	 * {@link DoorsEntry#getFRONT_Number()} etc...<br/>
	 * <br/>
	 * 
	 * 2. Calls {@link IElevationDTO#buildDoors(FrameType, int)} for the
	 * elevation
	 * 
	 * @param dto
	 * @param caseDoorEntries
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void addDoorConstructionData(final IElevationDTO dto, final List<DoorsEntry> caseDoorEntries) {
		int numOfDoors = 0;

		for (final DoorsEntry doorsEntryImpl : caseDoorEntries) {
			final FrameType frameType = frameTypeConversion.get(doorsEntryImpl.getTypeOfDoor());
			switch (dto.getElevationType()) {
			case FRONT:
				numOfDoors = doorsEntryImpl.getFRONT_Number();
				break;
			case BACK:
				numOfDoors = doorsEntryImpl.getBACK_Number();
				break;
			default:
				// Do nothing, we do not handle doors on other elevations
				break;
			}

			dto.addDoors(frameType, numOfDoors);
		}
	}

	/**
	 * <p>
	 * Calls the following methods :-
	 * <ul>
	 * <li>
	 * {@link SpssElevationReader#buildExternalWall(IElevationDTO, Enum146, ElevateEntry)}
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param dto
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void addWallConstructionData(final IElevationDTO dto, final Enum146 predominentWallStructure, final ElevateEntry elevationEntry) {
		buildExternalWall(dto, predominentWallStructure, elevationEntry);
	}

	/**
	 * 1. Gets the elevation type from {@link IElevationDTO}, then uses this to
	 * find out whether the elevation has external and internal wall insulation
	 * using {@link ElevateEntry#getFRONTFACE_CavityWallInsulation()}
	 * {@link ElevateEntry#getFRONTFACE_ExternalInsulation()} etc.. for the
	 * current elevation. If the value is missing for these sets it to
	 * {@link Enum10}.<br/>
	 * <br/>
	 * 
	 * 2. Uses {@link SpssElevationReader#installedInsulation} to return whether
	 * insulation is installed for type.<br/>
	 * <br/>
	 * 
	 * 3. Sets the wall construction type using {@link Enum146} and
	 * {@link SpssElevationReader#wallTypeConversion}.<br/>
	 * <br/>
	 * 
	 * 4. Calls
	 * {@link IElevationDTO#buildExternalWallData(WallConstructionType, Boolean, Boolean, Boolean)}
	 * .
	 * 
	 * @param dto
	 * @param predominentWallStructure
	 * @param elevationEntry
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void buildExternalWall(final IElevationDTO dto, final Enum146 predominentWallStructure, final ElevateEntry elevationEntry) {
		if (logger.isDebugEnabled()) {
			logger.debug("buildExternalWall() aacode:{}, elevation:" + dto.getElevationType(), dto.getAacode());
		}

		if (dto.getTenthsAttached() == 10) {
			// don't setup external wall data if wall is attached completely
			dto.setExternalWallConstructionType(Optional.<WallConstructionType>absent());
			dto.setInternalInsulation(Optional.<Boolean>absent());
			dto.setExternalInsulation(Optional.<Boolean>absent());
			dto.setCavityInsulation(Optional.<Boolean>absent());
			return;
		}
		
		WallConstructionType wallType = null;

		boolean hasCavityWallInsulation = false;
		boolean hasExternalWallInsulation = false;

		Enum10 cavityWallInsulation = Enum10.__MISSING;
		Enum10 externallWallInsulation = Enum10.__MISSING;

		switch (dto.getElevationType()) {
		case FRONT:
			cavityWallInsulation = elevationEntry.getFRONTFACE_CavityWallInsulation();
			externallWallInsulation = elevationEntry.getFRONTFACE_ExternalInsulation();
			break;
		case BACK:
			cavityWallInsulation = elevationEntry.getBACKFACE_CavityWallInsulation();
			externallWallInsulation = elevationEntry.getBACKFACE_ExternalInsulation();
			break;
		case LEFT:
			cavityWallInsulation = elevationEntry.getLEFTFACE_CavityWallInsulation();
			externallWallInsulation = elevationEntry.getLEFTFACE_ExternalInsulation();
			break;
		case RIGHT:
			cavityWallInsulation = elevationEntry.getRIGHTFACE_CavityWallInsulation();
			externallWallInsulation = elevationEntry.getRIGHTFACE_ExternalInsulation();
			break;
		default:
			break;
		}

		if (cavityWallInsulation == null) {
			cavityWallInsulation = Enum10.__MISSING;
		}
		if (externallWallInsulation == null) {
			externallWallInsulation = Enum10.__MISSING;
		}

		hasCavityWallInsulation = installedInsulation.get(cavityWallInsulation);
		hasExternalWallInsulation = installedInsulation.get(externallWallInsulation);
		wallType = wallTypeConversion.get(predominentWallStructure);

		if (wallType == null) {
			logger.error("Wall type is null for predominant wall structure {}", predominentWallStructure);
		}

		dto.setExternalWallConstructionType(Optional.of(wallType));
		dto.setInternalInsulation(Optional.<Boolean>absent());
		dto.setExternalInsulation(Optional.of(hasExternalWallInsulation));
		dto.setCavityInsulation(Optional.of(hasCavityWallInsulation));
	}

	/**
	 * <p>
	 * Adds following information to the DTO :-
	 * <ul>
	 * <li>ElevationType</li>
	 * <li>Tenths Attached - uses
	 * {@link Spss2012HelperMethods#getTenthsAttached(ElevationType, ElevateEntry, FlatdetsEntry, boolean)}
	 * </li>
	 * <li>Tenths Party Wall - uses
	 * {@link Spss2012HelperMethods#getTenthsOpening(ElevationType, ElevateEntry)}
	 * </li>
	 * <li>Tenths Opening</li>
	 * <li>External wall construction type</li>
	 * <li></li>
	 * </ul>
	 * </p>
	 * 
	 * @param dto
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void addElevationData(final IElevationDTO dto, final ElevationType elevationType, final ElevateEntry elevateEntry, final FlatdetsEntry flatDetsEntry, final boolean isHouse) {
		final double tenthsAttached = getTenthsAttached(elevationType, elevateEntry, flatDetsEntry, isHouse);
		final double tenthsOpening = getTenthsOpening(elevationType, elevateEntry);
		final double tenthsPartyWall = getTenthsPartyWall(elevationType, elevateEntry, flatDetsEntry, isHouse);

		dto.setElevationType(elevationType);
		dto.setTenthsAttached(tenthsAttached);
		dto.setTenthsOpening(tenthsOpening);
		dto.setTenthsPartyWall(tenthsPartyWall);
	}

	/**
	 * @return
	 * @see uk.org.cse.stockimport.ehcs2010.spss.elementreader.AbsSpssReader#readClass()
	 */
	@Override
	protected Class<?> readClass() {
		return ElevationDTO.class;
	}

}
