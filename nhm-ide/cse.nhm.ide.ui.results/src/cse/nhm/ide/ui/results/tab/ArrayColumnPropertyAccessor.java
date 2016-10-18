package cse.nhm.ide.ui.results.tab;

import java.util.Arrays;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

public class ArrayColumnPropertyAccessor implements IColumnPropertyAccessor<Object[]> {
	private List<String> header;

	public ArrayColumnPropertyAccessor(String[] header) {
		this.header = Arrays.asList(header);
	}

	@Override
	public Object getDataValue(Object[] rowObject, int columnIndex) {
		return rowObject[columnIndex];
	}

	@Override
	public void setDataValue(Object[] rowObject, int columnIndex, Object newValue) {
		rowObject[columnIndex] = newValue;
	}

	@Override
	public int getColumnCount() {
		return header.size();
	}

	@Override
	public String getColumnProperty(int columnIndex) {
		return header.get(columnIndex);
	}

	@Override
	public int getColumnIndex(String propertyName) {
		return header.indexOf(propertyName);
	}
}
