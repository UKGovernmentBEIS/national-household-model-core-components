package uk.org.cse.stockimport.domain.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;

/**
 * Utility for reading/writing values from fields. This has probably been written 9 million times before. 
 * @author hinton
 *
 * @param <T>
 */
abstract class FieldReader<T> {
	public abstract Optional<String> validate(final String in);
	public abstract T read(final String in);
	public abstract String write(final T in);
	
	static class EnumProperty<E extends Enum<E>> extends FieldReader<E> {
		private final Class<E> propertyClass;
		private final Map<String, Object> values;
		
		@SuppressWarnings("rawtypes")
		public EnumProperty(final Class<E> clazz) {
			propertyClass = clazz;
			final HashMap<String, Object> values = new HashMap<>();
			for (final Object o : propertyClass.getEnumConstants()) {
				values.put(((Enum)o).name().toLowerCase(), o);
				values.put(String.valueOf(o).toLowerCase(), o);
			}
			this.values = ImmutableMap.copyOf(values);
		}

		@Override
		public Optional<String> validate(final String value) {
			if (values.containsKey(value.toLowerCase())) {
				return Optional.absent();
			} else {
				return Optional.of(value + " is not one of " + values.keySet());
			}
		}

		@Override
		public E read(final String value) {
			return propertyClass.cast(values.get(value.toLowerCase()));
		}
		
		@Override
		public String write(final E value) {
			return value.name().toLowerCase();
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public String getDescription() {
			final StringBuffer sb = new StringBuffer();
			
			for (final Object o : propertyClass.getEnumConstants()) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				
				sb.append("<code>" + String.valueOf(o).toLowerCase() + "</code>");
				if (!((Enum)o).name().equalsIgnoreCase(String.valueOf(o))) {
					sb.append("( or ");
					sb.append("<code>" + ((Enum)o).name().toLowerCase() + "</code>");
					sb.append(")");
				}
			}
			
			return "one of " + sb.toString();
		}
		
		@Override
		public String getSQLType() {
			// need to emit a create type statement.
			return propertyClass.getSimpleName();
		}
		
		@Override
		public String getSQLTypeDef() {
			final List<String> types = new ArrayList<>();
			
			for (final Object o : propertyClass.getEnumConstants()) {
				types.add("'" + String.valueOf(o).toLowerCase() + "'");
				
				if (!((Enum)o).name().equalsIgnoreCase(String.valueOf(o))) {
					types.add("'" + ((Enum)o).name().toLowerCase() + "'");
				}
			}
			
			return "CREATE TYPE " + propertyClass.getSimpleName() + " AS ENUM (" + Joiner.on(", ").join(types) + ");";
		}
	}
	
	static class StringProperty extends FieldReader<String> {
		@Override
		public Optional<String> validate(final String in) {
			return Optional.absent();
		}

		@Override
		public String read(final String in) {
			return in;
		}

		@Override
		public String write(final String in) {
			return in;
		}
		
		@Override
		public String getDescription() {
			return "any text";
		}
		
		@Override
		public String getSQLType() {
			return "varchar(100)";
		}
	}
	
	static class DoubleProperty extends FieldReader<Double> {
		@Override
		public Optional<String> validate(final String in) {
			try {
				Double.parseDouble(in);
				return Optional.absent();
			} catch (final NumberFormatException nfe) {
				return Optional.of(in + " is not a decimal number");
			}
		}

		@Override
		public Double read(final String in) {
			return Double.parseDouble(in);
		}

		@Override
		public String write(final Double in) {
			return Double.toString(in);
		}		
		
		@Override
		public String getDescription() {
			return "a decimal";
		}
		
		@Override
		public String getSQLType() {
			return "double precision";
		}
	}
	
	static class IntegerProperty extends FieldReader<Integer> {
		@Override
		public Optional<String> validate(final String in) {
			try {
				Integer.parseInt(in);
				return Optional.absent();
			} catch (final NumberFormatException nfe) {
				return Optional.of(in + " is not an integer");
			}
		}

		@Override
		public Integer read(final String in) {
			return Integer.parseInt(in);
		}

		@Override
		public String write(final Integer in) {
			return Integer.toString(in);
		}
		
		@Override
		public String getDescription() {
			return "an integer";
		}
		
		@Override
		public String getSQLType() {
			return "int";
		}
	}
	
	static class BooleanProperty extends FieldReader<Boolean> {
		@Override
		public Optional<String> validate(final String in) {
			if (in.equalsIgnoreCase("true") || in.equalsIgnoreCase("false")) {
				return Optional.absent();
			} else {
				return Optional.of( in + " is neither 'true' nor 'false'");
			}
		}

		@Override
		public Boolean read(final String in) {
			return "true".equalsIgnoreCase(in);
		}

		@Override
		public String write(final Boolean in) {
			return in ? "true" : "false";
		}
		
		@Override
		public String getDescription() {
			return "'true' or 'false'";
		}
		
		@Override
		public String getSQLType() {
			return "boolean";
		}
	}
	
