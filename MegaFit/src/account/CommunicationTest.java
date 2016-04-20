package account;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class CommunicationTest {
	
	private static ArrayList<Account> friendsList;

	public static void main(String[] args) {
		ClientThread client = null;
		ThreadInterCom comms = new ThreadInterCom();
		//ClientThread client1 = null;
		try {
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
			 * Account newAccount = new Account();
			 * newAccount.setLoginStatus(LoginStatus.LOGGED_IN);
			 * newAccount.setNumber("1275819324");
			 * newAccount.setName("CoconutMuma1");
			 * newAccount.setPassword("wubbalubbadubdub");
			 * newAccount.setSaveDate(Long.toString(System.currentTimeMillis()));
			 * newAccount.setLevel("1893");
			 * newAccount.setXP("2799");
			 * newAccount.setGainz("11");
			 * 
			 */
			
			Account newAccount1 = new Account();
			newAccount1.loadAccount("src/res/clientAccounts/", "1334239452");
			
			Account newAccount2 = new Account();
			newAccount2.loadAccount("src/res/clientAccounts/", "1275819324");
			
			Account newAccount3 = new Account();
			newAccount2.loadAccount("src/res/clientAccounts/", "1165286964");
	
			//Protocol.LOGIN.concat(" : CrazyEightz15,froyoballs")
			//Protocol.LOGOUT.concat(" : " + newAccount3.getNumber())
			client = new ClientThread(InetAddress.getLocalHost().getHostName(), 4444, comms, newAccount1, Protocol.SEARCH_FRIEND.concat(" : CoconutMuma1"));
			client.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Account account = new Account();
		
		try {
			boolean waiting = true;
			while (waiting) {
				String input = comms.receive();
				System.out.println("Client Main received this interrupt: " + input);
				if (input.equals(Protocol.END)) {
					//thread finished successfully
					account = client.getAccount();
					client.join();
					waiting = false;
					System.out.println("Client thread joined and finished successfully");
				} else if (input.equals(Protocol.RETRIEVE_FRIENDS)) {
					friendsList = new ArrayList<Account>();
					friendsList = client.getFriendsList();
					System.out.println("My friends list:\n" + friendsList.get(0).getName() + "," + friendsList.get(1).getName());
				} else if (input.equals(Protocol.DECLARE_FRIEND)) {
					Account searchResult = client.getFriendSearch();
					System.out.println("Friend search result: " + searchResult.getName() + "," + searchResult.getNumber());
				} else if (input.startsWith(Protocol.ERROR)) {
					//thread encountered an error
					System.out.println(getError(input));
					client.join();
					waiting = false;
					System.out.println("Client thread joined although errors occured");
				}
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (account != null) {
			System.out.println("Returned Account is...");
			for (String i : account.saveSequence()) {
				System.out.println(i);
			}
		} 
		
	}
	
	public static String getError(String args) {
		System.out.println(args);
		String error = args.substring(0, args.indexOf(","));
		String location = args.substring(0, args.indexOf(" : "));
		return error + " in " + location + "for state: " + args.substring(args.indexOf(" : ")+3);
	}
	
	

}
