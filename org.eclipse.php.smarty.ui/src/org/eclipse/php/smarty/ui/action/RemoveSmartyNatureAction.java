package org.eclipse.php.smarty.ui.action;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.smarty.core.SmartyCorePlugin;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RemoveSmartyNatureAction implements IObjectActionDelegate {

	private IWorkbenchPart part = null;
	private IScriptProject selProj = null; 
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		part = targetPart;
	}

	public void run(IAction action) {
		if(selProj == null)
			return;
		
		try {

			IProjectDescription description = selProj.getProject().getDescription();
			
			if(description.hasNature(SmartyCorePlugin.NATURE_ID)) {
				//
				// remove the nature
				//
				String[] prevNatures = description.getNatureIds();
				String[] newNatures = new String[prevNatures.length - 1];
				
				int i=0;
				for (String nature : prevNatures) {
					if(!nature.equals(SmartyCorePlugin.NATURE_ID)) {
						newNatures[i++] = nature;
					}
				}
				
				description.setNatureIds(newNatures);
				selProj.getProject().setDescription(description, null);  
				
//				MessageDialog.openInformation(
//					this.part.getSite().getShell(),
//					"Smarty",
//					"Smarty nature removed");
			}
		}
		catch (Exception e) {
			MessageDialog.openInformation(
			         this.part.getSite().getShell(),
			         "Smarty",
			         "removing Smarty nature can not be executed");
			
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {		
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection)selection;
			if(!selection.isEmpty()) {
				selProj = (IScriptProject)structuredSel.getFirstElement();
				
				IProjectDescription description;
				try {
					description = selProj.getProject().getDescription();

					if(!description.hasNature(SmartyCorePlugin.NATURE_ID)) {
						action.setEnabled(false);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
}
