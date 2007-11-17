package org.eclipse.php.smarty.internal.core.compiler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.php.internal.debug.core.preferences.PHPexeItem;
import org.eclipse.php.internal.debug.core.preferences.PHPexes;
import org.eclipse.php.smarty.core.SmartyCorePlugin;

public class SmartyCompiler{
	
	final public static URL SMARTY_COMPILER = SmartyCorePlugin
												.getDefault()
												.getBundle()
												.getEntry("Ressources/SmartyCompiler.php");

	public static String compile(IFile file) throws Exception{
		
		PHPexeItem[] phpExes = PHPexes.getInstance().getAllItems();

		if(phpExes.length == 0){
			throw new Exception("Didn't find any PHP executable, please define one in the PHP preferences.");
		}

		String phpExe = phpExes[0].getPhpEXE().toString();

		String[] args = {phpExe, FileLocator.resolve(SMARTY_COMPILER).getPath(),
							file.getLocation().toOSString()};

		Process p = Runtime.getRuntime().exec(args, null);
		p.waitFor();

		BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
		return output.readLine();
	}
}
