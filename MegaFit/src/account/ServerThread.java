package account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket = null;
	private ServerProtocol sProtocol;
	
	public ServerThread(Socket socket) {
		//set name of thread to the account name + connection
		super("localhost");
		this.socket = socket;
		System.out.println(socket.getLocalPort());
		System.out.println(socket.getPort());
	}
	
	@Override
	public void run() {
		boolean loginAttempted = false, loginSuccessful = false;
		sProtocol = new ServerProtocol();
		try (
			PrintWriter send = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		) {
			String inputLine = null, outputLine = "";
			//initialise the protocol for communication here
			System.out.println("Server read/write set up");
			while ((inputLine = receive.readLine()) != null) {
				if (outputLine.equals(Protocol.BYE)) {
					break;
				}
				
				if (outputLine.startsWith(Protocol.LOGIN)) {
					loginAttempted = true;
				}
				
				if (loginAttempted) {
					if (inputLine.equals(Protocol.COMPLETED)) {
						//tell server manager that this account has logged in 
						sProtocol.getAccount().getNumber();
					}
				}
				
				if (inputLine != null) 
					outputLine = sProtocol.processInput(inputLine);
				
				send.println(outputLine);
				if (outputLine != null)
					System.out.println("Server says: " + outputLine);
			}
			System.out.println("Server Socket Closed");
			socket.close();
			
		} catch (IOException e) {
			// Alert here
			e.printStackTrace();
		} 
	}

}
