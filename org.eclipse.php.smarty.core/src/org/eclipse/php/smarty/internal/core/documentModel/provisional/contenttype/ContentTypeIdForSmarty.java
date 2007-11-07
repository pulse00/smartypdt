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
package org.eclipse.php.smarty.internal.core.documentModel.provisional.contenttype;

import org.eclipse.php.smarty.core.SmartyCorePlugin;

public class ContentTypeIdForSmarty {
	/**
	 * The value of the contenttype id field must match what is specified in
	 * plugin.xml file. Note: this value is intentially set with default
	 * protected method so it will not be inlined.
	 */
	public final static String CONTENT_TYPE_ID_SMARTY = getConstantString();

	/**
	 * Don't allow instantiation.
	 */
	private ContentTypeIdForSmarty() {
		super();
	}

	static String getConstantString() {
		return SmartyCorePlugin.PLUGIN_ID + ".template"; //$NON-NLS-1$
	}
}