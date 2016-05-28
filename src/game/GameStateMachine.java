package game;

public class GameStateMachine {
	//game states
	private static final int START = 0, SELECT = 1, PERFORM_MOVE = 2, RECEIVE_MOVE = 3, END = 4;
	//message tags
	private static final String START_LOC = "strtLoc", START_OPP = "strtOpp", GAME_STARTED = "started";
	
	private int gameState = 0;
	
	public String processInput(String input) {
		String output = null;
		
		if (gameState == START) {
			if (input.equals(START_LOC)) {
				gameState = SELECT;
			} else if (input.equals(START_OPP)) {
				gameState = RECEIVE_MOVE;
			}
			output = GAME_STARTED;
		} else if (gameState == SELECT) {
			
		}
		
		return output;
	}
	
}
