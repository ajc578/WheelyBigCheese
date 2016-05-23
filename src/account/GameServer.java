package account;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameServer implements Runnable {

	private static final String DIRECTORY = "src/res/serverAccounts/";
	private static final int portNumber = 4446;
	private static final String hostName = "Ollie-PC";

	private Account account1;
	private Account account2;

	public GameServer(String client1, String client2) {
		account1 = new Account();
		try {
			account1 = AccountHandler.accountLoad(DIRECTORY, AccountHandler.generateAccountNum(client1));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		account2 = new Account();
		try {
			account2= AccountHandler.accountLoad(DIRECTORY, AccountHandler.generateAccountNum(client2));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {



	}

	public void requestGame() {
		try (
				Socket mySocket= new Socket(hostName, portNumber);
				PrintWriter send = new PrintWriter(mySocket.getOutputStream(), true);
				BufferedReader receive = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
		) {

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
