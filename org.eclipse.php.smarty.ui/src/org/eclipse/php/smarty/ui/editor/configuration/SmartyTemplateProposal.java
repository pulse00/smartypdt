package org.eclipse.php.smarty.ui.editor.configuration;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.php.internal.ui.editor.templates.PHPTemplateProposal;
import org.eclipse.swt.graphics.Image;

public class SmartyTemplateProposal extends PHPTemplateProposal {

	public SmartyTemplateProposal(Template template, TemplateContext context, IRegion region, Image image, int relevance) {
		super(template, context, region, image, relevance);
	}

}
