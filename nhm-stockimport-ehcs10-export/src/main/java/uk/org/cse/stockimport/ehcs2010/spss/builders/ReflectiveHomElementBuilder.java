package uk.org.cse.stockimport.ehcs2010.spss.builders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Optional;

import uk.org.cse.stockimport.ehcs2010.spss.AbstractCSVHomElementBuilder;

/**
 * @since 1.0
 */
public abstract class ReflectiveHomElementBuilder<T> extends AbstractCSVHomElementBuilder<T> {
	private final Class<T> elementClass;
	
	protected ReflectiveHomElementBuilder(final Class<T> elementClass) {
		this.elementClass= elementClass;
		initialize();
	}
	
	private final List<Method> getters = new ArrayList<Method>();

	private void initialize() {
		for (final Method m : elementClass.getMethods()) {
			if (m.getParameterTypes().length == 0 && m.getName().startsWith("get") || m.getName().startsWith("is")
					&& m.getReturnType() != Void.TYPE) {
				getters.add(m);
			}
		}
		
		Collections.sort(getters, new Comparator<Method>() {
			@Override
			public int compare(Method arg0, Method arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}});
	}
	
	@Override
	public String getFileName() {
		return elementClass.getSimpleName();
	}

	@Override
	public String[] buildHeader(T exampleElement) {
		final String[] headers = new String[getters.size()];
		
		for (int i = 0 ; i<headers.length; i++) {
			headers[i] = getters.get(i).getName();
			if (headers[i].startsWith("is")) headers[i] = headers[i].substring(2);
			else if (headers[i].startsWith("get")) headers[i] = headers[i].substring(3);
		}

		return headers;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] buildRow(T element) {
		final String[] row = new String[getters.size()];
		
		for (int i = 0 ; i<row.length; i++) {
			final Method getter = getters.get(i);
			row[i] = "";
			try {
				Object o = getter.invoke(element);
				if (o instanceof Optional) {
					if (((Optional) o).isPresent()) {
						final Object p = ((Optional) o).get();
						row[i] = p.toString();
					}
				} else {
					if (o != null) row[i] = o.toString();
				}
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			}
		}
		
		return row;
	}

	@Override
	public String getBuiltClassName() {
		return elementClass.getName();
	}
}
