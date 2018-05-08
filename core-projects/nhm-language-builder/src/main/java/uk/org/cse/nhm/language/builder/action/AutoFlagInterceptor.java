package uk.org.cse.nhm.language.builder.action;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.definition.action.IXFlaggedAction;
import uk.org.cse.nhm.language.definition.reporting.two.XReportDefinition;
import uk.org.cse.nhm.simulator.action.FlaggedComponentsAction;
import uk.org.cse.nhm.simulator.action.FlaggedStateAction;
import uk.org.cse.nhm.simulator.action.IUnifiedReport;
import uk.org.cse.nhm.simulator.factories.IActionFactory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;

public class AutoFlagInterceptor implements IAdapterInterceptor {

    final IActionFactory factory;

    @Inject
    public AutoFlagInterceptor(final IActionFactory factory) {
        super();
        this.factory = factory;
    }

    @Override
    public <T> boolean transforms(final Object input, final T object, final Class<T> clazz) {
        return input instanceof IXFlaggedAction
                && ((clazz.isAssignableFrom(FlaggedComponentsAction.class) && object instanceof IComponentsAction)
                || (clazz.isAssignableFrom(FlaggedStateAction.class) && object instanceof IStateAction));
    }

    @Override
    public <T> T transform(final Object input, final T object, final Class<T> clazz, IAdaptingScope scope) {
        if (transforms(input, object, clazz)) {
            final IXFlaggedAction a = (IXFlaggedAction) input;
            if (a.getTestFlags().isEmpty()
                    && a.getUpdateFlags().isEmpty()
                    && a.getReport().isEmpty()) {
                return object;
            }

            // need to adapt the reports in the thing into what they should be.
            final List<IUnifiedReport> reports = new ArrayList<>();

            for (final XReportDefinition def : a.getReport()) {
                reports.add(def.adapt(IUnifiedReport.class, scope));
            }

            if (object instanceof IComponentsAction) {
                return clazz.cast(factory.createFlaggedComponentsAction(
                        a.getTestFlags(),
                        a.getUpdateFlags(),
                        reports,
                        (IComponentsAction) object));
            } else if (object instanceof IStateAction) {
                return clazz.cast(factory.createFlaggedStateAction(
                        a.getTestFlags(),
                        a.getUpdateFlags(),
                        reports,
                        (IStateAction) object));
            } else {
                return object;
            }
        } else {
            return object;
        }
    }
}
