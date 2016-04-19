package account;

import java.util.ArrayList;
import java.util.List;

public class ClientProtocol extends Protocol {
	
	private static final int SEND_PROTOCOL = 0, CREATE_ACCOUNT = 1, LOGIN = 2,LOGOUT = 3, CHECK_LAST_SAVE_DATE = 4,
							 PUSH = 5, PULL = 6, SAVE = 7, GET_FRIENDS = 8, END = 9;
	private static final String directory = "src/res/clientAccounts/";
	
	private int state = SEND_PROTOCOL;
	private int index = 0;
	private ArrayList<Integer> saveAttributes;
	private String protocol = null;
	private boolean loginNew = false;
	private ArrayList<Account> friendsList = null;
	private Account friend = null;
	
	public String processInput(String input) {
		String output = null;
		if (input.equals(Protocol.ERROR)) {
			output = Protocol.BYE;
			index = 0;
			state = END;
		} else if (state == SEND_PROTOCOL) {
			if (input.equals(Protocol.WAITING)) {
				output = Protocol.HANDSHAKE;
			} else if (input.equals(Protocol.HANDSHAKE)) {
				if (protocol.startsWith(Protocol.CREATE_ACCOUNT)) {
					output = protocol;
					state = CREATE_ACCOUNT;
				} else if (protocol.startsWith(Protocol.LOGIN)) {
					String loginStatus = login(directory, protocol);
					if (loginStatus.equals(LoginStatus.LOGGED_IN)) {
						getAccount().setLoginStatus(LoginStatus.LOGGED_IN_LOCALLY);
						output = protocol;
						state = LOGIN;
					} else if (loginStatus.equals(LoginStatus.ACCOUNT_NOT_FOUND)){
						output = protocol;
						loginNew = true;
						state = LOGIN;
					} else if (loginStatus.equals(LoginStatus.LOGGED_OUT)) {
						System.out.println("Login Error - Client Account file exists although the users input doesn't match the name or password.");
						output = Protocol.ERROR;
						state = END;
					}
				} else if (protocol.startsWith(Protocol.LOGOUT)) {
					output = protocol;
					state = LOGOUT;
				} else if (protocol.startsWith(Protocol.SAVE)) {
					output = Protocol.DECLARE_ACCOUNT + " : " + getAccount().getNumber();
				} else if (protocol.startsWith(Protocol.RETRIEVE_FRIENDS)) {
					friendsList = new ArrayList<Account>();
					output = Protocol.DECLARE_ACCOUNT + " : " + getAccount().getNumber();
				}
			} else if (input.equals(Protocol.ACKNOWLEDGED)) {
				if (protocol.startsWith(Protocol.SAVE)) {
					output = protocol;
					state = SAVE;
				} else if (protocol.startsWith(Protocol.RETRIEVE_FRIENDS)) {
					output = protocol;
					state = GET_FRIENDS;
				}
			} else if (input.equals(Protocol.LOGOUT)) {
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == CREATE_ACCOUNT) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				if (createNewAccount(directory, protocol)) {
					getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					output = Protocol.COMPLETED;
					state = END;
				}
			} 
		} else if (state == LOGIN) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				if (!loginNew) {
					getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					getAccount().saveAccount(directory);
				} else {
					setAccount(new Account());
				}
				// output account last save date and time
				output = Protocol.LAST_SAVE_DATE + " : " + (loginNew == true ? "0" : getAccount().getSaveDate());
				state = CHECK_LAST_SAVE_DATE;
			}  
		} else if (state == LOGOUT) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				if (logout(directory, protocol)) {
					output = Protocol.COMPLETED;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			} 
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.equals(Protocol.PUSH_REQUEST)) {
				output = getAccount().getAttribute(saveAttributes.get(index));
				state = PUSH;
			} else if (input.equals(Protocol.PULL_REQUEST)) {
				output = Protocol.ACKNOWLEDGED;
				state = PULL;
			} else if (input.equals(Protocol.ACCOUNT_UP_TO_DATE)) {
				output = Protocol.BYE;
				state = END;
			} 
		} else if (state == PUSH) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				index += 1;
				if (index < saveAttributes.size()) {
					output = Protocol.DECLARE_SAVE + " : " + Integer.toString(saveAttributes.get(index)) + "," + getAccount().getAttribute(saveAttributes.get(index));
				} else {
					output = Protocol.COMPLETED;
					index = 0;
					state = END;
				}
			} 
		} else if (state == PULL) {
			if (input.startsWith(Protocol.DECLARE_SAVE)) {
				List<String> message = splitMessage(input);
				getAccount().editAccount(Integer.parseInt(message.get(0)), message.get(1));
				output = Protocol.ACKNOWLEDGED;
			} else if (input.equals(Protocol.COMPLETED)) {
				boolean saveSuccess = false;
				if (!loginNew) 
					saveSuccess = getAccount().saveAccount(directory);
				else
					saveSuccess = getAccount().saveNewAccount(directory);
				
				if (saveSuccess) {
					index = 0;
					output = Protocol.COMPLETED;
					state = END;
				} else {
					System.out.println("Client Save Error - Client failed to save Account to file.");
					output = Protocol.ERROR;
					state = END;
				}
			} 
		} else if (state == SAVE) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				if (getAccount().saveAccount(directory)) {
					output = Protocol.DECLARE_SAVE + " : " + Integer.toString(saveAttributes.get(index)) + "," + getAccount().getAttribute(saveAttributes.get(index));
					state = PUSH;
				} else {
					System.out.println("Client Save Error - Client failed to save Account to file.");
					output = Protocol.ERROR;
					state = END;
				}
			} 
		} else if (state == GET_FRIENDS) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.startsWith(Protocol.DECLARE_FRIEND)) {
				List<String> friendTemp = Protocol.splitMessage(input);
				friend = new Account();
				for (int i = 0; i < friendTemp.size(); i++) {
					friend.editAccount(i, friendTemp.get(i));
				}
				friendsList.add(friend);
				output = Protocol.ACKNOWLEDGED;
			} else if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;
				state = END;
			} 
		} else if (state == END) {
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.END;
			} 
		} 
		
		return output;
	}
	
	public ArrayList<Account> getFriendsList() {
		return friendsList;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public void setSaveAttributes(ArrayList<Integer> saveAttributes) {
		this.saveAttributes = new ArrayList<Integer>();
		this.saveAttributes = saveAttributes;
	}
	
	
	
}
