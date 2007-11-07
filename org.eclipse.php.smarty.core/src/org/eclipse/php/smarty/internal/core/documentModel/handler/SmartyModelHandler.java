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
package org.eclipse.php.smarty.internal.core.documentModel.handler;

import org.eclipse.php.internal.core.documentModel.encoding.PHPDocumentCharsetDetector;
import org.eclipse.php.internal.core.documentModel.handler.PHPModelHandler;
import org.eclipse.php.internal.core.documentModel.loader.PHPDocumentLoader;
import org.eclipse.php.internal.core.documentModel.loader.PHPModelLoader;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.smarty.internal.core.documentModel.loader.SmartyDocumentLoader;
import org.eclipse.php.smarty.internal.core.documentModel.loader.SmartyModelLoader;
import org.eclipse.php.smarty.internal.core.documentModel.provisional.contenttype.ContentTypeIdForSmarty;
import org.eclipse.wst.sse.core.internal.document.IDocumentCharsetDetector;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;

public class SmartyModelHandler extends PHPModelHandler {

	/**
	 * Needs to match what's in plugin registry. 
	 * In fact, can be overwritten at run time with 
	 * what's in registry! (so should never be 'final')
	 */
	private static String ModelHandlerID = "org.eclipse.php.smarty.core.documentModel.handler"; //$NON-NLS-1$

	public SmartyModelHandler() {
		super();
		setId(ModelHandlerID);
		setAssociatedContentTypeId(ContentTypeIdForSmarty.CONTENT_TYPE_ID_SMARTY);
	}

	public IModelLoader getModelLoader() {
		return new SmartyModelLoader();
	}

	public IDocumentCharsetDetector getEncodingDetector() {
		return new PHPDocumentCharsetDetector();
	}

	public IDocumentLoader getDocumentLoader() {
		return new SmartyDocumentLoader();
	}

}
