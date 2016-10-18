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

import java.nio.file.Files;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import cse.nhm.ide.ui.wizards.ModelVersionSelectionPage;

public class StockImportWizard extends Wizard implements IImportWizard {
	
	StockImportWizardPage mainPage;
	ModelVersionSelectionPage modelSelectionPage;
	ImportRunningWizardPage importLogPage;
	
	public StockImportWizard() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		IFile file = mainPage.createNewFile();
        if (file == null)
            return false;
        return true;
	}
	 
	@Override
	public boolean performCancel() {
		try {
			Files.delete(importLogPage.getCompletedFilePath());
		} catch (final Exception ex) {}
		return super.performCancel();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Stock Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);
		mainPage = new StockImportWizardPage("Import Stock",selection); //NON-NLS-1
		modelSelectionPage = new ModelVersionSelectionPage();
		modelSelectionPage.setDescription(
				"The stock will be imported using the stock import code from the selected version. " +
				"The resulting stock will not be automatically reimported if the stock importer is updated.");
		importLogPage = new ImportRunningWizardPage(mainPage, modelSelectionPage);
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        super.addPages(); 
        addPage(mainPage);
        addPage(modelSelectionPage);
        addPage(importLogPage);
    }
}
