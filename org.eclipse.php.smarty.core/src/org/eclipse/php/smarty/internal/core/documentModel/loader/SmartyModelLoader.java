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
package org.eclipse.php.smarty.internal.core.documentModel.loader;

import org.eclipse.php.internal.core.documentModel.loader.PHPModelLoader;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.provisional.IModelLoader;

public class SmartyModelLoader extends PHPModelLoader {

	public IDocumentLoader getDocumentLoader() {
		if (documentLoaderInstance == null) {
			documentLoaderInstance = new SmartyDocumentLoader();
		}
		return documentLoaderInstance;
	}

	public IModelLoader newInstance() {
		return new SmartyModelLoader();
	}

}
