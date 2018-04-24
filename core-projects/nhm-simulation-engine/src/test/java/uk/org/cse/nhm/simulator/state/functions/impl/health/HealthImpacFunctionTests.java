package uk.org.cse.nhm.simulator.state.functions.impl.health;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;

import uk.ac.ucl.hideem.HealthModule;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.people.People.Occupant;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.SexType;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XDisease;
import uk.org.cse.nhm.language.definition.function.health.XHealthImpactFunction.XImpact;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * HealthImpacFunctionTests.
 *
 * @author trickyBytes
 */
@RunWith(MockitoJUnitRunner.class)
public class HealthImpacFunctionTests {

    private HealthModule healthModule = new HealthModule();

    @Mock
    IDimension<StructureModel> structure;
    @Mock
    IDimension<People> people;
    @Mock
    IDimension<BasicCaseAttributes> attributes;

    List<XDisease> diseases = ImmutableList.<XDisease> builder()
            .add(XDisease.CaV, XDisease.CP, XDisease.LC, XDisease.MI, XDisease.WCaV)
            .add(XDisease.WCV, XDisease.WMI)
            .build();
    XImpact impact = XImpact.Mortality;

    @Mock
    IComponentsScope scope;
    @Mock
    ILets lets;

    @Mock
    StructureModel structureModel;
    @Mock
    BasicCaseAttributes attributesModel;
    People peopleModel;

    @Before
    public void setUp() {
        List<Occupant> occupants = ImmutableList.<Occupant>builder()
                .add(new Occupant(SexType.MALE,40,true))
                .build();
        
        peopleModel = new People();
        peopleModel.setOccupants(occupants);
                
        when(scope.get(structure)).thenReturn(structureModel);
        when(scope.get(attributes)).thenReturn(attributesModel);
        when(scope.get(people)).thenReturn(peopleModel);
    }

    @Test
    public void canGetAResultFromTheFunction() throws Exception {
        when(structureModel.getBuiltFormType()).thenReturn(BuiltFormType.SemiDetached);
        when(structureModel.getMainFloorLevel()).thenReturn(1);
        when(structureModel.getFloorArea()).thenReturn(100d);
        when(attributesModel.getRegionType()).thenReturn(RegionType.London);
                
        HealthImpactFunction function = new HealthImpactFunction(healthModule,structure,people,attributes,
                num(10),num(10),num(18),num(18), num(20),num(19),num(1),num(0),bool(true),bool(true),bool(true),bool(true),
                num(0.8),num(0.8),diseases, impact);
        
        Number result = function.compute(scope, lets);
        assertFalse("Result is NaN",Double.isNaN(result.doubleValue()));
    }

    @SuppressWarnings("unchecked")
    private IComponentsFunction<Number> num(double value) {
        IComponentsFunction<Number> f = mock(IComponentsFunction.class);
        Number out = mock(Number.class);
        
        when(f.compute(scope, lets)).thenReturn(out);
        when(out.doubleValue()).thenReturn(value);
        when(out.intValue()).thenReturn((int) value);
        
        return f;
    }
    
    @SuppressWarnings("unchecked")
    private IComponentsFunction<Boolean> bool(boolean value) {
        IComponentsFunction<Boolean> f = mock(IComponentsFunction.class);
        when(f.compute(scope, lets)).thenReturn(value);
        return f;
    }

}
