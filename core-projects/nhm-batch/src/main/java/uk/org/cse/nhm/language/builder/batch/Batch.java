package uk.org.cse.nhm.language.builder.batch;

import java.util.Iterator;
import java.util.List;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;
import uk.org.cse.nhm.language.builder.batch.substitute.Substitution;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.PrettyPrinter;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;

public class Batch implements Iterator<IBatchInstance> {
	private final ISExpression scenario;
	private final List<String> placeholders;
	
	private final Iterator<List<Object>> inputs;
	private final Integer bound;
	
	public final List<IError> problems;

	public Batch(final List<IError> problems, final IBatchInputs inputs, final ISExpression scenario) {
		Preconditions.checkNotNull(scenario, "Batch was constructed with a null scenario.");
		
		this.problems = problems;
		this.scenario = scenario;
		
		final Optional<Integer> maybeBound = inputs.getBound();
		if(!maybeBound.isPresent()) {
			throw new IllegalArgumentException("Batch scenario run had unbounded inputs. Need to specify a bound so that we know how many variations of the scenario to run.");
		} else {
			this.bound = maybeBound.get(); 
		}
		
		this.placeholders = inputs.getPlaceholders();
		this.inputs = inputs.iterator();
	}
	
	public List<IError> getProblems() {
		return problems;
	}
	
	private ImmutableMap<String, String> asMap(final List<String> keys, final List<Object> values) {
		final ImmutableMap.Builder<String, String> result = ImmutableMap.builder();
		
		final int size = keys.size();
		for(int i=0; i < size; i++) {
			result.put(keys.get(i), values.get(i).toString());
		}
		
		return result.build();
	}
	
	public int getBound() {
		return bound;
	}
	
	public List<String> getPlaceholders() {
		return placeholders;
	}

	@Override
	public boolean hasNext() {
		return inputs.hasNext();
	}

	@Override
	public IBatchInstance next() {
		return Substitution.substitute(scenario, asMap(placeholders, inputs.next()));
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not implemented");
	}
	
	public String getUntemplatedScenario() {
		try {
			return PrettyPrinter.print(Node.copy(scenario));
		} catch (final UnfinishedExpressionException e) {
			return "error in input";
		}
	}
}
