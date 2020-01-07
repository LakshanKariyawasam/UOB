package com.perisic.rmiserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.perisic.rmiinterface.RemoteQuestions;

/**
 * Use this class to start the server. 
 * @author Marc Conrad
 *
 */
public class StartServer {
	/**
	 * Entry point of the server. 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		System.out.println("Attempting to start the Question Server..."); 
		try {
			
			RemoteQuestions quizepath = new SurveyImplementation();
//			
//			System.setProperty("java.rmi.server.hostname", "112.135.4.68");
//			System.setProperty("java.security.policy", "security.policy");
//			if (System.getSecurityManager() == null) {
//				System.setSecurityManager(new SecurityManager());
//			}
			
			Registry reg = LocateRegistry.createRegistry(1099);
			reg.rebind("quizepathservice",quizepath);

			System.out.println("Service started. Welcome to the RMI Question Service!");

		} catch (RemoteException e) {
			System.out.println("An error occured: "+e.toString()); 
			e.printStackTrace();
		} 

	}

}
