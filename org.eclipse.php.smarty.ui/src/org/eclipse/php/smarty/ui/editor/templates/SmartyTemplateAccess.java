package org.eclipse.php.smarty.ui.editor.templates;

import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.smarty.ui.SmartyUiPlugin;

public class SmartyTemplateAccess extends ScriptTemplateAccess {
	// Template
	private static final String CUSTOM_TEMPLATES_KEY = "org.eclipse.php.smarty.Templates"; //$NON-NLS-1$

	private static SmartyTemplateAccess instance;

	public static SmartyTemplateAccess getInstance() {
		if (instance == null) {
			instance = new SmartyTemplateAccess();
		}

		return instance;
	}

	/*
	 * @see org.eclipse.dltk.ui.templates.ScriptTemplateAccess#getPreferenceStore()
	 */
	protected IPreferenceStore getPreferenceStore() {
		return SmartyUiPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * @see org.eclipse.dltk.ui.templates.ScriptTemplateAccess#getContextTypeId()
	 */
	protected String getContextTypeId() {
		return SmartyTemplateContextType.SMARTY_CONTEXT_TYPE_ID;
	}

	/*
	 * @see org.eclipse.dltk.ui.templates.ScriptTemplateAccess#getCustomTemplatesKey()
	 */
	protected String getCustomTemplatesKey() {
		return CUSTOM_TEMPLATES_KEY;
	}
}
