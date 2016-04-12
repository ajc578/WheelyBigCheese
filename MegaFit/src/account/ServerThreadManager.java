package account;

import java.util.ArrayList;

public class ServerThreadManager extends Thread {
	
	private ArrayList<String> loggedIn = new ArrayList<String>();
	private int users = 0;
	private ArrayList<ServerThread> manager;
	private boolean running = true;
	
	public ServerThreadManager() {
		manager = new ArrayList<ServerThread>();
	}
	
	public void addServerThread(ServerThread thread) {
		manager.add(thread);
	}
	
	@Override
	public void run() {
		while (running) {
			
		}
	}
	
}
