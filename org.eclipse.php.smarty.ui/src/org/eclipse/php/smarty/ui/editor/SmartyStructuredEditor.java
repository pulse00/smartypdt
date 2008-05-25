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

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionChangedHandler;
import org.eclipse.php.internal.core.resources.ExternalFileWrapper;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.smarty.internal.core.documentModel.parser.SmartySourceParser;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;

public class SmartyStructuredEditor extends PHPStructuredEditor {
	private boolean isExternal;
	private IPreferencesPropagatorListener phpVersionListener;
	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		IResource resource = null;
		IPath externalPath = null;
		isExternal = false;
		if (input instanceof IFileEditorInput) {
			// This is the existing workspace file
			final IFileEditorInput fileInput = (IFileEditorInput) input;
			resource = fileInput.getFile();

			//we add this test to provide model for PHP files opened from RSE (temp) project
			IProject proj = resource.getProject();
			if (proj.isAccessible() && proj.hasNature(PHPUiConstants.RSE_TEMP_PROJECT_NATURE_ID)) {
				PHPWorkspaceModelManager.getInstance().getModelForProject(proj, true);
			}
		} else if (input instanceof IStorageEditorInput) {
			// This kind of editor input usually means non-workspace file, like
			// PHP file which comes from include path, remote file which comes from
			// Web server while debugging, file from ZIP archive, etc...

			final IStorageEditorInput editorInput = (IStorageEditorInput) input;
			final IStorage storage = editorInput.getStorage();

			if (storage instanceof ZipEntryStorage) {
				resource = ((ZipEntryStorage) storage).getProject();
			} else if (storage instanceof LocalFileStorage && ((LocalFileStorage) storage).getProject() != null) {
				resource = ((LocalFileStorage) storage).getProject();
			} else {
				// This is, probably, a remote storage:
				externalPath = storage.getFullPath();
				resource = ExternalFileWrapper.createFile(externalPath.toString());
			}
		} else if (input instanceof IURIEditorInput || input instanceof NonExistingPHPFileEditorInput) {
			// External file editor input. It's usually used when opening PHP file
			// via "File -> Open File" menu option, or using D&D:
			//OR
			// When we are dealing with an Untitled PHP document and the underlying PHP file
			// does not really exist, but is still considered as an "External" file.
			if (input instanceof NonExistingPHPFileEditorInput) {
				externalPath = ((NonExistingPHPFileEditorInput) input).getPath();
			} else {
				externalPath = URIUtil.toPath(((IURIEditorInput) input).getURI());
			}
			resource = ExternalFileWrapper.createFile(externalPath.toString());
		}

		if (resource instanceof IFile) {
			if (PHPModelUtil.isPhpFile((IFile) resource)) {
				// Add file decorator entry to the list of external files:
				if (externalPath != null && resource instanceof ExternalFileWrapper) {
					ExternalFilesRegistry.getInstance().addFileEntry(externalPath.toString(), (ExternalFileWrapper) resource);
					isExternal = true;
				}
				// Remove an older record from the external files registry in case this editor
				// is being reused to display a new content.
				IEditorInput oldInput = getEditorInput();
				if (oldInput != null && oldInput instanceof IStorageEditorInput) {
					String storagePath = ((IStorageEditorInput) oldInput).getStorage().getFullPath().toString();
					ExternalFilesRegistry.getInstance().removeFileEntry(storagePath);
				}
				SmartySourceParser.editFile.set(resource);
				super.doSetInput(input);
				
				PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(phpVersionListener);
			} else {
				super.doSetInput(input);
				//				close(false);
			}
		} else {
			super.doSetInput(input);
		}
	}
	
}
