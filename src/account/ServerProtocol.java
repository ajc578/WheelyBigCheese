package account;

import java.util.ArrayList;
/**
 * This Class acts as a finite state machine to generate the conversation had between the server and client, 
 * driven by the <tt>processInput(Object)</tt> to determine the next state.
 * This method generates a reply message, based on the current protocol and input Object,
 * to the client's input message (produced from the equivalent {@link ClientThread} <tt>processInput(Object)</tt> method. <br>
 * The communications sent between the server and client act to modify, load and save the users account details, as well as
 * view their friends accounts.
 * <p>
 * The inputs and outputs to the <tt>processInput</tt> method are predominantly from a set of <tt>String</tt> constants in the {@link Protocol} class.
 * These form the building blocks of the conversations had between the {@link ServerThread} and the {@link ClientThread}.
 * Some inputs/outputs use the constants for the start of a message tag. For example:
 * <ul>
 * Protocol.LOGIN + " : <tt>username</tt>,<tt>password</tt>"
 * </ul>
 * Other inputs/outputs just contain the String constant alone. For example
 * <ul>
 * <p>
 * Protocol.HELLO
 * </ul>
 * <p>
 * Each state contains the predetermined response and reply to an expected input sent from the server. 
 * If, for example the login credentials appended in the Protocol.LOGIN tagged <tt>String</tt> are incorrect,
 * then the <tt>processInput(Object)</tt> method will return an error message in the form:
 * <ul>
 * Protocol.ERROR
 * </ul>
 * <p>
 * When the error message is received by the recipient <tt>protocol(Object)</tt> method, the <tt>state</tt> 
 * can be used to determine the possible cause of the error. However the <tt>ServerThread</tt> doesn't handle
 * errors, as they are only related to the client's main application.
 * <p>
 * The state machine is moved out of the standby loop when the client input changes, or when the
 * {@link ServerManager} notifies the <tt>ServerThread</tt> of a game request.
 *
 * <STRONG>Protocol Functionality Includes:</STRONG> <br>
 * <p>
 * <ul>
 * Login, <br>
 * Logout, <br>
 * Create New Account, <br>
 * Save, <br>
 * Friend Functionality (Search, Add, Remove, Retrieve).
 * </ul>
 *
 * <STRONG>Common <tt>Protocol</tt> messages</STRONG>
 * <p>
 * <ul>
 * Protocol.HELLO      :    Initialises the first communications had by the server and client. <br>
 * Protocol.STANDBYE   :    Informs the server/client threads to enter a sleep-check cycle. <br>
 * Protocol.SUCCESS    :    Informs the server/client that a protocol has been completed successfully. <br>
 * Protocol.BYE        :    Predominantly sent by the client to inform the server that specific conversation in finished <br>
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
	 * Takes an input Object and, based on the <tt>state</tt>, performs the necessary functions to the users
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
		} else if (input.equals("null") || input.equals(Protocol.STANDBYE)) {
			//used in standby loop when there isn't a protocol for the server/client to process.
			output = Protocol.STANDBYE;
			protocolMessage = "";
			state = WAITING;
			//indicates the start of a new conversation.
		} else if (input.equals(Protocol.HANDSHAKE)) {
			output = Protocol.HANDSHAKE;
			state = WAITING;
		} else if (state == WAITING) {
			//all acknowledge the input and set the next state.
			//sum if statements also save the input to protocolMessage for reference in a later state
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
				if (myAccount.getAllFriendAccounts() != null) {
					temp = myAccount.getAllFriendAccounts();
					if (!temp.toString().equals("[]")) {
						friends = temp;
						output = Protocol.ACKNOWLEDGED;
						state = SEND_FRIENDS;
					} else {
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
			} else if (input.startsWith(Protocol.EXT_GAME_REQ)) {
				protocolMessage = input;
				output = Protocol.EXT_GAME_REQ;
			}
		} else if (state == CREATE_ACCOUNT) {
			if (input.equals(Protocol.WAITING)) {
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
				String loginStatus = myAccount.login(directory, protocolMessage);
				if (loginStatus.equals(LoginStatus.LOGGED_IN)) {
					localAccount = myAccount.getAccount().getNumber();
					myAccount.getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					myAccount.saveAccount(directory);
					output = Protocol.COMPLETED;
					state = CHECK_LAST_SAVE_DATE;
				} else if (loginStatus.equals(LoginStatus.IN_USE)) {
					output = Protocol.ERROR + " : " + loginStatus;
					state = END;
				} else if (loginStatus.equals(LoginStatus.ACCOUNT_NOT_FOUND)) {
					output = Protocol.ERROR + " : " + loginStatus;
					state = END;
				} else if (loginStatus.equals(LoginStatus.LOGGED_OUT)) {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == LOGOUT) {
			if (input.startsWith(Protocol.SAVE)) {

				if (myAccount.logout(directory)) {
					output = Protocol.LOGOUT_SUCCESS;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.startsWith(Protocol.LAST_SAVE_DATE)) {
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
			} else if (input.equals("") && (inputObject instanceof Account)) {
				Account temp = (Account) inputObject;
				myAccount.setAccount(temp);
				if (myAccount.saveAccount(directory)) {
					if (protocolMessage.equals(Protocol.LOGOUT)) {
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
				output = myAccount.getAccount();
			} else if (input.equals(Protocol.RECEIVED)) {
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == SEND_FRIENDS) {
			if (input.equals(Protocol.WAITING)) {
				output = friends;
			} else if (input.equals(Protocol.RECEIVED)) {
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == ADD_FRIEND) {
			if (input.equals(Protocol.WAITING)) {
				myAccount.addFriend(getMessage(protocolMessage));
				myAccount.saveAccount(directory);
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == DEL_FRIEND) {
			if (input.equals(Protocol.WAITING)) {
				System.out.println("message passed to delFriend in server: " + getMessage(protocolMessage));
				myAccount.delFriend(getMessage(protocolMessage));
				myAccount.saveAccount(directory);
				output = Protocol.COMPLETED;
				state = END;
			}
		} else if (state == SEARCH_FRIEND) {
			if (input.equals(Protocol.WAITING)) {
				System.out.println(getMessage(protocolMessage));
				ArrayList<Account> searchResult = myAccount.searchFriend(getMessage(protocolMessage));
				if (!searchResult.toString().equals("[]")) {
					output = searchResult;
				} else {
					output = Protocol.ERROR + " : " + Protocol.NO_MATCHES;
				}
			} else if (input.equals(Protocol.RECEIVED)) {
				output = Protocol.COMPLETED;
				state = END;
			} else if (input.equals(Protocol.ERROR)) {
				output = Protocol.STANDBYE;
				state = END;
			}
		} else if (state == END) {
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.STANDBYE;
				state = WAITING;
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.STANDBYE;
				state = WAITING;
			} if (input.equals(Protocol.ERROR_CONFIRMED)) {
				output = Protocol.STANDBYE;
				state = WAITING;
			}
		}

		return output;
	}

	public String getAccountNumber() {
		return localAccount;
	}

	public int getState() {
		return state;
	}

}
