package uk.org.cse.nhm.language.definition.reporting.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.ISelfValidating;

@Category(CategoryType.REPORTING)
public abstract class XColumnAggregation extends XElement implements ISelfValidating {
	public boolean hasName() {
		return getName() != null;
	}

	static abstract class XNumberAggregation extends XColumnAggregation {
		@Override
		protected String getFunctionBaseName() {
			return "number";
		}

		@Override
		protected Class<? extends XFunction> getFunctionBase() {
			return XNumber.class;
		}
	}

	@Doc("produces the weighted mean of the value")
	@Bind("mean")
	public static class XMean extends XNumberAggregation {

	}

	@Doc("produces the minimum of the value")
	@Bind("min")
	public static class XMin extends XNumberAggregation {

	}

	@Doc("produces the maximum of the value")
	@Bind("max")
	public static class XMax extends XNumberAggregation {

	}

	@Doc("produces a weighted n-tile of the value - by default the 0.5-tile or median.")
	@Bind("n-tile")
	public static class XTile extends XNumberAggregation {
		private double p = 0.5;

		@BindPositionalArgument(0)
		@Doc("The tile to compute, as a proportion; 0.5 is the median, 0.1 is the first decile, and so on.")
		public double getP() {
			return p;
		}

		public void setP(double p) {
			this.p = p;
		}
	}

	@Doc("produces the weighted sum of the value")
	@Bind("sum")
	public static class XSum extends XNumberAggregation {

	}

	@Doc("produces an estimate of the weighted variance of the value; it is computed using the algorithm in West (1979): Communications of the ACM, 22, 9, 532-535: Updating Mean and Variance Estimates: An Improved Method")
	@Bind("variance")
	public static class XVariance extends XNumberAggregation {

	}

	@Doc("produces the count of houses where the value is boolean true or numeric nonzero")
	@Bind("count")
	public static class XCount extends XColumnAggregation {
		@Override
		protected String getFunctionBaseName() {
			return "boolean";
		}

		@Override
		protected Class<? extends XFunction> getFunctionBase() {
			return XBoolean.class;
		}
	}

	@Doc("produces the count of houses where the value is one of the given literal values")
	@Bind("in")
	public static class XIs extends XColumnAggregation {
		private List<String> value = new ArrayList<>();

		@Doc("the literal value of the column will be compared with these strings")
		@BindPositionalArgument(0)
		public List<String> getValue() {
			return value;
		}

		public void setValue(List<String> value) {
			this.value = value;
		}

		@Override
		protected String getFunctionBaseName() {
			return "category";
		}

		@Override
		protected Class<? extends XFunction> getFunctionBase() {
			return XCategoryFunction.class;
		}
	}

	protected abstract Class<? extends XFunction> getFunctionBase();
	protected abstract String getFunctionBaseName();

	@Override
	public List<IError> validate(final Deque<XElement> context) {
		final Iterator<XElement> it = context.descendingIterator();
		while (it.hasNext()) {
			final XElement e = it.next();
			if (e instanceof XReportColumn) {
				final XReportColumn col = (XReportColumn) e;
				final XFunction f = col.getValue();
				if (f != null && !getFunctionBase().isInstance(f)) {
					return Collections.singletonList(
							BasicError.warningAt(getLocation(),
									String.format("%s cannot be summarised with %s, as it is not a %s",
											f.getIdentifier().getName(),
											getIdentifier().getName(),
											getFunctionBaseName())));
				} else {
					break;
				}
			}
		}
		return Collections.emptyList();
	}
}
