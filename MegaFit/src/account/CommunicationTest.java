package account;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

public class CommunicationTest {
	
	private static ArrayList<Account> friendsList;

	public static void main(String[] args) throws JAXBException {
			/*
			 * ----- Protocols to choose from -----
			 * 
			 * Protocol.CREATE_ACCOUNT.concat(" : CoconutMuma1,wubbalubbadubdub")
			 * Protocol.CREATE_ACCOUNT.concat(" : WubbaLubba10,donkeyKong")
			 * Protocol.LOGIN.concat(" : WubbaLubba10,donkeyKong")
			 * Protocol.LOGIN.concat(" : CoconutMuma1,wubbalubbadubdub")
			 * Protocol.LOGIN.concat(" : CrazyEightz15,froyoballs")
			 * newAccount1, Protocol.SAVE
			 * newAccount2, Protocol.SAVE
			 * newAccount3, Protocol.SAVE
			 * newAccount1, Protocol.SAVE.concat(" : 5,4")
			 * newAccount2, Protocol.SAVE.concat(" : 5,4")
			 * newAccount3, Protocol.SAVE.concat(" : 5,4")
			 * Protocol.LOGOUT.concat(" : " + newAccount1.getNumber())
			 * Protocol.LOGOUT.concat(" : " + newAccount2.getNumber())
			 * Protocol.LOGOUT.concat(" : " + newAccount3.getNumber())
			 * newAccount1, Protocol.RETRIEVE_FRIENDS
			 * newAccount1, Protocol.ADD_FRIEND.concat(" : CoconutMuma1")
			 * newAccount1, Protocol.REMOVE_FRIEND.concat(" : CoconutMuma1")
			 * newAccount1, Protocol.SEARCH_FRIEND.concat(" : CoconutMuma1")
			 * 
			 * 
			 * Account newAccount = new Account();
			 * newAccount.setLoginStatus(LoginStatus.LOGGED_IN);
			 * newAccount.setNumber("1334239452");
			 * newAccount.setName("WubbaLubba10");
			 * newAccount.setPassword("donkeyKong");
			 * newAccount.setSaveDate(Long.toString(System.currentTimeMillis()));
			 * newAccount.setLevel("456");
			 * newAccount.setXP("1111");
			 * newAccount.setGainz("38");
			 * 
			 * 
			 * Account newAccount = new Account();
			 * newAccount.setLoginStatus(LoginStatus.LOGGED_IN);
			 * newAccount.setNumber("1275819324");
			 * newAccount.setName("CoconutMuma1");
			 * newAccount.setPassword("wubbalubbadubdub");
			 * newAccount.setSaveDate(Long.toString(System.currentTimeMillis()));
			 * newAccount.setLevel("1893");
			 * newAccount.setXP("2799");
			 * newAccount.setGainz("11");
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