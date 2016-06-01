package account;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
/**
 * This Class acts as a finite state machine to generate the conversation had between the server and client, 
 * driven by the <code>processInput(Object)</code> to determine the next state.
 * This method generates a reply message, based on the current protocol and input Object,
 * to the server's input message (produced from the equivalent {@link ServerThread} <code>processInput(Object)</code> method. <br>
 * The communications sent between the server and client act to modify, load and save the users account details, as well as
 * view their friends accounts.
 * <p>
 * The inputs and outputs to the <code>processInput</code> method are predominantly from a set of <code>String</code> constants in the {@link Protocol} class.
 * These form the building blocks of the conversations had between the {@link ServerThread} and the {@link ClientThread}.
 * Some inputs/outputs use the constants for the start of a message tag. For example:
 * <ul>
 * <li>Protocol.LOGIN + " : <code>username</code>,<code>password</code>"</li>
 * </ul>
 * Other inputs/outputs just contain the String constant alone. For example
 * <ul>
 * <li>Protocol.HELLO</li>
 * </ul>
 * <p>
 * Each state contains the predetermined response and reply to an expected input sent from the server. 
 * If, for example the login credentials appended in the Protocol.LOGIN tagged <code>String</code> are incorrect,
 * then the <code>processInput(Object)</code> method will return an error message in the form:
 * <ul>
 * <li>Protocol.ERROR</li>
 * </ul>
 * <p>
 * When the error message is received by the recipient <code>protocol(Object)</code> method, the <code>state</code> 
 * can be used to determine the possible cause of the error. For example, if an error message is
 * received in this <code>ClientThread</code>, while in the <code>PULL</code> state, the recipient can determine that there was an 
 * error pulling from the server and that the server could not load the {@link Account} <code>Object</code> to send.
 * <p>
 * The state machine is moved out of the standbye loop either when the server input changes, or when the
 * {@link ClientSide} sets a new <code>protocol</code> in the <code>ClientThread</code>.
 *
 * <STRONG>Protocol Functionality Includes:</STRONG> 
 * <p>
 * <ul>
 * <li>Login, </li>
 * <li>Logout, </li>
 * <li>Create New Account, </li>
 * <li>Save, </li>
 * <li>Friend Functionality (Search, Add, Remove, Retrieve).</li>
 * </ul>
 *
 * <STRONG>Common <code>Protocol</code> messages</STRONG>
 * <p>
 * <ul>
 * <li>Protocol.HELLO      :    Initialises the first communications had by the server and client. </li>
 * <li>Protocol.STANDBYE   :    Informs the server/client threads to enter a sleep-check cycle. </li>
 * <li>Protocol.SUCCESS    :    Informs the server/client that a protocol has been completed successfully. </li>
 * <li>Protocol.BYE        :    Predominantly sent by the client to inform the server that specific conversation in finished </li>
 * </ul>
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class ClientProtocol extends Protocol {

	private static final int WAITING = 0, CREATE_ACCOUNT = 1, LOGIN = 2,LOGOUT = 3, CHECK_LAST_SAVE_DATE = 4,
			PUSH = 5, PULL = 6, GET_FRIENDS = 7, ADD_FRIEND = 8, DEL_FRIEND = 9, SEARCH_FRIEND = 10, END = 11;
	private static final String directory = "src/res/clientAccounts/";

	private int state = WAITING;
	private String protocol = null;
	private boolean loginNew = false;
	private ArrayList<Account> friendsList = null;
	private Account friend = null;
	private AccountHandler myAccount = new AccountHandler();
	/**
	 * Takes an input Object and, based on the <code>state</code>, performs the necessary functions to the users
	 * account and returns an output Object as a reply. See Class description for more details.
	 * @param inputObject Predominantly of class String. The conversation input used to drive the state machine.
	 * @return output Predominantly of class String. The reply to the conversation input.
	 */
	@SuppressWarnings("unchecked")
	public Object processInput(Object inputObject) {
		Object output = "null";
		String input = "";
		if (inputObject instanceof String) {
			input = (String) inputObject;
		}
		//Acknowledge error if one exists
		if (input.startsWith(Protocol.ERROR)) {
			output = Protocol.ERROR_CONFIRMED;
			state = END;
		} else if (input.equals(Protocol.STANDBY)) {
			//reset protocol and state and return to the standby loop
			protocol = "";
			state = WAITING;
			output = Protocol.STANDBY;
		} else if (state == WAITING) {
			if (input.equals(Protocol.HANDSHAKE)) {
				//determines the next state from the protocol tags and outputs the protocol message to the server
				if (protocol.startsWith(Protocol.CREATE_ACCOUNT)) {
					output = protocol;
					state = CREATE_ACCOUNT;
				} else if (protocol.startsWith(Protocol.LOGIN)) {
					//determines, from the output of the login method, if the account was logged in in successfully or not
					String loginStatus = myAccount.login(directory, protocol);
					//If the login if successful
					if (loginStatus.equals(LoginStatus.LOGGED_IN)) {
						//Set the local login status and output credentials to remote server login
						myAccount.getAccount().setLoginStatus(LoginStatus.LOGGED_IN_LOCALLY);
						output = protocol;
						state = LOGIN;
					} else if (loginStatus.equals(LoginStatus.ACCOUNT_NOT_FOUND)){
						//Could be a new login on a new device, therefore treat as new login
						output = protocol;
						loginNew = true;
						state = LOGIN;
					} else if (loginStatus.equals(LoginStatus.IN_USE)) {
						//The account is already logged in, therefore the user cannot login
						output = Protocol.ERROR;
						state = END;
					} else {
						output = Protocol.ERROR;
						state = END;
					}
				} else if (protocol.startsWith(Protocol.LOGOUT)) {
					output = protocol;
					state = LOGOUT;
				} else if (protocol.startsWith(Protocol.SAVE)) {
					myAccount.saveAccount(directory);
					output = Protocol.SAVE;
					state = PUSH;
				} else if (protocol.equals(Protocol.RETRIEVE_FRIENDS)) {
					friendsList = new ArrayList<Account>();
					output = protocol;
					state = GET_FRIENDS;
				} else if (protocol.startsWith(Protocol.ADD_FRIEND)) {
					output = protocol;
					state = ADD_FRIEND;
				} else if (protocol.startsWith(Protocol.REMOVE_FRIEND)) {
					output = protocol;
					state = DEL_FRIEND;
				} else if (protocol.startsWith(Protocol.SEARCH_FRIEND)) {
					output = protocol;
					state = SEARCH_FRIEND;
				} else if (protocol.startsWith(Protocol.LOCAL_GAME_REQ)) {
					output = protocol;
				} 
			} else if (input.equals(Protocol.LOGOUT)) {
				//if server logout successful, attempt local logout
				if (myAccount.logout(directory)) {
					output = Protocol.LOGOUT_SUCCESS;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
				//If a friend makes an external game request
			} else if (input.startsWith(Protocol.EXT_GAME_REQ)) {
				String opponent = Protocol.getMessage(protocol);
				//Create Alert to get user input and return the result to the server
				if (gameRequestDialog(opponent)) {
					output = Protocol.GAME_ACCEPTED;
				} else {
					output = Protocol.GAME_DECLINED;
				}
			}
		} else if (state == CREATE_ACCOUNT) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				//if server create account was successful, then attempt local create account and output result
				if (myAccount.createNewAccount(directory, protocol)) {
					output = Protocol.BYE;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == LOGIN) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				// If remote server login was successful, then save the account, 
				// or construct a new account for a new login
				if (!loginNew) {
					myAccount.getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					myAccount.saveAccount(directory);
				} else {
					myAccount.setAccount(new Account());
				}
				// output account last save date and time as system time for the server to determine whether to pull to or push from the remote account.
				output = Protocol.LAST_SAVE_DATE + " : " + (loginNew == true ? "0" : myAccount.getAccount().getLastSaved());
				state = CHECK_LAST_SAVE_DATE;
			}
		} else if (state == LOGOUT) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = myAccount.getAccount();
			} else if (input.equals(Protocol.LOGOUT_SUCCESS)) {
				//if account logged out from server successfully, attempt local logout and return success
				if (myAccount.logout(directory)) {
					output = Protocol.BYE;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
			//Checks the result from the server as to whether the client needs to pull or push on a server login.
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.equals(Protocol.PUSH_REQUEST)) {
				//if local account is the most recent version of the user's account, push to server
				output = Protocol.ACKNOWLEDGED;
				state = PUSH;
			} else if (input.equals(Protocol.PULL_REQUEST)) {
				//if remote server account is the most recent version of the user's account, pull from server
				output = Protocol.ACKNOWLEDGED;
				state = PULL;
			} else if (input.equals(Protocol.ACCOUNT_UP_TO_DATE)) {
				//Otherwise account is up-to-date and the login protocol has finished successfully
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == PUSH) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				//Send the server account to the client
				output = myAccount.getAccount();
				state = END;
			}
		} else if (state == PULL) {
			//pull from the server's account
			if (input.equals("") && (inputObject instanceof Account)) {
				Account temp = (Account) inputObject;
				myAccount.setAccount(temp);
				//attempt to save (marshal) the account to the client directory
				if (myAccount.saveAccount(directory)) {
					output = Protocol.RECEIVED;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == GET_FRIENDS) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
				//if the input is an ArrayList of Accounts, they are the friend accounts we requested
			} else if (input.equals("") && (inputObject instanceof ArrayList<?>) && (((ArrayList<?>) inputObject).get(0) instanceof Account)) {
				friendsList = (ArrayList<Account>) inputObject; //convert to the friend account list
				//inform server that the friends list was received successfully 
				output = Protocol.RECEIVED;
				state = END;
			}
		} else if (state == ADD_FRIEND) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				//if the requested friend was added to the user's friends list successfully, add locally and save (marshal) the account.
				myAccount.addFriend(getMessage(protocol));
				myAccount.saveAccount(directory);
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == DEL_FRIEND) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				//if the requested friend was deleted from the user's friends list successfully, delete locally and save (marshal) the account.
				myAccount.delFriend(getMessage(protocol));
				myAccount.saveAccount(directory);
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == SEARCH_FRIEND) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
				//if the input is an ArrayList of Accounts, they are the search result list of accounts
			} else if (input.equals("") && (inputObject instanceof ArrayList<?>) && (((ArrayList<?>) inputObject).get(0) instanceof Account)) {
				friendsList = (ArrayList<Account>) inputObject;
				//if the accounts are received successfully, notify the server of success
				if (friendsList != null)
					output = Protocol.RECEIVED;
				else
					output = Protocol.ERROR;
				state = END;
			}
		} else if (state == END) {
			//default end protocol messages
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;
				state = WAITING;
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.STANDBY;
				state = WAITING;
			}
		} else {
			state = WAITING;
		}
		return output;
	}
	/**
	 * Sets the state of the finite state machine.
	 * @param state the state to set the finite state machine to.
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * Sets the local account field to be modified by the protocol outcomes.
	 * @param account The account to be modified.
	 */
	public void setAccount(Account account) {
		this.myAccount.setAccount(account);
	}
	/**
	 * Gets the local account field.
	 * @return The local account.
	 */
	public Account getAccount() {
		return myAccount.getAccount();
	}
	/**
	 * Gets a single friend account set as a result of a friend protocol message.
	 * @return The friends account object.
	 */
	public Account getFriend() {
		return friend;
	}
	/**
	 * Gets a list of friend accounts or search result account list set as a result of a 
	 * <code>Protocol.RETRIEVE_FRIENDS</code> or <code>Protocol.SEARCH_FRIEND</code> protocol respectively.
	 *
	 * @return The list of accounts stored in friendsList.
	 */
	public ArrayList<Account> getFriendsList() {
		return friendsList;
	}
	/**
	 * Sets the protocol to determine the new state flow of conversations
	 * @param protocol the new protocol message tagged with a <code>Protocol</code> constant.
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * Creates an <code>Alert</code> to warn the user of an external game request
	 * and also waits for the users response to the request.
	 * @param opponent The opponents user name making the request.
	 * @return The users response to the Alert dialog.
	 * @see Alert
	 */
	private boolean gameRequestDialog(String opponent) {
		boolean gameAccepted = false;
		boolean timeout = false;

		//create alert content
		Alert gameAlert = new Alert(AlertType.CONFIRMATION);
		gameAlert.setTitle("Game Request");
		gameAlert.setHeaderText(opponent + " would like to play a game with you");
		gameAlert.setContentText("Press 'Accept' to start the game, or 'Decline' to cancel.");

		//add response buttons
		ButtonType accept = new ButtonType("Accept");
		ButtonType decline = new ButtonType("Decline");

		gameAlert.getButtonTypes().setAll(accept, decline);

		//cancel input if the user doesn't respond within the response time period of 15 seconds
		Timer maxWait = new Timer();
		maxWait.schedule(new TimerTask() {

			@Override
			public void run() {
				gameAlert.close();
			}
		}, 15000);

		//gets users choice
		Optional<ButtonType> choice = gameAlert.showAndWait();
		maxWait.cancel();
		if (!timeout) {
			if (choice.get() == accept) {
				gameAccepted = true;
			}
		}

		return gameAccepted;
	}
}
