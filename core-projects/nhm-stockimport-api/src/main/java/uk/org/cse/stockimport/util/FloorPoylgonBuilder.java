package uk.org.cse.stockimport.util;

import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.__MISSING;

import uk.org.cse.stockimport.domain.geometry.SimplePolygon;
import uk.org.cse.stockimport.domain.types.AddedFloorModulePosition;

/**
 * TODO this appears to be a duplicate of SpssFloorPolygonBuilder
 *
 */
public final class FloorPoylgonBuilder {
	/**
	 * Used to convert meter dimensions into CMS
	 * 
	 * @since 1.0
	 */
	public static final int SCALING_FACTOR = 100;

	/**
	 * <p>
	 * Always builds floors in clockwise direction starting from 0,0 (x,y), with
	 * the next point always being a positive x value.
	 * </p>
	 * 
	 * @assumption If additional module exists but there is no dimension data
	 *             for it, build floor as a square.
	 * 
	 * @param mainDepth
	 * @param mainWidth
	 * @param additionalDepth
	 * @param additionalWidth
	 * @param addionalModuleLocation
	 * @return
	 * @since 0.0.1-SNAPSHOT
	 */
	public static final SimplePolygon createFloorPolygon(final Double mainDepth,
			final Double mainWidth, final Double additionalDepth, final Double additionalWidth,
			AddedFloorModulePosition addionalModuleLocation) {

		int mainDepthInMilli = 0;
		int mainWidthInMilli = 0;
		int addDepthInMilli = 0;
		int addWidthInMilli = 0;
		int centerPoint;
		int addCenterPoint;

		final SimplePolygon.Builder floor = SimplePolygon.builder();
		floor.add(0, 0);

		if ((mainWidth != null && mainDepth != null)) {
			mainDepthInMilli = (int) (mainDepth * SCALING_FACTOR);
			mainWidthInMilli = (int) (mainWidth * SCALING_FACTOR);

			addionalModuleLocation = (addionalModuleLocation == null ? __MISSING
					: addionalModuleLocation);

			if (hasAdditionalModule(addionalModuleLocation)) {
				if ((additionalDepth != null && additionalDepth > 0)
						&& (additionalWidth != null && additionalWidth > 0)) {
					addDepthInMilli = (int) (additionalDepth * SCALING_FACTOR);
					addWidthInMilli = (int) (additionalWidth * SCALING_FACTOR);
				} else {
					// Has no data for additional module so assume no additional
					// module
					addionalModuleLocation = __MISSING;
				}
			}

			switch (addionalModuleLocation) {

			case RightElevation_Front:
				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(addDepthInMilli, mainWidthInMilli);
				floor.add(addDepthInMilli, mainWidthInMilli
						+ addWidthInMilli);
				floor.add(0, mainWidthInMilli + addWidthInMilli);

				break;
			case RightElevation_Centre:
				centerPoint = mainDepthInMilli / 2;
				addCenterPoint = addDepthInMilli / 2;

				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(centerPoint + addCenterPoint, mainWidthInMilli);
				floor.add(centerPoint + addCenterPoint, mainWidthInMilli
						+ addWidthInMilli);
				floor.add(centerPoint - addCenterPoint, mainWidthInMilli
						+ addWidthInMilli);
				floor.add(centerPoint - addCenterPoint, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case RightElevation_Back:
				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli
						+ addWidthInMilli);
				floor.add(mainDepthInMilli - addDepthInMilli,
						mainWidthInMilli + addWidthInMilli);
				floor.add(mainDepthInMilli - addDepthInMilli,
						mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case FrontElevation_Left:
				floor.add(mainDepthInMilli + addDepthInMilli, 0);
				floor.add(mainDepthInMilli + addDepthInMilli,
						mainWidthInMilli);
				floor.add(addDepthInMilli, mainWidthInMilli);
				floor.add(addDepthInMilli, addWidthInMilli);
				floor.add(0, addWidthInMilli);

				break;
			case FrontElevation_Centre:
				centerPoint = mainWidthInMilli / 2;
				addCenterPoint = addWidthInMilli / 2;

				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);
				floor.add(0, centerPoint + addCenterPoint);
				floor.add(0 - addDepthInMilli, centerPoint
						+ addCenterPoint);
				floor.add(0 - addDepthInMilli, centerPoint
						- addCenterPoint);
				floor.add(0, centerPoint - addCenterPoint);

				break;
			case FrontElevation_Right:
				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0 - addDepthInMilli, mainWidthInMilli);
				floor.add(0 - addDepthInMilli, mainWidthInMilli
						- addWidthInMilli);
				floor.add(0, mainWidthInMilli - addWidthInMilli);

				break;
			case LeftElevation_Back:
				floor.add(mainDepthInMilli - addDepthInMilli, 0);
				floor.add(mainDepthInMilli - addDepthInMilli,
						0 - addWidthInMilli);
				floor.add(mainDepthInMilli, 0 - addWidthInMilli);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case LeftElevation_Front:
				floor.add(addDepthInMilli, 0);
				floor.add(addDepthInMilli, addWidthInMilli);
				floor.add(mainDepthInMilli, addWidthInMilli);
				floor.add(mainDepthInMilli, mainWidthInMilli
						+ addWidthInMilli);
				floor.add(0, mainWidthInMilli + addWidthInMilli);

				break;
			case LeftElevation_Centre:
				centerPoint = mainDepthInMilli / 2;
				addCenterPoint = addDepthInMilli / 2;

				floor.add(centerPoint - addCenterPoint, 0);
				floor.add(centerPoint - addCenterPoint,
						0 - addWidthInMilli);
				floor.add(centerPoint + addCenterPoint,
						0 - addWidthInMilli);
				floor.add(centerPoint + addCenterPoint, 0);
				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case BackElevation_Left:
				floor.add(mainDepthInMilli + addDepthInMilli, 0);
				floor.add(mainDepthInMilli + addDepthInMilli,
						addWidthInMilli);
				floor.add(mainDepthInMilli, addWidthInMilli);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case BackElevation_Centre:
				centerPoint = mainWidthInMilli / 2;
				addCenterPoint = addWidthInMilli / 2;

				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, centerPoint - addCenterPoint);
				floor.add(mainDepthInMilli + addDepthInMilli, centerPoint
						- addCenterPoint);
				floor.add(mainDepthInMilli + addDepthInMilli, centerPoint
						+ addCenterPoint);
				floor.add(mainDepthInMilli, centerPoint + addCenterPoint);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case BackElevation_Right:
				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli
						- addWidthInMilli);
				floor.add(mainDepthInMilli + addDepthInMilli,
						mainWidthInMilli - addWidthInMilli);
				floor.add(mainDepthInMilli + addDepthInMilli,
						mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			case __MISSING:
			case Unknown:
			case NoAdditionalPart:
			default:
				// Square
				floor.add(mainDepthInMilli, 0);
				floor.add(mainDepthInMilli, mainWidthInMilli);
				floor.add(0, mainWidthInMilli);

				break;
			}
		}

		return floor.build();
	}

	/**
	 * Returns false if {@link Enum1677} equals {@link Enum1677#__MISSING},
	 * {@link Enum1677#NoAdditionalPart} or {@link Enum1677#Unknown}, otherwise
	 * returns true.
	 * 
	 * @param addionalModuleLocation
	 *            {@link Enum1677}
	 * @return boolean true if has an additional module
	 * @since 0.0.1-SNAPSHOT
	 */
	public static final boolean hasAdditionalModule(
			AddedFloorModulePosition addionalModuleLocation) {
		addionalModuleLocation = (addionalModuleLocation == null ? __MISSING
				: addionalModuleLocation);

		switch (addionalModuleLocation) {
		case __MISSING:
		case NoAdditionalPart:
		case Unknown:
			return false;
		default:
			return true;
		}
	}
}
