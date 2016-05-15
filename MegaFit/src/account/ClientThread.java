package account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import account.Account;

public class ClientThread extends Thread {

	private volatile Lock threadLock = new ReentrantLock();
	private volatile Lock mainLock = new ReentrantLock();
	private String hostName;
	private int portNumber;
	private boolean forcedClose = false;
	private volatile boolean finished = false;
	private ClientProtocol cProtocol;
	private volatile ArrayList<Account> friendsList;
	private volatile Account friend;
	private volatile boolean newMessage = false;
	private volatile String threadOutput;
	private volatile String mainInput = "";

	public ClientThread(String hostName, int portNumber, Lock threadLock, Lock mainLock) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.threadLock = threadLock;
		this.mainLock = mainLock;
		this.cProtocol = new ClientProtocol();
	}

	// used by parent class to return account once logged in
	public Account getAccount() {
		return cProtocol.getAccount();
	}

	public synchronized Account getFriendSearch() {
		return friend;
	}

	public synchronized ArrayList<Account> getFriendsList() {
		return friendsList;
	}

	@Override
	public void run() {
		try (Socket mySocket = new Socket(hostName, portNumber);
			ObjectOutputStream send = new ObjectOutputStream(mySocket.getOutputStream());
				ObjectInputStream receive = new ObjectInputStream(mySocket.getInputStream());
		) {

			Object inputObject, outputObject;
			send.writeObject(Protocol.STANDBYE);
			boolean executing = false;

			System.out.println("Client read/write set up");
			while ((inputObject = receive.readObject()) != null && !forcedClose) {
				if (newMessage) {
					newMessage = false;
					executing = true;
					cProtocol.setProtocol(mainInput);
					outputObject = Protocol.HANDSHAKE;
					System.out.println("newMessage recognised in client thread.");
				} else {
					outputObject = cProtocol.processInput(inputObject);
				}
				
				send.writeObject(outputObject);

				if (!outputObject.equals("null")) {
					if (isDone(inputObject, outputObject)) {
						commsOutput();
						mainInput = "";
						executing = false;
					}
					if (inputObject instanceof String) {
						if (((String) inputObject).equals(Protocol.LOGOUT_SUCCESS)) {
							threadOutput = Protocol.LOGOUT_SUCCESS;
							System.out.println("Client Thread preparing to logout.");
							break;
						}
					}
				}

				if (!executing) {
					Thread.sleep(300);
				} else {
					System.out.println("client thread inputLine: " + inputObject);
					System.out.println("client thread outputLine: " + outputObject);
				}
			}
			System.out.println("Client Socket Closed");
			commsOutput();
			//possibly inform client main of force close 
			forcedClose = false;
			mySocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unknown Host Exception");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO Exception");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void commsOutput() throws InterruptedException {
		threadLock.lock();
		while (true) {
			if (!mainLock.tryLock()) {
				threadLock.unlock();
				break;
			} else {
				mainLock.unlock();
				Thread.sleep(333);
			}
		}
	}

	private boolean isDone(Object inputLine, Object outputLine) {
		boolean needForReturn = false;
		if (outputLine instanceof String) {
			String output = (String) outputLine;
			if (output.equals(Protocol.BYE)) {
				threadOutput = Protocol.SUCCESS;
				needForReturn = true;
			} else if (output.equals(Protocol.ERROR)) {
				threadOutput = Protocol.ERROR + "," + Protocol.CLIENT + " : " + mainInput;
				needForReturn = true;
			}
		} 
		if (inputLine instanceof String) {
			String input = (String) inputLine;
			if (input.startsWith(Protocol.EXT_GAME_REQ)) {
				threadOutput = input;
				needForReturn = true;
			} else if (input.equals(Protocol.ERROR)) {
				System.out.println("Server Error recognised in client thread");
				threadOutput = Protocol.ERROR + "," + Protocol.SERVER + " : " + mainInput;
				needForReturn = true;
			} else if (input.startsWith(Protocol.ERROR)) {
				if (input.contains(LoginStatus.IN_USE)) {
					threadOutput = Protocol.ERROR + "," + Protocol.SERVER + " :  Account already logged in";
					needForReturn = true;
				} else if (input.contains(LoginStatus.ACCOUNT_NOT_FOUND)) {
					threadOutput = Protocol.ERROR + "," + Protocol.SERVER + " :  Account doesn't exist";
					needForReturn = true;
				} else if (input.contains(Protocol.NO_FRIENDS)) {
					System.out.println("Error - 'no friends' - detected in client thread");
					threadOutput = Protocol.ERROR + "," + " Account" + " :  Account friends list empty";
					needForReturn = true;
				}
			}
		}
		return needForReturn;
	}
	
	public void setAccount(Account account) {
		cProtocol.setAccount(account);
	}

	public String getThreadOutput() {
		return threadOutput;
	}

	public void setThreadOutput(String threadOutput) {
		this.threadOutput = threadOutput;
	}

	public void setFlag() {
		this.newMessage = true;
	}

	public String getMainInput() {
		return mainInput;
	}

	public void setMainInput(String mainInput) {
		this.mainInput = mainInput;
	}

	public boolean isFinished() {
		return finished;
	}

	public void closeConnection() {
		this.forcedClose = true;
	}

}
