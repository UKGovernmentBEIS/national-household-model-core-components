package uk.org.cse.nhm.language.builder.action;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.action.XFlagAction;
import uk.org.cse.nhm.simulator.action.DoNothingAction;
import uk.org.cse.nhm.simulator.factories.IActionFactory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;

public class RegisterAdapter extends ReflectingAdapter {
	final IActionFactory measureFactory;
	
	@Inject
	public RegisterAdapter(final Set<IConverter> delegates, final Set<IAdapterInterceptor> interceptors, final IActionFactory measureFactory) {
		super(delegates, interceptors);
		this.measureFactory = measureFactory;
	}
	
	@Adapt(XFlagAction.class)
	public IComponentsAction buildFlagAction2(
			@Prop(XFlagAction.P.flags) final List<Glob> flags){
		return new DoNothingAction(); /* We hijack AutoFlagInterceptor here. */ 
	}
}
