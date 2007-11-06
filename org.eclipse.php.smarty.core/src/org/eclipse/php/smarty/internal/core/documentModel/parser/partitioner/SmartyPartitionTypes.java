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

import java.util.Arrays;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public abstract class SmartyPartitionTypes {

	public static final String SMARTY_DEFAULT = "org.eclipse.php.SMARTY_DEFAULT"; //$NON-NLS-1$
	public static final String SMARTY_COMMENT = "org.eclipse.php.SMARTY_COMMENT"; //$NON-NLS-1$
	public static final String SMARTY_STRING = "org.eclipse.php.SMARTY_STRING"; //$NON-NLS-1$
	public static final String SMARTY_QUOTED_STRING = "org.eclipse.php.SMARTY_QUOTED_STRING"; //$NON-NLS-1$
	public static final String SMARTY_BACKTICK = "org.eclipse.php.SMARTY_BACKTICK"; //$NON-NLS-1$

	public final static String[] configuredPartitions = new String[] { SMARTY_DEFAULT, SMARTY_COMMENT, SMARTY_STRING, SMARTY_QUOTED_STRING, SMARTY_BACKTICK};

	public static boolean isSmartyPartition(String regionType) {
		return regionType == SMARTY_DEFAULT || regionType == SMARTY_COMMENT || regionType == SMARTY_STRING || regionType == SMARTY_QUOTED_STRING || regionType == SMARTY_BACKTICK;
	}

	/**
	 * Returns starting region of the current partition
	 * 
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return Starting region of the current partition
	 * @throws BadLocationException
	 */
	public static final ITextRegion getPartitionStartRegion(IPhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		ITextRegion internalRegion = region.getPhpToken(offset);
		ITextRegion startRegion = internalRegion;
		while (internalRegion.getStart() != 0) {
			internalRegion = region.getPhpToken(internalRegion.getStart() - 1);
			if (region.getPartition(internalRegion.getStart()) != partitionType) {
				break;
			}
			startRegion = internalRegion;
		}
		return startRegion;
	}

	/**
	 * Returns offset where the current partition starts
	 * 
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return Starting offset of the current partition
	 * @throws BadLocationException
	 */
	public static final int getPartitionStart(IPhpScriptRegion region, int offset) throws BadLocationException {
		ITextRegion startRegion = getPartitionStartRegion(region, offset);
		return startRegion.getStart();
	}

	/**
	 * Returns region current partition ends on
	 * 
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return Ending region of the current partition
	 * @throws BadLocationException
	 */
	public static final ITextRegion getPartitionEndRegion(IPhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		ITextRegion internalRegion = region.getPhpToken(offset);
		ITextRegion endRegion = internalRegion;
		while (internalRegion.getEnd() != region.getLength()) {
			internalRegion = region.getPhpToken(internalRegion.getEnd());
			if (region.getPartition(internalRegion.getStart()) != partitionType) {
				break;
			}
			endRegion = internalRegion;
		}
		return endRegion;
	}

	/**
	 * Returns offset where the current partition ends
	 * 
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return Ending offset of the current partition
	 * @throws BadLocationException
	 */
	public static final int getPartitionEnd(IPhpScriptRegion region, int offset) throws BadLocationException {
		ITextRegion endRegion = getPartitionEndRegion(region, offset);
		return endRegion.getEnd();
	}

	/**
	 * Returns partition which corresponds to the provided offset
	 * 
	 * @param region Region containing current offset
	 * @param offset Current position relative to the containing region
	 * @return typed region containing partition
	 * @throws BadLocationException
	 */
	public static final ITypedRegion getPartition(IPhpScriptRegion region, int offset) throws BadLocationException {
		String partitionType = region.getPartition(offset);
		int startOffset = getPartitionStart(region, offset);
		int endOffset = getPartitionEnd(region, offset);
		return new TypedRegion(startOffset, endOffset - startOffset, partitionType);
	}
}
