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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.smarty.internal.core.documentModel.parser.SmartySourceParser;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;

public class SmartyStructuredEditor extends PHPStructuredEditor {
	private boolean isExternal;
	private IPreferencesPropagatorListener phpVersionListener;

	public SmartyStructuredEditor() {
	}
	@SuppressWarnings({ "restriction", "unchecked" })
	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		IResource resource = null;
		isExternal = false;

		if (input instanceof IFileEditorInput) {
			// This is the existing workspace file
			final IFileEditorInput fileInput = (IFileEditorInput) input;
			resource = fileInput.getFile();
		} else if (input instanceof IStorageEditorInput) {
			// This kind of editor input usually means non-workspace file, like
			// PHP file which comes from include path, remote file which comes
			// from
			// Web server while debugging, file from ZIP archive, etc...

//			final IStorageEditorInput editorInput = (IStorageEditorInput) input;
//			final IStorage storage = editorInput.getStorage();

//			if (storage instanceof ZipEntryStorage) {
//				resource = ((ZipEntryStorage) storage).getProject();
//			}
		}

		if (resource instanceof IFile) {
			if (PHPToolkitUtil.isPhpFile((IFile) resource) || "tpl".equals(resource.getFullPath().getFileExtension())) {

				SmartySourceParser.editFile.set(resource);

				super.doSetInput(input);

			} else {
				super.doSetInput(input);
			}
		} else {
			isExternal = true;
			super.doSetInput(input);
		}

		ImageDescriptor imageDescriptor = input.getImageDescriptor();
		if (imageDescriptor != null) {
			setTitleImage(JFaceResources.getResources().createImageWithDefault(imageDescriptor));
		}
		if (isShowingOverrideIndicators()) {
			installOverrideIndicator(true);
		}
	}
	
}
