package account;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.scene.control.Alert.AlertType;
import presentationViewer.ExceptionFx;
/**
 * A Class used by the main application thread to communicate with
 * the {@link ClientThread}. The methods set the protocol for the next conversation
 * between the server and client. If an output is expected from that protocol,
 * the <code>receive()</code> method is used to confirm that the protocol ran as 
 * expected (or encountered errors), and then the appropriate getter can be used to
 * retrieve the desired object.
 * <p>
 * The syntax for using these protocol setter methods is as follows:
 * <p>
 * <pre>
 * {@code
 * 		clientSide.protocol-setter-here();
 * 		while (true) { 
 * 		String output = client.receive(); 
 * 		if (output.equals("expected-success-message")) { 
 * 			retrieve output object here
 *			break;
 * 		} else if (output.startsWith("expected-error-message")) {
 * 			handle error here
 * 			break; 
 * 		} 
 * 	} 
 * }
 * </pre>
 * 
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class ClientSide {
	
	//locks used for the custom synchronous receive method
	private Lock mainLock = new ReentrantLock();
	private Lock threadLock = new ReentrantLock();
	//instance of the client thread
	private ClientThread client = null;
	private boolean accessible = true;
	/**
	 * Initialises the {@link ClientThread} with a port number of the
	 * socket the server is listening to. The two {@link ReentrantLock}s 
	 * are passed in to allow for safe, synchronous access of objects in the
	 * <code>ClientThread</code>.
	 * @param portNumber the port number on which the server is polling
	 * @throws UnknownHostException
	 */
	public ClientSide(int portNumber) throws UnknownHostException {
		client = new ClientThread(InetAddress.getLocalHost().getHostName(), portNumber, threadLock, mainLock);
		client.start();
	}
	/**
	 * Checks for a client thread error.
	 * @return True if the client encountered an error, false otherwise.
	 */
	public boolean isConnectionError() {
		return client.isConnectionError();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'login protocol' and provides the user's login credentials
	 * @param username the username of the account
	 * @param password the password of the account
	 */
	public void login(String username, String password) {
		client.setMainInput(Protocol.LOGIN.concat(" : " + username + "," + password));
		client.setFlag();
	}
	/**
	 * Forces the client thread to terminate.
	 */
	public void closeConnection() {
		client.closeConnection();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'create account protocol' and provides the user's credentials
	 * for this new account.
	 * 
	 * @param username the username of the new account.
	 * @param password the password of the new account.
	 * @param firstname the first name of the user.
	 * @param lastname the last name of the user.
	 * @param weight the weight of the user.
	 * @param height the height of the user.
	 * @param DOB the date of birth of the user.
	 * @param email the email address of the user.
	 */
	public void createAccount(String username, String password, String firstname, String lastname,
							  String weight, String height, String DOB, String email) {
		client.setMainInput(Protocol.CREATE_ACCOUNT.concat(" : " + username + "," + password + ","
				+ firstname + "," + lastname + "," + weight + "," + height + ","
				+ DOB + "," + email));
		client.setFlag();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'save account protocol' and provides the user's account
	 * from the main application to save.
	 * 
	 * @param account the account to be saved.
	 */
	public void save(Account account) {
		client.setMainInput(Protocol.SAVE);
		client.setAccount(account);
		client.setFlag();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'logout account protocol' and provides the user's account
	 * from the main application to save before the connection closes.
	 * 
	 * @param account the account to save on logout.
	 */
	public void logout(Account account) {
		client.setMainInput(Protocol.LOGOUT);
		client.setAccount(account);
		client.setFlag();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'retrieve friends protocol'.
	 */
	public void findFriends() {
		client.setMainInput(Protocol.RETRIEVE_FRIENDS);
		client.setFlag();
	}
	/**
	 * Retrieves the friends list or search list set as a result
	 * of the <code>findFriends</code> or <code>searchFriends</code> methods.
	 * 
	 * @return The clients friend list.
	 */
	public ArrayList<Account> getFriendsList() {
		return client.getFriendsList();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'search-for-friends protocol' using the search argument
	 * provided.
	 * 
	 * @param search the String to search similar accounts for.
	 */
	public void searchFriend(String search) {
		client.setMainInput(Protocol.SEARCH_FRIEND.concat(" : " + search));
		client.setFlag();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'add friends protocol' using the friend account's username
	 * provided.
	 * 
	 * @param accountName the username of the friend's account to add to friends list.
	 */
	public void addFriend(String accountName) {
		client.setMainInput(Protocol.ADD_FRIEND.concat(" : " + accountName));
		client.setFlag();
	}
	/**
	 * Sets the protocol message in the <code>ClientThread</code> to 
	 * to the 'remove friends protocol' using the friend account's username
	 * provided.
	 * 
	 * @param accountName the username of the friend's account to remove from friends list.
	 */
	public void removeFriend(String accountName) {
		client.setMainInput(Protocol.REMOVE_FRIEND.concat(" : " + accountName));
		client.setFlag();
	}
	/**
	 * Gets the account field of the <code>ClientThread</code>. Used to retrieve
	 * the account for the logged in user when the account has been modified
	 * by the server or when an existing user has logged in on a new device 
	 * for the first time.
	 * 
	 * @return The account field of the <code>ClientThread</code>.
	 */
	public Account getAccount() {
		return client.getAccount();
	}
	/**
	 * Waits for the <code>ClientThread</code> to finish.
	 * 
	 * @see InterruptedException
	 */
	public void join() {
		try {
			client.join();
		} catch (InterruptedException e) {
			//This should never be called
			System.out.println("ClientSide: Client Thread could not be joinded");
		}
	}
	/**
	 * Retrieves the success of the conversation (protocol) between the
	 * server and client. Uses the {@link ReentrantLock}s provided to the
	 * <code>ClientThread</code> in order to determine when the conversation has finished
	 * and when the output can be retrieved. The locks are used like flags to
	 * indicate when the <code>ClientThread</code> has finished communications with the server.
	 * When the <code>threadLock</code> is locked by the <code>ClientThread</code>, this initiates
	 * the retrieval of the success message.
	 * 
	 * <p>
	 * See this Class documentation ({@link ClientSide}) for a guide on how to use this method.
	 * 
	 * @return The success/error message produced by the <code>ClientThread</code>.
	 */
	public String receive() {
		String input = "waiting";
		// check if client has finished communications.
		if (!threadLock.tryLock()) {
			//retrieve the output message
			input = client.getThreadOutput();
			if (!checkIfConnectionLost(input)) {
				generateReminderAlert();
				return "waiting";
			}
			// indicates to client thread that the message has been received 
			mainLock.lock();
			while (true) {
				// when the thread lock can be accessed, this signifies end of 
				//message transfer between client and main threads.
				if (threadLock.tryLock()) {
					threadLock.unlock();
					mainLock.unlock();
					break;
				}
			}
		} else {
			//reset lock if client hasn't finished commnications
			threadLock.unlock();
		}
		return input;
	}
	/**
	 * Generates the offline alert.
	 * 
	 * @see ExceptionFx
	 */
	private void generateReminderAlert() {
		ExceptionFx except = new ExceptionFx(AlertType.WARNING, "Offline Error",
				"You are not connected to the server",
				"You're session has been switched to offline. This means"
						+ " that all social features wil be inaccessible. "
						+ "You will need to restart the program to reconnect.");
		except.show();
	}
	/**
	 * Checks to see if the input string is equal to <code>Protocol.LOST_CONNECTION</code>.
	 * 
	 * @param input the protocol message to check.
	 * @return True if the input is <code>Protocol.LOST_CONNECTION</code>.
	 */
	private boolean checkIfConnectionLost(String input) {
		if (input.equals(Protocol.LOST_CONNECTION)) {
			accessible = false;
		}
		return accessible;
	}
	/**
	 * Checks whether the <code>ClientThread</code> is still alive.
	 * 
	 * @return True if the client thread is alive and accessible.
	 */
	public boolean isAccessible() {
		return accessible;
	}

}
