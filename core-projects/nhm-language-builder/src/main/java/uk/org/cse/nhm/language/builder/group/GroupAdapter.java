package uk.org.cse.nhm.language.builder.group;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.group.*;
import uk.org.cse.nhm.simulator.factories.IGroupFactory;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.impl.SetOperationDwellingGroups.Operation;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Builder adapter for the main groups.
 * 
 * @author hinton
 *
 */
public class GroupAdapter extends ReflectingAdapter {
	private final IGroupFactory groupFactory;
	
	@Inject
	public GroupAdapter(final Set<IConverter> delegates, final IGroupFactory groupFactory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.groupFactory = groupFactory;
	}

	@Adapt(XAllHousesGroup.class)
	public IDwellingGroup buildAllHouses() {
		return groupFactory.getAllHouses();
	}
	
	@Adapt(XProbabilisticMembershipGroup.class)
	public IDwellingGroup buildRandomSampling(
			@Prop(XProbabilisticMembershipGroup.P.FUNCTION) final IComponentsFunction<Number> function, 
			@Prop(XGroupWithSource.P.SOURCE) final IDwellingGroup source) {
		return groupFactory.createRandomSamplingGroup(source, function);
	}
	
	@Adapt(XRandomSampleGroup.class)
	public IDwellingGroup buildRandomSubset(
			@Prop(XRandomSampleGroup.P.PROPORTION) final double size, 
			@Prop(XGroupWithSource.P.SOURCE) final IDwellingGroup source) {
		return groupFactory.createRandomSubsetGroup(source, size);
	}
	
	@Adapt(XFilterGroup.class)
	public IDwellingGroup buildFilter(
			@Prop(XGroupWithSource.P.SOURCE) final IDwellingGroup source, 
			@Prop(XFilterGroup.P.FUNCTION) final IComponentsFunction<Boolean> function) {
		return groupFactory.createFilteredGroup(source, function);
	}
	
	@Adapt(XReferenceGroup.class)
	public IDwellingGroup buildReference(
			@Prop(XReferenceGroup.P.REFERENCE) final IDwellingGroup ref) {
		return ref;
	}
	
	
	@Adapt(XDifferenceGroup.class)
	public IDwellingGroup buildDifference(
			@Prop(XGroupWithSources.P.SOURCES) final List<IDwellingGroup> sources
			) {
		return groupFactory.createOperationGroup(sources, Operation.DIFFERENCE);
	}
	
	@Adapt(XIntersectionGroup.class)
	public IDwellingGroup buildIntersection(
			@Prop(XGroupWithSources.P.SOURCES) final List<IDwellingGroup> sources
			) {
		return groupFactory.createOperationGroup(sources, Operation.INTERSECTION);
	}
	
	@Adapt(XUnionGroup.class)
	public IDwellingGroup buildUnion(
			@Prop(XGroupWithSources.P.SOURCES) final List<IDwellingGroup> sources
			) {
		return groupFactory.createOperationGroup(sources, Operation.UNION);
	}
}
