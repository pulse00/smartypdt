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

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.php.internal.core.documentModel.loader.PHPDocumentLoader;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.smarty.internal.core.documentModel.parser.SmartySourceParser;
import org.eclipse.php.smarty.internal.core.documentModel.parser.partitioner.SmartyStructuredTextPartitioner;
import org.eclipse.wst.sse.core.internal.document.IDocumentLoader;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;

public class SmartyDocumentLoader extends PHPDocumentLoader {
	
	public RegionParser getParser() {
		SmartySourceParser parser = new SmartySourceParser();
		// for the "static HTML" case, we need to initialize
		// Blocktags here.
		addHTMLishTag(parser, "script"); //$NON-NLS-1$
		addHTMLishTag(parser, "style"); //$NON-NLS-1$
		return parser;
	}

	public IDocumentLoader newInstance() {
		return new SmartyDocumentLoader();
	}

	public IDocumentPartitioner getDefaultDocumentPartitioner() {
		return new SmartyStructuredTextPartitioner();
	}
}
