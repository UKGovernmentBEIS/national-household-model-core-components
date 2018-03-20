package uk.org.cse.nhm.language.sexp;

import java.util.Set;

import javax.validation.Validator;

import org.apache.bval.guice.ValidationModule;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.name.Names;

import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.batch.XBatch;
import uk.org.cse.nhm.language.parse.LanguageElements;

public class ScenarioParserFactory {
	public static final String IS_BATCH_MODE =  "uk.org.cse.nhm.language.sexp.ScenarioParserFactory.IS_BATCH_MODE";
	
	private static final LanguageElements language = LanguageElements.get();

	private final Validator batchValidator;

	private final Validator standardValidator;
	
	static class ValidationConfigurationModule extends AbstractModule {
		
		private final boolean batch;

		public ValidationConfigurationModule(final boolean b) {
			this.batch = b;
		}

		@Override
		protected void configure() {
			bind(Boolean.class).annotatedWith(Names.named(IS_BATCH_MODE)).toInstance(batch);
		}
	}
	
	public ScenarioParserFactory() {
		final ClassLoader tcc = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
			this.batchValidator = Guice.createInjector(
					new ValidationModule(),
					new ValidationConfigurationModule(true)
					).getInstance(Validator.class);
			
			this.standardValidator = Guice.createInjector(
					new ValidationModule(),
					new ValidationConfigurationModule(false)
					).getInstance(Validator.class);
		} finally {
			Thread.currentThread().setContextClassLoader(tcc);
		}
	}
	
	public IScenarioParser<XScenario> buildDefaultScenarioParser() {
		return buildCustomParser(XScenario.class, false);
	}

	public IScenarioParser<XScenario> buildBatchInstanceParser() {
		return buildCustomParser(XScenario.class, true);
	}
	
	public IScenarioParser<XBatch> buildBatchParser() {
		return buildCustomParser(XBatch.class, false);
	}
	
	public <A extends XElement> IScenarioParser<A> buildCustomParser(
			final Class<A> output,
			final boolean useBatchValidator,
			final Class<?>... additionalClasses) {
		
		final Set<Class<?>> possibleClasses =
				ImmutableSet.<Class<?>>builder()
					.addAll(ImmutableSet.<Class<?>>copyOf(language.all()))
					.addAll(ImmutableSet.<Class<?>>copyOf(additionalClasses))
				.build();
		
		return new ScenarioParser<>(
				output, 
				useBatchValidator ? batchValidator : standardValidator,
				possibleClasses, 
				Defaults.DEFAULT_ATOM_IO);
	}
}
