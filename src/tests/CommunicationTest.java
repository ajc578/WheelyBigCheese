package tests;

import java.io.File;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

import account.Account;
import account.AccountHandler;
import account.ClientSide;
import account.LoginStatus;
import account.Protocol;
/**
 * A Class to test the server and client communications. This class tests
 * each protocol message twice to ensure the success output is correct and 
 * also the error/failure output is handled properly.
 * <p>
 * NOTE --- The server must be run before this class!!! --- NOTE
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
			System.out.println("The server is running - Test 1 Passed");
		} else {
			System.out.println("The server is not running - Test 1 Failed");
		}
		//mandatory sleep between each communication to allow time for the previous
		//protocol to finish running.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {}
		
		//output string from the client side
		String clientOutput = "waiting";
		
		//Test an unsuccessful account creation - account name already exists -- TEST 2
		client.createAccount("IncredibleBulk", "Password1", "Testy", "Test", "75", "1.80", "20/20/2020", "test@mail.co.uk");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The new account was created successfully -- Test 2 Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The new account could not be created -- Test 2 Passed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {}
		
		//Test a successful account creation -- TEST 3
		Account testAccount1 = null;
		client.createAccount("TestAccount2", "Password2", "Testy2", "Test2", "80", "1.90", "10/10/1010", "test2@mail.co.uk");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				testAccount1 = client.getAccount();
				System.out.println("The new account was created successfully -- Test 3 Passed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The new account could not be created -- Test 3 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//Test a successful logout -- TEST 4
		client.logout(testAccount1);
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.LOGOUT_SUCCESS)) {
				System.out.println("The account was successfully logged out -- Test 4 Passed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not logout -- Test 4 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//re-establish connection with the server/client
		clientConnected = false;
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
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//Test an unsuccessful account login - TEST 5
		client.login("TestAccount1", "12983274");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The account was logged in successfully -- Test 5 Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not be logged in -- Test 5 Passed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//Test a successful account login - TEST 6
		Account testAccount2 = null;
		client.login("TestAccount1", "Password1");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				testAccount2 = client.getAccount();
				System.out.println("The account was logged in successfully -- Test 6 Passed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not be logged in -- Test 6 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		
		/*Test a successful account save -- TEST 7*/
		//first make edit to the account before saving
		AccountHandler testHandler1 = new AccountHandler();
		//get the old name to compare later
		String firstName1 = testAccount2.getFirstName();
		String firstName2 = "NameChange";
		//change the first name of the test account to Test
		testAccount2.setFirstName(firstName2);
		testHandler1.setAccount(testAccount2);
		//attempt the save
		Account testAccount3 = null;
		client.save(testHandler1.getAccount());
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				testAccount3 = client.getAccount();
				if (testAccount3.getFirstName().equals(firstName2)) {
					System.out.println("The account was saved successfully -- Test 7 Passed");
				} else {
					System.out.println("The account could not be saved to the server -- Test 7 Failed");
				}
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not be saved to the server -- Test 7 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//set the correct name of the account back to allow the test to be re run.
		testAccount2.setFirstName(firstName1);
		client.save(testAccount2);
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		/*Test the retrieve friends protocol for an empty friend list -- TEST 8*/
		client.findFriends();
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				System.out.println("The protocol returned a not empty friends list -- Test 8 Failed");
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The protocol returned an empty friends list -- Test 8 Passed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		/*Test the add friends protocol for successful result -- TEST 9*/
		client.addFriend("TheB3AST");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				testAccount3 = client.getAccount();
				if (testAccount3.getFriends().get(0).equals("TheB3AST")) {
					System.out.println("The friend was successfully added to the account -- Test 9 Passed");
				} else {
					System.out.println("The friend could not be added to the account -- Test 9 Failed");
				}
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The friend could not be added to the account -- Test 9 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		/*Test the retrieve friend protocol for a not empty friends list -- TEST 10*/
		client.findFriends();
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				if (client.getFriendsList().get(0).getUsername().equals("TheB3AST")) {
					System.out.println("The protocol returned the correct friend's account -- Test 10 Passed");
				} else {
					System.out.println("The protocol returned an empty friends list -- Test 10 Failed");
				}
				break;
			} else if (clientOutput.contains(Protocol.NO_FRIENDS)) {
				System.out.println("The protocol returned an empty friends list -- Test 10 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		/*Test the delete friend protocol works -- TEST 11*/
		client.removeFriend("TheB3AST");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				if (client.getAccount().getFriends().toString().equals("[]")) {
					System.out.println("The friend was removed from the account -- Test 11 Passed");
				} else {
					System.out.println("The Friend could not be removed from the account -- Test 11 Failed");
				}
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The Friend could not be removed from the account -- Test Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		/*Test an unsuccessful search result -- TEST 12*/
		client.searchFriend("zzzzzzzzzzzzzz");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				if (client.getFriendsList().toString().equals("[]")) {
					System.out.println("There were no accounts found to the search result, but protocol still returned successful -- Test 12 Failed");
				} else {
					System.out.println("There were similar accounts found to the search input -- Test 12 Failed");
				}
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("There were no similar accounts found to the input search string -- Test 12 Passed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		/*Test a successful search result -- TEST 13*/
		client.searchFriend("TheB3AST");
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.SUCCESS)) {
				if (client.getFriendsList().toString().equals("[]")) {
					System.out.println("There were no accounts found to the search result, but protocol still returned successful -- Test 13 Failed");
				} else {
					if (client.getFriendsList().get(0).getUsername().equals("TheB3AST")) {
						System.out.println("The correct account was retrieved from the server directory -- Test 13 Passed");
					} else {
						System.out.println("An incorrect account was retrieved from the server directory -- Test 13 Failed");
					}
				}
				break;
			} else if (clientOutput.contains(Protocol.NO_MATCHES)) {
				System.out.println("There were no similar accounts found to the input search string -- Test 13 Failed");
				break;
			}
		}
		
		try {
			Thread.sleep(1000);
			clientOutput = "waiting";
		} catch (InterruptedException e1) {}
		
		//now set the account back to its original status so the test can be run again.
		//final logout
		client.logout(testAccount2);
		while (true) {
			clientOutput = client.receive();
			if (clientOutput.equals(Protocol.LOGOUT_SUCCESS)) {
				break;
			} else if (clientOutput.contains(Protocol.ERROR)) {
				System.out.println("The account could not logout. Therefore the test can't be re run...");
				break;
			}
		}
		//delete the newly created account to allow the test to be re run.
		File tempClient = new File("src/res/clientAccounts/" + testAccount1.getNumber() + ".xml");
		File tempServer = new File("src/res/serverAccounts/" + testAccount1.getNumber() + ".xml");
		if (tempClient.exists() && tempClient.isFile() && tempServer.exists() && tempServer.isFile()) {
			tempClient.delete();
			tempServer.delete();
		}
		
		System.out.println("The test have fully run");
	}

}