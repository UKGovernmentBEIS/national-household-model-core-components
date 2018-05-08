package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchExternalWalls extends StructureFunction<Boolean> implements IComponentsFunction<Boolean> {

    private final Predicate<WallConstructionType> wallTypeMatcher;
    private final Predicate<Set<WallInsulationType>> insulationMatcher;
    private final boolean allWalls;

    @Inject
    public MatchExternalWalls(final IDimension<StructureModel> structure,
            @Assisted final Predicate<WallConstructionType> wallTypeMatcher,
            @Assisted final Predicate<Set<WallInsulationType>> insulationMatcher,
            @Assisted final boolean allWalls) {
        super(structure);
        this.wallTypeMatcher = wallTypeMatcher;
        this.insulationMatcher = insulationMatcher;
        this.allWalls = allWalls;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        for (final Storey s : getStructure(scope).getStoreys()) {
            for (final IWall w : s.getImmutableWalls()) {
                if (w.isPartyWall()) {
                    continue;
                }
                final boolean typeMatches = wallTypeMatcher.apply(w.getWallConstructionType());
                final boolean insMatches = insulationMatcher.apply(w.getWallInsulationTypes());

                if (allWalls) {
                    if (!(typeMatches && insMatches)) {
                        return false; // at least one wall is bad
                    }
                } else {
                    if (typeMatches && insMatches) {
                        return true; // at least one wall is good
                    }
                }
            }
        }

        return allWalls; // either all walls were good, or no walls were good
    }
}
