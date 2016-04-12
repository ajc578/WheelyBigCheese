package account;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ServerManager extends Thread {
	
	public ServerManager() {}
	
	private volatile boolean running = true;
	private Queue<ServerThread> clients = new LinkedList<ServerThread>();
	private ArrayList<String> activeAccounts = new ArrayList<String>();
	//private Account currentAccount = new Account();
	
	public void addThread(ServerThread thread) {
		this.clients.add(thread);
	}
	
	public void performRequest(String request) {
		if (request.startsWith(Protocol.LOGIN)) {
			activeAccounts.add(Protocol.getMessage(request));
		} else if (request.startsWith(Protocol.LOGOUT)) {
			System.out.println("Removing user from activeAccounts. " + request);
			activeAccounts.remove(Protocol.getMessage(request));
		}
	}
	
	/*public void checkThread(Iterator<ServerThread> iterator) {
		while (iterator.hasNext()) {
			ServerThread i = iterator.next();
			if (i.isLoginRequested()) {
				if (!i.getAccountNum().equals(null)) {
					if (activeAccounts.offer(i.getAccountNum())) {
						System.out.println("account number successfully added to queue.");
					}
					i.notifyMe();
				}
			} else if (i.isLogoutRequested()) {
				if (!i.getAccountNum().equals(null)) {
					if (activeAccounts.remove(i.getAccountNum())) {
						System.out.println("account : " + i.getAccountNum() + " has been removed from active accounts.");
					}
				}
			} else if (i.isFinished()) {
				try {
					i.join();
					System.out.println("Thread joined successfully.");
					iterator.remove();
					//might have to remove null serverthread from list
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}*/
	
	@Override
	public void run() {
		ArrayList<String> previouslyActiveAccounts = new ArrayList<String>();
		while (running) {
			//checkThread((Iterator<ServerThread>) clients.listIterator());
			String clientRequest = null;
			ServerThread currentClient = clients.poll();
			if (currentClient != null) {
				try {
					clientRequest = currentClient.alertST();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				performRequest(clientRequest);
				
				boolean clientFinished = false;
				while (!clientFinished) {
					clientFinished = currentClient.isFinished();
				}
				
				try {
					currentClient.join();
					System.out.println("Thread joined successfully.");
					//might have to remove null serverthread from list
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (!activeAccounts.equals(previouslyActiveAccounts)) {
				for (String i : activeAccounts) {
					System.out.println(i + " is online");
				}
				previouslyActiveAccounts = activeAccounts;
			}
		
		}
	}
	
}
