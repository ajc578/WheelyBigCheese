package account;

import javax.xml.bind.JAXBException;
import java.net.UnknownHostException;
import java.util.ArrayList;
/**
 * A Class to test the server and client communications. This class tests
 * each protocol message twice to ensure the success output is correct and 
 * also the error/failure output is handled properly.
 * 
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 */
public class CommunicationTest {

	private static ArrayList<Account> friendsList;

	public static void main(String[] args) throws JAXBException {
		/*
		 * ----- Protocols to choose from -----
		 * 
		 * Protocol.CREATE_ACCOUNT.concat(" : username,password,firstname,surname,weight,height,DOB,email")
		 * Protocol.LOGIN.concat(" : username,password")
		 * Protocol.SAVE
		 * Protocol.LOGOUT
		 * Protocol.RETRIEVE_FRIENDS
		 * Protocol.ADD_FRIEND.concat(" : friendUsername")
		 * Protocol.REMOVE_FRIEND.concat(" : friendUsername")
		 * Protocol.SEARCH_FRIEND.concat(" : searchString")
		 * 
		 */


		Account account = new Account();
		AccountHandler accountManager1 = new AccountHandler();
		accountManager1.setAccount(AccountHandler.accountLoad("src/res/clientAccounts/", AccountHandler.generateAccountNum("GainTrain")));
		accountManager1.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager1.saveAccount("src/res/clientAccounts/");
		accountManager1.setAccount(AccountHandler.accountLoad("src/res/serverAccounts/", AccountHandler.generateAccountNum("GainTrain")));
		accountManager1.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager1.saveAccount("src/res/serverAccounts/");
		AccountHandler accountManager2 = new AccountHandler();
		accountManager2.setAccount(AccountHandler.accountLoad("src/res/clientAccounts/", AccountHandler.generateAccountNum("TheBench")));
		accountManager2.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager2.saveAccount("src/res/clientAccounts/");
		accountManager2.setAccount(AccountHandler.accountLoad("src/res/serverAccounts/", AccountHandler.generateAccountNum("TheBench")));
		accountManager2.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager2.saveAccount("src/res/serverAccounts/");
		ClientSide client = null;
		try {
			client = new ClientSide(4444);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//client.login("GainTrain", "amazeBallz");
		//client.createAccount("TheBench", "wubbalubbadubdub", "Colonel", "Sanders", "80", "1.90", "1//04/1995", "dd691@york.ac.uk");
		client.login("TheBench", "wubbalubbadubdub");
		String clientOutput = "waiting";
		while (true) {
			if (!(clientOutput = client.receive()).equals("waiting")) {
				account = client.getAccount();
				break;
			}
		}
		System.out.println("Final client thread output in main" + clientOutput);
		if (account != null) {
			System.out.println("Returned Account is...");
			System.out.println(account);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*client.addFriend("GainTrain");
		clientOutput = "waiting";
		while (true) {
			if (!(clientOutput = client.receive()).equals("waiting")) {
				account = client.getAccount();
				break;
			}
		}
		System.out.println("Final client thread output in main" + clientOutput);
		if (account != null) {
			System.out.println("Returned Account is...");
			System.out.println(account);
		} 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		client.findFriends();
		clientOutput = "waiting";
		ArrayList<Account> friendsList = new ArrayList<Account>();
		while (true) {
			String output = client.receive();
			if (output.equals(Protocol.SUCCESS)) {
				friendsList = client.getFriendsList();
				break;
			} else if (output.startsWith(Protocol.ERROR)) {
				System.out.println("Error returned in clinet main: " + output);
				break;
			}
		}
		System.out.println("Final client thread output in main" + clientOutput);
		if (friendsList.size() != 0) {
			System.out.println("Returned FriendsList is...");
			for (Account i : friendsList) {
				System.out.println("New Friend...");
				System.out.println(i);
			}
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.logout(account);
		clientOutput = "waiting";
		while (true) {
			String output = client.receive();
			if (output.equals(Protocol.LOGOUT_SUCCESS)) {
				break;
			} else if (output.startsWith(Protocol.ERROR)) {
				System.out.println("Error returned in clinet main: " + output);
				break;
			}
		}
		System.out.println("Logout complete in client main.");
	}

	public static String getError(String args) {
		System.out.println(args);
		String error = args.substring(0, args.indexOf(","));
		String location = args.substring(0, args.indexOf(" : "));
		return error + " in " + location + "for state: " + args.substring(args.indexOf(" : ")+3);
	}

}