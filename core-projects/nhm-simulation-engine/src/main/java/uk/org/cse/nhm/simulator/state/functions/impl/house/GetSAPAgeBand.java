package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetSAPAgeBand extends BasicAttributesFunction<SAPAgeBandValue> {

    final int sap;

    @AssistedInject
    public GetSAPAgeBand(
            final IDimension<BasicCaseAttributes> basicDimension,
            @Assisted final int sap
    ) {
        super(basicDimension);
        this.sap = sap;
    }

    @Override
    public SAPAgeBandValue compute(final IComponentsScope scope, final ILets lets) {
        final BasicCaseAttributes basic = getAttributes(scope);
        return SAPAgeBandValue.fromYear(
                (sap < 2012) ? Math.min(basic.getBuildYear(), 2009) : basic.getBuildYear(),
                basic.getRegionType());
    }
}
