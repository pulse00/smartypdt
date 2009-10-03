package org.eclipse.php.smarty.ui;

import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;

public class SmartyContributionContextTypeRegistry extends
		ContributionContextTypeRegistry {
	@Override
	public TemplateContextType getContextType(String id) {
		// TODO Auto-generated method stub
//		if("php".equals(id)){
//			id = "smarty";
//		}
		return super.getContextType(id);
	}
}
