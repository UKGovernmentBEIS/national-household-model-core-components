package uk.org.cse.stockimport.ehcs2010.spss;

import uk.org.cse.stockimport.domain.schema.DTOMapper;

public abstract class AbstractAutoHomElementBuilder<T> extends AbstractCSVHomElementBuilder<T> {
	private final DTOMapper<T> mapper;
	private final Class<? extends T> clazz;
	
	public AbstractAutoHomElementBuilder(final Class<? extends T> clazz) {
		this.mapper = new DTOMapper<T>(clazz);
		this.clazz = clazz;
	}
	
	@Override
	public String[] buildHeader(final T exampleElement) {
		return mapper.writeHeader();
	}

	@Override
	public String[] buildRow(final T element) {
		return mapper.write(element);
	}

	@Override
	public String getBuiltClassName() {
		return clazz.getName();
	}

	@Override
	public String getFileName() {
		return mapper.getName();
	}
}
