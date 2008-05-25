package org.eclipse.php.smarty.internal.core.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.smarty.core.SmartyCorePlugin;

public class SmartyCompiler{
	
	final public static URL SMARTY_COMPILER = SmartyCorePlugin
												.getDefault()
												.getBundle()
												.getEntry("Ressources/SmartyCompiler.php");

	public static String compile(IFile file) throws Exception{
		// gets the first php executable 
		String phpExe = getPhpExecutable();

		// get the current filename (OS sensitive)
		final String compilerFilename = getFilename();
		
		// construct args for execution
		String[] args = {phpExe, compilerFilename, file.getLocation().toOSString()};

		// run the php code and wait for results
		Process p = Runtime.getRuntime().exec(args);
		p.waitFor();

		// return the results from the process  
		BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
		return output.readLine();
	}

	private static String getPhpExecutable() throws Exception {
		PHPexeItem phpExe = PHPexes.getInstance().getDefaultItem("org.eclipse.php.debug.core.zendDebugger");
		
		if (phpExe == null){
			throwError();
		}
		String phpExeName = phpExe.getExecutable().toString();
		
		return phpExeName;
	}

	private static String getFilename() throws IOException, Exception {
		final URL resolve = FileLocator.resolve(SMARTY_COMPILER);
		if (resolve == null) {
			throwError();
		}
		
		IPath path = new Path(resolve.getFile());
		final String compilerFilename = path.toOSString();
		return compilerFilename;
	}
	
	private static void throwError() throws Exception {
		throw new Exception("Didn't find the default PHP executable, please define a default PHP executable of type 'Zend Debugger'.");
	}
}
