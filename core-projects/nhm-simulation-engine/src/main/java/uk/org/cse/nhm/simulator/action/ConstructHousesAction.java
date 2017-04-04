package uk.org.cse.nhm.simulator.action;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;

/**
 * Converts from {} XML element to itself.
 * 
 * Implements creating a state change which demolishes houses.
 * 
 * @author glenns
 */
public class ConstructHousesAction extends AbstractNamed implements IStateAction {
	private final Set<IDimension<?>> dimensionsToCopy;
	private final ITimeDimension time;
	private final IDimension<ITechnologyModel> technology;
	private final IDimension<BasicCaseAttributes> basicAttributes;
	
	@Inject
	public ConstructHousesAction(
			final ITimeDimension time, 
			final IDimension<ITechnologyModel> technology, 
			final IDimension<BasicCaseAttributes> basicAttributes,
			@Named(IDimension.CONSTRUCT_COPY) final Set<IDimension<?>> dimensionsToCopy
			) {
		this.time = time;
		this.technology = technology;
		this.basicAttributes = basicAttributes;
		final ImmutableSet.Builder<IDimension<?>> builder = ImmutableSet.builder();
		
		for (final IDimension<?> d : dimensionsToCopy) {
			if (d == technology || d == basicAttributes) continue;
			builder.add(d);
		}
		
		this.dimensionsToCopy = builder.build();
	}

	@Override
	public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
		final IBranch branch = scope.getState();
		final ImmutableSet.Builder<IDwelling> allCreated = ImmutableSet.builder();
		final DateTime now = scope.getState().get(time, null).get(lets);
		for (final IDwelling original : dwellings) {
			final IDwelling created = branch.createDwelling(original.getWeight());
			allCreated.add(created);
			branch.set(technology, created, 
					updateBoilerInstallationYears(branch.get(technology, original),
							now.getYear()));
			branch.set(basicAttributes, created, 
					basicAttributesWithNewDate(branch.get(basicAttributes, original), 
							now.getYear()));
			
			for (final IDimension<?> dimension : dimensionsToCopy) {
				setDimensionFromOriginal(dimension, branch, original, created);
			}
		}
		return allCreated.build();
	}

    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        return dwellings;
    }
    
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
	
	private ITechnologyModel updateBoilerInstallationYears(final ITechnologyModel tech, final int thisYear) {
		final ITechnologyModel copy = EcoreUtil.copy(tech);

		final Set<IHeatSource> heatSources = new HashSet<IHeatSource>();

		if (copy.getCommunityHeatSource() != null) {
			heatSources.add(copy.getCommunityHeatSource());
		}
		if (copy.getIndividualHeatSource() != null) {
			heatSources.add(copy.getIndividualHeatSource());
		}

		if (copy.getSecondarySpaceHeater() != null) {
			final IRoomHeater heater = copy.getSecondarySpaceHeater();
			if (heater instanceof IBackBoiler) {
				heatSources.add((IHeatSource) heater);
			}
		}

		if (heatSources.size() > 0) {
			for (final IHeatSource heatSource : heatSources) {
				heatSource.setInstallationYear(thisYear);
			}
			return copy;
		} else {
			return tech;
		}
	}

	private BasicCaseAttributes basicAttributesWithNewDate(final BasicCaseAttributes existingBasic, final int newConstructionYear) {
		return new BasicCaseAttributes(existingBasic.getAacode(), existingBasic.getDwellingCaseWeight(), existingBasic.getHouseholdCaseWeight(),
				existingBasic.getRegionType(), existingBasic.getMorphologyType(), existingBasic.getTenureType(), newConstructionYear, existingBasic.getSiteExposure());
	}
	
	
	private static <T> void setDimensionFromOriginal(
			final IDimension<T> dimension, 
			final IBranch branch,
			final IDwelling original,
			final IDwelling copy) {
		branch.set(dimension, copy, branch.get(dimension, original));
	}
}
