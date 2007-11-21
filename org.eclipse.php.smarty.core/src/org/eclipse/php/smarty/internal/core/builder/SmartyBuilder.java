package org.eclipse.php.smarty.internal.core.builder;

import java.util.Map;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.core.project.build.IPHPBuilderExtension;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.smarty.core.SmartyCorePlugin;
import org.eclipse.php.smarty.internal.core.compiler.SmartyCompiler;
import org.eclipse.php.smarty.internal.core.json.JSONObject;

public class SmartyBuilder implements IPHPBuilderExtension {
	
	public boolean isEnabled() {
		return true;
	}

	public void startupOnInitialize(IncrementalProjectBuilder builder) {
		//Initialization logic
	}

	public void clean(IncrementalProjectBuilder builder, IProgressMonitor monitor) throws CoreException {
		cleanBuild(builder.getProject());
	}

	public IProject[] build(IncrementalProjectBuilder builder, int kind, Map args, IProgressMonitor monitor) throws CoreException {

		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			fullBuild(builder, monitor);
			return null;
		}
		
		IResourceDelta delta = builder.getDelta(builder.getProject());
		if (delta == null) {
			return null;
		}

		buildDelta(delta, monitor);
		
		return null;
	}

	private void fullBuild(IncrementalProjectBuilder builder, IProgressMonitor monitor) {
		try {
			IProject project = builder.getProject();
			project.accept(new SmartyResourceVisitor(monitor));
		} catch (CoreException e) {
			SmartyCorePlugin.log(e);
			return;
		}  
	}

	private void buildDelta(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		// the visitor does the work.
		delta.accept(new SmartyDeltaVisitor());
	}

	private void cleanBuild(IProject project) {
		try {
			if (!project.hasNature(PHPNature.ID)) {
				return;
			}
		} catch (CoreException e) {
			SmartyCorePlugin.log(e);
			return;
		}

	}
	
	public static final String BUILDER_ID = "org.eclipse.php.smarty.internal.core.builder.SmartyBuilderExtension";
	// used to examine if a file is a smarty template
	private static final IContentTypeManager CONTENT_TYPE_MANAGER = Platform.getContentTypeManager();
	
	private static final String PHP_PROBLEM_MARKER_TYPE = PHPCorePlugin.ID +".phpproblemmarker";
	
	private void addMarker(IFile file, String message, int lineNumber,
			int severity) {
		try {
			IMarker marker = file.createMarker(PHP_PROBLEM_MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			if (lineNumber == -1) {
				lineNumber = 1;
			}
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		} catch (CoreException e) {
			// catch the exception and alert user
		}
	}

	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(PHP_PROBLEM_MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}
	
	private boolean isSmartyTemplate(IFile file) {
		final int numSegments = file.getFullPath().segmentCount();
		final String filename = file.getFullPath().segment(numSegments - 1);
		final IContentType contentType = CONTENT_TYPE_MANAGER.getContentType(SmartyCorePlugin.PLUGIN_ID + ".template");
		if (contentType.isAssociatedWith(filename)) {
			return true;
		}
		return false;
	}
	
	private void validate(IFile file) {
		deleteMarkers(file);
		try {
			String result = SmartyCompiler.compile(file);
			if(result != null){
				JSONObject marker = new JSONObject(result);
				addMarker(file, marker.getString("message"),
							marker.getInt("line"), IMarker.SEVERITY_ERROR);
			}
		} catch (Exception e){
			addMarker(file, e.getMessage(), 1, IMarker.SEVERITY_ERROR);
		}
	}
	
	class SmartyDeltaVisitor implements IResourceDeltaVisitor {
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) {
			switch (delta.getResource().getType()) {
				//only process files with PHP content type
				case IResource.FILE:
					processFileDelta(delta);
					return false;

					//only process projects with PHP nature
				case IResource.PROJECT:
					return processProjectDelta(delta);

				default:
					return true;
			}
		}
		
		private void processFileDelta(IResourceDelta fileDelta) {
			//System.out.println("processFileDelta");
			IFile file = (IFile) fileDelta.getResource();

			switch (fileDelta.getKind()) {
				case IResourceDelta.ADDED:
				case IResourceDelta.CHANGED:
					if (isSmartyTemplate(file)) 
						validate(file);
					break;
				case IResourceDelta.REMOVED:
					// removed automatically from the validator, no need to enforce
					break;
			}
		}
		
		private boolean processProjectDelta(IResourceDelta projectDelta) {
			IProject project = (IProject) projectDelta.getResource();
			try {
				if (!project.hasNature(PHPNature.ID)) {
					return false;
				}
			} catch (CoreException e) {
				SmartyCorePlugin.log(e);
				return false;
			}
			return true;
		}
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
			/* //check if the project contains PHP
			if (PHPWorkspaceModelManager.getInstance().getModelForProject(project, true) == null) {
				return false;
			}
			*/ 
			PHPProjectOptions projectOptions = PHPProjectOptions.forProject(project);
			projectOptions.validateIncludePath();
			return true;
		}

		private void handle(IFile file) {
			if (monitor.isCanceled()) {
				return;
			}
			
			if (!isSmartyTemplate(file)) {
				return;
			}
			monitor.subTask(NLS.bind("Parsing: {0} ...", file.getFullPath().toPortableString()));

			validate(file); 

		}
	}
}