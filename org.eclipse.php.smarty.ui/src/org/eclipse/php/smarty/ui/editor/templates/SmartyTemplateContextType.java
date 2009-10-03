package org.eclipse.php.smarty.ui.editor.templates;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.IDocument;

public class SmartyTemplateContextType extends ScriptTemplateContextType {
	
	public static final String SMARTY_CONTEXT_TYPE_ID = "smarty"; //$NON-NLS-1$
	
	public ScriptTemplateContext createContext(IDocument document, int offset, int length, ISourceModule sourceModule) {
		return new SmartyTemplateContext(this, document, offset, length, sourceModule);
	}

}
