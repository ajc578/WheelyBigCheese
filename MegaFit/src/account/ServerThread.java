package account;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private Socket socket = null;
	private ServerProtocol sProtocol;
	private volatile boolean finished = false;
	private volatile boolean loginRequest = false, logoutRequest = false, otherRequest = false;
	private volatile boolean requested = false;
	private volatile String accountNum = null;
	private volatile String smRequest = "";
	
	public ServerThread(Socket socket) {
		//set name of thread to the account name + connection
		super("localhost");
		this.socket = socket;
		System.out.println(socket.getLocalPort());
		System.out.println(socket.getPort());
	}
	
	@Override
	public void run() {
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
				
				if (inputLine.startsWith(Protocol.LOGOUT)) {
					accountNum = Protocol.getMessage(inputLine);
					logoutRequest = true;
				}
						
						//tell server manager that this account has logged in 
						/*loginRequest = true;
						try {
							this.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("Dont worry i think it's chill...");
							e.printStackTrace();
						}
						accountNum = sProtocol.getAccount().getNumber();
						System.out.println("account login request true");*/
				
				//NB: might potentially add functionality to store the protocol input message on logins and logouts.
				
				if (inputLine != null) {
					outputLine = sProtocol.processInput(inputLine);
					if (inputLine.startsWith(Protocol.DECLARE_ACCOUNT) && 
						   outputLine.equals(Protocol.ACKNOWLEDGED)) {
						accountNum = Protocol.getMessage(inputLine);
						//otherRequest = true;
					}
					
					if ((sProtocol.getState() == ServerProtocol.LOGIN || 
							sProtocol.getState() == ServerProtocol.CHECK_LAST_SAVE_DATE) 
								&& outputLine.equals(Protocol.COMPLETED)) {
						System.out.println("Attampt to login recognised.");
						try {
							waitForSM(Protocol.LOGIN + " : " + sProtocol.getAccount().getNumber());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("Server Thread has been interrupted during login.");
							e.printStackTrace();
						}
					} else if (inputLine.startsWith(Protocol.LOGOUT)) {
						System.out.println("Attampt to logout recognised.");
						try {
							waitForSM(inputLine);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							System.out.println("Server Thread has been interrupted during logout.");
							e.printStackTrace();
						}
					}
				}
				
				send.println(outputLine);
				if (outputLine != null)
					System.out.println("Server says: " + outputLine);
			}
			System.out.println("Server Socket Closed");
			finished = true;
			socket.close();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// Alert here
			e.printStackTrace();
		} 
	}
	
	public synchronized String waitForSM(String request) throws InterruptedException {
		String requestLine = null;
		notify();
		while (!requested) {
			wait();
		}
		smRequest = request;
		System.out.println("account login/logout request true");
		notify();
	
		return requestLine;
	}
	
	public synchronized String alertST() throws InterruptedException {
		requested = true;
		notify();
		while (smRequest.equals("")) {
			System.out.println("SM is about to wait");
			notify();
			wait();
			System.out.println("SM is waiting");
		}
		return smRequest;
	}
	
	public void setRequested(boolean requested) {
		this.requested = requested;
	}
	
	public void notifyMe() {
		this.notify();
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public boolean isLoginRequested() {
		return loginRequest;
	}
	
	public boolean isLogoutRequested() {
		return logoutRequest;
	}
	
	public String getAccountNum() {
		if (loginRequest) {
			loginRequest = false;
		} else if (logoutRequest) {
			logoutRequest = false;
		} else if (otherRequest) {
			otherRequest = false;
		}
		return accountNum;
	}

}
