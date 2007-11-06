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
package org.eclipse.php.smarty.internal.core.documentModel.parser.partitioner;

import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.smarty.internal.core.documentModel.parser.SmartyRegionContext;
import org.eclipse.wst.html.core.internal.text.StructuredTextPartitionerForHTML;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class SmartyStructuredTextPartitioner extends StructuredTextPartitionerForHTML {

	public String getContentType(final int offset, final boolean preferOpenPartitions) {
		final ITypedRegion partition = getPartition(offset);
		return partition == null ? null : partition.getType();
	}

	public String getPartitionType(final ITextRegion region, final int offset) {
		// if php region
		if (SmartyPartitionTypes.isSmartyPartition(region.getType()))
			return SmartyPartitionTypes.SMARTY_DEFAULT;

		// else do super 
		return super.getPartitionType(region, offset);
	}

	public static String[] getConfiguredContentTypes() {
		return SmartyPartitionTypes.configuredPartitions;
	}
	
	public IDocumentPartitioner newInstance() {
		return new SmartyStructuredTextPartitioner();
	}

	public ITypedRegion getPartition(int offset) {
		
		// in case we are in the end of document
		// we return the partition of last region
		int docLength = fStructuredDocument.getLength();
		if (offset == docLength) {
			return super.getPartition(offset - 1);
		}
		return super.getPartition(offset);
	}
	
	
}
