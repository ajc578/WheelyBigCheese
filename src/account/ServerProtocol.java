package account;

import java.util.ArrayList;	

public class ServerProtocol extends Protocol {

	public static final int WAITING = 0, RECEIVE_PROTOCOL = 1, CREATE_ACCOUNT = 2, LOGIN = 3, LOGOUT = 4, CHECK_LAST_SAVE_DATE = 5,
			PUSH = 6, PULL = 7, SEND_FRIENDS = 8, ADD_FRIEND = 9, DEL_FRIEND = 10, SEARCH_FRIEND = 11, END = 12;
	private static final String directory = "src/res/serverAccounts/";
	private static final int saveTimeDifferenceBoundary = 5000;

	private String localAccount;
	private int state = WAITING;
	private String protocolMessage = null;
	private ArrayList<Account> friends;
	private AccountHandler myAccount = new AccountHandler();

	public Object processInput(Object inputObject) {
		Object output = "null";
		String input = "";
		if (inputObject instanceof String) {
			input = (String) inputObject;
		}
		if (input.startsWith(Protocol.ERROR)) {
			output = Protocol.ERROR_CONFIRMED;
			state = END;
		} else if (input.equals("null") || input.equals(Protocol.STANDBYE)) {
			output = Protocol.STANDBYE;
			protocolMessage = "";
			state = WAITING;
		} else if (state == WAITING) {
			if (input.equals(Protocol.HANDSHAKE)) {
				output = Protocol.HANDSHAKE;
			} else if (input.startsWith(Protocol.CREATE_ACCOUNT)) {
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
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.STANDBYE;
			} if (input.equals(Protocol.ERROR_CONFIRMED)) {
				output = Protocol.STANDBYE;
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
