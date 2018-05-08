package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class SurveyCodeFunction extends BasicAttributesFunction<String> {

    @Inject
    public SurveyCodeFunction(final IDimension<BasicCaseAttributes> bad) {
        super(bad);
    }

    @Override
    public String compute(final IComponentsScope scope, final ILets lets) {
        return getAttributes(scope).getAacode();
    }
}
