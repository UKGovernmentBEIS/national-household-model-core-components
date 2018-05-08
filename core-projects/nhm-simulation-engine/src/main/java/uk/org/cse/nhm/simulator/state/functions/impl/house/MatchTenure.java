package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchTenure extends BasicAttributesFunction<Boolean> implements IComponentsFunction<Boolean> {

    final TenureType tenureType;

    @Inject
    public MatchTenure(final IDimension<BasicCaseAttributes> bad, @Assisted final TenureType tenureType) {
        super(bad);
        this.tenureType = tenureType;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return getAttributes(scope).getTenureType().equals(tenureType);
    }
}
