package account;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import account.Account;
import account.AccountHandler;

public class CommunicationTest {
	
	private static ArrayList<Account> friendsList;

	public static void main(String[] args) {
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
		AccountHandler accountManager = new AccountHandler();
		accountManager.setAccount(AccountHandler.accountLoad("src/res/clientAccounts/", AccountHandler.generateAccountNum("GainTrain")));
		accountManager.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager.saveAccount("src/res/clientAccounts/");
		accountManager.setAccount(AccountHandler.accountLoad("src/res/serverAccounts/", AccountHandler.generateAccountNum("GainTrain")));
		accountManager.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager.saveAccount("src/res/serverAccounts/");
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
		/*account.setGainz(20);
		account.setLevel(4);
		client.addFriend("GainTrain");
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

class ClientSide {
	
	private Lock mainLock = new ReentrantLock();
	private Lock threadLock = new ReentrantLock();
	private Account localAccount = new Account();
	private ClientThread client = null;
	
	public ClientSide(int portNumber) throws UnknownHostException {
				client = new ClientThread(InetAddress.getLocalHost().getHostName(), portNumber, threadLock, mainLock);
				client.start();
	}
	
	public void login(String username, String password) {
		client.setMainInput(Protocol.LOGIN.concat(" : " + username + "," + password));
		client.setFlag();
	}
	
	public void createAccount(String username, String password, String firstname, String lastname, 
									String weight, String height, String DOB, String email) {
		client.setMainInput(Protocol.CREATE_ACCOUNT.concat(" : " + username + "," + password + ","
								+ firstname + "," + lastname + "," + weight + "," + height + ","
								+ DOB + "," + email));
		client.setFlag();
	}
	
	public void save(Account account) {
		client.setMainInput(Protocol.SAVE);
		client.setAccount(account);
		client.setFlag();
	}
	
	public void logout(Account account) {
		client.setMainInput(Protocol.LOGOUT);
		client.setAccount(account);
		client.setFlag();
	}
	
	public void findFriends() {
		client.setMainInput(Protocol.RETRIEVE_FRIENDS);
		client.setFlag();
	}
	
	public ArrayList<Account> getFriendsList() {
		return client.getFriendsList();
	}
	
	public void searchFriend(String accountName) {
		client.setMainInput(Protocol.SEARCH_FRIEND.concat(" : " + accountName));
		client.setFlag();
	}
	
	public Account getFriendSearch() {
		return client.getFriendSearch();
	}
	
	public void addFriend(String accountName) {
		client.setMainInput(Protocol.ADD_FRIEND.concat(" : " + accountName));
		client.setFlag();
	}
	
	public void removeFriend(String accountName) {
		client.setMainInput(Protocol.REMOVE_FRIEND.concat(" : " + accountName));
		client.setFlag();
	}
	
	public Account getAccount() {
		return client.getAccount();
	}
	
	public String receive() {
		String input = "waiting";
		if (!threadLock.tryLock()) {
			input = client.getThreadOutput();
			mainLock.lock();
			while (true) {
				if (threadLock.tryLock()) {
					threadLock.unlock();
					mainLock.unlock();
					break;
				}
			}
		} else {
			threadLock.unlock();
		}
		return input;
	}
	
	public Account getLocalAccount() {
		return localAccount;
	}

	public void setLocalAccount(Account localAccount) {
		this.localAccount = localAccount;
	}
	
	public Lock getMainLock() {
		return mainLock;
	}

	public void setMainLock(Lock mainLock) {
		this.mainLock = mainLock;
	}

	public Lock getThreadLock() {
		return threadLock;
	}

	public void setThreadLock(Lock threadLock) {
		this.threadLock = threadLock;
	}
	
}
