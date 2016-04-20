package account;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Protocol {
	public static final boolean SEND = true, RECEIVE = false;
	public static final String DECLARE_PROTOCOL = "pType", HANDSHAKE = "hello", DECLARE_ACCOUNT = "acc", DECLARE_SAVE = "toSave",
							   CREATE_ACCOUNT = "create", EXISITING_ACCOUNT = "existAcc", LOGIN = "login", INCORRECT_LOGIN = "falseLogin", WAITING = "waiting", 
							   LOGOUT = "logout", LOGOUT_FAILED = "failLogout", LAST_SAVE_DATE = "lastSave", PULL_REQUEST = "pull", PUSH_REQUEST = "push", LOGIN_NEW = "newLogin", SAVE = "save",
							   ACKNOWLEDGED = "ack", COMPLETED = "done", ERROR = "error", BYE = "bye", END = "end", SERVER = "serv", CLIENT = "clnt", ACCOUNT_UP_TO_DATE = "upToDate",
							   RETRIEVE_FRIENDS = "getFriends", ADD_FRIEND = "addFriend", REMOVE_FRIEND = "delFriend", DECLARE_FRIEND = "decFriend", SEARCH_FRIEND = "srchFriend",
							   TIMEOUT = "timeout";
	private static final String clientDirectory = "src/res/clientAccounts/";
	
	private Account account;
	private boolean processType;
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setProcessType(boolean type) {
		this.processType = type;
	}
	
	public boolean getProcessType() {
		return processType;
	}
	
	public static String readLine(File file, int index) {
		String output = null;
		if (file.exists() && file.isFile()) {
			try (
				BufferedReader br = new BufferedReader(new FileReader(file));
			) {
				String line = null;
				int i = 0;
				while ((line = br.readLine()) != null) {
					if (i == index)
						break;
					i++;
				} 
				output = line;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public static String generateAccountNum(String name) {
		String accountNum;
		Long seed = new Long(name.hashCode());
		FixedGenerator generator = new FixedGenerator(seed);
		accountNum = Integer.toString(generator.nextPositiveInt());
		
		return accountNum;
	}
	
	public boolean createNewAccount(String directory, String protocol) {
		boolean success = false;
		String line = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		List<String> nameAndPassword = Arrays.asList(line.split("\\s*,\\s*"));
		String accountNum = generateAccountNum(nameAndPassword.get(0));
		
		if (checkUnique(directory,accountNum)) {
		
			Account newAccount = new Account();
			newAccount.setLoginStatus(LoginStatus.LOGGED_IN);
			newAccount.setNumber(accountNum);
			newAccount.setName(nameAndPassword.get(0));
			newAccount.setPassword(nameAndPassword.get(1));
			newAccount.setSaveDate(Long.toString(System.currentTimeMillis()));
			newAccount.setLevel("0");
			newAccount.setXP("0");
			newAccount.setGainz("0");
			
			setAccount(newAccount);
			
			if (newAccount.saveNewAccount(directory))
				success = true;
		} 
		
		return success;
	}
	
	public String login(String directory, String protocol) {
		String loginSuccess = LoginStatus.LOGGED_OUT;
		String line = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		List<String> nameAndPassword = Arrays.asList(line.split("\\s*,\\s*"));
		String accountNum = generateAccountNum(nameAndPassword.get(0));
		
		System.out.println("Login Name: " + nameAndPassword.get(0));
		System.out.println("Login Password: " + nameAndPassword.get(1));
		System.out.println("Login Number: " + accountNum);
		
		File test = new File(directory + accountNum + ".txt");
		if (test.exists() && test.isFile()) {
			String savedPassword = readLine(test,Account.PASSWORD_INDEX); // 2 is the index for the password
			if (nameAndPassword.get(1).equals(savedPassword)) {
				loginSuccess = LoginStatus.LOGGED_IN;
			}
		} else if (directory.equals(clientDirectory)) {
			loginSuccess = LoginStatus.ACCOUNT_NOT_FOUND;
		}
		
		if (loginSuccess.equals(LoginStatus.LOGGED_IN)) {
			//account = new Account(nameAndPassword.get(0), nameAndPassword.get(1), accountNum);
			account = new Account();
			account.loadAccount(directory, accountNum);
		} 
		
		return loginSuccess;
	}
	
	public static boolean checkSoloLogin(String directory, String protocol) {
		boolean soloLogin = true;
		
		String accountNum = generateAccountNum(splitMessage(protocol).get(0));
		File temp = new File(directory + accountNum + ".txt");
		if (readLine(temp, 0).equals(LoginStatus.LOGGED_IN)) {
			soloLogin = false;
		}
		return soloLogin;
	}
	
	public boolean logout(String directory, String protocol) {
		boolean logoutSuccess = false;
		String accountNum = protocol.substring(protocol.lastIndexOf(" : ") + 3);
		
		File test = new File(directory + accountNum + ".txt");
		if (test.exists() && test.isFile()) {
			account = new Account();
			account.loadAccount(directory, accountNum);
			account.setLoginStatus(LoginStatus.LOGGED_OUT);
			if (account.saveAccount(directory)) {
				logoutSuccess = true;
			}
		}
		
		return logoutSuccess;
	}
	
	public boolean checkUnique(String directory, String accountNum) {
		boolean unique = false;
		File uniqueTest = new File(directory + accountNum + ".txt");
		if (!uniqueTest.exists() || uniqueTest.isDirectory())
			unique = true;
		
		return unique;
	}
	
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
