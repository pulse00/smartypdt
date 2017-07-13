/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.smarty.ui.editor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.ui.editor.highlighter.LineStyleProviderForPHP;
import org.eclipse.php.smarty.internal.core.documentModel.parser.SmartyRegionContext;
import org.eclipse.php.smarty.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * Colors the Smarty tokens 
 * @author Roy, 2007
 */
public class LineStyleProviderForSmarty extends LineStyleProviderForPHP {

	static {
		fColorTypes.put(SmartyRegionContext.SMARTY_OPEN, PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_CLOSE, PreferenceConstants.EDITOR_BOUNDARYMARKER_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_CONTENT, PreferenceConstants.EDITOR_LABEL_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_COMMENT, PreferenceConstants.EDITOR_COMMENT_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_VARIABLE, PreferenceConstants.EDITOR_VARIABLE_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_DELIMITER, PreferenceConstants.EDITOR_LABEL_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_WHITESPACE, PreferenceConstants.EDITOR_LABEL_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_NUMBER, PreferenceConstants.EDITOR_NUMBER_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_LABEL, PreferenceConstants.EDITOR_LABEL_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_CONSTANT_ENCAPSED_STRING, PreferenceConstants.EDITOR_STRING_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_DOUBLE_QUOTES_CONTENT, PreferenceConstants.EDITOR_STRING_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_DOUBLE_QUOTES_START, PreferenceConstants.EDITOR_STRING_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_DOUBLE_QUOTES_END, PreferenceConstants.EDITOR_STRING_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_BACKTICK_END, PreferenceConstants.EDITOR_LABEL_COLOR);
		fColorTypes.put(SmartyRegionContext.SMARTY_BACKTICK_START, PreferenceConstants.EDITOR_LABEL_COLOR);
	}

	protected TextAttribute getAttributeFor(ITextRegion region) {
		final String type = region.getType();
		if (type != null && type.startsWith("SMARTY_")) {
			return getAttributeFor(type);
		}

		return super.getAttributeFor(region);
	}

}
