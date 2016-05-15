package account;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ServerManager extends Thread {
	
	private static final String serverDirectory = "src/res/serverAccounts/";
	private Object key;
	private volatile boolean running = true;
	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
	private ThreadInterCom comms;
	//private Account currentAccount = new Account();
	private boolean timeout = false;
	
	public ServerManager() {
		this.setName("ServerManager");
		
	}
	
	public synchronized void addThread(ServerThread thread) {
		this.clients.add(thread);
	}
	
	public void performRequest(String request, ServerThread currentClient) {
		
		
		
	}
	
	@Override
	public void run() {
		while (running) {
			boolean reqInCycle = false;
			for (ServerThread i : clients) {
				if (i.getGameStatus() == GameRequest.LOCAL) {
					reqInCycle = true;
					String[] opponents = i.returnOpponents();
					String player1 = opponents[0];
					String player2 = opponents[1];
					for (ServerThread j : clients) {
						if (j.getAccountNumber().equals(player2)) {
							if (!j.getThreadStatus() && j.getGameStatus() == GameRequest.WAITING) {
								timeout = false;
								j.setOpponent(player1);
								j.setGameStatus(GameRequest.EXTERNAL);
								Timer timer = new Timer();
								timer.schedule(new TimerTask() {

									@Override
									public void run() {
										timeout = true;
									}
									
								}, 20000);
								
								boolean gameAccepted = false;
								while (true) {
									if (j.getGameStatus() == GameRequest.ACCEPTED) {
										gameAccepted = true;
										timer.cancel();
										break;
									} else if (j.getGameStatus() == GameRequest.DECLINED) {
										timer.cancel();
										break;
									}
									if (timeout == true) {
										timer.cancel();
										break;
									}
								}
								
								if (gameAccepted) {
									ThreadInterCom gameComms = new ThreadInterCom();
									i.setGameLock(gameComms);
									j.setGameLock(gameComms);
									i.setGameStatus(GameRequest.ACCEPTED);
								} else {
									i.setGameStatus(GameRequest.DECLINED);
								}
							}
						} 
					}
				}
			}
			
			if (!reqInCycle) {
				try {
					//If no game requests were found in the cycle, reduce stress on processor by sleeping
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
