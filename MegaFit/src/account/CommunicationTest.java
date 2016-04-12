package account;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommunicationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientThread client = null;
		try {
			/*
			 * ----- Protocols to choose from -----
			 * 
			 * Protocol.CREATE_ACCOUNT.concat(" : WubbaLubba10,donkeyKong")
			 * Protocol.LOGIN.concat(" : WubbaLubba10,donkeyKong")
			 * newAccount, Protocol.SAVE
			 * Protocol.LOGIN.concat(" : GainzKing22,pewpewtoo2")
			 * newAccount, Protocol.SAVE.concat(" : 5,4")
			 * Protocol.LOGOUT.concat(" : " + newAccount.getNumber())
			 * 
			 */
			
			Account newAccount = new Account();
			newAccount.setLoginStatus(LoginStatus.LOGGED_IN);
			newAccount.setNumber("1334239452");
			newAccount.setName("WubbaLubba10");
			newAccount.setPassword("donkeyKong");
			newAccount.setSaveDate(Long.toString(System.currentTimeMillis()));
			newAccount.setLevel("200");
			newAccount.setXP("555");
			newAccount.setGainz("20");
			client = new ClientThread(InetAddress.getLocalHost().getHostName(),4444, Protocol.CREATE_ACCOUNT.concat(" : WubbaLubba10,donkeyKong"));
			client.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while (client.isFinished() == false) {
			
		}
		
		Account account = client.getAccount();
		try {
			client.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String i : account.saveSequence()) {
			System.out.println(i);
		}
		System.out.println("finished");
		
	}

}
