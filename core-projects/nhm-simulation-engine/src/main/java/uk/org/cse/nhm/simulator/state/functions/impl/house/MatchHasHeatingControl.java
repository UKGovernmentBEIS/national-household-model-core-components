package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchHasHeatingControl extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final IDimension<ITechnologyModel> technologies;
	private final HeatingSystemControlType controlType;
	
	@AssistedInject
	public MatchHasHeatingControl(
			final IDimension<ITechnologyModel> technologies,
			@Assisted final HeatingSystemControlType controlType) {
		super();
		this.technologies = technologies;
		this.controlType = controlType;
	}

	@Override
	public Boolean compute(IComponentsScope scope, ILets lets) {
		IPrimarySpaceHeater primary = scope.get(technologies).getPrimarySpaceHeater();
		
		EList<HeatingSystemControlType> controls = null;
		StorageHeaterControlType control;
		if(primary instanceof ICentralHeatingSystem) {
			controls = ((ICentralHeatingSystem)primary).getControls();
		} else if (primary instanceof IWarmAirSystem){
			controls = ((IWarmAirSystem)primary).getControls();
		}
		//TODO: Secondary space heater - 
		//scope.get(technologies).getSecondarySpaceHeater().isThermostatFitted();
		
		return controls != null ? controls.contains(controlType) : false;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(technologies);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}