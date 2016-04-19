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
			newAccount1.setLoginStatus(LoginStatus.LOGGED_IN);
			newAccount1.setNumber("1334239452");
			newAccount1.setName("WubbaLubba10");
			newAccount1.setPassword("donkeyKong");
			newAccount1.setSaveDate(Long.toString(System.currentTimeMillis()));
			newAccount1.setLevel("456");
			newAccount1.setXP("1111");
			newAccount1.setGainz("38");
			newAccount1.setFriendsList("#CoconutMuma1,CrazyEightz15#");
			
			Account newAccount2 = new Account();
			newAccount2.setLoginStatus(LoginStatus.LOGGED_IN);
			newAccount2.setNumber("1275819324");
			newAccount2.setName("CoconutMuma1");
			newAccount2.setPassword("wubbalubbadubdub");
			newAccount2.setSaveDate(Long.toString(System.currentTimeMillis()));
			newAccount2.setLevel("1893");
			newAccount2.setXP("2799");
			newAccount2.setGainz("11");
			newAccount2.setFriendsList("#WubbaLubba10,CrazyEightz15#");
			
			Account newAccount3 = new Account();
			newAccount3.setLoginStatus(LoginStatus.LOGGED_IN);
			newAccount3.setNumber("1165286964");
			newAccount3.setName("CrazyEightz15");
			newAccount3.setPassword("froyoballs");
			newAccount3.setSaveDate(Long.toString(System.currentTimeMillis()));
			newAccount3.setLevel("333");
			newAccount3.setXP("3333");
			newAccount3.setGainz("3");
			newAccount3.setFriendsList("#CoconutMuma1,WubbaLubba10#");
			//Protocol.LOGIN.concat(" : CrazyEightz15,froyoballs")
			//Protocol.LOGOUT.concat(" : " + newAccount3.getNumber())
			client = new ClientThread(InetAddress.getLocalHost().getHostName(), 4444, comms, newAccount1, Protocol.RETRIEVE_FRIENDS);
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
		
		/*try {
			String input1 = client1.waiting();
			if (input1.equals(Protocol.END)) {
				//thread finished successfully
				Account account1 = client.getAccount();
				client1.finishAccepted();
				client1.join();
				System.out.println("Client thread joined and finished successfully");
			} else {
				//thread encountered an error
				System.out.println(getProtocol(input1));
				client1.join();
				System.out.println("Client thread joined although errors occured");
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		/*boolean clientFinished = false, clientError = false;
		while (!clientFinished && !clientError) {
			clientFinished = client.isFinished();
			clientError = client.getErrorFlag();
		}
		
		if (!clientError) {
		
			Account account = client.getAccount();
			try {
				System.out.println("Trying to join thread");
				client.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (String i : account.saveSequence()) {
				System.out.println(i);
			}
			System.out.println("finished sucessfully");
			
		} else {
			String errorMessage = "";
			try {
				errorMessage = getProtocol(client.waiting());
				System.out.println(errorMessage);
				while (!clientFinished) {
					clientFinished = client.isFinished();
				}
				System.out.println("Trying to join thread");
				client.join();
			} catch (InterruptedException e) {
				System.out.println("Error - error retrival error in main thread at method client.getAlert().");
				e.printStackTrace();
			}
			System.out.println("Error in thread finished sucessfully");
		}
*/		
	}
	
	public static String getError(String args) {
		System.out.println(args);
		String error = args.substring(0, args.indexOf(","));
		String location = args.substring(0, args.indexOf(" : "));
		return error + " in " + location + "for state: " + args.substring(args.indexOf(" : ")+3);
	}
	
	

}
