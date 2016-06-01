package account;

import java.io.IOException;
import java.net.ServerSocket;
/**
 * This is the main class to run the server side of the MegaFit application.
 * This class waits for connections to be made to the server socket and then
 * assigns a new socket and server thread for the client to communicate via.
 * When a connection is made, the <code>ServerThread</code> is passed to the
 * <code>ServerManager</code> to keep a record of all active accounts/users.
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 * 
 * @see ServerSocket
 * @see ServerThread
 * @see ServerManager
 */
public class ServerSide {
	/**
	 * Waits for a new connection, sets up a server thread to deal with the 
	 * new request and then passes the server thread to the server manager.
	 * <p>
	 * Then returns to polling for another new connection to be made to
	 * the server socket.
	 * 
	 * @param args the parameters to run the server side.
	 * 
	 */
	public static void main(String[] args) {
		//The default port number the server socket is run on.	
		int portNumber = 4444;
		
		try (
				ServerSocket sSocket = new ServerSocket(portNumber);
		) {
			//instantiates and runs the server manager on start up
			ServerManager sm = new ServerManager();
			sm.start();
			int i = 0;
			while (true) {
				//waits for a client thread to connect to the server socket
				//and returns a new socket for the server/client threads to 
				//communicate over.
				ServerThread newServer = new ServerThread(sSocket.accept(), i);
				newServer.start();
				//adds the server thread to the array list in the server manager of all active accounts
				sm.addThread(newServer);
				System.out.println("Connection detected by server");
				i++;
			}

		} catch (NumberFormatException e) {
			System.out.println("The server side has crashed!");
		} catch (IOException e) {
			System.out.println("The server side has crashed!");
		}

	}



}
