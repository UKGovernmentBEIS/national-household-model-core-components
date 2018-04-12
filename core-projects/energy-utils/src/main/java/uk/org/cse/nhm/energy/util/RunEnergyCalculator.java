package uk.org.cse.nhm.energy.util;

import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.BredemExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.WeeklyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.impl.BREDEMHeatingSeasonalParameters;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.util.EObjectSerializer;

/**
 * A simple main methody thing which lets you run the energy calculator on raw JSON input
 * with full debug logging on.
 */
public class RunEnergyCalculator {
    public static void main(final String[] args) throws Exception {
        // setup energy calculator
        final ObjectMapper mapper = getMapper();

        final EnergyCalculatorCalculator calc = new EnergyCalculatorCalculator();

        final IHeatingSchedule schedule =
            new WeeklyHeatingSchedule(
                DailyHeatingSchedule.fromHours(7, 8, 18, 23),
                DailyHeatingSchedule.fromHours(7, 23));

        // only running one month because we don't care
        final ISeasonalParameters climate = new BREDEMHeatingSeasonalParameters(MonthType.January,
        		new IWeather() {
					
					@Override
					public double getWindSpeed(MonthType month) {
						return 4;
					}
					
					@Override
					public double getHorizontalSolarFlux(MonthType month) {
						return 25;
					}
					
					@Override
					public double getExternalTemperature(MonthType month) {
						return 5;
					}
				},
                52.0 * Math.PI / 180, schedule);

        SurveyCase in = null;
        while ((in = mapper.readValue(System.in, SurveyCase.class)) != null) {
            final IEnergyCalculatorParameters parameters = new BredemExternalParameters(
            		EnergyCalculatorType.BREDEM2012,
            		ElectricityTariffType.FLAT_RATE,
            		21,
            		Optional.<Double>absent(),
            		Optional.of(3.0),
            		in.getPeople().getNumberOfPeople()
        		);

            final IEnergyCalculationResult x = calc.evaluate(in, parameters, new ISeasonalParameters[] {climate})[0];

            for (final EnergyType et : EnergyType.values()) {
            	System.out.println(String.format("%s\t%s\t%s", et, x.getEnergyState().getTotalDemand(et),
            			x.getEnergyState().getTotalSupply(et)
            			));
            }
        }
    }

    static ObjectMapper getMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.registerModule(new GuavaModule());

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

        mapper.setTimeZone(DateTimeZone.getDefault().toTimeZone());

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper.registerModule(createExtraMappingModule());
        return mapper;
    }

    static Module createExtraMappingModule() {
        final SimpleModule module = new SimpleModule();

        module.addSerializer(ITechnologyModel.class, new EObjectSerializer.Serializer<ITechnologyModel>(
                                 ITechnologyModel.class
                                 ));

        module.addDeserializer(ITechnologyModel.class, new EObjectSerializer.Deserializer<ITechnologyModel>(
                                   ITechnologyModel.class,
                                   ImmutableList.of(
                                       ITechnologiesPackage.eINSTANCE,
                                       IBoilersPackage.eINSTANCE
                                       )
                                   ));
        return module;
    }
}


