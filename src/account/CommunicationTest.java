package account;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
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

	/**
	 * Runs through all the different protocol tests. The different protocols are:
	 * <p>
	 * <ul>
	 * <li>Protocol.CREATE_ACCOUNT.concat(" : username,password,firstname,surname,weight,height,DOB,email")</li>
	 * <li>Protocol.LOGIN.concat(" : username,password")</li>
	 * <li>Protocol.SAVE</li>
	 * <li>Protocol.LOGOUT</li>
	 * <li>Protocol.RETRIEVE_FRIENDS</li>
	 * <li>Protocol.ADD_FRIEND.concat(" : friendUsername")</li>
	 * <li>Protocol.REMOVE_FRIEND.concat(" : friendUsername")</li>
	 * <li>Protocol.SEARCH_FRIEND.concat(" : searchString")</li>
	 * </ul>
	 * 
	 * @param args the arguments to run the main.
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException {
		//sets the logout status of the accounts to logged out in order to run tests smoothly.
		Account account = new Account();
		AccountHandler accountManager1 = new AccountHandler();
		accountManager1.setAccount(AccountHandler.accountLoad("src/res/clientAccounts/", AccountHandler.generateAccountNum("IncredibleBulk")));
		accountManager1.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager1.saveAccount("src/res/clientAccounts/");
		accountManager1.setAccount(AccountHandler.accountLoad("src/res/serverAccounts/", AccountHandler.generateAccountNum("IncredibleBulk")));
		accountManager1.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager1.saveAccount("src/res/serverAccounts/");
		AccountHandler accountManager2 = new AccountHandler();
		accountManager2.setAccount(AccountHandler.accountLoad("src/res/clientAccounts/", AccountHandler.generateAccountNum("TheB3AST")));
		accountManager2.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager2.saveAccount("src/res/clientAccounts/");
		accountManager2.setAccount(AccountHandler.accountLoad("src/res/serverAccounts/", AccountHandler.generateAccountNum("TheB3AST")));
		accountManager2.getAccount().setLoginStatus(LoginStatus.LOGGED_OUT);
		accountManager2.saveAccount("src/res/serverAccounts/");
		
		//delete the previously created account from the server/client directories
		
		
		int portNumber = 4444;
		boolean clientConnected = false;
		ClientSide client = null;
		//starts the client side. -- TEST 1
		for (int i = 0; i < 5; i++) {
			try {
				client = new ClientSide(portNumber);
				if (!client.isConnectionError())
					clientConnected = true;
				break;
			} catch (UnknownHostException e1) {
				System.out.println("Could not connected to port 4444");
			}

		}
		if (clientConnected) {
			System.out.println("The server is running");
		} else {
			System.out.println("The server is not running");
		}
		//mandatory sleep between each communication to allow time for the previous
		//protocol to finish running.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {}
		
		//output string from the client side
		String clientOutput = "waiting";
		
		//Test an unsuccessful account creation - account name already exists -- TEST 2
		client.createAccount("IncredibleBulk", "Password1", "Testy", "Test", "75", "1.80", "20/20/2020", "test@mail.co.uk");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The new account was created successfully -- Test Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The new account could not be created -- Test Passed");
				break;
			}
		}
		
		//Test a successful account creation -- TEST 3
		Account testAccount1 = null;
		client.createAccount("TestAccount1", "Password1", "Testy", "Test", "75", "1.80", "20/20/2020", "test@mail.co.uk");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				testAccount1 = client.getAccount();
				System.out.println("The new account was created successfully -- Test Passed" + testAccount1.toString());
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The new account could not be created -- Test Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(2000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//Test an unsuccessful logout -- TEST 4
		client.createAccount("IncredibleBulk", "Password1", "Testy", "Test", "75", "1.80", "20/20/2020", "test@mail.co.uk");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The new account was created successfully -- Test Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The new account could not be created -- Test Passed");
				break;
			}
		}
		
		try {
			Thread.sleep(2000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//Test a successful account login - TEST 4
		client.login("IncredibleBulk", "Yeah1234");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The account was logged in successfully -- Test Passed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not be logged in -- Test Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(2000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//Test an unsuccessful account login - TEST 5
		client.login("IncredibleBulk", "12983274");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The account was logged in successfully -- Test Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not be logged in -- Test Passed");
			}
		}
		
		try {
			Thread.sleep(2000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}

		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The account was logged in successfully -- Test Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not be logged in -- Test Passed");
			}
		}
		
		
		
		
		
		client.login("TheBench", "wubbalubbadubdub");
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