package uk.org.cse.nhm.language.builder.function;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.enums.XWallInsulationRule;

public class MapWallTypes {
	public static final Set<WallConstructionType> getWallConstructionTypes(final XWallConstructionTypeRule x) {
		switch (x) {
		case Any:		return WallConstructionType.getExternalWallTypes();
		case AnyCavity: return EnumSet.of(WallConstructionType.Cavity, WallConstructionType.TimberFrame, WallConstructionType.MetalFrame, WallConstructionType.SystemBuild);
		case AnySolid:	return EnumSet.of(WallConstructionType.GraniteOrWhinstone, WallConstructionType.Sandstone, WallConstructionType.SolidBrick, WallConstructionType.Cob);
		case Cavity:	return EnumSet.of(WallConstructionType.Cavity);
		case Cob:		return EnumSet.of(WallConstructionType.Cob);
		case GraniteOrWhinstone: return EnumSet.of(WallConstructionType.GraniteOrWhinstone);
		case MetalFrame: return EnumSet.of(WallConstructionType.MetalFrame);
		case Sandstone:  return EnumSet.of(WallConstructionType.Sandstone);
		case SolidBrick: return EnumSet.of(WallConstructionType.SolidBrick);
		case SystemBuild: return EnumSet.of(WallConstructionType.SystemBuild);
		case TimberFrame: return EnumSet.of(WallConstructionType.TimberFrame);
		default: throw new RuntimeException("Unknown XWallConsructionType " + x);
		}
	}
	
	public static final Predicate<WallConstructionType> getPredicateMatching(final XWallConstructionTypeRule x) {
		final Set<WallConstructionType> matchingTypes = getWallConstructionTypes(x);
		return new Predicate<WallConstructionType>() {
			@Override
			public boolean apply(@Nullable final WallConstructionType wct) {
				return matchingTypes.contains(wct);
			}
			
			@Override
			public String toString() {
				return String.format("in %s", matchingTypes);
			}
		};
	}
	
	public static final Predicate<Set<WallInsulationType>> getPredicateMatching(final XWallInsulationRule x) {
		switch (x) {
		case NoCavity:
		case NoExternal:
		case NoInsulation:
		case NoInternal:
		case SomeInsulation:
		case Any:
		case OnlyExternal:
		case OnlyFilledCavity:
		case OnlyInternal:
			break;
		default:
			throw new RuntimeException("Unknown XWallInsulation "+ x);
		}
		
		return new Predicate<Set<WallInsulationType>>() {
			@Override
			public boolean apply(final @Nullable Set<WallInsulationType> arg0) {
				switch (x) {
				case NoCavity:		return !arg0.contains(WallInsulationType.FilledCavity);
				case NoExternal:	return !arg0.contains(WallInsulationType.External);
				case NoInsulation:	return arg0.isEmpty();
				case NoInternal:	return !arg0.contains(WallInsulationType.Internal);
				case SomeInsulation:return !arg0.isEmpty();
				case Any:			return true;
				case OnlyFilledCavity:  return arg0.equals(EnumSet.of(WallInsulationType.FilledCavity)); 
				case OnlyExternal:  return arg0.equals(EnumSet.of(WallInsulationType.External)); 
				case OnlyInternal:  return arg0.equals(EnumSet.of(WallInsulationType.Internal)); 
				default: return false;
				}
			}
			
			@Override
			public String toString() {
				return "" + x;
			}
		};
	}

	public static Predicate<IWall> getPredicateMatching(
			final XWallConstructionTypeRule construction, final XWallInsulationRule insulation) {
		final Predicate<WallConstructionType> type = getPredicateMatching(construction);
		final Predicate<Set<WallInsulationType>> ins = getPredicateMatching(insulation);
		return new Predicate<IWall>() {
			@Override
			public boolean apply(final IWall input) {
				return type.apply(input.getWallConstructionType()) &&
						ins.apply(input.getWallInsulationTypes());
			}
			@Override
			public String toString() {
				return String.format("construction = %s and insulation = %s", construction, insulation);
			}
		};
	}

	public static Predicate<Set<WallInsulationType>> getPredicateMatching(
			final Optional<Boolean> cavity, 
			final Optional<Boolean> internal, 
			final Optional<Boolean> external) {
		final EnumSet<WallInsulationType> required = EnumSet.noneOf(WallInsulationType.class);
		final EnumSet<WallInsulationType> forbidden = EnumSet.noneOf(WallInsulationType.class);
		if (cavity.isPresent()) {
			(cavity.get() ? required : forbidden).add(WallInsulationType.FilledCavity);
		}
		if (internal.isPresent()) {
			(internal.get() ? required : forbidden).add(WallInsulationType.Internal);
		}
		if (external.isPresent()) {
			(external.get() ? required : forbidden).add(WallInsulationType.External);
		}
		return setwisePredicate(required, forbidden);
	}

	private static Predicate<Set<WallInsulationType>> setwisePredicate(final EnumSet<WallInsulationType> required, final EnumSet<WallInsulationType> forbidden) {
		if (required.isEmpty() && forbidden.isEmpty()) {
			return Predicates.alwaysTrue();
		} else if (Collections.disjoint(required, forbidden)) {
			return new Predicate<Set<WallInsulationType>>() {
				@Override
				public boolean apply(final Set<WallInsulationType> input) {
					if (input.containsAll(required)) {
						if (Collections.disjoint(forbidden, input)) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				}
				@Override
				public String toString() {
					return String.format("in %s and not in %s", required, forbidden);
				}
			};
		} else {
			return Predicates.alwaysFalse();
		}
	}
}
