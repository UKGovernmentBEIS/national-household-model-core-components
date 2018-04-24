package uk.org.cse.nhm.stock.io;

import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.util.EObjectSerializer;

/**
 * A module which makes an objectmapper suitable for sending data over the
 * logging queue. This still uses a special serializer for handling EObjects.
 * 
 * It is the default mapper which you will get if you don't ask for anything special.
 * 
 * @author hinton
 * 
 */
public class StandardJacksonModule extends AbstractModule {
	@Override
	protected void configure() {
		final ObjectMapper mapper = new ObjectMapper();		
		mapper.registerModule(new JodaModule());
		mapper.registerModule(new GuavaModule());

		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
//		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		
		mapper.setTimeZone(DateTimeZone.getDefault().toTimeZone());

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		mapper.registerModule(createExtraMappingModule());
		bind(ObjectMapper.class).toInstance(mapper);
	}

	private Module createExtraMappingModule() {
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
