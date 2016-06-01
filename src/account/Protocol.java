package account;

import java.util.Arrays;
import java.util.List;
/**
 * A Class containing the static String constants used in the server client 
 * communications. Also contains static methods for retrieving parameters stored in
 * the message string sent between the server and client.
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class Protocol {
	public static final boolean SEND = true, RECEIVE = false;
	public static final String  HANDSHAKE = "hello", 
								DECLARE_SAVE = "toSave",
								CREATE_ACCOUNT = "create", 
								LOGIN = "login", 
								WAITING = "waiting",
								LOGOUT = "logout", 
								LAST_SAVE_DATE = "lastSave", 
								PULL_REQUEST = "pull", 
								PUSH_REQUEST = "push", 
								SAVE = "save",
								ACKNOWLEDGED = "ack", 
								COMPLETED = "done", 
								ERROR = "error", 
								BYE = "bye", 
								END = "end", 
								SERVER = "serv", 
								CLIENT = "clnt", 
								ACCOUNT_UP_TO_DATE = "upToDate",
								RETRIEVE_FRIENDS = "getFriends", 
								ADD_FRIEND = "addFriend", 
								REMOVE_FRIEND = "delFriend", 
								SEARCH_FRIEND = "srchFriend",
								STANDBY = "standbye", 
								FINISHED = "fin", 
								LOCAL_GAME_REQ = "locGReq", 
								EXT_GAME_REQ = "extGReq", 
								GAME_ACCEPTED = "gameAcc",
								GAME_DECLINED = "gameDec", 
								ERROR_CONFIRMED = "errConf", 
								LOGOUT_SUCCESS = "logOSuc", 
								LOGIN_SUCCESS = "logISuc", 
								SUCCESS = "suc", 
								RECEIVED = "rec",
								NO_FRIENDS = "noFriends", 
								LOST_CONNECTION = "lost", 
								NO_MATCHES = "noMatch";

	/**
	 * Removes the protocol tag from the input String.
	 * @param line the input string to remove the protocol tag from.
	 * @return The contents of the input String without the protocol tag.
	 */
	public static String getMessage(String line) {
		if (line.contains(" : "))
			return line.substring(line.indexOf(" : ") + 3);
		else
			return line;
	}
	/**
	 * Removes the protocol tag from the input String and also splits
	 * up the String data separated by commas.
	 * 
	 * @param message the input String to retrieve the data from.
	 * @return A list containing the separated String data from the input message.
	 */
	public static List<String> splitMessage(String message) {
		message = getMessage(message);
		//Split message on commas
		List<String> content = Arrays.asList(message.split("\\s*,\\s*"));
		return content;
	}

}
