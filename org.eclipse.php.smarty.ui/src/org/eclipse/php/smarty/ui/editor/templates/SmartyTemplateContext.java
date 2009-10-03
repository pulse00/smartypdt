package org.eclipse.php.smarty.ui.editor.templates;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.TemplateContextType;

public class SmartyTemplateContext extends ScriptTemplateContext {

	protected SmartyTemplateContext(TemplateContextType type,
			IDocument document, int completionOffset, int completionLength,
			ISourceModule sourceModule) {
		super(type, document, completionOffset, completionLength, sourceModule);
		// TODO Auto-generated constructor stub
	}

}
