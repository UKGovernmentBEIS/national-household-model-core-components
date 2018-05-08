package uk.org.cse.nhm.stock.io;

import java.util.Date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.util.EObjectSerializer;

/**
 * A module which makes an objectmapper suitable for talking to the stock a la
 * spring data.
 *
 * @author hinton
 *
 */
public class StockJacksonModule extends AbstractModule {

    public static final String NAME = "uk.org.cse.nhm.ipc.guice.StockJacksonModule.mapper";

    public static class DateToDateTimeConverter extends StdConverter<Date, DateTime> {

        @Override
        public DateTime convert(final Date arg0) {
            return new DateTime(arg0);
        }
    }

    private static SimpleModule createExtraMappingModule() {
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

        final JsonDeserializer<? extends DateTime> dateToDateTime
                = new StdDelegatingDeserializer<DateTime>(new DateToDateTimeConverter());

        module.addDeserializer(DateTime.class, dateToDateTime);

        return module;
    }

    protected ObjectMapper createMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(createExtraMappingModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JodaModule());
        return mapper;
        // removed this: MongoJackModule.configure(mapper);
    }

    @Override
    protected void configure() {

        bind(ObjectMapper.class).annotatedWith(Names.named(NAME)).toInstance(createMapper());
    }
}
