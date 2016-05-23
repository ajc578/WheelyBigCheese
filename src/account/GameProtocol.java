package account;

public class GameProtocol {
	
	public static final String LOCAL_GAME_REQ = "locGReq", EXT_GAME_REQ = "extGReq", GAME_ACCEPTED = "gameAcc", GAME_DECLINED = "gameDec";
	//private static final int gamestates;
	
	private int state;
	
	public String processInput(String input) {
		String output = null;
		
		if (input.startsWith(LOCAL_GAME_REQ)) {
			
		} else if (input.startsWith(EXT_GAME_REQ)) {
			
		}
			
			
		
		return output;
	}
	
}
