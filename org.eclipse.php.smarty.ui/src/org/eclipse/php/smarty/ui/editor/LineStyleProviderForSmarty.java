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

import java.util.Collection;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.provisional.style.AbstractLineStyleProvider;
import org.eclipse.wst.sse.ui.internal.provisional.style.Highlighter;
import org.eclipse.wst.sse.ui.internal.provisional.style.LineStyleProvider;

/**
 * Colors the Smarty tokens 
 * @author Roy, 2007
 */
public class LineStyleProviderForSmarty extends AbstractLineStyleProvider implements LineStyleProvider {

	@Override
	protected TextAttribute getAttributeFor(ITextRegion region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IPreferenceStore getColorPreferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void loadColors() {
		// TODO Auto-generated method stub
		
	}
}
