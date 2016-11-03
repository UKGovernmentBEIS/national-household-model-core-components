package uk.org.cse.nhm.simulator.state.functions.impl.lookup;

import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.language.definition.function.lookup.LookupRule;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class LookupFunctionTest {
	private LookupFunction create(
			final List<IComponentsFunction<?>> keys,
			final LookupEntry... rules) {
		return new LookupFunction(
				mock(ILogEntryHandler.class),
				false,
				keys, 
				ImmutableList.copyOf(rules), 
				new ConstantComponentsFunction<Number>(Name.of("test"), Double.NaN));
	}
	
	abstract static class TestFunction<T> implements IComponentsFunction<T> {
		private final Map<Object, Object> values = new HashMap<>();
		public TestFunction(final Object...objects) {
			for (int i = 0; i<objects.length/2; i++) {
				values.put(objects[2*i], objects[2*i+1]);
			}
		}
		
		@Override
		public Name getIdentifier() {
			return Name.of("test");
		}

		@SuppressWarnings("unchecked")
		@Override
		public T compute(final IComponentsScope scope, final ILets lets) {
			return (T) values.get(scope);
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return Collections.emptySet();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return Collections.emptySet();
		}
	}
	
	@Test
	public void lookupOnEnumsWorks() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		final IComponentsScope scope2 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<RegionType>(scope1, RegionType.London, scope2, RegionType.EastMidlands) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("London")), 
						ConstantComponentsFunction.<Number>of(Name.of("londonvalue"), 9d))
				);
		
		final double londonvalue = lf.compute(scope1, ILets.EMPTY);
		final double othervalue = lf.compute(scope2, ILets.EMPTY);
		
		Assert.assertEquals(9d, londonvalue, 0d);
		Assert.assertTrue(Double.isNaN(othervalue));
	}
	
	@Test
	public void lookupOnEnumsWorks2() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		final IComponentsScope scope2 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<RegionType>(scope1, RegionType.London, scope2, RegionType.EastMidlands) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("London")), 
						ConstantComponentsFunction.<Number>of(Name.of("londonvalue"), 9d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("eastmidlands")), 
						ConstantComponentsFunction.<Number>of(Name.of("emvalue"), 8d))
				);
		
		final double londonvalue = lf.compute(scope1, ILets.EMPTY);
		final double othervalue = lf.compute(scope2, ILets.EMPTY);
		
		Assert.assertEquals(9d, londonvalue, 0d);
		Assert.assertEquals(8d, othervalue, 0d);
	}
	
	@Test
	public void lookupWithMultipleMatchSelectsFirst() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<RegionType>(scope1, RegionType.London) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("London")), 
						ConstantComponentsFunction.<Number>of(Name.of("londonvalue"), 9d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("London")), 
						ConstantComponentsFunction.<Number>of(Name.of("emvalue"), 8d))
				);
		
		final double londonvalue = lf.compute(scope1, ILets.EMPTY);
		
		Assert.assertEquals(9d, londonvalue, 0d);
	}
	
	@Test
	public void wildcardMatcherWorks() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<RegionType>(scope1, RegionType.EasternScotland) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("London")), 
						ConstantComponentsFunction.<Number>of(Name.of("londonvalue"), 9d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("*")), 
						ConstantComponentsFunction.<Number>of(Name.of("emvalue"), 8d))
				);
		
		final double londonvalue = lf.compute(scope1, ILets.EMPTY);
		
		Assert.assertEquals(8d, londonvalue, 0d);
	}
	
	@Test
	public void bitMatcherWorks() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<Boolean>(scope1, true) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("false")), 
						ConstantComponentsFunction.<Number>of(Name.of("londonvalue"), 9d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("true")), 
						ConstantComponentsFunction.<Number>of(Name.of("emvalue"), 8d))
				);
		
		final double londonvalue = lf.compute(scope1, ILets.EMPTY);
		
		Assert.assertEquals(8d, londonvalue, 0d);
	}
	
	@Test
	public void multiwayLookupWorks() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<Boolean>(scope1, true) {}, new TestFunction<Integer>(scope1, 9) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("false"), LookupRule.of(">8")), 
						ConstantComponentsFunction.<Number>of(Name.of("londonvalue"), 9d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("true"), LookupRule.of("<9")), 
						ConstantComponentsFunction.<Number>of(Name.of("emvalue"), 8d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("true"), LookupRule.of("9..10")), 
						ConstantComponentsFunction.<Number>of(Name.of("winner"), 1d))
				);
		
		final double londonvalue = lf.compute(scope1, ILets.EMPTY);
		
		Assert.assertEquals(1d, londonvalue, 0d);
	}
	
	@Test
	public void rangeMatchWorks() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		final IComponentsScope scope2 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<Double>(scope1, 13d, scope2, 10d) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("<10")), 
						ConstantComponentsFunction.<Number>of(Name.of("lessthan"), 0d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("13")), 
						ConstantComponentsFunction.<Number>of(Name.of("exact"), 1d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("8..10")), 
						ConstantComponentsFunction.<Number>of(Name.of("range"), 2d))
				);
		
		final double f13 = lf.compute(scope1, ILets.EMPTY);
		
		Assert.assertEquals(1d, f13, 0d);
		
		final double f10 = lf.compute(scope2, ILets.EMPTY);
		
		Assert.assertEquals(2d, f10, 0d);
	}
	
	@Test
	public void faulty() {
		final IComponentsScope scope1 = mock(IComponentsScope.class);
		final IComponentsScope scope2 = mock(IComponentsScope.class);
		final IComponentsScope scope3 = mock(IComponentsScope.class);
		
		final LookupFunction lf = create(
				ImmutableList.<IComponentsFunction<?>>of(new TestFunction<Double>(scope1, 2020d, scope2, 2025d, scope3, 2030d) {}),
				new LookupEntry(ImmutableList.of(LookupRule.of("2020")), 
						ConstantComponentsFunction.<Number>of(Name.of("f2020"), 1d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("2025")), 
						ConstantComponentsFunction.<Number>of(Name.of("f2025"), 2d)),
				new LookupEntry(ImmutableList.of(LookupRule.of("2030")), 
						ConstantComponentsFunction.<Number>of(Name.of("f2030"), 3d))
				);
		
		final double v2020 = lf.compute(scope1, ILets.EMPTY);
		final double v2025 = lf.compute(scope2, ILets.EMPTY);
		final double v2030 = lf.compute(scope3, ILets.EMPTY);
		
		
		Assert.assertEquals(1d, v2020, 0d);
		Assert.assertEquals(2d, v2025, 0d);
		Assert.assertEquals(3d, v2030, 0d);
	}

    private static IComponentsFunction<Number> number(final String s) {
        return ConstantComponentsFunction.<Number>of(Name.of("s"), Double.parseDouble(s));
    }
    
    @Test
    public void hideemLookupFunction() {
        final IComponentsScope scope1 = mock(IComponentsScope.class);

        final LookupFunction lf = create(ImmutableList.<IComponentsFunction<?>>of(new TestFunction<Number>(scope1, 3) {},
                                                                                  new TestFunction<Number>(scope1, 1d) {},
                                                                                  new TestFunction<Number>(scope1, 61.9) {},
                                                                                  new TestFunction<Number>(scope1, 0d) {}),

                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("1")  ,LookupRule.of(">50")     ,LookupRule.of("1")    )  ,number("1")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("2")  ,LookupRule.of(">50")     ,LookupRule.of("1") )     ,number("2")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("3")  ,LookupRule.of(">50")     ,LookupRule.of("1")    )  ,number("3")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("4")  ,LookupRule.of(">50")     ,LookupRule.of("1") )     ,number("4")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("1")  ,LookupRule.of(">50")     ,LookupRule.of("2")    )  ,number("5")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("2")  ,LookupRule.of(">50")     ,LookupRule.of("2") )     ,number("6")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("3")  ,LookupRule.of(">50")     ,LookupRule.of("2")    )  ,number("7")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("4")  ,LookupRule.of(">50")     ,LookupRule.of("2") )     ,number("8")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("1")  ,LookupRule.of(">50")     ,LookupRule.of("3")    )  ,number("9")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("2")  ,LookupRule.of(">50")     ,LookupRule.of("3") )     ,number("10")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("4")  ,LookupRule.of(">50")     ,LookupRule.of("3") )     ,number("12")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("3")  ,LookupRule.of(">50")     ,LookupRule.of("3")    )  ,number("11")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("1")  ,LookupRule.of("<=50")    ,LookupRule.of("1")    )  ,number("13")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("2")  ,LookupRule.of("<=50")    ,LookupRule.of("1") )     ,number("14")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("3")  ,LookupRule.of("<=50")    ,LookupRule.of("1")    )  ,number("15")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("4")  ,LookupRule.of("<=50")    ,LookupRule.of("1") )     ,number("16")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("1")  ,LookupRule.of("<=50")    ,LookupRule.of("2")    )  ,number("17")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("2")  ,LookupRule.of("<=50")    ,LookupRule.of("2") )     ,number("18")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("3")  ,LookupRule.of("<=50")    ,LookupRule.of("2")    )  ,number("19")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("4")  ,LookupRule.of("<=50")    ,LookupRule.of("2") )     ,number("20")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("1")  ,LookupRule.of("<=50")    ,LookupRule.of("3")    )  ,number("21")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("2")  ,LookupRule.of("<=50")    ,LookupRule.of("3") )     ,number("22")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("3")  ,LookupRule.of("<=50")    ,LookupRule.of("3")    )  ,number("23")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("6..7") ,LookupRule.of("4")  ,LookupRule.of("<=50")    ,LookupRule.of("3") )     ,number("24")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("1") )     ,number("25")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("1") )     ,number("26")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("1")     ) ,number("27")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("1") )     ,number("28")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("2")     ) ,number("29")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("2") )     ,number("30")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("2")     ) ,number("31")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("2") )     ,number("32")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("3")     ) ,number("33")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("3") )     ,number("34")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("3")     ) ,number("35")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("8")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("3") )     ,number("36")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("1")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("37")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("1")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("38")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("1")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("39")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("1")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("40")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("1")  ,LookupRule.of("<=131")   ,LookupRule.of("*")     ) ,number("41")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("2")  ,LookupRule.of("<=131")   ,LookupRule.of("*") )     ,number("42")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("3")  ,LookupRule.of("<=131")   ,LookupRule.of("*")     ) ,number("43")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("4")  ,LookupRule.of("<=131")   ,LookupRule.of("*") )     ,number("44")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("1")  ,LookupRule.of(">131")    ,LookupRule.of("*")     ) ,number("45")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("2")  ,LookupRule.of(">131")    ,LookupRule.of("*") )     ,number("46")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("3")  ,LookupRule.of(">131")    ,LookupRule.of("*")     ) ,number("47")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("2")    ,LookupRule.of("4")  ,LookupRule.of(">131")    ,LookupRule.of("*") )     ,number("48")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("3")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("49")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("3")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("50")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("3")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("51")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("3")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("52")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("5")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("53")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("5")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("54")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("5")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("55")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("5")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("56")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("4")    ,LookupRule.of("1")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("61")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("4")    ,LookupRule.of("2")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("62")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("4")    ,LookupRule.of("3")  ,LookupRule.of("*")       ,LookupRule.of("*")     ) ,number("63")),
                                         new LookupEntry(ImmutableList.of(LookupRule.of("4")    ,LookupRule.of("4")  ,LookupRule.of("*")       ,LookupRule.of("*") )     ,number("64"))
                                         );

        Assert.assertEquals(49d, lf.compute(scope1, ILets.EMPTY), 0.001);
    }
}
