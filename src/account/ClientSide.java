package account;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientSide {
	
	private Lock mainLock = new ReentrantLock();
	private Lock threadLock = new ReentrantLock();
	private Account localAccount = new Account();
	private ClientThread client = null;
	
	public ClientSide(int portNumber) throws UnknownHostException {
				client = new ClientThread(InetAddress.getLocalHost().getHostName(), portNumber, threadLock, mainLock);
				client.start();
	}
	
	public boolean isConnectionError() {
		return client.isConnectionError();
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
