package org.eclipse.php.smarty.internal.core.builder;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.smarty.core.SmartyCorePlugin;
import org.eclipse.php.smarty.internal.core.compiler.SmartyCompiler;
import org.eclipse.php.smarty.internal.core.json.JSONObject;

public class SmartyBuildParticipant implements IBuildParticipant {
	// used to examine if a file is a smarty template
	private static final IContentTypeManager CONTENT_TYPE_MANAGER = Platform.getContentTypeManager();
	
	private static final String PHP_PROBLEM_MARKER_TYPE = PHPCorePlugin.ID +".phpproblemmarker";
	private static final String WARNING_PREFIX = "Warning: ";
	@Override
	public void build(IBuildContext context) throws CoreException {
		IFile file = context.getFile();
		if (!isSmartyTemplate(file)) {
			return;
		}

		validate(file); 
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
			String[] s = result.split("\\\n");
			for (int i=0; i < s.length; i++) {
				System.out.println(s[i]);
				if(s[i] != null && s[i].trim().length() != 0){
					if(s[i].startsWith("{")){
						JSONObject marker = new JSONObject(s[i]);
						addMarker(file, marker.getString("message"),
									marker.getInt("line"), IMarker.SEVERITY_ERROR);
					}else if(s[i].startsWith(WARNING_PREFIX)){
						addMarker(file, s[i].substring(WARNING_PREFIX.length(), s[i].length()), 1, IMarker.SEVERITY_WARNING);
					}
					
				}
			}
			
		} catch (Exception e){
			addMarker(file, e.getMessage(), 1, IMarker.SEVERITY_ERROR);
		}
	}
	
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
	
}
