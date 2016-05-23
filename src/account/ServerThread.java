package account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

	private Socket socket = null;
	private ServerProtocol sProtocol;
	private volatile boolean finished = false;
	//new variables
	private boolean gamePlaying = false;
	private volatile boolean busy = false;
	private GameProtocol gProtocol;
	private volatile ThreadInterCom gameComms;
	private volatile String localAccount;
	private volatile String opponentAccount;
	private volatile int gameStatus = GameRequest.WAITING;


	public ServerThread(Socket socket, int i) {
		//set name of thread to the account name + connection
		super("Connection: " + i);
		this.socket = socket;
		System.out.println(socket.getLocalPort());
		System.out.println(socket.getPort());
	}

	@Override
	public void run() {
		sProtocol = new ServerProtocol();
		try (
				ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream receive = new ObjectInputStream(socket.getInputStream());
		) {

			Object inputObject = null, outputObject = null;
			boolean loginAttempted = false;
			System.out.println("Server read/write set up");
			while ((inputObject = receive.readObject()) != null) {

				if (!gamePlaying) {
					if (busy == false && !inputObject.equals(Protocol.STANDBYE)) {
						busy = true;
					}
					String output = searchForGameReq(inputObject, send);
					if (output != null) {
						if (!output.equals("")) {
							outputObject = output;
						}
					} else {
						outputObject = sProtocol.processInput(inputObject);
					}
					send.writeObject(outputObject);

					if (!outputObject.equals("null")) {
						if (inputObject != null && (inputObject instanceof String) ?  ((String) inputObject).startsWith(Protocol.LOGIN): false) {
							loginAttempted = true;
						}
						if (loginAttempted && outputObject.equals(Protocol.COMPLETED)) {
							loginAttempted = false;
							localAccount = sProtocol.getAccountNumber();
						}

						if (outputObject.equals(Protocol.STANDBYE)) {
							gameStatus = GameRequest.WAITING;
							busy = false;
						}

						if (outputObject.equals(Protocol.LOGOUT_SUCCESS)) {
							break;
						}
					}
					if (!busy) {
						//System.out.println("server thread is sleeping...");
						Thread.sleep(333); // this number is used to fit with timeout timer for game request in manager.
					} else {
						System.out.println("InputObject is : " + inputObject);
						System.out.println("OutputObject is : " + outputObject);
					}
				} else {
					//This is where the game protocol comms will take place

					//at end of game, need to set gamePlaying to false

				}


			}
			System.out.println("Server Socket Closed");
			socket.close();
		} catch (IOException e) {
			// Alert here
			e.printStackTrace();
		} catch (InterruptedException e) {
			// For the sleep calls
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playGame(int state) {

	}

	public String searchForGameReq(Object inputObject, ObjectOutputStream send) throws InterruptedException, IOException {
		String output = null;
		if (inputObject instanceof String) {
			String inputLine = (String) inputObject;
			if (inputLine.equals(Protocol.GAME_ACCEPTED)) {
				//for external request
				gameStatus = GameRequest.ACCEPTED;
				gProtocol = new GameProtocol();
				Thread.sleep(1000); // waits for manager.
				gamePlaying = true;
				output = "";
			} else if (inputLine.equals(Protocol.GAME_DECLINED)) {
				//for external request
				gameStatus = GameRequest.DECLINED;
				Thread.sleep(1000);
				gameStatus = GameRequest.WAITING;
				output = "";
			} else if (gameStatus == GameRequest.EXTERNAL) {
				//inform client of external game request
				busy = true;
				send.writeObject(Protocol.EXT_GAME_REQ + " : " + opponentAccount); // may send twice depending on how long it takes for opponent to accept game
				output = Protocol.STANDBYE;
			} else if (inputLine.startsWith(Protocol.LOCAL_GAME_REQ)) {
				opponentAccount = Protocol.getMessage(inputLine);
				gameStatus = GameRequest.LOCAL;
				output = Protocol.STANDBYE;
				send.writeObject(output);
			} else if (gameStatus == GameRequest.ACCEPTED) {
				// local game has been accepted by opponent
				gameStatus = GameRequest.THREAD_BUSY;
				gProtocol = new GameProtocol();
				busy = true;
				gamePlaying = true;
			} else if (gameStatus == GameRequest.DECLINED) {
				// local game has been declined by opponent
				gameStatus = GameRequest.WAITING;
				output = Protocol.GAME_DECLINED;
				send.writeObject(output);
			}
		}

		return output;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean getThreadStatus() {
		return busy;
	}

	public int getGameStatus() {
		if (gameStatus == GameRequest.LOCAL) {
			gameStatus = GameRequest.RECOGNISED;
		}

		return gameStatus;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	public void setGameLock(ThreadInterCom gameComms) {
		busy = true;
		gameComms = new ThreadInterCom();
		this.gameComms = gameComms;
	}

	public String[] returnOpponents() {
		String[] opponents = {localAccount, opponentAccount};
		return opponents;
	}

	public void setOpponent(String opponentAccount) {
		this.opponentAccount = opponentAccount;
	}

	public String getAccountNumber() {
		return localAccount;
	}

}
