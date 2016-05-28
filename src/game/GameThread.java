package game;

import account.ThreadInterCom;

public class GameThread  {
	
	private UserInputUI userIn;
	private ThreadInterCom comms;
	
	public GameThread(UserInputUI userIn, ThreadInterCom comms) {
		this.userIn = userIn;
		this.comms = comms;
	}
	
//	public Move waitForUserIn() {
//		comms.receive();
//	}
	
}
