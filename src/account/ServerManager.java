package account;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/**
 * This Class is used to store the <tt>ServerThreads</tt> of all the connections
 * to the server. This Class was intended to be used to pass game requests between
 * two clients, but unfortunately there was not enough time to complete the MegaFit Game.
 * <p>
 * The manager iterates through all the <tt>ServerThreads</tt> and checks whether a client 
 * has made a game request. If one is found, then the manager will notify the target client
 * that someone would like to start a game session with them. The manager then waits for the 
 * response. If the target client accepts the game request, then the request client is notified and
 * a synchronous communications object ({@link InterThreadComms}) is passed to both in oder to 
 * communicate synchronously.
 * 
 *
 * <p> <STRONG> Developed by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Tested by </STRONG> <p>
 * Oliver Rushton
 * <p> <STRONG> Developed for </STRONG> <p>
 * BOSS
 * @author Oliver Rushton
 * 
 * @see ServerThread
 * @see ServerSide
 */
public class ServerManager extends Thread {

	private volatile boolean running = true;
	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();
	private boolean timeout = false;
	/**
	 * Constructor just sets the name of the thread for thread management.
	 */
	public ServerManager() {
		this.setName("ServerManager");
	}
	/**
	 * Adds a newly connected client <tt>ServerThread</tt> to the list.
	 * 
	 * @param thread the <tt>ServerThread</tt> of the new connection
	 */
	public synchronized void addThread(ServerThread thread) {
		this.clients.add(thread);
	}
	
	/**
	 * Continuously iterates through each ServerThread in the <tt>clients</tt> list,
	 * handles game requests and initialises synchronous communications between two
	 * ServerThreads if the game is accepted by the target party.
	 * <p>
	 * See this class's documentation for more detail.
	 */
	@Override
	public void run() {
		while (running) {
			boolean reqInCycle = false;
			for (ServerThread i : clients) {
				//checks if a game request has been made
				if (i.getGameStatus() == GameRequest.LOCAL) {
					reqInCycle = true;
					//retrieves the usernames of the two players
					String[] opponents = i.returnOpponents();
					String player1 = opponents[0];
					String player2 = opponents[1];
					//finds the username of the server thread if both players are online
					for (ServerThread j : clients) {
						if (j.getAccountNumber().equals(player2)) {
							// if target user is online, check that they aren't busy or already playing a game
							if (!j.getThreadStatus() && j.getGameStatus() == GameRequest.WAITING) {
								timeout = false;
								j.setOpponent(player1);
								//notify the target client that someone wants to start a game with them.
								j.setGameStatus(GameRequest.EXTERNAL);
								//Initialises timeout timer to wait for their response
								Timer timer = new Timer();
								timer.schedule(new TimerTask() {

									@Override
									public void run() {
										//if the target user doesn't reply in time, the request is cancelled
										timeout = true;
									}

								}, 20000); // waits 20 seconds for response before breaking while loop

								boolean gameAccepted = false;
								while (true) {
									//waits for the target user's game status to return whether they accept or not.
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
								//if the target user accepts
								if (gameAccepted) {
									//initialise the synchronous communications object and pass to both players.
									ThreadInterCom gameComms = new ThreadInterCom();
									i.setGameLock(gameComms);
									j.setGameLock(gameComms);
									//inform the request user that their opponent has accepted the game.
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
