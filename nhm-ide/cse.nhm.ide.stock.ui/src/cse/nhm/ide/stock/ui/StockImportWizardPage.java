/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package cse.nhm.ide.stock.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;


public class StockImportWizardPage extends WizardNewFileCreationPage {
	
	protected FileFieldEditor editor;
	private java.nio.file.Path result;

	public StockImportWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		setTitle(pageName); //NON-NLS-1
		setDescription("Create a stock from a stock import package"); //NON-NLS-1
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createAdvancedControls(org.eclipse.swt.widgets.Composite)
	 */	
	protected void createAdvancedControls(Composite parent) {
		Composite fileSelectionArea = new Composite(parent, SWT.NONE);
		GridData fileSelectionData = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL);
		fileSelectionArea.setLayoutData(fileSelectionData);

		GridLayout fileSelectionLayout = new GridLayout();
		fileSelectionLayout.numColumns = 3;
		fileSelectionLayout.makeColumnsEqualWidth = false;
		fileSelectionLayout.marginWidth = 0;
		fileSelectionLayout.marginHeight = 0;
		fileSelectionArea.setLayout(fileSelectionLayout);
		
		editor = new FileFieldEditor("fileSelect","Select File: ",fileSelectionArea); //NON-NLS-1 //NON-NLS-2
		editor.getTextControl(fileSelectionArea).addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				IPath path = new Path(StockImportWizardPage.this.editor.getStringValue());
				// strip extension and add .stock
				final String ext = path.getFileExtension();
				final String name = path.lastSegment();
				final String nameWithExt = name.substring(0, name.length() - ext.length()) + "stock";
				setFileName(nameWithExt);
			}
		});
		String[] extensions = new String[] { "*.zip" }; //NON-NLS-1
		editor.setFileExtensions(extensions);
		fileSelectionArea.moveAbove(null);

	}
	
	 /* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createLinkTarget()
	 */
	protected void createLinkTarget() {
	}

	public void setPathToResult(final java.nio.file.Path result) {
		this.result = result;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
	protected InputStream getInitialContents() {
		try {
			return Files.newInputStream(result);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public java.nio.file.Path getPathToSource() {
		return Paths.get(editor.getStringValue());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getNewFileLabel()
	 */
	protected String getNewFileLabel() {
		return "Imported Stock Name:"; //NON-NLS-1
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validateLinkedResource()
	 */
	protected IStatus validateLinkedResource() {
		return new Status(IStatus.OK, "cse.nhm.ide.stock.ui", IStatus.OK, "", null); //NON-NLS-1 //NON-NLS-2
	}
	
	@Override
	public IFile createNewFile() {
		final IFile in = super.createNewFile();
		if (in != null) {
			try {
				Files.delete(result);
				result = null;
			} catch (IOException e) {
			}
		}
		return in;
	}
}
