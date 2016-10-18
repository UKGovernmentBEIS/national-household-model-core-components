package cse.nhm.ide.ui.results.tab;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.groupBy.GroupByModel;
import org.eclipse.nebula.widgets.nattable.freeze.CompositeFreezeLayer;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TransformedList;


/**
 * Always encapsulate the body layer stack in an AbstractLayerTransform to
 * ensure that the index transformations are performed in later commands.
 * 
 * @param <T>
 */
class BodyLayerStack<T> extends AbstractLayerTransform {
	private final SortedList<T> sortedList;
	private final FilterList<T> filterList;
	private final IDataProvider bodyDataProvider;
	private final DataLayer bodyDataLayer;
	private final SelectionLayer selectionLayer;
	private final GroupByModel groupByModel = new GroupByModel();
	private final GlazedListsEventLayer<T> glazedListsEventLayer;
	private final EventList<T> eventList;

	public BodyLayerStack(final List<T> values,
			final IColumnPropertyAccessor<T> columnPropertyAccessor) {
		eventList = GlazedLists.eventList(values);
        final TransformedList<T, T> rowObjectsGlazedList = GlazedLists.threadSafeList(eventList);

        // use the SortedList constructor with 'null' for the Comparator
        // because the Comparator will be set by configuration
        this.sortedList = new SortedList<T>(rowObjectsGlazedList, null);
        // wrap the SortedList with the FilterList
        this.filterList = new FilterList<T>(this.sortedList);

        this.bodyDataProvider =
                new ListDataProvider<T>(this.filterList, columnPropertyAccessor);
        this.bodyDataLayer = new DataLayer(getBodyDataProvider());

        // layer for event handling of GlazedLists and PropertyChanges
        this.glazedListsEventLayer =
                new GlazedListsEventLayer<T>(this.bodyDataLayer, this.filterList);

        this.selectionLayer = new SelectionLayer(getGlazedListsEventLayer());
        final ViewportLayer viewportLayer = new ViewportLayer(getSelectionLayer());

        final FreezeLayer freezeLayer = new FreezeLayer(this.selectionLayer);
        final CompositeFreezeLayer compositeFreezeLayer =
                new CompositeFreezeLayer(freezeLayer, viewportLayer, this.selectionLayer);

        setUnderlyingLayer(compositeFreezeLayer);
	}

	public SelectionLayer getSelectionLayer() {
		return this.selectionLayer;
	}

	public EventList<T> getEventList() {
		return this.eventList;
	}

	public SortedList<T> getSortedList() {
		return this.sortedList;
	}

	public FilterList<T> getFilterList() {
		return this.filterList;
	}

	public GlazedListsEventLayer<T> getGlazedListsEventLayer() {
		return glazedListsEventLayer;
	}
	
	public IDataProvider getBodyDataProvider() {
		return this.bodyDataProvider;
	}

	public GroupByModel getGroupByModel() {
		return this.groupByModel;
	}

	public DataLayer getBodyDataLayer() {
		return this.bodyDataLayer;
	}
}