package account;

import java.util.Arrays;
import java.util.List;

public class Protocol {
	public static final boolean SEND = true, RECEIVE = false;
	public static final String DECLARE_PROTOCOL = "pType", HANDSHAKE = "hello", DECLARE_ACCOUNT = "acc", DECLARE_SAVE = "toSave",
			CREATE_ACCOUNT = "create", EXISITING_ACCOUNT = "existAcc", LOGIN = "login", INCORRECT_LOGIN = "falseLogin", WAITING = "waiting",
			LOGOUT = "logout", LOGOUT_FAILED = "failLogout", LAST_SAVE_DATE = "lastSave", PULL_REQUEST = "pull", PUSH_REQUEST = "push", LOGIN_NEW = "newLogin", SAVE = "save",
			ACKNOWLEDGED = "ack", COMPLETED = "done", ERROR = "error", BYE = "bye", END = "end", SERVER = "serv", CLIENT = "clnt", ACCOUNT_UP_TO_DATE = "upToDate",
			RETRIEVE_FRIENDS = "getFriends", ADD_FRIEND = "addFriend", REMOVE_FRIEND = "delFriend", DECLARE_FRIEND = "decFriend", SEARCH_FRIEND = "srchFriend",
			TIMEOUT = "timeout", STANDBYE = "standbye", FINISHED = "fin", LOCAL_GAME_REQ = "locGReq", EXT_GAME_REQ = "extGReq", GAME_ACCEPTED = "gameAcc",
			GAME_DECLINED = "gameDec", ERROR_CONFIRMED = "errConf", LOGOUT_SUCCESS = "logOSuc", LOGIN_SUCCESS = "logISuc", SUCCESS = "suc", RECEIVED = "rec", 
			NO_FRIENDS = "noFriends", LOST_CONNECTION = "lost";
			
	
	public static String getMessage(String line) {
		if (line.contains(" : "))
			return line.substring(line.indexOf(" : ") + 3);
		else
			return line;
	}

	public static List<String> splitMessage(String message) {
		message = getMessage(message);
		List<String> content = Arrays.asList(message.split("\\s*,\\s*"));
		return content;
	}

	public static List<String> getFriends(String list) {
		String temp = list.replaceAll("#", "");
		return splitMessage(temp);
	}

}
