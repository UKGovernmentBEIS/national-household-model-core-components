package com.larkery.jasb.sexp.parse;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;

/**
 * A snapshot copy of an IDataSource.
 */
public class DataSourceSnapshot implements IDataSource<String> {
	private final String root;
	private final Map<String, String> components;
	private final List<Arrow> arrows;

    public interface IDependencyCollector {
        public void add(final String from, final String to);
    }

    private static final IDependencyCollector NOPE = new IDependencyCollector() {
            @Override public void add(final String from, final String to) {}
        };
    
	public static class Arrow {
		final String from;
		final String relation;
		final String to;
		
		@JsonCreator
		public Arrow(
				@JsonProperty("from") String from, 
				@JsonProperty("relation") String relation, 
				@JsonProperty("to") String identifier) {
			super();
			this.from = from;
			this.relation = relation;
			this.to = identifier;
		}
		
		@Override
		public String toString() {
			return String.format("%d (%s) -> %d", from, relation, to);
		}

		public String getFrom() {
			return from;
		}

		public String getRelation() {
			return relation;
		}

		public String getTo() {
			return to;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result
					+ ((relation == null) ? 0 : relation.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Arrow other = (Arrow) obj;
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (relation == null) {
				if (other.relation != null)
					return false;
			} else if (!relation.equals(other.relation))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			return true;
		}
	}
	
	@JsonCreator
	public DataSourceSnapshot(
			@JsonProperty("root")       final String root,
			@JsonProperty("components") final Map<String, String> components,
			@JsonProperty("arrows") 	final List<Arrow> arrows) {
		this.root = root;
		this.components = ImmutableMap.copyOf(components);
		this.arrows = ImmutableList.copyOf(arrows);
	}

    public static <T> DataSourceSnapshot copy(final IDataSource<T> copy, final IErrorHandler errors) {
        return copy(copy, errors, NOPE);
    }
    
	public static <T> DataSourceSnapshot copy(final IDataSource<T> copy, final IErrorHandler errors, final IDependencyCollector dc) {
		final Map<String, String> contents = new HashMap<String, String>();
		final ImmutableList.Builder<Arrow> arrows = ImmutableList.builder();
		
		final IDataSource<T> wrapper = new IDataSource<T>() {
			{
				insert(null, copy.root(), errors);
			}
			
			private void insert(final Seq relation, final T result, final IErrorHandler errors) {
				final String sresult = String.valueOf(result);
				if (!contents.containsKey(sresult)) {
					try {
						contents.put(sresult, IOUtils.toString(copy.open(result)));
					} catch (IOException e) {
						if (relation != null) {
							errors.handle(BasicError.at(relation, 
									String.format("Unable to resolve include %s: %s",
											relation,
											e.getMessage()
											)
									));
						} else {
							errors.handle(BasicError.nowhere(
									String.format("Unable to resolve scenario %s: %s",
											relation,
											e.getMessage()
											)
									));
						}
						contents.put(sresult, "");
					}
				}
			}
			
			@Override
			public T root() {
				return copy.root();
			}

			@Override
			public T resolve(final Seq relation, IErrorHandler errors) {
				final T result = copy.resolve(relation, errors);
				final String sresult = String.valueOf(result);
				// so we have
				// relation.getLocation().name
				// + relation
				// => resul

                dc.add(relation.getLocation().name, sresult);
                
				final Arrow a = new Arrow(
						relation.getLocation().name,
						String.valueOf(relation), 
						sresult);
				
				arrows.add(a);
				
				// we are going to open() it here if we can
				// so we know the error message
				
				insert(relation, result, errors);
				
				return result;
			}

			@Override
			public Reader open(T resolved) throws IOException {
				final String s = String.valueOf(resolved);
				if (contents.containsKey(s)) {
					return new StringReader(contents.get(s));
				} else {
					throw new IOException("There is no entry in the snapshot at " + resolved);
				}
			}
		};
		
		Includer.source(wrapper, wrapper.root(), errors).accept(ISExpressionVisitor.IGNORE);
		
		return new DataSourceSnapshot(String.valueOf(copy.root()), contents, arrows.build());
	}
	
	@JsonIgnore
	@Override
	public String root() {
		return root;
	}

	@JsonIgnore
	@Override
	public String resolve(final Seq relation, IErrorHandler errors) {
		final String base = relation.getLocation().name;
		final String delta = String.valueOf(relation);
		for (final Arrow a : arrows) {
			if (a.from.equals(base) && a.relation.equals(delta)) {
				return a.to;
			}
		}
		return "";
	}

	@JsonIgnore
	@Override
	public Reader open(String resolved) {
		String string = components.get(resolved);
		return new StringReader(string == null ? "" : string);
	}

	@JsonIgnore
	public String rootText() {
		return components.get(root);
	}
	
	public Map<String, String> getComponents() {
		return components;
	}

	public String getRoot() {
		return root;
	}
	
	public List<Arrow> getArrows() {
		return arrows;
	}
	
	@JsonIgnore
	public Map<String, String> getContentsMap() {
		return components;
	}

	public static DataSourceSnapshot just(String string) {
		return new DataSourceSnapshot("just", ImmutableMap.of("just", string), ImmutableList.<Arrow>of());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrows == null) ? 0 : arrows.hashCode());
		result = prime * result
				+ ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSourceSnapshot other = (DataSourceSnapshot) obj;
		if (arrows == null) {
			if (other.arrows != null)
				return false;
		} else if (!arrows.equals(other.arrows))
			return false;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		return true;
	}
}
