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

public class AddSmartyNatureAction implements IObjectActionDelegate {

	private IScriptProject selProj = null;
	private IWorkbenchPart part = null; 
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		part = targetPart;
	}

	public void run(IAction action) {
		if(selProj == null)
			return;
		
		try {
			
			IProjectDescription description = selProj.getProject().getDescription();
            String[] prevNatures = description.getNatureIds();
            String[] newNatures = new String[prevNatures.length + 1];
            System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
            newNatures[prevNatures.length] = SmartyCorePlugin.NATURE_ID;                       
            description.setNatureIds(newNatures);
            selProj.getProject().setDescription(description, null);  
            
//            MessageDialog.openInformation(
//			         this.part.getSite().getShell(),
//			         "Smarty",
//			         "Smarty nature created");
		}
		catch (Exception e) {
			MessageDialog.openInformation(
			         this.part.getSite().getShell(),
			         "Smarty",
			         "adding Smarty nature can not be executed");
			
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

					if(description.hasNature(SmartyCorePlugin.NATURE_ID)) {
						action.setEnabled(false);
					}
				} catch (CoreException e) {
					e.printStackTrace();
				}
				
			}
		}
	}	
	
}
