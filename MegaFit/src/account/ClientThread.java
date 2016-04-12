package account;

/*
 * !!!VERY IMPORTANT!!!
 * When user attempts to login, first the clientAccount package should be checked to see if the file exists.
 * If it does, then the String args in this constructor should be set to Protocol.LOGIN with the extra details.
 * If it doesn't exist, then the String args need to be set to Protocol.LOGIN_NEW with the extra details.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientThread extends Thread {
	
	private String args;
	private String hostName;
	private Account account;
	private int portNumber;
	private boolean forcedClose = false;
	private volatile boolean finished = false;
	private ClientProtocol cProtocol;
	
	public ClientThread(String hostName, int portNumber, Account account, String args) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.account = account;
		this.cProtocol = new ClientProtocol();
		this.cProtocol.setAccount(account);
		this.args = args;
	}
	
	public ClientThread(String hostName, int portNumber, String args) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.cProtocol = new ClientProtocol();
		this.cProtocol.setAccount(account);
		this.args = args;
	}
	
	//used by parent class to return account once logged in
	public Account getAccount() {
		return cProtocol.getAccount();
	}
	
	@Override
	public void run() {
		try (
			Socket mySocket= new Socket(hostName, portNumber);
			PrintWriter send = new PrintWriter(mySocket.getOutputStream(), true);
			BufferedReader receive = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
		) {
			
			System.out.println(mySocket.getLocalPort());
			System.out.println(mySocket.getPort());
			String serverOutput, clientOutput;
			send.println(Protocol.HANDSHAKE);
			
			cProtocol.setSaveAttributes(detectSaveArgs());
			cProtocol.setProtocol(args);
			
			System.out.println("Client read/write set up");
			while ((serverOutput = receive.readLine()) != null && !forcedClose) {
				//read and interpret input from server
				// if client output is equal to an error, then return the error from the thread!!! therefore need to add more complexity to error messages in client protocol
				clientOutput = cProtocol.processInput(serverOutput);
				if (serverOutput != null)
					System.out.println("Output From Server: " + serverOutput);
				if (clientOutput == Protocol.END)
					break;
				send.println(clientOutput);
				if (clientOutput != null)
					System.out.println("OLutput from Client: " + clientOutput);
			}
			System.out.println("Client Socket Closed");
			finished = true;
			forcedClose = false;
			mySocket.close();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unknown Host Exception");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO Exception");
			e.printStackTrace();
		} 
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void closeConnection() {
		this.forcedClose = true;
	}
	
	private ArrayList<Integer> detectSaveArgs() {
		
		//create account - Protocol.CREATE : name,password
		//login - Protocol.LOGIN : name,password
		//save account - Protocol.SAVE
		//save lines - Protocol.SAVE : lineNum,lineNum,etc...
		
		ArrayList<Integer> saveAttributes = new ArrayList<Integer>();
		int[] transfer = {Account.LOGIN_INDEX, Account.NUM_INDEX, Account.NAME_INDEX, Account.PASSWORD_INDEX, 
				Account.DATE_INDEX, Account.LEVEL_INDEX, Account.XP_INDEX, Account.GAINZ_INDEX};
		for (int i : transfer) {
			saveAttributes.add(i);
		}
		
		if (args.startsWith(Protocol.SAVE + " : ")) {
			saveAttributes = new ArrayList<Integer>();
			String list = null;
			list = args.substring(args.lastIndexOf(" : ") + 3);
			args = Protocol.SAVE;
			List<String> attributeList = Arrays.asList(list.split("\\s*,\\s*"));
			for (int i = 0; i < attributeList.size(); i++) {
				saveAttributes.add(Integer.parseInt(attributeList.get(i)));
				System.out.println("Save line : " + saveAttributes.get(i));
			}
			
		} 
		return saveAttributes;
	}
	
}
