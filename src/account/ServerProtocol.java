package account;

import java.util.ArrayList;
/**
 * This Class acts as a finite state machine to generate the conversation had between the server and client, 
 * driven by the <code>processInput(Object)</code> to determine the next state.
 * This method generates a reply message, based on the current protocol and input Object,
 * to the client's input message (produced from the equivalent {@link ClientThread} <code>processInput(Object)</code> method. <br>
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
 * can be used to determine the possible cause of the error. However the <code>ServerThread</code> doesn't handle
 * errors, as they are only related to the client's main application.
 * <p>
 * The state machine is moved out of the standby loop when the client input changes, or when the
 * {@link ServerManager} notifies the <code>ServerThread</code> of a game request.
 *
 * <STRONG>Protocol Functionality Includes:</STRONG> <br>
 * <ul>
 * <li>Login, </li>
 * <li>Logout, </li>
 * <li>Create New Account, </li>
 * <li>Save, </li>
 * <li>Friend Functionality (Search, Add, Remove, Retrieve).</li>
 * </ul>
 *
 * <STRONG>Common <code>Protocol</code> messages</STRONG>
 * <ul>
 * <li>Protocol.HELLO      :    Initialises the first communications had by the server and client.</li>
 * <li>Protocol.STANDBYE   :    Informs the server/client threads to enter a sleep-check cycle.</li>
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
public class ServerProtocol extends Protocol {

	public static final int WAITING = 0, RECEIVE_PROTOCOL = 1, CREATE_ACCOUNT = 2, 
							LOGIN = 3, LOGOUT = 4, CHECK_LAST_SAVE_DATE = 5,
							PUSH = 6, PULL = 7, SEND_FRIENDS = 8, ADD_FRIEND = 9, 
							DEL_FRIEND = 10, SEARCH_FRIEND = 11, END = 12;
	private static final String directory = "src/res/serverAccounts/";
	private static final int saveTimeDifferenceBoundary = 5000;

	private String localAccount;
	private int state = WAITING;
	private String protocolMessage = null;
	private ArrayList<Account> friends;
	private AccountHandler myAccount = new AccountHandler();
	/**
	 * 
	 * Takes an input Object and, based on the <code>state</code>, performs the necessary functions to the users
	 * account and returns an output Object as a reply. See Class description for more details.
	 * @param inputObject Predominantly of class String. The conversation input used to drive the state machine.
	 * @return output Predominantly of class String. The reply to the conversation input.
	 */
	public Object processInput(Object inputObject) {
		Object output = "null";
		String input = "";
		if (inputObject instanceof String) {
			input = (String) inputObject;
		}
		if (input.startsWith(Protocol.ERROR)) {
			//error check at top to prevent having in each state
			output = Protocol.ERROR_CONFIRMED;
			state = END;
		} else if (input.equals("null") || input.equals(Protocol.STANDBY)) {
			//used in standby loop when there isn't a protocol for the server/client to process.
			output = Protocol.STANDBY;
			protocolMessage = "";
			state = WAITING;
			//indicates the start of a new conversation.
		} else if (input.equals(Protocol.HANDSHAKE)) {
			output = Protocol.HANDSHAKE;
			state = WAITING;
		} else if (state == WAITING) {
			//all acknowledge the input and set the next state.
			//some of the statements also save the input to protocolMessage for reference in a later state
			if (input.startsWith(Protocol.CREATE_ACCOUNT)) {
				protocolMessage = input;
				output = Protocol.ACKNOWLEDGED;
				state = CREATE_ACCOUNT;
			} else if (input.startsWith(Protocol.LOGIN)) {
				protocolMessage = input;
				output = Protocol.ACKNOWLEDGED;
				state = LOGIN;
			} else if (input.startsWith(Protocol.LOGOUT)) {
				protocolMessage = input;
				output = Protocol.ACKNOWLEDGED;
				state = PULL;
			} else if (input.startsWith(Protocol.SAVE)) {
				output = Protocol.ACKNOWLEDGED;
				state = PULL;
			} else if (input.equals(Protocol.RETRIEVE_FRIENDS)) {
				ArrayList<Account> temp = new ArrayList<Account>();
				//Checks to see if the friends list has been created
				if (myAccount.getAllFriendAccounts() != null) {
					temp = myAccount.getAllFriendAccounts();
					//checks to see if the friend list is empty
					if (!temp.toString().equals("[]")) {
						//sets the friends array list to the friends field
						friends = temp;
						//inform client that the friends request has been acknowledged in the server
						output = Protocol.ACKNOWLEDGED;
						state = SEND_FRIENDS;
					} else {
						//returns NO Frinds error if the user has no friends
						output = Protocol.ERROR + " : " + Protocol.NO_FRIENDS;
						state = END;
					}
				} else {
					output = Protocol.ERROR + " : " + Protocol.NO_FRIENDS;
					state = END;
				}
			} else if (input.startsWith(Protocol.ADD_FRIEND)) {
				protocolMessage = input;
				output = Protocol.ACKNOWLEDGED;
				state = ADD_FRIEND;
			} else if (input.startsWith(Protocol.REMOVE_FRIEND)) {
				protocolMessage = input;
				output = Protocol.ACKNOWLEDGED;
				state = DEL_FRIEND;
			} else if (input.startsWith(Protocol.SEARCH_FRIEND)) {
				protocolMessage = input;
				output = Protocol.ACKNOWLEDGED;
				state = SEARCH_FRIEND;
				//The server manager can send game requests to each server thread
				//the request is then sent back to teh client
			} else if (input.startsWith(Protocol.EXT_GAME_REQ)) {
				protocolMessage = input;
				output = Protocol.EXT_GAME_REQ;
			}
		} else if (state == CREATE_ACCOUNT) {
			if (input.equals(Protocol.WAITING)) {
				//Checks whether new account was created successfully
				if (myAccount.createNewAccount(directory, protocolMessage)) {
					output = Protocol.COMPLETED;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == LOGIN) {
			if (input.equals(Protocol.WAITING)) {
				//returns whether the login was successful or not
				String loginStatus = myAccount.login(directory, protocolMessage);
				//if successful login
				if (loginStatus.equals(LoginStatus.LOGGED_IN)) {
					//set the account number to indicate which account this server thread is managing
					localAccount = myAccount.getAccount().getNumber();
					//set the login status of the account to true and save
					myAccount.getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					myAccount.saveAccount(directory);
					output = Protocol.COMPLETED;
					//move to next state which checks whether the account needs to be
					//pushed or pulled to/from the server.
					state = CHECK_LAST_SAVE_DATE;
				//if someone is already using the account, return the error to the client
				} else if (loginStatus.equals(LoginStatus.IN_USE)) {
					output = Protocol.ERROR + " : " + loginStatus;
					state = END;
				//if the account doesn't exist in the server repository return the error to the client
				} else if (loginStatus.equals(LoginStatus.ACCOUNT_NOT_FOUND)) {
					output = Protocol.ERROR + " : " + loginStatus;
					state = END;
				//if the login failed for any other reasons, return the error to the client
				} else if (loginStatus.equals(LoginStatus.LOGGED_OUT)) {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == LOGOUT) {
			//attempt to save the account
			if (input.startsWith(Protocol.SAVE)) {
				//attempt logout and return the success status
				if (myAccount.logout(directory)) {
					output = Protocol.LOGOUT_SUCCESS;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		//checks whether the the server needs to pull or push from/to the client
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.startsWith(Protocol.LAST_SAVE_DATE)) {
				//calculates the difference in the server and clients copy of the account's
				//last save date, and pushes/pulls to make both the server/client has the most 
				//up to date difference
				long difference = myAccount.getAccount().getLastSaved() - Long.parseLong(getMessage(input));
				if (difference < -saveTimeDifferenceBoundary) {
					output = Protocol.PUSH_REQUEST;
					state = PULL;
				} else if (difference > saveTimeDifferenceBoundary) {
					output = Protocol.PULL_REQUEST;
					state = PUSH;
				} else {
					output = Protocol.ACCOUNT_UP_TO_DATE;
					state = END;
				}
			}
		} else if (state == PULL) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.ACKNOWLEDGED;
				//if an object has been sent that isn't a String, but is an Account...
			} else if (input.equals("") && (inputObject instanceof Account)) {
				Account temp = (Account) inputObject;
				//pass the account for the server thread to it's account handler
				myAccount.setAccount(temp);
				//attempt ot save the account
				if (myAccount.saveAccount(directory)) {
					//checks if this is a last save due to a logout request
					if (protocolMessage.equals(Protocol.LOGOUT)) {
						//replies with the success of the logout attempt
						if (myAccount.logout(directory)) {
							output = Protocol.LOGOUT_SUCCESS;
							state = END;
						} else {
							output = Protocol.ERROR;
							state = END;
						}
					} else {
						output = Protocol.COMPLETED;
						state = END;
					}
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == PUSH) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				//pushes server's copy of the account to the client
				output = myAccount.getAccount();
			} else if (input.equals(Protocol.RECEIVED)) {
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == SEND_FRIENDS) {
			if (input.equals(Protocol.WAITING)) {
				//send the friends list set in the Waiting state
				//for a retrieve friends request
				output = friends;
				//check if client received the account successfully
			} else if (input.equals(Protocol.RECEIVED)) {
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == ADD_FRIEND) {
			if (input.equals(Protocol.WAITING)) {
				//retrieve the username from the message and add to friends list in the account
				myAccount.addFriend(getMessage(protocolMessage));
				myAccount.saveAccount(directory);
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == DEL_FRIEND) {
			if (input.equals(Protocol.WAITING)) {
				//retrieve the username from the message and deletes them from
				//the friends list of this account
				myAccount.delFriend(getMessage(protocolMessage));
				myAccount.saveAccount(directory);
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == SEARCH_FRIEND) {
			if (input.equals(Protocol.WAITING)) {
				//searches the server directory for related accounts to the input message
				ArrayList<Account> searchResult = myAccount.searchFriend(getMessage(protocolMessage));
				//check to see if the search results are empty or not, and output result
				if (!searchResult.toString().equals("[]")) {
					output = searchResult;
				} else {
					output = Protocol.ERROR + " : " + Protocol.NO_MATCHES;
				}
				//check if the client received the search results
			} else if (input.equals(Protocol.RECEIVED)) {
				output = Protocol.COMPLETED;
				state = END;
			} else if (input.equals(Protocol.ERROR)) {
				output = Protocol.STANDBY;
				state = END;
			}
		} else if (state == END) {
			//at the end of a protocol, return the standby message
			//this makes both server and client threads enter a standby loop 
			//until a new protocol is initiated
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.STANDBY;
				state = WAITING;
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.STANDBY;
				state = WAITING;
			} if (input.equals(Protocol.ERROR_CONFIRMED)) {
				output = Protocol.STANDBY;
				state = WAITING;
			}
		}

		return output;
	}
	/**
	 * Retrieves the account number of the account this server thread is managing
	 * @return the account number of this connection.
	 */
	public String getAccountNumber() {
		return localAccount;
	}

}
