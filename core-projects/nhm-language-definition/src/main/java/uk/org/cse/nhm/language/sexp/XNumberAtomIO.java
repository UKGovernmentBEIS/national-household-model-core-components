package uk.org.cse.nhm.language.sexp;

import java.util.Collections;
import java.util.Set;

import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.io.atom.NumberAtomIO;

/**
 * Modifies the JASB number reader to handle translating XNumber from
 * constants as well as Double, etc.
 * 
 * @author hinton
 *
 */
public class XNumberAtomIO extends NumberAtomIO {
	@Override
	public boolean canReadTo(final Class<?> output) {
		return super.canReadTo(output)
				|| output.isAssignableFrom(XNumberConstant.class);
	}
	
	@Override
	public boolean canWrite(final Object object) {
		return super.canWrite(object) || object instanceof XNumberConstant;
	}
	
	@Override
	public <T> Optional<T> read(final String in, final Class<T> out) {
		if (out.isAssignableFrom(XNumberConstant.class)) {
			final Optional<Double> d = super.read(in, Double.class);
			if (d.isPresent()) {
				final XNumberConstant constant = new XNumberConstant();
				constant.setValue(d.get());
				return Optional.of(out.cast(constant));
			} else {
				return Optional.absent();
			}
		} else {
			return super.read(in, out);
		}
	}
	
	@Override
	public String write(final Object object) {
		if (object instanceof XNumberConstant) {
			return super.write(((XNumberConstant) object).getValue());
		} else {
			return super.write(object);
		}
	}
	
	@Override
	public Set<String> getLegalValues(final Class<?> out) {
		if (out.isAssignableFrom(Double.class) || out.isAssignableFrom(Float.class) || out.isAssignableFrom(XNumberConstant.class)) {
			return ImmutableSet.of("0", "25%", "50%", "100%");
		} else if (out.isAssignableFrom(Integer.class) || out.isAssignableFrom(Long.class)) {
			return ImmutableSet.of("1", "2", "3", "4", "5");
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public String getDisplayName(final Class<?> out) {
		if (out.isAssignableFrom(Double.class) || out.isAssignableFrom(Float.class) || out.isAssignableFrom(XNumberConstant.class)) {
			return "Real Number";
		} else if (out.isAssignableFrom(Integer.class) || out.isAssignableFrom(Long.class)) {
			return "Whole Number";
		} else {
			return "";
		}
	}
}
