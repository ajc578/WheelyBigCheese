package account;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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

	@SuppressWarnings("unchecked")
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
			if (input.equals("null")) {
				System.out.println("The input is null to the client protocol.");
			}
			protocol = "";
			state = WAITING;
			output = Protocol.STANDBYE;
		} else if (state == WAITING) {
			if (input.equals(Protocol.HANDSHAKE)) {
				if (protocol.startsWith(Protocol.CREATE_ACCOUNT)) {
					output = protocol;
					state = CREATE_ACCOUNT;
				} else if (protocol.startsWith(Protocol.LOGIN)) {
					String loginStatus = myAccount.login(directory, protocol);
					if (loginStatus.equals(LoginStatus.LOGGED_IN)) {
						myAccount.getAccount().setLoginStatus(LoginStatus.LOGGED_IN_LOCALLY);
						output = protocol;
						state = LOGIN;
					} else if (loginStatus.equals(LoginStatus.ACCOUNT_NOT_FOUND)){
						output = protocol;
						loginNew = true;
						state = LOGIN;
					} else if (loginStatus.equals(LoginStatus.IN_USE)) {
						System.out.println("Login Error - Client Account file exists although the users input doesn't match the name or password.");
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
				if (myAccount.logout(directory)) {
					output = Protocol.LOGOUT_SUCCESS;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			} else if (input.startsWith(Protocol.EXT_GAME_REQ)) {
				String opponent = Protocol.getMessage(protocol);
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
				if (!loginNew) {
					myAccount.getAccount().setLoginStatus(LoginStatus.LOGGED_IN);
					myAccount.saveAccount(directory);
				} else {
					myAccount.setAccount(new Account());
				}
				// output account last save date and time
				output = Protocol.LAST_SAVE_DATE + " : " + (loginNew == true ? "0" : myAccount.getAccount().getLastSaved());
				state = CHECK_LAST_SAVE_DATE;
			}
		} else if (state == LOGOUT) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = myAccount.getAccount();
			} else if (input.equals(Protocol.LOGOUT_SUCCESS)) {
				if (myAccount.logout(directory)) {
					output = Protocol.BYE;
					state = END;
				} else {
					output = Protocol.ERROR;
					state = END;
				}
			}
		} else if (state == CHECK_LAST_SAVE_DATE) {
			if (input.equals(Protocol.PUSH_REQUEST)) {
				output = Protocol.LAST_SAVE_DATE + " : " + Long.toString(myAccount.getAccount().getLastSaved());
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
				output = myAccount.getAccount();
				state = END;
			}
		} else if (state == PULL) {
			if (input.equals("") && (inputObject instanceof Account)) {
				Account temp = (Account) inputObject;
				myAccount.setAccount(temp);
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
			} else if (input.equals("") && (inputObject instanceof ArrayList<?>) && (((ArrayList<?>) inputObject).get(0) instanceof Account)) {
				friendsList = (ArrayList<Account>) inputObject;
				System.out.println("Friends list in client protocol. Null test. Name: " + friendsList.get(0).getUsername());
				output = Protocol.RECEIVED;
				state = END;
			}
		} else if (state == ADD_FRIEND) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				myAccount.addFriend(getMessage(protocol));
				myAccount.saveAccount(directory);
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == DEL_FRIEND) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals(Protocol.COMPLETED)) {
				myAccount.delFriend(getMessage(protocol));
				myAccount.saveAccount(directory);
				output = Protocol.BYE;
				state = END;
			}
		} else if (state == SEARCH_FRIEND) {
			if (input.equals(Protocol.ACKNOWLEDGED)) {
				output = Protocol.WAITING;
			} else if (input.equals("") && (inputObject instanceof Account)) {
				friend = new Account();
				friend = (Account) inputObject;
				output = Protocol.RECEIVED;
				state = END;
			}
		} else if (state == END) {
			if (input.equals(Protocol.COMPLETED)) {
				output = Protocol.BYE;
			} else if (input.equals(Protocol.BYE)) {
				output = Protocol.STANDBYE;
			}
		}
		return output;
	}

	public void setAccount(Account account) {
		this.myAccount.setAccount(account);
	}

	public Account getAccount() {
		return myAccount.getAccount();
	}

	public Account getFriend() {
		return friend;
	}

	public ArrayList<Account> getFriendsList() {
		return friendsList;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
		System.out.println("the protocol has been set to: " + this.protocol);
	}

	private boolean gameRequestDialog(String opponent) {
		boolean gameAccepted = false;
		boolean timeout = false;

		Alert gameAlert = new Alert(AlertType.CONFIRMATION);
		gameAlert.setTitle("Game Request");
		gameAlert.setHeaderText(opponent + " would like to play a game with you");
		gameAlert.setContentText("Press 'Accept' to start the game, or 'Decline' to cancel.");

		ButtonType accept = new ButtonType("Accept");
		ButtonType decline = new ButtonType("Decline");

		gameAlert.getButtonTypes().setAll(accept, decline);

		Timer maxWait = new Timer();
		maxWait.schedule(new TimerTask() {

			@Override
			public void run() {
				gameAlert.close();
			}
		}, 15000);

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
