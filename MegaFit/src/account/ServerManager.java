package account;

/*
 * ---------- NOTE ----------
 * It's worth noting that the server manager could implement
 * a executor and thread pool which would handle all client requests
 * without causing a back log.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class ServerManager extends Thread {
	
	private static final String serverDirectory = "src/res/serverAccounts/";
	private Object key;
	private volatile boolean running = true;
	private Queue<ServerThread> clients = new LinkedList<ServerThread>();
	private ArrayList<Account> activeAccounts = new ArrayList<Account>();
	private ThreadInterCom comms;
	//private Account currentAccount = new Account();
	
	public ServerManager() {
		this.setName("ServerManager");
		File serverDir = new File(serverDirectory);
		if (serverDir.isDirectory()) {
			File[] allAccounts = serverDir.listFiles();
			for (File i : allAccounts) {
				String status = isLoggedIn(i);
				if (!status.equals("")) {
					Account temp = new Account();
					String accountNum = i.getName().substring(0, i.getName().indexOf(".txt"));
					if (temp.loadAccount(serverDirectory, accountNum)) {
						temp.setUpdateTime(System.currentTimeMillis());
						activeAccounts.add(temp);
					}
				}
			}
		}
	}
	
	private String isLoggedIn(File account) {
		String accountNum = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(account))) 
		{
			String line = "";
			if ((line = br.readLine()) != null) {
				if (line.equals(LoginStatus.LOGGED_IN)) {
					if ((line = br.readLine()) != null) {
						accountNum = line;
					}
				} 
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return accountNum;
	}
	
	public synchronized void addThread(ServerThread thread) {
		this.clients.add(thread);
	}
	
	private void loadAccount(String accountNum) {
		Account temp = new Account();
		if (temp.loadAccount(serverDirectory, accountNum)) {
			temp.setUpdateTime(System.currentTimeMillis());
			System.out.println("Account added to activeAccounts in SM.");
			activeAccounts.add(temp);
		} 
	}
	
	private void removeAccount(String accountNum) {
		for (int i = 0; i < activeAccounts.size(); i++) {
			if (activeAccounts.get(i).getNumber().equals(accountNum)) {
				activeAccounts.remove(i);
				break;
			}
		}
		reduceListSize();
	}
	
	private void reduceListSize() {
		ArrayList<Account> temp = new ArrayList<Account>();
		for (Account i : activeAccounts) {
			if (!i.getNumber().equals(null)) {
				temp.add(i);
			}
		}
		activeAccounts = temp;
	}
	
	public void checkAccountsActive() {
		for (int i = 0; i < activeAccounts.size(); i++) {
			if (System.currentTimeMillis() - activeAccounts.get(i).getUpdateTime() > 1000 * 60 * 10) { //10 minute timeout
				activeAccounts.remove(i);
				i--;
			}
		}
	}
	
	public void performRequest(String request, ServerThread currentClient) {
		if (request.startsWith(Protocol.LOGIN)) {
			System.out.println("Login attempt in SM: " + request);
			loadAccount(Protocol.getMessage(request));
			//set time in another arraylist when this account logged in using System.currentTimeMillis()
			//if SM doesn't receive a message from this account within the next 10 mins, then force this account to log out.
		} else if (request.startsWith(Protocol.LOGOUT)) {
			System.out.println("Removing user from activeAccounts. " + request);
			removeAccount(Protocol.getMessage(request));
		} else if (request.equals(Protocol.BYE)) {
			try {
				currentClient.setFinished(true);
				System.out.println("Attempting to join sub thread to SM");
				currentClient.join();
				System.out.println("Thread joined successfully.");
				//might have to remove null serverthread from list
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
	
	@Override
	public void run() {
		ArrayList<Account> previouslyActiveAccounts = new ArrayList<Account>();
		while (running) {
			//checkThread((Iterator<ServerThread>) clients.listIterator());
			comms = new ThreadInterCom();
			String clientRequest = "";
			ServerThread currentClient = clients.poll();
			//System.out.println("Server name is: " + currentClient.getName());
			if (currentClient != null) {
				currentClient.setInterCom(comms);
				currentClient.start();
				while (!clientRequest.equals(Protocol.BYE)) {
					clientRequest = comms.receive();
					System.out.println("The SM received this request: " + clientRequest);
					performRequest(clientRequest, currentClient);
					//currentClient.resetSMRequest();
				}
			}
			//System.out.println("Server Manager waiting to receive new client.");
			
			if (activeAccounts.size() != previouslyActiveAccounts.size()) {
				for (Account i : activeAccounts) {
					System.out.println(i.getNumber() + " is online");
				}
				previouslyActiveAccounts = activeAccounts;
			}
		
		}
	}
	
}
