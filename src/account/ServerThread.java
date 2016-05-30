package account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/**
 * This Class is a thread maintained within the {@link ServerManager} and used to
 * communicate with the {@link ClientThread}. A Thread is necessary as
 * the {@link ServerSide} needs to poll on the socket to which the clients connect. 
 * Each <tt>Server/ClientThread</tt> contains a <tt>Server/ClientProtocol</tt> object 
 * used to process/generate the inputs/outputs to drive the conversation. 
 * See {@link ServerProtocol} for description of the state driven (protocol) communications.
 * <p>
 * The <tt>run</tt> method of this <tt>Thread</tt> reads and writes Objects
 * (predominantly of type <tt>String</tt>) from and to the client socket.
 * The communications are maintained within a <tt>while-loop</tt>
 * which only breaks if a user successfully logs out or a Socket IOException occurs.
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class ServerThread extends Thread {

	private Socket socket = null;
	private ServerProtocol sProtocol;
	//new variables
	private boolean gamePlaying = false;
	private volatile boolean busy = false;
	private volatile ThreadInterCom gameComms; //This will be used in future for MegaFit Game.
	private volatile String localAccount;
	private volatile String opponentAccount;
	private volatile int gameStatus = GameRequest.WAITING;
	
	/**
	 * Constructor sets the socket field to the socket passed in from the
	 * <tt>ServerSide</tt>.
	 * 
	 * @param socket the socket on which the server/client will communicate
	 * @param i the index of the thread
	 */
	public ServerThread(Socket socket, int i) {
		//set name of thread to the account name + connection
		super("Connection: " + i);
		this.socket = socket;
	}
	/**
	 * 
	 * Contains the while loop in which the server/client communications are
	 * read from the socket, processed and then written to the socket.
	 * <p>
	 * ObjectOutput/InputStreams are used to read and write the messaqe objects.
	 * Any Exceptions that are thrown are all handled on the client end. Therefore
	 * There is no need for exception handling in the catch statements of the
	 * try-with-resource statement.
	 * <p>
	 * See the in-method comments for more details.
	 * @see ObjectOutputStream
	 * @see ObjectInputStream
	 * @see Socket
	 */
	 
	@Override
	public void run() {
		// initialise the server protocol for message processing
		sProtocol = new ServerProtocol();
		try (
				ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
		) {
			
			Object inputObject = null, outputObject = null;
			boolean loginAttempted = false;
			//read message from client
			while ((inputObject = receive.readObject()) != null) {
				//If not playing MegaFit Game
				if (!gamePlaying) {
					//if a new message has been sent by the client, set busy to true
					if (busy == false && !inputObject.equals(Protocol.STANDBYE)) {
						busy = true;
					}
					//check whether there is a game request or a game related message
					String output = searchForGameReq(inputObject, send);
					if (output != null) {
						//if the previous method detected a game related message
						if (!output.equals("")) {
							//write that object to the output stream
							outputObject = output;
						}
					} else {
						//normal protocol message processing to return a reply message
						outputObject = sProtocol.processInput(inputObject);
					}
					//write the output object to the output stream
					send.writeObject(outputObject);
					
					if (!outputObject.equals("null")) {
						//Detects a login attempt
						if (inputObject != null && (inputObject instanceof String) ?  ((String) inputObject).startsWith(Protocol.LOGIN): false) {
							loginAttempted = true;
						}
						//detect if login was completed successfully
						if (loginAttempted && outputObject.equals(Protocol.COMPLETED)) {
							loginAttempted = false;
							//set the account number field to the account number of the client connected
							localAccount = sProtocol.getAccountNumber(); //For MegaFit Game...
						}
						
						if (outputObject.equals(Protocol.STANDBYE)) {
							//clear game request
							gameStatus = GameRequest.WAITING;
							busy = false;
						}
						//detect successful logout to exit the while loop and end comms
						if (outputObject.equals(Protocol.LOGOUT_SUCCESS)) {
							break;
						}
					}
					if (!busy) {
						Thread.sleep(333); // this number is used to fit with timeout timer for game request in manager.
					} else {
						System.out.println("InputObject is : " + inputObject);
						System.out.println("OutputObject is : " + outputObject);
					}
				} else {
					//This is where the game protocol communications will take place
					//At the end of the game, need to set gamePlaying to false
				}


			}
			System.out.println("Server Socket Closed");
			//close the socket
			socket.close();
		} catch (IOException e) {
			System.out.println("IO exception in ServerThread!");
		} catch (InterruptedException e) {
			System.out.println("Interrupted exception in ServerThread!");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound exception in ServerThread!");
		}
	}
	
	public String searchForGameReq(Object inputObject, ObjectOutputStream send) throws InterruptedException, IOException {
		String output = null;
		if (inputObject instanceof String) {
			String inputLine = (String) inputObject;
			if (inputLine.equals(Protocol.GAME_ACCEPTED)) {
				//for external request
				gameStatus = GameRequest.ACCEPTED;
				//MegaFit Game comment: Construct a Game Protocol here for serverThread inter-communication
				Thread.sleep(1000); // waits for manager.
				gamePlaying = true;
				output = "";
			} else if (inputLine.equals(Protocol.GAME_DECLINED)) {
				//for external request
				gameStatus = GameRequest.DECLINED;
				Thread.sleep(1000);
				gameStatus = GameRequest.WAITING;
				output = "";
			} else if (gameStatus == GameRequest.EXTERNAL) {
				//inform client of external game request
				busy = true;
				send.writeObject(Protocol.EXT_GAME_REQ + " : " + opponentAccount); // may send twice depending on how long it takes for opponent to accept game
				output = Protocol.STANDBYE;
			} else if (inputLine.startsWith(Protocol.LOCAL_GAME_REQ)) {
				opponentAccount = Protocol.getMessage(inputLine);
				gameStatus = GameRequest.LOCAL;
				output = Protocol.STANDBYE;
				send.writeObject(output);
			} else if (gameStatus == GameRequest.ACCEPTED) {
				// local game has been accepted by opponent
				gameStatus = GameRequest.THREAD_BUSY;
				//MegaFit Game comment: Construct a Game Protocol here for serverThread inter-communication
				busy = true;
				gamePlaying = true;
			} else if (gameStatus == GameRequest.DECLINED) {
				// local game has been declined by opponent
				gameStatus = GameRequest.WAITING;
				output = Protocol.GAME_DECLINED;
				send.writeObject(output);
			}
		}

		return output;
	}
	/**
	 * Used by {@link ServerManager} to determine whether this thread is available
	 * @return True if this thread is not available, false otherwise.
	 */
	public boolean getThreadStatus() {
		return busy;
	}
	/**
	 * Gets the value of the gameStatus field. Used by the <tt>ServerManager</tt>.
	 * 
	 * @return the value of the gameStatus field.
	 */
	public int getGameStatus() {
		if (gameStatus == GameRequest.LOCAL) {
			gameStatus = GameRequest.RECOGNISED;
		}

		return gameStatus;
	}
	/**
	 * Sets the value of the gameStatus field. Used by the <tt>ServerManager</tt>.
	 * 
	 * @param gameStatus the new status to update the game status to.
	 */
	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
	/**
	 * Sets the synchronous communications object used to communicate between
	 * two server threads for a MegaFit Game session.
	 * 
	 * @param gameComms provides synchronous communications between ServerThreads.
	 */
	public void setGameLock(ThreadInterCom gameComms) {
		busy = true;
		gameComms = new ThreadInterCom();
		this.gameComms = gameComms;
	}
	/**
	 * Returns the two account usernames of two players for the MegaFit Game session.
	 * Used by the <tt>ServerManager</tt>.
	 * 
	 * @return Strign array containing both player account usernames for the game session.
	 */
	public String[] returnOpponents() {
		String[] opponents = {localAccount, opponentAccount};
		return opponents;
	}
	/**
	 * Sets the opponent account for a MegaFit Game session. Used by the
	 * <tt>ServerManager</tt>.
	 * 
	 * @param opponentAccount
	 */
	public void setOpponent(String opponentAccount) {
		this.opponentAccount = opponentAccount;
	}
	/**
	 * Gets the account number associated with this connection.
	 * @return the account number of the user this connection is associated with.
	 */
	public String getAccountNumber() {
		return localAccount;
	}

}
