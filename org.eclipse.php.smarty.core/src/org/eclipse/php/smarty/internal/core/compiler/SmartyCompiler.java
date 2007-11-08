package org.eclipse.php.smarty.internal.core.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants;
import org.eclipse.php.internal.core.preferences.CorePreferenceConstants.Keys;

public class SmartyCompiler implements Runnable{

	private ServerSocket serverSocket;
	private Socket socket;
	private int port = 6881;

	public static void compile(){
		
		System.err.println("toto"+	CorePreferenceConstants.getPreferenceStore().getDefaultString(PHPCoreConstants.ATTR_LOCATION));
		//PHPExecutableLaunchDelegate
//		PHPExecutableLaunchDelegate
//		* if you need simple execution (to call a php code that then will
//		connect to java via sockets) - use Process p =
//		Runtime.getRuntime().exec(args, null);
	}
	
	public void run() {
		do{
			
		}while(true);

//        try {
//
//              serverSocket = new ServerSocket(port , 1);
//
//              socket = serverSocket.accept();
//
//              handleEvents(socket);
//
//        } catch (final IOException e) {
//
//        } finally {
//
//              shutdown(false);
//
//        }
    }
	
	private void shutdown(boolean b) {
		// TODO Auto-generated method stub
	}

	protected void handleEvents(final Socket socket) {
	
	    try {
	
	          final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
	          String line;
	
	          Object value;
	
	          while ((line = reader.readLine()) != null)
	
	                try {
	
	                      //value = JSONValue.parse(line);
	
	                      //YourOwnParser.parseMessage(value);
	
	                } catch (final Throwable e) {
	
	                      // alert exception;
	
	                }
	
	    } catch (final Exception e) {
	
	          // alert exception;
	
	    }
	
	}
}
