package uk.org.cse.nhm.language.builder.batch;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.ipc.api.tasks.IScenarioSnapshot;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.AdaptingScope;
import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.batch.XBatch;
import uk.org.cse.nhm.language.sexp.IScenarioParser;
import uk.org.cse.nhm.language.sexp.IScenarioParser.IResult;
import uk.org.cse.nhm.language.visit.impl.AdapterInstaller;

public class BatchExpander {
	private final IScenarioParser<XBatch> parser;

	public BatchExpander(final IScenarioParser<XBatch> parser) {
		this.parser = parser;
	}
	
	public Batch expandAll(final IScenarioSnapshot source) {
		final IResult<XBatch> parsed = parser.parse(source);
		if (!parsed.getOutput().isPresent()) {
			return new Batch(parsed.getErrors(), new IBatchInputs() {
				
				@Override
				public Iterator<List<Object>> iterator() {
					return Collections.<List<Object>>emptyList().iterator();
				}
				
				@Override
				public List<String> getPlaceholders() {
					return null;
				}
				
				@Override
				public Optional<Integer> getBound() {
					return Optional.of(0);
				}
			}, 
			null);
		}
		return expandXBatch(parsed);
	}

    public Batch expandXBatch(final IResult<XBatch> parsed) {
        return expandXBatch(parsed.getOutput().get(), parsed.getErrors());
    }

    public Batch expandXBatch(final XBatch batch) {
        return expandXBatch(batch, Collections.<IError>emptyList());
    }

    private Batch expandXBatch(final XBatch batch, final List<IError> errors) {
        final BatchInputsAdapter adapter = new BatchInputsAdapter(ImmutableSet.<IConverter>of(),
                new Random(batch.getSeed()), Collections.<IAdapterInterceptor>emptySet());

        batch.getInputs().accept(new AdapterInstaller<XElement>(Collections.<IAdapter>singleton(adapter)));
        final IBatchInputs inputs = batch.getInputs().adapt(IBatchInputs.class, new AdaptingScope(ImmutableSet.<IConverter>of()));

        return new Batch(errors, inputs, batch.getScenario());
    }
}
