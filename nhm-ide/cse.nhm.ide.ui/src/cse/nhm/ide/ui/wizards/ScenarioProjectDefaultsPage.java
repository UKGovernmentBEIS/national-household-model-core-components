package cse.nhm.ide.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.google.common.base.CharMatcher;

public class ScenarioProjectDefaultsPage extends WizardPage {
	private Button createScenario;
	private DateTime startDate;
	private DateTime endDate;
	private CheckboxTreeViewer includesViewer;
	private ComboViewer stockViewer;

	private static void recursiveEnable(final Control c, final boolean enabled) {
		c.setEnabled(enabled);
		if (c instanceof Composite) {
			for (final Control c2 : ((Composite)c).getChildren()) {
				recursiveEnable(c2, enabled);
			}
		}
	}
	
	protected ScenarioProjectDefaultsPage() {
		super("Quick-start");
		setTitle("Quick-start scenario");
		setDescription("Get started quickly by adding a simple scenario to your project");
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(1, false));

		createScenario = new Button(container, SWT.CHECK);
		createScenario.setSelection(true);
		createScenario.setText("Create a quick-start scenario");

		final Group settingsGroup = new Group(container, SWT.NONE);
		settingsGroup.setText("Scenario settings");
		settingsGroup.setLayout(new GridLayout(4, false));
		settingsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		// on linux the datetime is stupid and does not respect the locale
		// on windows, I think it does respect the locale.
		
		final Label l1 = new Label(settingsGroup, SWT.NONE);
		l1.setText("Start date:");

		startDate = new DateTime(settingsGroup, SWT.BORDER);
		startDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label l2 = new Label(settingsGroup, SWT.NONE);
		l2.setText("End date:");

		endDate = new DateTime(settingsGroup, SWT.BORDER);
		endDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		final Label l3 = new Label(settingsGroup, SWT.NONE);
		l3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		l3.setText("Stock to use:");

		stockViewer = new ComboViewer(settingsGroup, SWT.NONE);
		final Combo stockCombo = stockViewer.getCombo();
		stockCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		final Group includesGroup = new Group(container, SWT.NONE);
		includesGroup.setLayout(new FillLayout(SWT.HORIZONTAL));
		includesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		includesGroup.setText("Include modules from other projects:");

		includesViewer = new CheckboxTreeViewer(includesGroup, SWT.BORDER);
		
		final int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		
		startDate.setDay(1);
		startDate.setMonth(0);
		startDate.setYear(thisYear);
		
		endDate.setDay(1);
		endDate.setMonth(0);
		endDate.setYear(thisYear + 5);
		
		// wire up viewers and stuff
		stockViewer.setContentProvider(new ArrayContentProvider());
		stockViewer.setInput(findStocks());
		stockViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IResource) return ((IResource) element).getFullPath().toPortableString();
				return super.getText(element);
			}
		});
		
		includesViewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {}
			
			@Override
			public void dispose() {}
			
			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof Object[]) return true;
				return false;
			}
			
			@Override
			public Object getParent(final Object element) {
				return null; // unsupported seek
			}
			
			@Override
			public Object[] getElements(final Object inputElement) {
				return (Object[]) inputElement;
			}
			
			@Override
			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof Object[]) {
					final Object[] oo = (Object[]) parentElement;
					if (oo.length > 1 && oo[1] instanceof Object[]) return (Object[]) oo[1];
				}
				return null;
			}
		});
		
		includesViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof IResource) return ((IResource) element).getName();
				if (element instanceof Object[]) {
					final Object[] oo = (Object[]) element;
					if (oo.length > 0 && oo[0] instanceof IResource) {
						return ((IResource) oo[0]).getName();
					}
				}
				return super.getText(element);
			}
		});
		
		includesViewer.setInput(findScenarios(ResourcesPlugin.getWorkspace().getRoot()));
		includesViewer.expandAll();
		
		
		// connect up events.
		
		createScenario.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final boolean check = createScenario.getSelection();
				recursiveEnable(settingsGroup, check);
				recursiveEnable(includesGroup, check);
			}
		});
	}

	private Object[] findScenarios(final IContainer container) {
		final List<Object> result = new ArrayList<>();
		try {
			for (final IResource res : container.members()) {
				if (res instanceof IContainer) {
					final Object[] contents = findScenarios((IContainer) res);
					if (contents.length > 0) {
						result.add(new Object[] {res, contents});
					}
				} else if (res instanceof IFile && res.getName().endsWith(".nhm")) {
					result.add(res);
				}
			}
		} catch (final CoreException e) {
			
		}
		
		return result.toArray();
	}
	
	private Object[] findStocks() {
		final List<Object> stocks = new ArrayList<>();
		
		try {
			ResourcesPlugin.getWorkspace().getRoot()
				.accept(new IResourceProxyVisitor() {
					@Override
					public boolean visit(final IResourceProxy proxy) throws CoreException {
						if (proxy.getType() == IResource.FILE && proxy.getName().endsWith(".stock")) {
							stocks.add(proxy.requestResource());
						}
						return true;
					}
				}, 0);
		} catch (final CoreException e) {}
		
		return stocks.toArray();
	}
	
	protected String quote(final String s) {
		if (CharMatcher.WHITESPACE.or(CharMatcher.is('"')).matchesAnyOf(s)) {
			return "\"" + s.replace("\"", "\\\"") + "\"";
		} else {
			return s;
		}
	}
	
	protected void createScenario(final IProject projectRoot) {
		if (createScenario.getSelection()) {
			// try creating scenario
			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			pw.printf(";; quick-start scenario for %s\n", projectRoot.getName());
			
			boolean anyModules = false;
			final Object[] modules = includesViewer.getCheckedElements();
			for (final Object o : modules) {
				if (o instanceof IFile) {
					if (!anyModules)
						pw.println(";; include required modules:");
					anyModules = true;
					final String name = ((IFile)o).getFullPath().toPortableString();
					pw.printf("(include-modules %s)\n", quote(name));
				}
			}
			
			// map over selected includes
			
			pw.println(";; scenario content:");
			
			pw.println("(scenario");
			
			
			pw.printf("    start-date: %s\n", dt(startDate));
			pw.printf("    end-date: %s\n", dt(endDate));
			if (stockViewer.getCombo().getText().isEmpty()) {
				pw.printf("    ;; you need a stock-id: argument to specify a stock.");
			} else {
				pw.printf("    stock-id: %s\n", quote(stockViewer.getCombo().getText()));
			}
			pw.println();

			if (anyModules) {
				pw.println("    ;; make sure our modules are set up correctly");
				pw.println("    (~init-modules)");
				pw.println();
			}
			
			pw.println("    ;; scenario goes here:");
			pw.println("    (on.dates (regularly)");
			pw.println("        (aggregate name: counts");
			pw.println("            (aggregate.count name:population)))");
			
			pw.println("    )");
			
			final IFile f = projectRoot.getFile(projectRoot.getName() + ".nhm");
			try {
				f.create(new ByteArrayInputStream(sw.toString().getBytes(StandardCharsets.UTF_8)), 0, null);
			} catch (final CoreException e) {}
		}
		
	}

	private String dt(final DateTime d) {
		if (d.getDay() == 1 && d.getMonth() == 0) {
			return String.valueOf(d.getYear());
		} else {
			return String.format("%d/%d/%d", d.getDay(), d.getMonth()+1, d.getYear());
		}
	}
}
