package account;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Testing {
	
	private static Lock mainLock = new ReentrantLock();
	private static Lock threadLock = new ReentrantLock();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Anus anus = new Anus();
		anus.start();
		while (true) {
			if (anus.getGameStatus() == GameRequest.ACCEPTED) {
				System.out.println("Game request accepted!");
				break;
			}
		}
		String protocol1 = null;
		String protocol2 = "null";
		
		if (!protocol1.equals("null")) {
			System.out.println("no crashes so test success");
		} 
		if (protocol2.equals("null")) {
			System.out.println("protocol2");
		}*/
		
		/*Anus anus = new Anus(mainLock, threadLock);
		anus.start();
		System.out.println("Starting gameStatus: " + anus.getGameStatus());
		while (true) {
			int temp = testThreadOutput(anus);
			if (temp != -1) {
				System.out.println("Ending gameStatus: " + temp);
				break;
			}
		}
		Anus anus1 = new Anus(mainLock, threadLock);
		anus1.start();
		while (true) {
			int temp = testThreadOutput(anus1);
			if (temp != -1) {
				System.out.println("Ending gameStatus: " + temp);
				break;
			}
		}
		
		System.out.println("Done :D");*/
		AccountHandler myAccount = new AccountHandler();
		/*String protocol = Protocol.CREATE_ACCOUNT + " : GainTrain,amazeBallz,Ollie,Rushton,oliver.rushton@hotmail.co.uk,22/01/1995,1.82,75";
		if (myAccount.createNewAccount("src/res/clientAccounts/", protocol)) {
			System.out.println("createAccountSuccess");
		} else {
			System.out.println("createAccountFailure");
		}*/
		
		//myAccount.setAccount(AccountHandler.accountLoad("src/res/clientAccounts/","1839696646"));
		String loginTest = Protocol.LOGIN + " : GainTrain,amazeBallz";
		if (myAccount.login("src/res/clientAccounts/",loginTest).equals(LoginStatus.LOGGED_IN)) {
			System.out.println(myAccount.getAccount().getHeight());
			System.out.println(myAccount.getAccount().getAchievements().get(0).getContent());
			System.out.println(myAccount.getAccount().getItems());
		}
		
		if (myAccount.logout("src/res/clientAccounts/")) {
			System.out.println("Logout success.");
		}
		
		
	}
	
	private static void changeMessage(String input) {
		input = "changed";
	}
	
	private static int testThreadOutput(Anus anus) {
		int output = -1;
		if (!threadLock.tryLock()) {
			output = anus.getGameStatus();
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
		return output;
	}

}

class Anus extends Thread {
	
	private volatile int gameStatus = GameRequest.EXTERNAL;
	
	private Lock mainLock = new ReentrantLock();
	private Lock threadLock = new ReentrantLock();
	
	public Anus(Lock mainLock, Lock threadLock) {
		this.mainLock = mainLock;
		this.threadLock = threadLock;
	}
	
	@Override
	public void run() {
		
		//gameStatus = GameRequest.ACCEPTED;
		for (int i = 0; i < 100000; i++) {
			
		}
		gameStatus = GameRequest.LOCAL;
		System.out.println("Thread is notifying main that gameStatus is ready for retrieval.");
		threadLock.lock();
		while (true) {
			if (!mainLock.tryLock()) {
				System.out.println("Main has read the gameStatus");
				threadLock.unlock();
				break;
			} else {
				mainLock.unlock();
				try {
					System.out.println("Sleeping...");
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}
}
