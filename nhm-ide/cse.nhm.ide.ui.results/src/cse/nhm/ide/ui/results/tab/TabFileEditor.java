package cse.nhm.ide.ui.results.tab;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.sort.SortConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultCharacterDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDateDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultDoubleDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultIntegerDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsSortModel;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow.DefaultGlazedListsFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultColumnHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultRowHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.theme.ModernNatTableThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.menu.HeaderMenuConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

/*
 * TODO Group, filter and sort
 * depend on types rather.
 * in particular filtering ranges would be nice
 * https://eclipse.googlesource.com/nattable/org.eclipse.nebula.widgets.nattable/+/8769620d582ee020008032e45e259a87287828af/org.eclipse.nebula.widgets.nattable.examples/src/org/eclipse/nebula/widgets/nattable/examples/_700_Integration/_706_SortableGroupByWithFilterExample.java
 */
public class TabFileEditor extends EditorPart {
	private NatTable tableControl;
	TypedCSVFile data;

	public TabFileEditor() {
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

	}

	@Override
	protected void setInput(final IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
		if (input instanceof IURIEditorInput) {
			setContentDescription(((IURIEditorInput) input).getURI().toString());
			data = TypedCSVFile.load((IURIEditorInput) input);
			display(data);
		}
	}

	private void display(final TypedCSVFile data) {
		if (tableControl == null) return;

        final ConfigRegistry configRegistry = new ConfigRegistry();
        final IColumnPropertyAccessor<Object[]> columnPropertyAccessor =
        		new ArrayColumnPropertyAccessor(data.header);

        final BodyLayerStack<Object[]> bodyLayerStack =
        		new BodyLayerStack<>(data.rows,
        				columnPropertyAccessor);
        bodyLayerStack.getBodyDataLayer().setConfigLabelAccumulator(new ColumnLabelAccumulator());

        for (int i = 0; i<data.types.length; i++) {
        	IDisplayConverter converter = null;

        	switch (data.types[i].type) {
			case BOOL:
				converter = new DefaultBooleanDisplayConverter();
				break;
			case DATE:
				converter = new DefaultDateDisplayConverter("yyyy/MM/dd");
				break;
			case EMPTY:
				break;
			case MIXED:
				break;
			case INTEGER:
				converter = new DefaultIntegerDisplayConverter();
				break;
			case DOUBLE:
			case NUMBER:
				converter = new DefaultDoubleDisplayConverter();
				break;
			case STRING:
				converter = new DefaultCharacterDisplayConverter();
				break;
			default:
				break;

        	}
        	if (converter != null)
        		configRegistry.registerConfigAttribute(
        			CellConfigAttributes.DISPLAY_CONVERTER,
        			converter,
        			DisplayMode.NORMAL,
        			ColumnLabelAccumulator.COLUMN_LABEL_PREFIX + i);

		configRegistry.registerConfigAttribute(
		     SortConfigAttributes.SORT_COMPARATOR,
		     data.types[i],
		     DisplayMode.NORMAL,
		     ColumnLabelAccumulator.COLUMN_LABEL_PREFIX + i
		     );
        }
        final IDataProvider columnHeaderDataProvider =
                new DefaultColumnHeaderDataProvider(data.header);
        final DataLayer columnHeaderDataLayer =
                new DefaultColumnHeaderDataLayer(columnHeaderDataProvider);

        columnHeaderDataLayer.setConfigLabelAccumulator(new ColumnLabelAccumulator());
        final ILayer columnHeaderLayer =
        		new SortHeaderLayer<Object[]> (
                new ColumnHeaderLayer(
                        columnHeaderDataLayer,
                        bodyLayerStack,
                        bodyLayerStack.getSelectionLayer()),
                new GlazedListsSortModel<Object[]>(
                		bodyLayerStack.getSortedList(),
                		columnPropertyAccessor,
                		configRegistry,
                		columnHeaderDataLayer)
                );


        final FilterRowHeaderComposite<Object[]> filterRowHeaderLayer =
                new FilterRowHeaderComposite<Object[]>(
		     new DefaultGlazedListsFilterStrategy<>(
			  bodyLayerStack.getFilterList(),
			  columnPropertyAccessor,
			  configRegistry),
		     columnHeaderLayer,
		     columnHeaderDataProvider,
		     configRegistry);

		final IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(bodyLayerStack.getBodyDataProvider());
		final DataLayer rowHeaderDataLayer = new DefaultRowHeaderDataLayer(rowHeaderDataProvider);
		final ILayer rowHeaderLayer = new RowHeaderLayer(rowHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());


        // build the corner layer
        final IDataProvider cornerDataProvider =
                new DefaultCornerDataProvider(
                        columnHeaderDataProvider,
                        rowHeaderDataProvider);
        final DataLayer cornerDataLayer =
                new DataLayer(cornerDataProvider);
        final ILayer cornerLayer =
                new CornerLayer(
                        cornerDataLayer,
                        rowHeaderLayer,
                        filterRowHeaderLayer);

        // build the grid layer
        final GridLayer gridLayer =
                new GridLayer(
                        bodyLayerStack,
                        filterRowHeaderLayer,
                        rowHeaderLayer,
                        cornerLayer);


        gridLayer.setConfigLabelAccumulator(new ColumnLabelAccumulator());

        tableControl.setLayer(gridLayer);
        tableControl.setConfigRegistry(configRegistry);
        tableControl.addConfiguration(new DefaultNatTableStyleConfiguration());
        tableControl.addConfiguration(new SingleClickSortConfiguration());

        tableControl.addConfiguration(new HeaderMenuConfiguration(tableControl) {
            @Override
            protected PopupMenuBuilder createCornerMenu(final NatTable natTable) {
                return super.createCornerMenu(natTable)
                        .withStateManagerMenuItemProvider();
            }
        });

		tableControl.configure();
		tableControl.setTheme(new ModernNatTableThemeConfiguration());
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(new GridLayout());
		parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		parent.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));

		this.tableControl = new NatTable(parent, false);

		display(data);

		tableControl.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		tableControl.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_BLACK));

		GridDataFactory.fillDefaults().grab(true, true).applyTo(this.tableControl);

	}

	@Override
	public void setFocus() {
	}
}
