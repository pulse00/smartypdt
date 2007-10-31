package org.eclipse.php.smarty.internal.core.builder;

import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.project.build.IPHPBuilderExtension;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPMarker;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.smarty.core.SmartyCorePlugin;

public class SmartyBuilder implements IPHPBuilderExtension {

	public static final String BUILDER_ID = SmartyCorePlugin.PLUGIN_ID + ".builder";
	public static final String CONTENTTYPE_ID = SmartyCorePlugin.PLUGIN_ID + ".template";
	private static final IContentTypeManager CONTENT_TYPE_MANAGER = Platform.getContentTypeManager();
	
	public boolean isEnabled() {
		return true;
	}

	public IProject[] build(IncrementalProjectBuilder builder, int kind,
			Map args, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public void clean(IncrementalProjectBuilder builder,
			IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	public void startupOnInitialize(IncrementalProjectBuilder builder) {
		// TODO Auto-generated method stub
		
	}

	private boolean isPHPAspectFile(IFile file) {
		final int numSegments = file.getFullPath().segmentCount();
		final String filename = file.getFullPath().segment(numSegments - 1);
		final IContentType contentType = CONTENT_TYPE_MANAGER.getContentType(CONTENTTYPE_ID);
		if (contentType.isAssociatedWith(filename)) {
			return true;
		}
		return false;
	}
	
	private void validate(IFile file) {
		
	}
	
	public class SmartyResourceVisitor implements IResourceVisitor {

		private IProgressMonitor monitor;

		public SmartyResourceVisitor(IProgressMonitor monitor) {
			this.monitor = monitor;
		}

		public boolean visit(IResource resource) {
			if(monitor.isCanceled()){
				return false;
			}
			// parse each PHP file with the parserFacade which adds it to
			// the model
			if (resource.getType() == IResource.FILE) {
				handle((IFile) resource);
				return false;
			}

			if (resource.getType() == IResource.PROJECT) {
				return handle((IProject) resource);
			}

			return true;
		}

		private boolean handle(IProject project) {
			PHPProjectOptions projectOptions = PHPProjectOptions.forProject(project);
			projectOptions.validateIncludePath();
			return true;
		}

		private void handle(IFile file) {
			if (monitor.isCanceled()) {
				return;
			}
			
			if (!isSmartyFile(file)) {
				return;
			}
			
			monitor.subTask(NLS.bind("Parsing: {0} ...", file.getFullPath().toPortableString()));

			validate(file); 

		}
	}
}
