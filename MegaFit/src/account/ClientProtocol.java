package account;

import java.util.ArrayList;
import java.util.List;

public class ClientProtocol extends Protocol {
	
	private static final int SEND_PROTOCOL = 0, CREATE_ACCOUNT = 1, LOGIN = 2,LOGOUT = 3, CHECK_LAST_SAVE_DATE = 4,
							 PUSH = 5, PULL = 6, SAVE = 7, END = 8;
	private static final String directory = "src/res/clientAccounts/";
	
	private int state = SEND_PROTOCOL;
	private int index = 0;
	private ArrayList<Integer> saveAttributes;
	private String protocol = null;
	private boolean loginNew = false;
	
	public String processInput(String input) {
		String output = null;
		if (state == SEND_PROTOCOL) {
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
				}
			} else if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = protocol;
				state = SAVE;
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
			} else if (input.equals(Protocol.EXISITING_ACCOUNT)) {
				//error has occured
				System.out.println("Create Account Error! - Account already exists.");
				output = Protocol.BYE;
				state = END;
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
			} else if (input.equals(Protocol.INCORRECT_LOGIN)) {
				output = Protocol.BYE;
				state = END;
			} else if (input.equals(Protocol.ERROR)) {
				System.out.println("Login Error - Server couldn't login. Please try again.");
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == LOGOUT) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				if (logout(directory, protocol)) {
					output = Protocol.COMPLETED;
					state = END;
				}
			} else if (input.equals(Protocol.LOGOUT_FAILED)) {
				//this should never happen really
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.equals(Protocol.PUSH_REQUEST)) {
				output = getAccount().getAttribute(saveAttributes.get(index));
				state = PUSH;
			} else if (input.equals(Protocol.PULL_REQUEST)) {
				output = Protocol.ACKNOWLEDGED;
				state = PULL;
			} else if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;
				state = END;
			} else if (input.equals(Protocol.ERROR)) {
				System.out.println("Check Save Date Error - Server couldn't compare last save date with client.");
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
			} else if (input.equals(Protocol.ERROR)) {
				System.out.println("Push To Server Error - Server failed to receive client account.");
				index = 0;
				state = END;
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
			} else if (input.equals(Protocol.ERROR)) {
				System.out.println("Server Receive Protocol Error - The server failed to receive the pType : save.");
				output = Protocol.ERROR;
				state = END;
			}
		} else if (state == END) {
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.END;
			} else if (input.equals(Protocol.ERROR)) {
				System.out.println("Protocol Completion Error - Server failed to send protocol complete message.");
				output = Protocol.BYE;
			}
		} 
		
		return output;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public void setSaveAttributes(ArrayList<Integer> saveAttributes) {
		this.saveAttributes = new ArrayList<Integer>();
		this.saveAttributes = saveAttributes;
	}
	
	
	
}
