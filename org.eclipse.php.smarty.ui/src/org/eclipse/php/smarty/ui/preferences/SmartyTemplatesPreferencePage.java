package org.eclipse.php.smarty.ui.preferences;

import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.smarty.ui.SmartyUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

public class SmartyTemplatesPreferencePage extends TemplatePreferencePage {

	
	public SmartyTemplatesPreferencePage() {
		setPreferenceStore(SmartyUiPlugin.getDefault().getPreferenceStore());
		setTemplateStore(SmartyUiPlugin.getDefault().getTemplateStore());
		setContextTypeRegistry(SmartyUiPlugin.getDefault().getTemplateContextRegistry());
	}
	
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IPHPHelpContextIds.TEMPLATES_PREFERENCES);
		getControl().notifyListeners(SWT.Help, new Event());
    }
}