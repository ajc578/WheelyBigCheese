package account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ServerThread extends Thread {
	
	private Socket socket = null;
	private ServerProtocol sProtocol;
	private String triggerMessage = "";
	private volatile boolean finished = false;
	private volatile ThreadInterCom comms;
	private Timer timeoutTimer;
	
	public ServerThread(Socket socket, int i) {
		//set name of thread to the account name + connection
		super("Connection: " + i);
		this.socket = socket;
		System.out.println(socket.getLocalPort());
		System.out.println(socket.getPort());
	}
	
	public void setInterCom(ThreadInterCom comms) {
		this.comms = comms;
	}
	
	@Override
	public void run() {
		sProtocol = new ServerProtocol();
		try (
			PrintWriter send = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		) {
			//start timeout timer incase of unknown error
			Thread timeoutThread = new Thread(new Runnable() {
				public void run() {
					timeoutTimer = new Timer();
					timeoutTimer.schedule(new TimerTask() {
						public void run() {
							comms.send(Protocol.BYE);
							timeoutTimer.cancel();
						}
					}, 10000);
				}
			});
			timeoutThread.start();
			
			String inputLine = null, outputLine = "";
			//initialise the protocol for communication here
			System.out.println("Server read/write set up");
			while ((inputLine = receive.readLine()) != null) {
				if (finished == true) {
					timeoutTimer.cancel();
					break;
				}		
				outputLine = sProtocol.processInput(inputLine);
				send.println(outputLine);
				//NB: might potentially add functionality to store the protocol input message on logins and logouts.
				
				if (outputLine != null) {
					checkSTUpdates(inputLine, outputLine);
					System.out.println("Client says: " + inputLine + ".\nServer says: " + outputLine);
				}
			}
			System.out.println("Server Socket Closed");
			socket.close();
		} catch (IOException e) {
			// Alert here
			e.printStackTrace();
		}
	}
	
	private void checkSTUpdates(String inputLine, String outputLine) {
		if (outputLine.equals(Protocol.BYE)) {
			System.out.println("Attempt to close thread recognised.");
			comms.send(outputLine);
		} else if (inputLine.startsWith(Protocol.DECLARE_ACCOUNT) &&
				outputLine.equals(Protocol.ACKNOWLEDGED)) {
			triggerMessage = Protocol.DECLARE_ACCOUNT;
		} else if (inputLine.startsWith(Protocol.LOGIN)) {
			triggerMessage = Protocol.LOGIN;
		} else if (inputLine.startsWith(Protocol.LOGOUT)) {
			System.out.println("Attempt to logout recognised.");
			comms.send(inputLine);
		} else if (inputLine.startsWith(Protocol.CREATE_ACCOUNT)) {
			triggerMessage = Protocol.CREATE_ACCOUNT;
		} else if (!triggerMessage.equals("")) {
			if (outputLine.equals(Protocol.COMPLETED) && triggerMessage.equals(Protocol.LOGIN)) {
				System.out.println("Server login accepted and notifying SM");
				comms.send(triggerMessage + " : " + sProtocol.getAccount().getNumber());
			} 
		} 
		
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
