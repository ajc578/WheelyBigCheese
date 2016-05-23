package account;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSide {

	public static void main(String[] args) {
		
		/*if (args.length != 1) {
			System.err.println("Incorrect Number of strings. Correct usage is: 'java ServerSide <port number>'");
			System.exit(1);
			//insert alert here
		}*/

		//int portNumber = Integer.parseInt(args[0]);
		int portNumber = 4444;

		try (
				ServerSocket sSocket = new ServerSocket(portNumber);
		) {
			ServerManager sm = new ServerManager();
			sm.start();
			int i = 0;
			while (true) {
				ServerThread newServer = new ServerThread(sSocket.accept(), i);
				newServer.start();
				sm.addThread(newServer);
				System.out.println("Connection detected by server");
				i++;
			}

		} catch (NumberFormatException e) {
			// alert here
			e.printStackTrace();
		} catch (IOException e) {
			// alert here
			e.printStackTrace();
		}

	}



}
