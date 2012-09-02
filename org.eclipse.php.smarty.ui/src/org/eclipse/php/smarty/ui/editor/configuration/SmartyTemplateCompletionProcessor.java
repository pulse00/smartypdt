package org.eclipse.php.smarty.ui.editor.configuration;

import org.eclipse.dltk.ui.templates.ScriptTemplateAccess;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateCompletionProcessor;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.php.smarty.ui.SmartyUiPlugin;
import org.eclipse.php.smarty.ui.editor.templates.SmartyTemplateAccess;
import org.eclipse.php.smarty.ui.editor.templates.SmartyTemplateContextType;
import org.eclipse.swt.graphics.Image;

@SuppressWarnings("restriction")
public class SmartyTemplateCompletionProcessor extends
		PhpTemplateCompletionProcessor {

	public SmartyTemplateCompletionProcessor(
			ScriptContentAssistInvocationContext context, boolean explicit) {
		super(context, explicit);
		setContextTypeId(SmartyTemplateContextType.SMARTY_CONTEXT_TYPE_ID);
	}
	
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return super.computeCompletionProposals(viewer, offset);
	}
	
	@Override
	protected String getContextTypeId() {
		return SmartyTemplateContextType.SMARTY_CONTEXT_TYPE_ID;
	}
	
	@Override
	protected ScriptTemplateAccess getTemplateAccess() {
		// TODO Auto-generated method stub
		return SmartyTemplateAccess.getInstance();
	}
	
	@Override
	protected ICompletionProposal createProposal(Template template,
			TemplateContext context, IRegion region, int relevance) {
		// TODO Auto-generated method stub
		return new SmartyTemplateProposal(template, context, region, getImage(template), relevance);
	}
	
	protected Image getImage(Template template) {
		//TODO change PHPPluginImages.DESC_TEMPLATE to smarty's
		return SmartyUiPlugin.getImageDescriptorRegistry().get(PHPPluginImages.DESC_TEMPLATE);
	}

	protected ContextTypeRegistry getTemplateContextRegistry() {
		return SmartyUiPlugin.getDefault().getTemplateContextRegistry();
	}

	protected TemplateStore getTemplateStore() {
		return SmartyUiPlugin.getDefault().getTemplateStore();
	}

}
