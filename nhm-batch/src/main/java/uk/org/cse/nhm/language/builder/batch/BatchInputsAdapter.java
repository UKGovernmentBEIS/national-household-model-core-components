package uk.org.cse.nhm.language.builder.batch;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.batch.inputs.BoundedInput;
import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;
import uk.org.cse.nhm.language.builder.batch.inputs.RangeInput;
import uk.org.cse.nhm.language.builder.batch.inputs.TableInput;
import uk.org.cse.nhm.language.builder.batch.inputs.combinators.ConcatCombinator;
import uk.org.cse.nhm.language.builder.batch.inputs.combinators.ProductCombinator;
import uk.org.cse.nhm.language.builder.batch.inputs.combinators.RepetitionsInput;
import uk.org.cse.nhm.language.builder.batch.inputs.combinators.ZipCombinator;
import uk.org.cse.nhm.language.builder.batch.inputs.distributions.Discrete;
import uk.org.cse.nhm.language.builder.batch.inputs.distributions.Gaussian;
import uk.org.cse.nhm.language.builder.batch.inputs.distributions.Triangular;
import uk.org.cse.nhm.language.builder.batch.inputs.distributions.Uniform;
import uk.org.cse.nhm.language.builder.batch.inputs.distributions.UniformInteger;
import uk.org.cse.nhm.language.definition.batch.inputs.XInputBound;
import uk.org.cse.nhm.language.definition.batch.inputs.XRange;
import uk.org.cse.nhm.language.definition.batch.inputs.XSingleInput;
import uk.org.cse.nhm.language.definition.batch.inputs.XTable;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XCartesianProduct;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XCombinator;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XConcatenate;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XRepetitions;
import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XZip;
import uk.org.cse.nhm.language.definition.batch.inputs.random.XDiscreteInput;
import uk.org.cse.nhm.language.definition.batch.inputs.random.XGaussianInput;
import uk.org.cse.nhm.language.definition.batch.inputs.random.XTriangularInput;
import uk.org.cse.nhm.language.definition.batch.inputs.random.XUniformInput;
import uk.org.cse.nhm.language.definition.batch.inputs.random.XUniformIntegerInput;

import com.google.common.base.Optional;

public class BatchInputsAdapter extends ReflectingAdapter {
	
	private final Random random;

	@Inject
	public BatchInputsAdapter(final Set<IConverter> delegates, final Random random, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.random = random;
	}

	@Adapt(XRange.class)
	public IBatchInputs buildRange(
			@Prop(XSingleInput.P.PLACEHOLDER) final String placeholder,
			@Prop(XRange.P.START) final double start,
			@Prop(XRange.P.STEP) final double step,
			@Prop(XRange.P.END) final Double end){
		if(end == null) {
			return new RangeInput(placeholder, start, step);
		} else {
			return new RangeInput(placeholder, start, step, end);
		}
	}
	
	@Adapt(XTable.class)
	public IBatchInputs buildTable(
		@Prop(XTable.P.HEADER) final List<String> headers,
		@Prop(XTable.P.ROWS) final List<List<Object>> rows
			) {
		return new TableInput(headers, rows);
	}
	
	@Adapt(XZip.class)
	public IBatchInputs buildZip(
			@Prop(XCombinator.P.DELEGATES) final List<IBatchInputs> delegates) {
		return new ZipCombinator(delegates);
	}
	
	@Adapt(XCartesianProduct.class)
	public IBatchInputs buildProduct(
			@Prop(XCombinator.P.DELEGATES) final List<IBatchInputs> delegates) {
		return new ProductCombinator(delegates);
	}
	
	@Adapt(XConcatenate.class)
	public IBatchInputs buildConcat(
			@Prop(XCombinator.P.DELEGATES) final List<IBatchInputs> delegates) {
		return new ConcatCombinator(delegates);
	}
	
	@Adapt(XUniformInput.class)
	public IBatchInputs buildUniform(
			@Prop(XSingleInput.P.PLACEHOLDER) final String placeholder,
			@Prop(XUniformInput.P.START) final double start,
			@Prop(XUniformInput.P.END) final double end){
		return new Uniform(random, placeholder, start, end);
	}
	
	@Adapt(XUniformIntegerInput.class)
	public IBatchInputs buildUniformInteger(
			@Prop(XSingleInput.P.PLACEHOLDER) final String placeholder,
			@Prop(XUniformIntegerInput.P.START) final int start,
			@Prop(XUniformIntegerInput.P.END) final int end) {
		return new UniformInteger(random, placeholder, start, end);
	}
	
	@Adapt(XGaussianInput.class)
	public IBatchInputs buildGaussian(
			@Prop(XSingleInput.P.PLACEHOLDER) final String placeholder,
			@Prop(XGaussianInput.P.MEAN) final double mean,
			@Prop(XGaussianInput.P.STANDARD_DEVIATION) final double standardDeviation) {
		return new Gaussian(random, placeholder, mean, standardDeviation);
	}
	
	@Adapt(XTriangularInput.class)
	public IBatchInputs buildTriangular(
			@Prop(XSingleInput.P.PLACEHOLDER) final String placeholder,
			@Prop(XTriangularInput.P.START) final double start,
			@Prop(XTriangularInput.P.END) final double end,
			@Prop(XTriangularInput.P.PEAK) final double peak) {
		return new Triangular(random, placeholder, start, end, peak);
	}
	
	@Adapt(XDiscreteInput.class)
	public IBatchInputs buildDiscrete(
			@Prop(XSingleInput.P.PLACEHOLDER) final String placeholder,
			@Prop(XDiscreteInput.P.CHOICES) final List<Discrete.WeightedChoice> choices) {
		return new Discrete(random, placeholder, choices);
	}
	
	@Adapt(XDiscreteInput.XChoice.class)
	public Discrete.WeightedChoice buildChoice(
			@Prop(XDiscreteInput.XChoice.P.VALUE) final Object value,
			@Prop(XDiscreteInput.XChoice.P.WEIGHT) final int weight) {
		return new Discrete.WeightedChoice(value, weight);
	}
	
	@Adapt(XInputBound.class)
	public IBatchInputs buildBound(
			@Prop(XInputBound.P.BOUND) final int bound,
			@Prop(XInputBound.P.DELEGATE) final IBatchInputs delegate) {
		return new BoundedInput(bound, delegate);
	}
	
	@Adapt(XRepetitions.class)
	public IBatchInputs buildRepetitions(
			@Prop(XRepetitions.P.count) final int count,
			@Prop(XRepetitions.P.independentVariables) final Optional<IBatchInputs> vars
			) {
		return new RepetitionsInput(random, count, vars);
	}
}