	static class LongDateProperty extends FieldReader<DateTime> {
		@Override
		public Optional<String> validate(final String in) {
			try {
				Long.parseLong(in);
				return Optional.absent();
			} catch (final NumberFormatException nfe) {
				return Optional.of(in + " is not a long");
			}
		}

		@Override
		public DateTime read(final String in) {
			return new DateTime(Long.parseLong(in));
		}

		@Override
		public String write(final DateTime in) {
			return Long.toString(in.getMillis());
		}
		
		@Override
		public String getDescription() {
			return "A UNIX timestamp (integer milliseconds since the epoch)";
		}
		
		@Override
		public String getSQLType() {
			return "bigint";
		}
	}
	
	static class ListProperty<Q> extends FieldReader<List<Q>> {
		final FieldReader<Q> delegate;
		
		public ListProperty(final FieldReader<Q> delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		public Optional<String> validate(final String in) {
			if (in.trim().isEmpty()) {
				return Optional.absent();
			}
			final String[] parts = in.split(";");
			final StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i<parts.length; i++) {
				final Optional<String> error = delegate.validate(parts[i]);
				if (error.isPresent()) {
					sb.append(String.format("%s (element %d) ", error.get(), i+1));	
				}
			}

			if (sb.length() > 0) {
				return Optional.of(sb.toString());
			} else {
				return Optional.absent();
			}
		}

		@Override
		public List<Q> read(final String in) {
			if (in.trim().isEmpty()) return new ArrayList<>();
			final String[] parts = in.split(";");
			final List<Q> result = new ArrayList<>();
			for (final String s : parts) {
				result.add(delegate.read(s.trim()));
			}
			return result;
		}

		@Override
		public String write(final List<Q> in) {
			final List<String> ins = new ArrayList<>();
			for (final Q q : in) {
				ins.add(delegate.write(q));
			}
			return Joiner.on(";").join(ins);
		}
		
		@Override
		public String getDescription() {
			return String.format("A ';'-separated sequence of %s", delegate.getDescription());
		}
		
		@Override
		public String getSQLType() {
			return delegate.getSQLType() + "[]";
		}
	}
	
	static class OptionalProperty<Q> extends FieldReader<Optional<Q>> {
		final FieldReader<Q> delegate;
		
		public OptionalProperty(final FieldReader<Q> delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		public Optional<String> validate(final String in) {
			if ("null".equalsIgnoreCase(in) || in.isEmpty()) {
				return Optional.absent();
			} else {
				return delegate.validate(in);
			}
		}

		@Override
		public Optional<Q> read(final String in) {
			if ("null".equalsIgnoreCase(in) || in.isEmpty()) {
				return Optional.absent();
			} else {
				return Optional.of(delegate.read(in));
			}
		}

		@Override
		public String write(final Optional<Q> in) {
			return in.isPresent() ? delegate.write(in.get()) : "";
		}
		
		@Override
		public String getDescription() {
			return String.format("<emphasis>empty</emphasis>, null, or %s", delegate.getDescription());
		}
		
		@Override
		public String getSQLType() {
			return delegate.getSQLType();
		}
	}
	
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	public static <T> FieldReader<T> of(final TypeToken<T> token) {
		if (TypeToken.of(String.class).isAssignableFrom(token)) {
			return (FieldReader<T>) new StringProperty();
		} else if (TypeToken.of(Integer.class).isAssignableFrom(token)|| TypeToken.of(int.class).isAssignableFrom(token)) {
			return (FieldReader<T>) new IntegerProperty();
		} else if (TypeToken.of(Double.class).isAssignableFrom(token) || TypeToken.of(double.class).isAssignableFrom(token)) {
			return (FieldReader<T>) new DoubleProperty();
		} else if (TypeToken.of(Boolean.class).isAssignableFrom(token)|| TypeToken.of(boolean.class).isAssignableFrom(token)) {
			return (FieldReader<T>) new BooleanProperty();
		} else if (TypeToken.of(DateTime.class).isAssignableFrom(token)) {
			return (FieldReader<T>) new LongDateProperty();
		} else if (new TypeToken<List<? extends Object>>() {}.isAssignableFrom(token)) {
			try {
				return (FieldReader<T>) new ListProperty<>(of(token.resolveType(List.class.getMethod("get", int.class).getGenericReturnType())));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new IllegalArgumentException("wat");
			}
		} else if (new TypeToken<Optional<? extends Object>> () {}.isAssignableFrom(token)) {
			try {
				return (FieldReader<T>) new OptionalProperty<>(of(token.resolveType(Optional.class.getMethod("get").getGenericReturnType())));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new IllegalArgumentException("wat");
			}
		} else if (token.getRawType().isEnum()) {
			return (FieldReader<T>) new EnumProperty<>((Class<Enum>) token.getRawType());
		} else {
			throw new IllegalArgumentException("Cannot automatically map " + token);
		}
	}
	public abstract  String getDescription();
	
	public abstract String getSQLType();
	public String getSQLTypeDef() {
		return "";
	}
}
