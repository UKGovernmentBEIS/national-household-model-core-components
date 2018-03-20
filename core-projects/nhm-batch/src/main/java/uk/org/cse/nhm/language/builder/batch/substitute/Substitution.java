package uk.org.cse.nhm.language.builder.batch.substitute;

import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Location;

import uk.org.cse.nhm.language.builder.batch.IBatchInstance;

public class Substitution {
	public static Set<String> getSubstitutions(final ISExpression input) {
		final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
		
		input.accept(new ISExpressionVisitor(){
			@Override
			public void open(final Delim delimeter) {}
		
			@Override
			public void locate(final Location loc) {}
		
			@Override
			public void comment(final String text) {}
		
			@Override
			public void close(final Delim delimeter) {}
		
			@Override
			public void atom(final String string) {
				if (string.startsWith("$")) {
					builder.add(string);
				}
			}
		});
		
		return builder.build();
	}
	
	public static IBatchInstance substitute(final ISExpression input, 
				final Map<String, String> parameters) {
		return new SubstitutedExpression(input, parameters);
	}
	
	static class SubstitutedExpression implements IBatchInstance {
		final ISExpression expression;
		final Map<String, String> parameters;
		
		SubstitutedExpression(final ISExpression expression,
				final Map<String, String> parameters) {
			super();
			
			Preconditions.checkNotNull(expression, "SubstitutedExpression was constructed with a null expression.");
			
			this.expression = expression;
			this.parameters = parameters;
		}
		
		@Override
		public Map<String, String> getParameters() {
			return parameters;
		}

		@Override
		public void accept(final ISExpressionVisitor visitor) {
			expression.accept(
					new ISExpressionVisitor(){
						@Override
						public void open(final Delim delimeter) {
							visitor.open(delimeter);
						}
					
						@Override
						public void locate(final Location loc) {
							visitor.locate(loc);
						}
					
						@Override
						public void comment(final String text) {
							visitor.comment(text);
						}
					
						@Override
						public void close(final Delim delimeter) {
							visitor.close(delimeter);
						}
					
						@Override
						public void atom(final String string) {
							if (parameters.containsKey(string)) {
								visitor.atom(parameters.get(string));
							} else {
								visitor.atom(string);
							}
						}
					});
		}
	}
}
