package account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * This Class is a thread maintained and used within the {@link ClientSide} to
 * communicate with the {@link ServerThread}. A Thread is necessary as
 * the client needs to be listening for server sent messages. Each <tt>Server/ClientThread</tt> 
 * contains a <tt>Server/ClientProtocol</tt> object used to process/generate the
 * inputs/outputs to drive the conversation. See {@link ClientProtocol} for 
 * description of the state driven (protocol) communications.
 * <p>
 * The <tt>run</tt> method of this <tt>Thread</tt> reads and writes Objects
 * (predominantly of type <tt>String</tt>) from the socket known by the
 * server. The communications are maintained within a <tt>while-loop</tt>
 * which only breaks if a user successfully logs out or a Socket IOException occurs.
 * <p>
 * If an Object needs to be returned by the <tt>ClientThread</tt> to the main thread,
 * then this class object will lock its <tt>threadLock</tt>. This indicates to the main
 * thread that it has something to return. It then checks whether this has been recognised
 * in the main thread by attempting to lock the <tt>mainLock</tt>. If unsuccessful, this 
 * indicates that the main has been notified of the return attempt and retrieved the 
 * output String. This output (<tt>threadOutput</tt>) either returns successful for the
 * attempted protocol, or it returns an error message.
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class ClientThread extends Thread {
	//the locks for safe inter-thread communications
	private volatile Lock threadLock = new ReentrantLock();
	private volatile Lock mainLock = new ReentrantLock();
	private String hostName;
	private int portNumber;
	private boolean forcedClose = false;
	private ClientProtocol cProtocol;
	private volatile boolean newMessage = false;
	private volatile String threadOutput;
	private volatile String mainInput = "";
	private volatile boolean connectionError = false;
	
	/**
	 * Initialises the threads fields and sets the thread name to "Client Thread".
	 * 
	 * @param hostName the host name of to pass to the socket
	 * @param portNumber the port number to pass to the socket
	 * @param threadLock the lock acting as a synchronous done flag for this class object
	 * @param mainLock the lock acting as synchronous done flag for main application.
	 */
	public ClientThread(String hostName, int portNumber, Lock threadLock, Lock mainLock) {
		super("Client Thread");
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.threadLock = threadLock;
		this.mainLock = mainLock;
		this.cProtocol = new ClientProtocol();
	}
	/**
	 * Returns whether there is a connection error or not.
	 * 
	 * @return True is the communications ran into a connection error, false otehrwise.
	 */
	public boolean isConnectionError() {
		return connectionError;
	}
	
	/**
	 * Retrieves the account from the <tt>ClientProtocol</tt>.
	 * 
	 * @return the account from the <tt>ClientProtocol</tt>.
	 * @see ClientProtocol
	 */
	public Account getAccount() {
		return cProtocol.getAccount();
	}
	/**
	 * Retrieves the friends list from the <tt>ClientProtocol</tt>.
	 * 
	 * @return the friends list from the cProtocol field.
	 */
	public synchronized ArrayList<Account> getFriendsList() {
		return cProtocol.getFriendsList();
	}
	/**
	 * Contains the while loop in which the server/client communications are
	 * read from the socket, processed and then written to the socket.
	 * <p>
	 * ObjectOutput/InputStreams are used to read and write the messaqe objects.
	 * Any Exceptions that are thrown set the connectionError field to true.
	 * <p>
	 * See the in-method comments for more details.
	 * @see ObjectOutputStream
	 * @see ObjectInputStream
	 * @see Socket
	 */
	@Override
	public void run() {
		//try with resource statement to create the socket and input/output streams.
		try (Socket mySocket = new Socket(hostName, portNumber);
			 ObjectOutputStream send = new ObjectOutputStream(mySocket.getOutputStream());
			 ObjectInputStream receive = new ObjectInputStream(mySocket.getInputStream());
		) {

			Object inputObject, outputObject;
			//mandatory initial communication
			send.writeObject(Protocol.STANDBYE);
			boolean executing = false;
			
			//read message from socket
			while ((inputObject = receive.readObject()) != null && !forcedClose) {
				//If a new protocol was set from the main
				if (newMessage) {
					newMessage = false;
					executing = true;
					//set the protocol for the following communications
					cProtocol.setProtocol(mainInput);
					//send start new conversation message
					outputObject = Protocol.HANDSHAKE;
				} else {
					//usual flow of messages - produces response from the client protocol field.
					outputObject = cProtocol.processInput(inputObject);
				}
				//send the reply message
				send.writeObject(outputObject);
				//catches null input to prevent errors in the following methods.
				if (!outputObject.equals("null")) {
					// checks for errors or end message of communications.
					if (isDone(inputObject, outputObject)) {
						//communicates the success of the communications to the main thread.
						commsOutput();
						//clears the protocol
						mainInput = "";
						executing = false;
					}
					if (inputObject instanceof String) {
						// check for the end of logout communications message
						if (((String) inputObject).equals(Protocol.LOGOUT_SUCCESS)) {
							//set the 'return to main' string to logout success
							threadOutput = Protocol.LOGOUT_SUCCESS;
							//exit the communications while loop
							break;
						}
					}
				}
				//if the server/client comms are in a standby polling loop, to ease 
				//stress on the processor, sleep for 0.3 s every cycle of the while loop. 
				if (!executing) {
					Thread.sleep(300);
				} else {
					//Left these in to see the flow of communications between server and client
					System.out.println("client thread inputLine: " + inputObject);
					System.out.println("client thread outputLine: " + outputObject);
				}
			}
			System.out.println("Client Socket Closed");
			// if we haven't forced close, send the success of logout message to main thread.
			if (!forcedClose)
				commsOutput();
			forcedClose = false;
			//close socket
			mySocket.close();
		} catch (UnknownHostException e) {
			connectionError = true;
		} catch (IOException e) {
			connectionError = true;
		} catch (InterruptedException e) {
			connectionError = true;
		} catch (ClassNotFoundException e) {
			connectionError = true;
		}
	}
	/**
	 * Informs the Main application that the threadOutput has been set and
	 * is ready to be retrieved. This keeps the retrieval of any objects from 
	 * this thread synchronous. The locks are used like flags, where a locked lock
	 * indicates an object needs to be received and an unlocked lock indicates there
	 * is nothing to return.
	 * 
	 * @throws InterruptedException
	 */
	private void commsOutput() throws InterruptedException {
		//set return flag
		threadLock.lock();
		while (true) {
			//wait for main to notify that it detected that the client thread has something to return
			if (!mainLock.tryLock()) {
				threadLock.unlock();
				break;
			} else {
				//sleep otherwise
				mainLock.unlock();
				Thread.sleep(333);
			}
		}
	}
	/**
	 * Determines from the input and output objects of the server/client communications
	 * whether a protocol has finished and the success of that protocol - i.e. if 
	 * an error occurred in the conversation flow, or if the communications occurred as planned.
	 * The <tt>threadOutput</tt> field is then set for the main to retrieve.
	 * 
	 * @param inputLine the input object sent from the server
	 * @param outputLine the reply output object from the client 
	 * @return True if an object needs to be returned to the main, false otherwise.
	 */
	private boolean isDone(Object inputLine, Object outputLine) {
		boolean needForReturn = false;
		if (outputLine instanceof String) {
			String output = (String) outputLine;
			//check protocol was successful or not
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
			//checks if the server has sent a game request from a friend user's account.
			if (input.startsWith(Protocol.EXT_GAME_REQ)) {
				threadOutput = input;
				needForReturn = true;
			} else if (input.equals(Protocol.ERROR)) {
				//for generic errors, we return whether the client or server caused the error
				//and what the protocol was at the time when the error occurred.
				threadOutput = Protocol.ERROR + "," + Protocol.SERVER + " : " + mainInput;
				needForReturn = true;
			} else if (input.startsWith(Protocol.ERROR)) {
				if (input.contains(LoginStatus.IN_USE)) {
					//TODO
					threadOutput = Protocol.ERROR + "," + Protocol.SERVER + " :  Account already logged in";
					needForReturn = true;
				/*
				 * Special cases are when no account matches the login credentials - ACCOUNT_NOT_FOUND,
				 * or if the user has no friends in there list to retrieve for the friends protocol - NO_FRIENDS,
				 * or if there were no similar accounts found to the search details provided - NO_MATCHES.
				 */
				} else if (input.contains(LoginStatus.ACCOUNT_NOT_FOUND)) {
					threadOutput = Protocol.ERROR + "," + Protocol.SERVER + " :  Account doesn't exist";
					needForReturn = true;
				} else if (input.contains(Protocol.NO_FRIENDS)) {
					System.out.println("Error - 'no friends' - detected in client thread");
					threadOutput = Protocol.ERROR + "," + " Account" + " :  Account friends list empty";
					needForReturn = true;
				} else if (input.contains(Protocol.NO_MATCHES)) {
					threadOutput = Protocol.ERROR + "," + " Search Result Empty";
					needForReturn = true;
				}
			}
		}
		return needForReturn;
	}
	/**
	 * Sets the account field.
	 * 
	 * @param account the <tt>Account</tt> to set the account field to.
	 */
	public void setAccount(Account account) {
		cProtocol.setAccount(account);
	}
	/**
	 * Retrieves the string value of the threadOutput field.
	 * 
	 * @return The success/error message of the protocol communications.
	 */
	public String getThreadOutput() {
		return threadOutput;
	}
	/**
	 * Sets the new message flag to indicate that a new protocol has been set by the main
	 * and to start the new communications.
	 */
	public void setFlag() {
		this.newMessage = true;
	}
	/**
	 * Sets the mainInput field to the input String.
	 * 
	 * @param mainInput The start protocol message that initiates the 
	 * 					flow of conversation between the server and client.
	 */
	public void setMainInput(String mainInput) {
		this.mainInput = mainInput;
	}
	/**
	 * Sets the value of the forcedClose field. If set to true, this forces the
	 * server/client socket connection to close.
	 */
	public void closeConnection() {
		this.forcedClose = true;
	}

}
