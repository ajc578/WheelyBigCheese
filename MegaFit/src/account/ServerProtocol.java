package account;

import java.util.List;

public class ServerProtocol extends Protocol {
	
	public static final int WAITING = 0, RECEIVE_PROTOCOL = 1, CREATE_ACCOUNT = 2, LOGIN = 3, LOGOUT = 4, CHECK_LAST_SAVE_DATE = 5, 
							 PUSH = 6, PULL = 7, SEND_FRIENDS = 8, END = 9;
	private static final int NUM_OF_ATTRIBUTES = 10;
	private static final String directory = "src/res/serverAccounts/";
	private static final int saveTimeDifferenceBoundary = 5000;
	
	private int state = WAITING;
	private int index = 0;
	private String protocolMessage = null; 
	private List<String> friends;
	
	public String processInput(String input) {
		String output = null;
		
		if (input.equals(Protocol.ERROR)) {
			output = Protocol.BYE;
			index = 0;
			state = END;
		} else if (state == WAITING) {
			if (input.equals(Protocol.HANDSHAKE)) {
				output = Protocol.HANDSHAKE;
				state = RECEIVE_PROTOCOL;
			} else { 
				output = Protocol.WAITING;
				System.out.println("Server is waiting to shake hands");
			}
		} else if (state == RECEIVE_PROTOCOL) {
			if (input.startsWith(Protocol.DECLARE_ACCOUNT)) {
				setAccount(new Account());
				if (getAccount().loadAccount(directory, getMessage(input))) {
					if (getAccount().getLoginStatus().equals(LoginStatus.LOGGED_IN)) {
						output = Protocol.ACKNOWLEDGED;
					} else {
						output = Protocol.LOGOUT;
						state = END;
					}
				} else {
					output = Protocol.ERROR;
					state = END;
				}
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
				state = LOGOUT;
			} else if (input.startsWith(Protocol.SAVE)) {
				output = Protocol.ACKNOWLEDGED;
				state = PULL;
			} else if (input.startsWith(Protocol.RETRIEVE_FRIENDS)) {
				friends = Protocol.getFriends(getAccount().getFriendsList());
				output = Protocol.ACKNOWLEDGED;
				state = SEND_FRIENDS;
			}
		} else if (state == CREATE_ACCOUNT) {
			if (input.equals(Protocol.WAITING)) {
				if (createNewAccount(directory, protocolMessage)) {
					output = Protocol.COMPLETED;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			} 
		} else if (state == LOGIN) {
			if (input.equals(Protocol.WAITING)) {
				if (login(directory, protocolMessage).equals(LoginStatus.LOGGED_IN)) {
					getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					getAccount().saveAccount(directory);
					output = Protocol.COMPLETED;
					state = CHECK_LAST_SAVE_DATE;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			} 
		} else if (state == LOGOUT) {
			if (input.equals(Protocol.WAITING)) {
				//System.out.println(protocolMessage);
				if (logout(directory, protocolMessage)) {
					output = Protocol.COMPLETED;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.startsWith(Protocol.LAST_SAVE_DATE)) {
				long difference = Long.parseLong(getAccount().getSaveDate()) - Long.parseLong(getMessage(input));
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
			if (input.startsWith(Protocol.DECLARE_SAVE)) {
				List<String> message = splitMessage(input);
				getAccount().editAccount(Integer.parseInt(message.get(0)), message.get(1));
				output = Protocol.ACKNOWLEDGED;
			} else if (input.equals(Protocol.COMPLETED)){
				if (getAccount().saveAccount(directory)) {
					output = Protocol.COMPLETED;
					state = END;
				} else {
					System.out.println("Client Save Error - Client failed to save Account to file.");
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == PUSH) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				if (index < NUM_OF_ATTRIBUTES) {
					output = Protocol.DECLARE_SAVE + " : " + Integer.toString(index) + "," + getAccount().getAttribute(index);
					index += 1;
				} else {
					output = Protocol.COMPLETED;
					index = 0;
					state = END;
				}
			}
		} else if (state == SEND_FRIENDS) {
			String[] friendArray;
			String friendLine = "";
			if (input.equals(Protocol.WAITING) || input.equals(Protocol.ACKNOWLEDGED)) {
				if (index < friends.size()) {
					Account temp = Account.accountLoad(directory, Protocol.generateAccountNum(friends.get(index)));
					if (temp.getNumber() != null) {
						friendArray = temp.saveSequence();
						System.out.println("friendArray in ServerProtocol: " + friendArray[0] + "," + friendArray[1]);
						for (int i = 0; i < NUM_OF_ATTRIBUTES; i++) {
							if (i == 0) {
								friendLine = friendArray[i];
							} else {
								friendLine = friendLine + "," + friendArray[i];
							}
						}
						index++;
						output = Protocol.DECLARE_FRIEND + " : " + friendLine;
					} else {
						output = Protocol.ERROR;
						state = END;
					}
				} else {
					output = Protocol.COMPLETED;
					state = END;
				}
			}
		}  else if (state == END) {
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;	
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.BYE;	
			}
		}
		
		return output;
	}
	
	public int getState() {
		return state;
	}
	
}
