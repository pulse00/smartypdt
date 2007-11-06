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
package org.eclipse.php.smarty.internal.core.documentModel.parser;

public interface SmartyRegionContext {
	public static final String SMARTY_OPEN = "SMARTY_OPEN"; //$NON-NLS-1$
	public static final String SMARTY_CLOSE = "SMARTY_CLOSE"; //$NON-NLS-1$
	public static final String SMARTY_CONTENT = "SMARTY_CONTENT"; //$NON-NLS-1$
	public static final String SMARTY_COMMENT = "SMARTY_COMMENT"; //$NON-NLS-1$
	public static final String SMARTY_VARIABLE = "SMARTY_VARIABLE"; //$NON-NLS-1$
	public static final String SMARTY_DELIMITER = "SMARTY_DELIMITER"; //$NON-NLS-1$
	public static final String SMARTY_WHITESPACE = "SMARTY_WHITESPACE"; //$NON-NLS-1$
	public static final String SMARTY_NUMBER = "SMARTY_NUMBER"; //$NON-NLS-1$	
	public static final String SMARTY_LABEL = "SMARTY_LABEL"; //$NON-NLS-1$
	public static final String SMARTY_CONSTANT_ENCAPSED_STRING = "SMARTY_CONSTANT_ENCAPSED_STRING"; //$NON-NLS-1$
	public static final String SMARTY_DOUBLE_QUOTES_CONTENT = "SMARTY_DOUBLE_QUOTES_CONTENT"; //$NON-NLS-1$	
	public static final String SMARTY_DOUBLE_QUOTES_START = "SMARTY_DOUBLE_QUOTES_START"; //$NON-NLS-1$
	public static final String SMARTY_DOUBLE_QUOTES_END = "SMARTY_DOUBLE_QUOTES_END"; //$NON-NLS-1$
	public static final String SMARTY_BACKTICK_END = "ST_SMARTY_BACKTICK_END"; //$NON-NLS-1$	
	public static final String SMARTY_BACKTICK_START = "SMARTY_BACKTICK_START"; //$NON-NLS-1$	

}
