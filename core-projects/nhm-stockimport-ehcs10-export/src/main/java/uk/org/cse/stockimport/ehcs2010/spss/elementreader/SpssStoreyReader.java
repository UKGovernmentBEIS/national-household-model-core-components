package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.hasBasement;
import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.hasRoomInRoof;
import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.isHouse;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.Dimensions_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Dimensions_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.FlatdetsEntry;
import uk.org.cse.nhm.ehcs10.physical.InteriorEntry;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.ShapeEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.FlatdetsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.InteriorEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ShapeEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1677;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1722;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.SimplePolygon;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;
import uk.org.cse.stockimport.ehcs2010.spss.SpssFloorPolygonBuilder;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * SpssStoreyReader, builds stories a story for each floor of a house/flat.
 * 
 * @author richardt
 * @version $Id: SpssStoreyReader.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class SpssStoreyReader extends AbsSpssReader<IStoreyDTO> {
	static class BuiltStorey implements Comparable<BuiltStorey> {
		final double area;
		final IStoreyDTO dto;
		
		public BuiltStorey(final double area, final IStoreyDTO dto) {
			super();
			this.area = area;
			this.dto = dto;
		}

		public BuiltStorey copy() {
			final StoreyDTO copyOfDTO = new StoreyDTO();
			copyOfDTO.setAacode(dto.getAacode());
			copyOfDTO.setCeilingHeight(dto.getCeilingHeight());
			copyOfDTO.setLocationType(dto.getLocationType());
			copyOfDTO.setPolygon(dto.getPolygon());
			copyOfDTO.setStoreyHeight(dto.getStoreyHeight());
			return new BuiltStorey(this.area, copyOfDTO);
		}

		@Override
		public int compareTo(final BuiltStorey arg0) {
			return StoreyDTO.StoreyLocationTypeComparator.compare(this.dto, arg0.dto);
		}
	}
	
	
	private static final Logger logger = LoggerFactory.getLogger(SpssStoreyReader.class);
	/** */
	private final String spssUnknown = "88";
	/** Room in roof scaling factor area should be half next highest room */
	private final double roomInRoofScalingFactor = 2.0;
	/**
	 * Default ceiling height(meters) for room in roof see SAP 2005 RdSAP S3.6 @since
	 * 1.0
	 */
	public static final double roomInRoofStoryHeight = 2.45;
	/** Storey height(meters) addition SAP 2005 RdSAP S3.3 @since 1.0 */
	public static final double storeyHeightAddition = 0.25;

	/**
	 * @since 1.0
	 */
	public SpssStoreyReader(final String executionId, final IHouseCaseSourcesRepositoryFactory providerFactory) {
		super(executionId, providerFactory);
	}

	@Override
	public List<IStoreyDTO> read(final IHouseCaseSources<Object> provider) {
		if (logger.isDebugEnabled())
			logger.debug("building house case:{}", provider.getAacode());
		return buildStoreysForHouseCase(provider);
	}

	protected List<IStoreyDTO> buildStoreysForHouseCase(final IHouseCaseSources<Object> provider) {
		final List<BuiltStorey> storeys = new ArrayList<BuiltStorey>();

		final Physical_09Plus10EntryImpl physicalProps = provider.requireOne(Physical_09Plus10EntryImpl.class);

		final Dimensions_09Plus10Entry dimensions = provider.requireOne(Dimensions_09Plus10Entry.class);

		final FlatdetsEntry flatdetsEntry = provider.requireOne(FlatdetsEntry.class);
		final ServicesEntry services = provider.requireOne(ServicesEntry.class);
		final InteriorEntry interiorEntry = provider.requireOne(InteriorEntry.class);
		final ShapeEntry shapeEntry = provider.requireOne(ShapeEntry.class);

		final int noFloorsAbvGrnd = physicalProps.getNumberOfFloorsAboveGround();
		final double totalFloorArea = dimensions.getTotalFloorArea();

		if (isHouse(physicalProps)) {
			buildStoreysForHouse(storeys, shapeEntry, provider.getAacode(), noFloorsAbvGrnd, hasRoomInRoof(physicalProps), hasBasement(physicalProps));
		} else {
			buildFloorsForFlat(storeys, flatdetsEntry, shapeEntry, services, provider.getAacode(), noFloorsAbvGrnd);
		}
		
		Collections.sort(storeys);
		
		adjustStoreyDimensions(storeys, totalFloorArea);
		adjustCeilingHeights(storeys, interiorEntry);
		adjustStoreyHeights(storeys);

		if (storeys.isEmpty()) {
			logger.warn("Did not build any storeys for {}", provider.getAacode());
		}

		final ArrayList<IStoreyDTO> justTheDTOs = new ArrayList<>();
		for (final BuiltStorey s : storeys) {
			justTheDTOs.add(s.dto);
		}
		return justTheDTOs;
	}

	/**
	 * Builds standard 3 levels of floor for building using
	 * {@link FloorLocationType} as the first level and determining next level
	 * to add using {@link FloorLocationType#getNextLevel(FloorLocationType)}.
	 * 
	 * @param floors
	 * @param shapeEntry
	 * @param houseCaseRef
	 * @param level1Type
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void buildStandardFloors(final List<BuiltStorey> storeys, final ShapeEntry shapeEntry, final String houseCaseRef, final FloorLocationType level1Type, final int numOfStories) {

		int numOfStoriesToCreate = numOfStories;
		final Enum1677 additionPartLoc = shapeEntry.getMODULESHAPEAdditionalPart_Location();
		
		if (numOfStories > 0) {
			// Added so that we can add at least one floor, even if it has no
			// dimension data yet
			final Double mainDepth = shapeEntry.getMAIN1STLEVEL_Depth() == null ? 0 : shapeEntry.getMAIN1STLEVEL_Depth();
			final Double mainWidth = shapeEntry.getMAIN1STLEVEL_Width() == null ? 0 : shapeEntry.getMAIN1STLEVEL_Width();

			final Optional<BuiltStorey> level1 = createFloor(
					level1Type, 
					mainDepth, 
					mainWidth, 
					shapeEntry.getADDITIONAL1STLEVEL_Depth(), 
					shapeEntry.getADDITIONAL1STLEVEL_Width(), 
					houseCaseRef,
					shapeEntry.getADDITIONAL1STLEVEL_Level(), 
					additionPartLoc);

			addFloor(storeys, level1);
			numOfStoriesToCreate--;
		}

		// Build other floors
		buildOtherFloors(level1Type, shapeEntry, numOfStoriesToCreate, houseCaseRef, storeys);
	}

	protected void buildOtherFloors(final FloorLocationType level1Type, final ShapeEntry shapeEntry, final int numToCreate,  final String houseCaseRef,
			final List<BuiltStorey> storeys) {
		BuiltStorey topStorey = storeys.isEmpty() ? null : storeys.get(storeys.size()-1);
		
		Double mainWidth = null;
		Double mainDepth = null;
		Double addWidth = null;
		Double addDepth = null;
		String addLevel = null;
		final Enum1677 additionPartLoc = shapeEntry.getMODULESHAPEAdditionalPart_Location();

		FloorLocationType lastLevel = level1Type;
		FloorLocationType levelType;
		
		for (int ct = 1; ct <= numToCreate; ct++) {
			levelType = FloorLocationType.getNextLevel(lastLevel);
			mainWidth = null;
			mainDepth = null;
			addWidth = null;
			addDepth = null;
			addLevel = null;

			if (ct == 1) {
				mainWidth = shapeEntry.getMAIN2NDLEVEL_Width();
				mainDepth = shapeEntry.getMAIN2NDLEVEL_Depth();
				addWidth = shapeEntry.getADDITIONAL2NDLEVEL_Width();
				addDepth = shapeEntry.getADDITIONAL2NDLEVEL_Depth();
				addLevel = shapeEntry.getADDITIONAL2NDLEVEL_Level();
			} else {
				// Use level 3 data floor for this and any higher floors
				mainWidth = shapeEntry.getMAIN3RDLEVEL_Width();
				mainDepth = shapeEntry.getMAIN3RDLEVEL_Depth();
				addWidth = shapeEntry.getADDITIONAL3RDLEVEL_Width();
				addDepth = shapeEntry.getADDITIONAL3RDLEVEL_Depth();
				addLevel = shapeEntry.getADDITIONAL3RDLEVEL_Level();
			}

			final Optional<BuiltStorey> newLevel;
			
			if (mainWidth == null || mainDepth == null) {
				logger.debug("no dimensions data for story:{}, ref:" + houseCaseRef + ", cloning prev floor", ct + 1);
				newLevel = Optional.of(topStorey.copy());
				newLevel.get().dto.setLocationType(levelType);
			} else {
				newLevel = createFloor(levelType, mainDepth, mainWidth, addDepth, addWidth, houseCaseRef, addLevel, additionPartLoc);
			}

			addFloor(storeys, newLevel);
			topStorey = newLevel.or(topStorey);
			lastLevel = levelType;
		}
	}

	/**
	 * <p>
	 * 1. If has basement then ShapeEntry MAIN1STLEVEL relates to basement,
	 * otherwise ground floor.
	 * </p>
	 * .
	 * 
	 * @param floors
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void buildStoreysForHouse(final List<BuiltStorey> storeys, final ShapeEntry shapeEntry, final String aacode, final int numFloorsAboveGround, final boolean roomInRoof, final boolean hasBasment) {

		// Number of floors above ground includes the room in roof so remove
		// from number of floors count
		// int numOfFloorsExcRoomInRoof = (numFloorsAboveGround = (roomInRoof ?
		// --numFloorsAboveGround
		// : numFloorsAboveGround));

		int numOfFloorsExcRoomInRoof = numFloorsAboveGround;

		// If the property has a basement then the first level we add is the
		// basement
		FloorLocationType level1Type = FloorLocationType.GROUND;

		if (hasBasment || isModule1Basement(shapeEntry)) {
			level1Type = FloorLocationType.BASEMENT;
			numOfFloorsExcRoomInRoof++;
		}

		buildStandardFloors(storeys, shapeEntry, aacode, level1Type, numOfFloorsExcRoomInRoof);

		// Room in roof - area is taken from highest current room
		if (roomInRoof) {
			final Optional<BuiltStorey> highestFloor = getHighestFloor(storeys);

			if (highestFloor.isPresent()) {
				final double dimension = Math.sqrt(highestFloor.get().area / roomInRoofScalingFactor);
				final Optional<BuiltStorey> roomInRoofFloor = createFloor(FloorLocationType.ROOM_IN_ROOF, dimension, dimension, 0d, 0d, aacode, spssUnknown, Enum1677.NoAdditionalPart);
				if (roomInRoofFloor.isPresent()) {
					addFloor(storeys, roomInRoofFloor);
				}
			} else {
				throw new IllegalArgumentException("The house has a room in roof, but no other storeys");
			}
		}
	}

	protected void buildFloorsForFlat(
			final List<BuiltStorey> storeys, final FlatdetsEntry flatDets, final ShapeEntry shapeEntry, final ServicesEntry services, 
			final String houseCaseRef,
			final int numFloorsAboveGround) {

		
		final FloorLocationType level1Type = getFlatPosition(shapeEntry, services);
		
		int numOfFloorsInFlat = Optional.fromNullable(flatDets.getDIMENSIONSOfFlat_NumberOfFloorsInFlat()).or(1);
		
		if (level1Type == null) {
			logger.warn(("HouseCase(" + houseCaseRef + ")|FlatFirstLevel|Not Set"));
		} else if (useModuleDataForFlat(flatDets)) {

			if (numOfFloorsInFlat > 0) {
				// Added so that we can add at least one floor, even if it has
				// no dimension data yet
				final double mainDepth = shapeEntry.getMAIN1STLEVEL_Depth() == null ? 0 : shapeEntry.getMAIN1STLEVEL_Depth();
				final double mainWidth = shapeEntry.getMAIN1STLEVEL_Width() == null ? 0 : shapeEntry.getMAIN1STLEVEL_Width();

                final Optional<BuiltStorey> level1 = createFloor(level1Type, mainDepth, mainWidth, shapeEntry.getADDITIONAL1STLEVEL_Depth(), shapeEntry.getADDITIONAL1STLEVEL_Width(), houseCaseRef,
						shapeEntry.getADDITIONAL1STLEVEL_Level(), shapeEntry.getMODULESHAPEAdditionalPart_Location());

				addFloor(storeys, level1);
				numOfFloorsInFlat--;
			}

			// Build other floors
			buildOtherFloors(level1Type, shapeEntry, numOfFloorsInFlat, houseCaseRef, storeys);
		} else {
			// Use dimension data from the FlatdetsEntry instead
			FloorLocationType locType;
			FloorLocationType prevLocType = null;
			Optional<BuiltStorey> storey;
			for (int ct = 1; ct <= numOfFloorsInFlat; ct++) {
				double mainDepth = 0d;
				double mainWidth = 0d;
				final double additionalDepth = 0d;
				final double additionalWidth = 0d;
				final String additionalFirstLevel = "";

				switch (ct) {
				case 1:
					locType = level1Type;
					mainDepth = flatDets.getDIMENSIONSOfFlat_Depth_MainFloor_();
					mainWidth = flatDets.getDIMENSIONSOfFlat_Width_MainFloor_();

					break;
				case 2:
					locType = FloorLocationType.getNextLevel(prevLocType);
					mainDepth = flatDets.getDIMENSIONSOfFlat_Depth_NextFloor_() == null ? flatDets.getDIMENSIONSOfFlat_Depth_MainFloor_() : flatDets.getDIMENSIONSOfFlat_Depth_NextFloor_();
					mainWidth = flatDets.getDIMENSIONSOfFlat_Width_NextFloor_() == null ? flatDets.getDIMENSIONSOfFlat_Width_MainFloor_() : flatDets.getDIMENSIONSOfFlat_Width_NextFloor_();
					break;
				default:
					final Optional<BuiltStorey> highestCurrentFloor = getHighestFloor(storeys);
					locType = FloorLocationType.getNextLevel(highestCurrentFloor.get().dto.getLocationType());
					
					mainDepth = mainWidth = Math.sqrt(highestCurrentFloor.get().area);

					break;
				}
				prevLocType = locType;
				storey = createFloor(locType, mainDepth, mainWidth, additionalDepth, additionalWidth, houseCaseRef, additionalFirstLevel,
						shapeEntry.getMODULESHAPEAdditionalPart_Location());
				addFloor(storeys, storey);
			}
		}
	}

	protected void addFloor(final List<BuiltStorey> storeys, final Optional<BuiltStorey> level1) {
		storeys.addAll(level1.asSet());
	}

	/**
	 * <p>
	 * Attempts to return the position of the flat, CAR specified it only needs
	 * to know if the flat is basement, ground floor, top floor or other.
	 * </p>
	 * 
	 * @assumption If
	 *             {@link Physical_09Plus10EntryImpl#getDwellingType_DWTYPE8X()}
	 *             is converted flat and {@link ServicesEntry#getDwellingType()}
	 *             is not ground floor, mid floor or top floor flat, then assume
	 *             the flat is on the ground floor.
	 * 
	 * @param shape
	 * @param services
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	protected FloorLocationType getFlatPosition(final ShapeEntry shape, final ServicesEntry services) {

		FloorLocationType levelType = null;
		if (isModule1Basement(shape) || services.getDwellingType().equals(Enum1722.BasementFlat)) {
			levelType = FloorLocationType.BASEMENT;
		} else if (services.getDwellingType().equals(Enum1722.GroundFloorFlat)) {
			levelType = FloorLocationType.GROUND;
		} else if (services.getDwellingType().equals(Enum1722.MidFloorFlat)) {
			levelType = FloorLocationType.FIRST_FLOOR;
		} else if (services.getDwellingType().equals(Enum1722.TopFloorFlat)) {
			levelType = FloorLocationType.TOP_FLOOR;
		} else {
			logger.error("getFlatPosition(): DwellingType{} does not correspond to flat", services.getDwellingType());
			levelType = FloorLocationType.GROUND;
		}

		return levelType;
	}

	/**
	 * Takes value
	 * {@link FlatdetsEntry#getDIMENSIONSOfFlat_ExternalDimensionsSameAsModule()}
	 * if the value is null {@link Enum10#Yes} then returns true, otherwise
	 * returns false.
	 * 
	 * @param entry
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	protected boolean useModuleDataForFlat(final FlatdetsEntry entry) {
		if (entry.getDIMENSIONSOfFlat_ExternalDimensionsSameAsModule() == null) {
			return true;
		}
		switch (entry.getDIMENSIONSOfFlat_ExternalDimensionsSameAsModule()) {
		case Yes:
			return true;
		default:
		    if(entry.getDIMENSIONSOfFlat_Width_MainFloor_() == null){
		        //Incorrect data, survey says use flatdets but then does not give any dimension data.
		        return true;
		    }
		    
			return false;
		}
	}

	/**
	 * <ol>
	 * <li>If we have values for main width and depth then calculates main total
	 * area on these values.</li>
	 * <li>If we have values for the additional width and depth
	 * (additionalFirstLevel doesn't equal 88) then calculates main total area
	 * on these values.</li>
	 * <li>Sum main and total areas / create sudo dimension for these using
	 * sqrt.</li>
	 * <li>Create new floor using those dimensions and floor location type.</li>
	 * </ol>
	 * <p>
	 * If we don't have any dimension data then the storey is not created</o>
	 * 
	 * <p>
	 * TODO: Some discrepancies with CHM Conversion, does CHM actually add
	 * additional area to each level? Doesn't seem to.
	 * </p>
	 * 
	 * @assumption At the moment because we have only one large floor including
	 *             additional area, we create a square floor, i.e. total areas
	 *             of main and additional then sqrt to get infer dimensions.
	 * 
	 * 
	 * @param locType
	 * @param mainDepth
	 * @param mainWidth
	 * @param additionalDepth
	 * @param additionalWidth
	 * @param houseCaseRef
	 * @param additionalFirstLevel
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	protected Optional<BuiltStorey> createFloor(final FloorLocationType locType, final Double mainDepth, final Double mainWidth, final Double additionalDepth, final Double additionalWidth, final String houseCaseRef,
			final String additionalFirstLevel, final Enum1677 addionalModuleLocation) {
		if (mainWidth != null && mainDepth != null) {
			final double mainArea = mainDepth * mainWidth;
			double additionalArea = 0d;

			if (!StringUtils.equals(additionalFirstLevel, spssUnknown)) {
				// TODO: Assuming all one big floor so adding to total for level
				if (additionalWidth != null && additionalDepth != null) {
					if (Enum1677.Unknown.equals(addionalModuleLocation) == false && Enum1677.__MISSING.equals(addionalModuleLocation) == false) {
						additionalArea = additionalWidth * additionalDepth;
					}
				} else {
					logger.warn("HouseCase(" + houseCaseRef + ")|Additional|" + locType + "|Null Width or Depth");
				}
			}

			final SimplePolygon p = createFloorPolygon(mainDepth, mainWidth, additionalDepth, additionalWidth, addionalModuleLocation);

			final StoreyDTO storey = new StoreyDTO();
			storey.setAacode(houseCaseRef);
			storey.setPolygon(p);
			storey.setLocationType(locType);
			
			return Optional.of(new BuiltStorey(mainArea+additionalArea, storey));			
		} else {
			return Optional.absent();
		}
	}

	/**
	 * <p>
	 * Builds a {@link Polygon} for the given dimensions.
	 * </p>
	 * 
	 * <ol>
	 * <li>Determines whether dimensions are null, if has main width and depth
	 * greater than zero then continues.</li>
	 * <li>If a floor has no additional width or depth, assume it is a square.</li>
	 * <li>Takes {@link Enum1677} and builds a corresponding floors based on
	 * this.</li>
	 * </ol>
	 * 
	 * @assumption If a floor has no additional width or depth, assume it is a
	 *             square.
	 * @param mainDepth
	 * @param mainWidth
	 * @param additionalDepth
	 * @param additionalWidth
	 * @param addionalModuleLocation
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	public static final SimplePolygon createFloorPolygon(final Double mainDepth, final Double mainWidth, final Double additionalDepth, final Double additionalWidth, final Enum1677 addionalModuleLocation) {
		return SpssFloorPolygonBuilder.createFloorPolygon(mainDepth, mainWidth, additionalDepth, additionalWidth, addionalModuleLocation);
	}

	/**
	 * Assumes either BB or -1 refer to basement level, takes value
	 * {@link ShapeEntry#getMAIN1STLEVEL_Level()}
	 * 
	 * @param shapeEntry
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	protected boolean isModule1Basement(final ShapeEntry shapeEntry) {
		boolean isBasement = false;
		final String level = shapeEntry.getMAIN1STLEVEL_Level();

		if (level != null && (level.equalsIgnoreCase("BB") || level.equalsIgnoreCase("-1"))) {
			isBasement = true;
		}

		return isBasement;
	}

	/**
	 * Sets the story heights of all floors, currently takes the current ceiling
	 * height and adds storeyHeightAddition to each floor other than the lowest
	 * floor.
	 * 
	 * @param storeyies
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void adjustStoreyHeights(final List<BuiltStorey> storeys) {
		FloorLocationType locType;
		for (int ct = 0; ct <= storeys.size() - 1; ct++) {
			final IStoreyDTO storey = storeys.get(ct).dto;
			locType = storey.getLocationType();

			if (ct == 0) {
				if (locType != null && locType.equals(FloorLocationType.ROOM_IN_ROOF)) {
					throw new RuntimeException("Lowest floor was room in roof, cannot set ceiling height");
				}
				storey.setStoreyHeight(storey.getCeilingHeight());
			} else if (locType != null && locType.equals(FloorLocationType.ROOM_IN_ROOF)) {
				storey.setStoreyHeight(storey.getCeilingHeight());
			} else {
				storey.setStoreyHeight(storey.getCeilingHeight() + storeyHeightAddition);
			}
		}
	}

	/**
	 * <p>
	 * Total Floor Area, is a derived value in EHS physical data-set, CAR's
	 * ensures that the total floor area of all floors matches this by applying
	 * a LSF.
	 * </p>
	 * <p>
	 * 1. Generate sum total of areas across floors.
	 * </p>
	 * <p>
	 * 2. For each floor create a scaling factor which is sqrt of total floor
	 * area / sum total floor area.
	 * </p>
	 * <p>
	 * 3. Multiply the width and length of each floor by the LSF and set these
	 * dimensions on the floor.
	 * </p>
	 * s
	 * 
	 * @param floors
	 *            {@link IStoreyDTO} objects to be harmonised
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void adjustStoreyDimensions(final List<BuiltStorey> storeys, final double totalFloorArea) {

		double sumTotalFloorArea = 0d;
		double scalingFactor;
		double newDimension = 0.0;

		// Sum all floor areas
		for (final BuiltStorey floor : storeys) {
			sumTotalFloorArea += floor.area;
		}

		if (totalFloorArea != sumTotalFloorArea) {
			if (sumTotalFloorArea > 0) {
				scalingFactor = Math.sqrt(totalFloorArea / sumTotalFloorArea);
			} else {
				scalingFactor = 1.00;
				newDimension = Math.sqrt(totalFloorArea / storeys.size());
			}

			// Apply Scaling Factor
			for (final BuiltStorey floor : storeys) {
				if (newDimension > 0.00) {
					floor.dto.setPolygon(SpssFloorPolygonBuilder.createFloorPolygon(newDimension, newDimension, 0.00, 0.00, Enum1677.__MISSING));
				} else {
					floor.dto.setPolygon(floor.dto.getPolygon().scale(scalingFactor));
				}
			}
		}
	}

	/**
	 * Adjust ceiling heights for all floors.
	 * <p>
	 * 1. Gets the average ceiling height using
	 * {@link SpssStoreyReader#calculateAverageCeilingHeight(InteriorEntry)}.
	 * </p>
	 * <p>
	 * 2. Story height is then set to average for every floor excluding lowest
	 * floor. Lowest floor height which is left as is.
	 * </p>
	 * <p>
	 * 3. If is room in roof then sets height to
	 * {@link SpssStoreyReader#roomInRoofStoryHeight}.
	 * </p>
	 * 
	 * @param storeys
	 * @since 0.0.1-SNAPSHOT
	 */
	protected void adjustCeilingHeights(final List<BuiltStorey> storeys, final InteriorEntry interiorEntry) {
		final double averageHeight = calculateAverageCeilingHeight(interiorEntry);

		FloorLocationType locType;
		for (int ct = 0; ct <= storeys.size() - 1; ct++) {
			final IStoreyDTO storey = storeys.get(ct).dto;
			locType = storey.getLocationType();

			if (ct == 0) {
				if (locType != null && locType.equals(FloorLocationType.ROOM_IN_ROOF)) {
					throw new RuntimeException("Lowest floor was room in roof, cannot set ceiling height");
				}
				storey.setCeilingHeight(averageHeight);
			} else if (locType != null && locType.equals(FloorLocationType.ROOM_IN_ROOF)) {
				storey.setCeilingHeight(roomInRoofStoryHeight);
			} else {
				storey.setCeilingHeight(averageHeight);
			}
		}
	}

	/**
	 * Calculates an average ceiling height which should be used be all floor
	 * except the lowest floor of the building (SAP 2005 RdSAP S3.3). Uses four
	 * values
	 * <ul>
	 * <li>{@link InteriorEntry#getLivingRoomCeilingHeight_Metres_()}</li>
	 * <li>{@link InteriorEntry#getKitchenCeilingHeight_Metres_()}</li>
	 * <li>
	 * {@link InteriorEntry#getBathroomCeilingHeight_Metres_()}</li>
	 * <li>
	 * {@link InteriorEntry#getBedroomCeilingHeight_Metres_()}</li>
	 * </ul>
	 * and returns the average value for these.
	 * 
	 * @return double average ceiling height
	 * @since 0.0.1-SNAPSHOT
	 */
	protected double calculateAverageCeilingHeight(final InteriorEntry interior) {

		int recoredRecorded = 0;
		double totalHeights = 0;

		if (interior.getLivingRoomCeilingHeight_Metres_() != null) {
			recoredRecorded++;
			totalHeights += interior.getLivingRoomCeilingHeight_Metres_();
		}
		if (interior.getKitchenCeilingHeight_Metres_() != null) {
			recoredRecorded++;
			totalHeights += interior.getKitchenCeilingHeight_Metres_();
		}
		if (interior.getBathroomCeilingHeight_Metres_() != null) {
			recoredRecorded++;
			totalHeights += interior.getBathroomCeilingHeight_Metres_();
		}
		if (interior.getBathroomCeilingHeight_Metres_() != null) {
			recoredRecorded++;
			totalHeights += interior.getBathroomCeilingHeight_Metres_();
		}

		return totalHeights / recoredRecorded;
	}

	@Override
	protected Set<Class<?>> getSurveyEntryClasses() {
		return ImmutableSet.<Class<?>> of(ShapeEntryImpl.class, Physical_09Plus10EntryImpl.class, Dimensions_09Plus10EntryImpl.class, FlatdetsEntryImpl.class,
				ServicesEntryImpl.class, InteriorEntryImpl.class);
	}

	/**
	 * Sorts collection using {@link StoreyDTO#StoreyLocationTypeComparator},
	 * returns highest current floor based on this order or null if floor list
	 * is empty or has no elements.
	 * 
	 * @param floors
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	protected Optional<BuiltStorey> getHighestFloor(final List<BuiltStorey> storeys) {
		if (storeys.isEmpty()) {
			return Optional.absent();
		}
		
		BuiltStorey top = storeys.get(0);
		for (final BuiltStorey s : storeys) {
			if (StoreyDTO.StoreyLocationTypeComparator.compare(top.dto, s.dto) <= 0) {
				top = s;
			}
		}
		
		return Optional.of(top);
	}

	/**
	 * @return
	 * @see uk.org.cse.stockimport.ehcs2010.spss.elementreader.AbsSpssReader#readClass()
	 */
	@Override
	protected Class<?> readClass() {
		return StoreyDTO.class;
	}

}
