package org.eclipse.php.smarty.ui.editor.configuration;

import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProposalComputer;

public class SmartyCompletionProposalComputer extends PHPCompletionProposalComputer{

	public SmartyCompletionProposalComputer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		// TODO Auto-generated method stub
		return new SmartyTemplateCompletionProcessor(context);
	}
}
